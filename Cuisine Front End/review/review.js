var auth_token = getCookie('auth_token');

const params = new URLSearchParams(window.location.search);
const productId = params.get('productId');

function getCookie(name) {
    var nameEQ = name + "=";
    var ca = document.cookie.split(';');
    for(var i=0;i < ca.length;i++) {
        var c = ca[i];
        while (c.charAt(0)==' ') c = c.substring(1,c.length);
        if (c.indexOf(nameEQ) == 0) return c.substring(nameEQ.length,c.length);
    }
    return null;
}

document.addEventListener("DOMContentLoaded", function() {
    checkToken(auth_token).then(isValidToken => {
        console.log(isValidToken);
        if (isValidToken) {
            console.log("Token found: ", auth_token)
            getReviews(auth_token, productId);
            document.getElementById("loginLink").style.display = 'none';
            document.getElementById("accountLink").style.display = 'block'; 
            document.getElementById("logOut").style.display = 'block';
        } else {
            console.log("No token found");
            document.getElementById("loginLink").style.display = 'block'; 
            document.getElementById("accountLink").style.display = 'none'; 
            document.getElementById("logOut").style.display = 'none';
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

function getReviews(token, productId) {
    fetch('http://localhost:3000/review/getReviewByProductId/' + productId, {
        method: 'GET',
        headers: {
            'Authorization': 'Bearer ' + token, 
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
        console.log('Reviews:', data);
    })
    .catch(error => console.error('Error fetching data:', error));
}