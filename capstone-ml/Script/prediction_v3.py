import pandas as pd
import tensorflow as tf
import tensorflow_hub as hub
import random
import tensorflow_text as text
from sklearn.preprocessing import LabelBinarizer

model = tf.keras.models.load_model(('final-model.h5'), custom_objects={'KerasLayer':hub.KerasLayer})

chat_user = input('Input Chat : ')


def get_answer(model, input):
    answer_data = pd.read_csv('answer_data.csv')

    # Fit data class intent dari dataset ke binarizernya
    intent_label = ['book_flight', 'change_date', 'change_name', 'checkin_online',
                    'child_onboard', 'covid_dose', 'extra_baggage', 'flight_document',
                    'flight_ehac', 'flight_idcard', 'flight_international',
                    'flight_recquirement', 'flight_refund', 'payment_status',
                    'payment_verification', 'policy_corona', 'positivie_covid',
                    'refund_status', 'refund_test', 'reschedule_flight',
                    'reschedule_infant', 'reschedule_insurance', 'reschedule_method',
                    'reschedule_multiconnecting', 'reschedule_partial',
                    'reschedule_payment', 'reschedule_refund', 'reschedule_seat',
                    'reschedule_specific', 'reschedule_voucher', 'resend_eticket',
                    'test_covid', 'travel_aboard', 'travel_voucher', 'greeting',
                    'greeting_response', 'courtesy_greeting', 'thanks', 'goodbye',
                    'attractions_Surabaya', 'attractions_Bali',
                    'attractions_Yogyakarta', 'attractions_Palembang',
                    'attractions_Medan']

    sentence = [input]

    binarizer = LabelBinarizer()
    binarizer.fit_transform(intent_label)

    # Prediksi intent chat user
    results = tf.nn.softmax(model(tf.constant(sentence)))
    intents = binarizer.inverse_transform(results.numpy())

    #Loop from dataframe to search the answer
    ans = []
    for index, answer in answer_data.iterrows():
        if intents[0] in answer['intent']:
            ans.append(answer['answer'])
        else:
            continue

    choice = (random.choice(ans))
    return choice

print(get_answer(model, chat_user))