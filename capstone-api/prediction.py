import pandas as pd

import tensorflow as tf
import tensorflow_hub as hub
import tensorflow_text as text
from sklearn.preprocessing import LabelBinarizer


model = tf.keras.models.load_model(('bert-model.h5'), custom_objects={'KerasLayer':hub.KerasLayer})

def predicts(input):
  trainfile="atis_intents_train.csv"
  traindf = pd.read_csv(trainfile)
  traindf.columns = ['intent', 'text']
  traindf = traindf.reindex(columns=['text', 'intent'])

  trainfeatures=traindf.copy()
  trainlabels=trainfeatures.pop("intent")

  binarizer=LabelBinarizer()
  trainlabels=binarizer.fit_transform(trainlabels.values)
  
  inputs = [input]

  results = tf.nn.softmax(model(tf.constant(inputs)))
  intents = binarizer.inverse_transform(results.numpy())
  # print(intents)

  # result_for_printing = \
  #   [f'{results[0]}']
  return intents[0]
  # print()

  # print(results[0])
  # return results[0]
  
def predict(inputs, results):
  result_for_printing = \
    [f'input: {inputs[i]:<30} : estimated intent: {results[i]}'
                         for i in range(len(inputs))]
  print(*result_for_printing, sep='\n')
  print()


examples = [
    ' i want to fly from boston at 838 am and arrive in denver at 1110 in the morning',  # this is the same sentence tried earlier
    ' what airlines have flights from baltimore to seattle',
    ' show me ground transportation in denver',
    ' what does fare code qo mean'
]

trainfile="atis_intents_train.csv"
traindf = pd.read_csv(trainfile)
traindf.columns = ['intent', 'text']
traindf = traindf.reindex(columns=['text', 'intent'])

trainfeatures=traindf.copy()
trainlabels=trainfeatures.pop("intent")

binarizer=LabelBinarizer()
trainlabels=binarizer.fit_transform(trainlabels.values)

results = tf.nn.softmax(model(tf.constant(examples)))
intents = binarizer.inverse_transform(results.numpy())

print(predicts(examples[0]))