from flask import Flask, request
import hashlib

app = Flask(__name__)

@app.route('/tokenGenerator/getToken', methods=['POST'])
def generate_token():
    employee = request.get_json()
    token = token_hash(employee['name'] + str(employee['id']))
    return token

def token_hash(input):
    try:
        # Create an instance of the SHA-256 algorithm
        digest = hashlib.sha256()

        # Apply the salt to the input string
        salted_input = input + "EIST2023"

        # Apply the hash function to the salted input
        hashed_bytes = digest.update(salted_input.encode())

        # Convert the hashed bytes to a hexadecimal representation
        hashed_hex = digest.hexdigest()

        return hashed_hex
    except Exception as e:
        print(e)

if __name__ == '__main__':
    app.run(host="localhost", port=8080, debug=True)