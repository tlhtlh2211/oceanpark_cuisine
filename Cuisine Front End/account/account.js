function getCookie(name) {
    var nameEQ = name + "=";
    var ca = document.cookie.split(';');
    for(var i=0; i < ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0) === ' ') c = c.substring(1,c.length);
        if (c.indexOf(nameEQ) === 0) return c.substring(nameEQ.length,c.length);
    }
    return null;
}

var auth_token = getCookie('auth_token');

document.addEventListener("DOMContentLoaded", function() {
    checkToken(auth_token).then(isValidToken => {
        console.log(isValidToken);
        if (isValidToken) {
            console.log("Token found: ", auth_token)
            getUserInfo(auth_token);
        } else {
            console.log("No token found");
            Swal.fire({
                title: 'Login Required',
                text: 'Your login has been expired!!',
                icon: 'question',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: 'Yes, log in!'
            }).then((result) => {
                if (result.isConfirmed) {
                    window.location.href = '/login/login.html';
                }
            });
        }
    }).catch(error => {
        console.error('Error during token validation:', error);
    });
});

function getUserInfo(token) {
    fetch('http://localhost:3000/user/getInfo', {
        method: 'GET',
        headers: {
            'Authorization': 'Bearer ' + token, 
            'Content-Type': 'application/json'
        }
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok ' + response.statusText);
        }
        return response.json();
    })
    .then(userInfo => {
        console.log('User Info:', userInfo);
        document.getElementById('name').textContent = userInfo.name || 'N/A';
        document.getElementById('email').textContent = userInfo.email || 'N/A';
        document.getElementById('studentID').textContent = userInfo.studentID || 'N/A';
        document.getElementById('role').textContent = userInfo.role || 'N/A';
        document.getElementById('bio').value = userInfo.bio || '';
    })
    .catch(error => {
        console.error('Error fetching user info:', error);
    });
}

function checkToken(auth_token) {
    if (auth_token === null) return Promise.resolve(false);
    return new Promise((resolve, reject) => {
        fetch('http://localhost:3000/user/checkToken', {
            method: 'GET',
            headers: {
                'Authorization': 'Bearer ' + auth_token,
                'Content-Type': 'application/json'
            }
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            resolve(data.message === "True");
        })
        .catch(error => {
            console.error('Error checking token:', error);
            reject(error);
        });
    });
}

function deleteCookie(name) {
    document.cookie = name + '=; path=/; expires=Thu, 01 Jan 1970 00:00:01 GMT;';
}

document.getElementById('logOut').addEventListener('click', function() {
    event.preventDefault();
    event.stopPropagation();
    Swal.fire({
        title: 'Log Out',
        text: 'Are you sure you want to log out?',
        icon: 'question',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Yes, log out!'
    }).then((result) => {
        if (result.isConfirmed) {

            deleteCookie('auth_token');
            window.location.href = '/login/login.html';
        }
    });
});