from flask import Flask, request, jsonify
from predictionv3 import get_answer

# Initialize Flask
app = Flask(__name__)

# Initialize Flask server (file predictionv3.py)
@app.route("/", methods=["POST"])
def hello():
    input = request.json['message']

    predicted_message = get_answer(input)
    return jsonify(predicted_message)

# Initialize Flask server (file predictionv3.py)
@app.route("/chat", methods=["GET", "POST"])
def chat():
    input = request.form['input']

    chatbotReply = get_answer(input)
    return jsonify(chatbotReply)

if __name__ == '__main__':
    # app.run(host='0.0.0.0', port=8080, debug=True)
    app.run(port=8080, debug=True)