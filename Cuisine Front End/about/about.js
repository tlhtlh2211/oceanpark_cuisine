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
    if (auth_token) {
        console.log("Token found: ", auth_token)
        document.getElementById("loginLink").style.display = 'none';
        document.getElementById("accountLink").style.display = 'block'; 
        document.getElementById("logOut").style.display = 'block';
    } else {
        console.log("No token found");
        document.getElementById("loginLink").style.display = 'block'; 
        document.getElementById("accountLink").style.display = 'none'; 
        document.getElementById("logOut").style.display = 'none';
    }
});

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