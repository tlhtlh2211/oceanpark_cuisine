var auth_token = getCookie('auth_token');

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
    if (auth_token) {
        getCategories(auth_token);
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


function getCategories(token) {
    fetch('http://localhost:3000/category/getOnline', {
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
        const table = document.getElementById('productTable');

        while (table.rows.length > 1) {
            table.deleteRow(1);
        }

        data.forEach(category => {
            console.log(category);
            const row = table.insertRow();
            const idCell = row.insertCell(0);
            const nameCell = row.insertCell(1);
            const statusCell = row.insertCell(2);

            idCell.textContent = category.id; 
            nameCell.textContent = category.name;
            statusCell.textContent = category.status;
        });
    })
    .catch(error => console.error('Error fetching data:', error));
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