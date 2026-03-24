# app.py
from flask import Flask, request, jsonify, render_template
import jwt
import datetime
from functools import wraps

app = Flask(__name__)

# IMPORTANT: In a production environment, store this in an environment variable!
app.config['SECRET_KEY'] = 'your_super_secret_key_here'

# Dummy database for demonstration
USERS = {
    "admin": "password123",
    "user": "mypassword"
}

# Decorator to enforce token authentication
def token_required(f):
    @wraps(f)
    def decorated(*args, **kwargs):
        token = None
        
        # Check if the token is passed in the headers
        if 'Authorization' in request.headers:
            # The header usually looks like: "Bearer <token>"
            parts = request.headers['Authorization'].split()
            if len(parts) == 2 and parts[0] == 'Bearer':
                token = parts[1]

        if not token:
            return jsonify({'message': 'Token is missing!'}), 401

        try:
            # Decode the token
            data = jwt.decode(token, app.config['SECRET_KEY'], algorithms=["HS256"])
            current_user = data['user']
        except jwt.ExpiredSignatureError:
            return jsonify({'message': 'Token has expired!'}), 401
        except jwt.InvalidTokenError:
            return jsonify({'message': 'Token is invalid!'}), 401

        return f(current_user, *args, **kwargs)
    
    return decorated

# Serve the HTML frontend
@app.route('/')
def index():
    return render_template('index.html')

# Endpoint to handle login and issue JWT
@app.route('/login', methods=['POST'])
def login():
    data = request.get_json()

    if not data or not data.get('username') or not data.get('password'):
        return jsonify({'message': 'Could not verify'}), 401

    username = data.get('username')
    password = data.get('password')

    # Verify user credentials
    if USERS.get(username) == password:
        # Generate the token with an expiration time of 30 minutes
        token = jwt.encode({
            'user': username,
            'exp': datetime.datetime.now(datetime.timezone.utc) + datetime.timedelta(minutes=30)
        }, app.config['SECRET_KEY'], algorithm="HS256")

        return jsonify({'token': token})

    return jsonify({'message': 'Invalid credentials'}), 401

# A protected endpoint that requires a valid JWT
@app.route('/protected', methods=['GET'])
@token_required
def protected_route(current_user):
    return jsonify({
        'message': f'Success! Hello, {current_user}. You have accessed a protected route.',
        'data': [1, 2, 3, 4, 5]
    })

if __name__ == '__main__':
    app.run(debug=True)