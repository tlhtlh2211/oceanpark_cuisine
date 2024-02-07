const register = document.getElementById('register');
const container = document.getElementById('container');
const login = document.getElementById('login');

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

    if (name === '' || email === '' || studentID === '' || password === '' || confirmPassword === ''){
       Swal.fire({
        title: 'Something went wrong!',
        text: 'Please try again!',
        icon: 'error',
        confirmButtonText: 'Try again'
        }); 
        return;
    }

    if (!email.includes('@') || !email.includes('.')) {
      Swal.fire({
      title: 'Wrong email format!',
      text: 'Please try again!',
      icon: 'error',
      confirmButtonText: 'Try again'
      }); 
      return;
    }

    if (!studentID.includes('V') || (studentID.length !== 10)) {
      Swal.fire({
      title: 'Wrong student ID format!',
      text: 'Please try again!',
      icon: 'error',
      confirmButtonText: 'Try again'
      }); 
      return;
    }

    if (password !== confirmPassword) {
      Swal.fire({
        title: 'Password does not match!',
        text: 'Please try again!',
        icon: 'error',
        confirmButtonText: 'Try again'
        }); 
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
            Swal.fire({
                title: 'Registration Successful!',
                text: 'Please login again to your account!',
                icon: 'success',
                confirmButtonText: 'Cool'
              }).then((result) => {
                if (result.value) {
                    window.location.href = '/login/login.html';
                }
              }); 
        } else {
            console.error('Signup failed:', response.statusText);
            return response.text();
        }
    })
    .then(data => {
        if (data === '{"Email already exists."}') {
            Swal.fire({
                title: 'Email already exists!',
                text: 'Please try again!',
                icon: 'error',
                confirmButtonText: 'Try again'
              }); 
        }
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
        if (!response.ok) {
          return response.text().then(text => {
            throw new Error(text || response.statusText);
          });
        }
        return response.json();
      })
    .then(data => {
        if (data && data.token) {
          setCookie('auth_token', data.token, 1);
          console.log("Login successful, token saved in cookie");
          Swal.fire({
            title: 'Login Successful!',
            text: 'Welcome to the site!',
            icon: 'success',
            confirmButtonText: 'Cool'
          }).then((result) => {
            if (result.value) {
              window.location.href = '/main/main.html';
            }
          });
        } else {
          throw new Error('Email or password is incorrect');
        }
    })
    .catch(error => {
        console.error('Error:', error.message);
        let errorMessage = error.message;
        try {
          const errorData = JSON.parse(errorMessage);
          errorMessage = errorData.message;
        } catch (e) {}
      
        Swal.fire({
          title: 'Error',
          text: errorMessage,
          icon: 'error',
          confirmButtonText: 'Try again'
        });
    });
}

const loginButton = document.getElementById('loginButton');
loginButton.addEventListener('click', handleLogin);

function setCookie(name, value, days) {
  var expires = "";
  if (days) {
      var date = new Date();
      date.setTime(date.getTime() + (days * 24 * 60 * 60 * 1000));
      expires = "; expires=" + date.toUTCString();
  }
  document.cookie = name + "=" + (value || "") + expires + "; path=/; Secure";
}


  