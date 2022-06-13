from flask import Flask, request, jsonify
from prediction import predicts

# Initialize Flask
app = Flask(__name__)

# Initialize Flask server 
@app.route("/", methods=["POST"])
def hello():
    input = request.json['message']

    predicted_message = predicts(input)
    return predicted_message

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=8080, debug=True)
    # app.run()