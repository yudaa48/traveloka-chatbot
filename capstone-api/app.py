from flask import Flask, request, jsonify
from prediction import get_answer

# Initialize Flask
app = Flask(__name__)

# Initialize Flask server (file prediction.py)
@app.route("/", methods=["POST"])
def new_world():
    return "Welcome to our API"

@app.route("/predict", methods=["POST"])
def hello():
    input = request.json['input']
   
    return jsonify(output = get_answer(input))

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=8080, debug=True) #uncomment if you want to run on GCP
    #app.run(port=8080, debug=True) #uncomment if you want to run on local