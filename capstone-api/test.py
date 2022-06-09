from flask import Flask, request, jsonify

# Initialize Flask
app = Flask(__name__)

# Initialize Flask server with error handling
@app.route("/", methods=["POST"])
def hello():
    message = request.json['message']

    predicted_message = predict(message)
    return message ## predicted_message

if __name__ == '__main__':
    # app.run(host='0.0.0.0', port=8080, debug=True)
    app.run()