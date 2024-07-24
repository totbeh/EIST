from flask import Flask, jsonify, request
import hashlib

app = Flask(__name__)

registeredList = []
preferencesList = []

def tokenHash(input: str):
    saltedInput = input + "EIST2023"
    hashedBytes = hashlib.sha256(saltedInput.encode('utf-8')).digest()
    hashedString = ''.join(format(x, '02x') for x in hashedBytes)
    return hashedString

@app.route('/event/registerEvent', methods=['POST'])
def register_event():
    data = request.get_json()
    token = tokenHash(data['name'] + str(data['id']))
    
    if token == data['token']:
        registeredList.append(data)
        return "Registration is successful", 201
    else:
        return "Registration failed", 400

@app.route('/event/recordPreferences', methods=['POST'])
def record_preferences():
    data = request.get_json()
    
    if 'eventPreferences' in data:
        preferencesList.append(data['eventPreferences'])
        return "Event preferences recorded successfully", 201
    else:
        return "Event preferences aren't set", 400

@app.route('/event/registeredList', methods=['GET'])
def get_registered_list():
    return jsonify(registeredList)

@app.route('/event/preferencesList', methods=['GET'])
def get_preferences_list():
    return jsonify(preferencesList)

if __name__ == '__main__':
    app.run(host="localhost", port=8081, debug=True)
