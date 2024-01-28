function updateNavigationLinks() {
    var accountLink = document.getElementById("account");
    var loginLink = document.getElementById("login");

    var isAuthenticated = localStorage.getItem('isAuthenticated');

    if (isAuthenticated) {
        accountLink.style.display = "block";
        loginLink.style.display = "none";
    } else {
        accountLink.style.display = "none";
        loginLink.style.display = "block";
    }
}

window.onload = function () {
    updateNavigationLinks();
};

function checkAuthentication() {
    $.ajax({
        url: '/check-auth', 
        method: 'GET',
        success: function(response) {
            if (response.isAuthenticated) {
                $('#loginLink').hide();
                $('#accountLink').show();
            } else {
                $('#loginLink').show();
                $('#accountLink').hide();
            }
        },
        error: function() {
            $('#loginLink').show();
            $('#accountLink').hide();
        }
    });
}

$(document).ready(function() {
    checkAuthentication();
});

document.addEventListener('DOMContentLoaded', function() {

    var searchIcon = document.getElementById('search-icon');
    var searchInput = document.getElementById('search-input');
    var closeIcon = document.getElementById('close-icon');

    function showSearchInput() {
        searchInput.classList.remove('hidden');
        closeIcon.classList.remove('hidden');
        searchIcon.classList.add('hidden');
    }

    function hideSearchInput() {
        searchInput.classList.add('hidden');
        closeIcon.classList.add('hidden');
        searchIcon.classList.remove('hidden');
    }

    searchIcon.addEventListener('click', showSearchInput);
    closeIcon.addEventListener('click', hideSearchInput);
});

document.addEventListener("DOMContentLoaded", function() {
    var isLoggedIn = checkAuthToken();
    console.log('Is logged in:', isLoggedIn);

    if (isLoggedIn) {
        document.getElementById("accountLink").style.display = 'block'; 
        document.getElementById("loginLink").style.display = 'none';
    } else {
        document.getElementById("accountLink").style.display = 'none'; 
        document.getElementById("loginLink").style.display = 'block'; 
    }
});

function checkAuthToken() {
    var cookies = document.cookie.split('; ');
    for (var i = 0; i < cookies.length; i++) {
        var cookie = cookies[i].split('=');
        console.log('Checking cookie:', cookie[0]);
        if (cookie[0] === 'auth_token' && cookie[1]) {
            return true; 
        }
    }
    return false;
}