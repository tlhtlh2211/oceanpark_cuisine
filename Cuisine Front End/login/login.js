const register = document.getElementById('register');
const container = document.getElementById('container');
const login = document.getElementById('login');

var isAuthenticated = false;
localStorage.setItem('isAuthenticated', isAuthenticated);

register.addEventListener('click', () => {
    container.classList.add("active");
});

login.addEventListener('click', () => {
    container.classList.remove("active");
});

function handleRegistration() {
    const name = document.getElementById('name').value;
    const email = document.getElementById('email').value;
    const studentID = document.getElementById('studentID').value;
    const password = document.getElementById('password').value;
    const confirmPassword = document.getElementById('confirmPassword').value;

    if (password !== confirmPassword) {
        alert('Passwords do not match');
        return;
    }

    const registrationData = {
        name: name,
        studentID: studentID,
        email: email,
        password: password
    };

    fetch('http://localhost:3000/user/signup', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(registrationData)
    })
    .then(response => {
        if (response.ok) {
            window.location.href = 'http://127.0.0.1:3000/main/main.html'; 
        } else {
            console.error('Signup failed:', response.statusText);
            return response.text();
        }
    })
    .then(data => {
        console.log('Registration successful:', data);
    })
    .catch(error => {
        console.error('Fetch error:', error);
    });
}

const submitRegistrationButton = document.getElementById('submitRegistration');
submitRegistrationButton.addEventListener('click', handleRegistration);

function handleLogin() {
    const email = document.getElementById('email_login').value;   
    const password = document.getElementById('password_login').value;

    const loginData = {
        email: email,
        password: password
    };

    console.log(loginData);

    fetch('http://localhost:3000/user/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(loginData)
    })
    .then(response => {
        if (response.ok) {
            var isAuthenticated = true;
            localStorage.setItem('isAuthenticated', isAuthenticated);
            window.location.href = 'http://127.0.0.1:3000/main/main.html';  
        } else {
            console.error('Signup failed:', response.statusText);
            return response.text(); 
        }
    })
    .then(errorData => {
        console.error('Error from server:', errorData);
    })
    .catch(error => {
        console.error('Fetch error:', error);
    });
}

const loginButton = document.getElementById('loginButton');
loginButton.addEventListener('click', handleLogin);

