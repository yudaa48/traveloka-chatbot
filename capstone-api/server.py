# Import Module
from flask import Flask, request, jsonify
from tensorflow.keras.models import load_model
import tensorflow as tf
import pandas as pd
import io
import os
import re
from google.cloud import storage


# Initialize Flask
app = Flask(__name__)

## Global model variable
model = None

# Download model file from cloud storage bucket
def download_pkl_model_file():
    # Model Bucket details
    BUCKET_NAME     = "ml-model-chabot"
    PROJECT_ID      = "capstone-chatbot-c22-cb04"
    GCS_PKL_FILE    = "tokenizerkebijakan.pickle" ## butuh tokenizer
    GCS_MODEL_FILE  = "model_kebijakan.h5"  ## export model

    # Initialise a client
    client   = storage.Client(PROJECT_ID)
    
    # Create a bucket object for our bucket
    bucket   = client.get_bucket(BUCKET_NAME)
    
    # Create a blob object from the filepath
    pkl   = bucket.blob(GCS_PKL_FILE)
    mdl   = bucket.blob(GCS_MODEL_FILE)

    folder = '/tmp/'
    if not os.path.exists(folder):
        os.makedirs(folder)
    # Download the file to a destination
    pkl.download_to_filename(folder + "policy_tokenizer.pickle") ## butuh tokenizer
    mdl.download_to_filename(folder + "policy_model.h5") ## export model

# Initialize Flask server with error handling
@app.route("/")
def hello():
    return "Welcome to machine learning model APIs!"

@app.route("/predict", methods=['POST'])
def preprocess_sentence(sentence):
  sentence = sentence.lower().strip()
  # creating a space between a word and the punctuation following it
  # eg: "he is a boy." => "he is a boy ."
  sentence = re.sub(r"([?.!,])", r" \1 ", sentence)
  sentence = re.sub(r'[" "]+', " ", sentence)
  # removing contractions
  sentence = re.sub(r"i'm", "i am", sentence)
  sentence = re.sub(r"he's", "he is", sentence)
  sentence = re.sub(r"she's", "she is", sentence)
  sentence = re.sub(r"it's", "it is", sentence)
  sentence = re.sub(r"that's", "that is", sentence)
  sentence = re.sub(r"what's", "that is", sentence)
  sentence = re.sub(r"where's", "where is", sentence)
  sentence = re.sub(r"how's", "how is", sentence)
  sentence = re.sub(r"\'ll", " will", sentence)
  sentence = re.sub(r"\'ve", " have", sentence)
  sentence = re.sub(r"\'re", " are", sentence)
  sentence = re.sub(r"\'d", " would", sentence)
  sentence = re.sub(r"\'re", " are", sentence)
  sentence = re.sub(r"won't", "will not", sentence)
  sentence = re.sub(r"can't", "cannot", sentence)
  sentence = re.sub(r"n't", " not", sentence)
  sentence = re.sub(r"n'", "ng", sentence)
  sentence = re.sub(r"'bout", "about", sentence)
  # replacing everything with space except (a-z, A-Z, ".", "?", "!", ",")
  sentence = re.sub(r"[^a-zA-Z?.!,]+", " ", sentence)
  sentence = sentence.strip()
  return sentence

def evaluate(sentence):
  sentence = preprocess_sentence(sentence)

  sentence = tf.expand_dims(
      START_TOKEN + tokenizer.encode(sentence) + END_TOKEN, axis=0)

  output = tf.expand_dims(START_TOKEN, 0)

  for i in range(MAX_LENGTH):
    predictions = model(inputs=[sentence, output], training=False)

    # select the last word from the seq_len dimension
    predictions = predictions[:, -1:, :]
    predicted_id = tf.cast(tf.argmax(predictions, axis=-1), tf.int32)

    # return the result if the predicted_id is equal to the end token
    if tf.equal(predicted_id, END_TOKEN[0]):
      break

    # concatenated the predicted_id to the output which is given to the decoder
    # as its input.
    output = tf.concat([output, predicted_id], axis=-1)

  return tf.squeeze(output, axis=0)

def predict(sentence):
  prediction = evaluate(sentence)

  predicted_sentence = tokenizer.decode(
      [i for i in prediction if i < tokenizer.vocab_size])

  print('Input: {}'.format(sentence))
  print('Output: {}'.format(predicted_sentence))

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000, debug=True)