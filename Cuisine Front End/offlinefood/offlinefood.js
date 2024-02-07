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
    checkToken(auth_token).then(isValidToken => {
        console.log(isValidToken);
        if (isValidToken) {
            console.log("Token found: ", auth_token)
            getCategories(auth_token);
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


function getCategories(token) {
    fetch('http://localhost:3000/category/getOffline', {
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
        i = 0;

        data.forEach(category => {
            i = i+1;
            console.log(category);
            const row = table.insertRow();
            const Cell = row.insertCell(0);
            const nameCell = row.insertCell(1);
            const CategoryCell = row.insertCell(2);
            const statusCell = row.insertCell(3);
            const idCell = row.insertCell(4);

            Cell.textContent = i; 
            CategoryCell.textContent = category.category;
            idCell.textContent = category.id;
            nameCell.textContent = category.name;
            nameCell.innerHTML = `<a href="/product/product.html?shopId=${category.id}">${category.name}</a>`;
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

document.getElementById('addName').addEventListener('click', function() {
    addNewEntry(auth_token);
});

function addNewEntry(token) {
    Swal.fire({
        title: 'Enter Shop Details',
        html:
            '<input id="swal-input1" class="swal2-input" placeholder="Shop Name">' +
            '<input id="swal-input2" class="swal2-input" placeholder="Category (What do they sell?)">'+
            '<select id="swal-input3" class="swal2-input">' +
                '<option value="offline">offline (In store)</option>' +
                '<option value="online">online (Shipping option)</option>' + 
            '</select>',
        focusConfirm: false,
        preConfirm: () => {
            return [
                document.getElementById('swal-input1').value,
                document.getElementById('swal-input2').value,
                document.getElementById('swal-input3').value
            ]
        },
        showCancelButton: true
    }).then((result) => {
        if (result.isConfirmed) {
            console.log('Entered shop name:', result.value[0]);
            console.log('Entered category:', result.value[1]);
            console.log('Selected status:', result.value[2]);
 
            var newName = result.value[0];
            var newCategory = result.value[1];
            var newStatus = result.value[2];

            if (newName.trim() === '' || newCategory.trim() === '') {
                Swal.fire({
                    title: 'Wrong input!',
                    text: 'You need to fill all the fields!',
                    icon: 'error',
                    confirmButtonText: 'Try again'
                })
                return;
            }
    
            var data = {
                name: newName,
                category: newCategory,
                status: newStatus
            };
    
            fetch('http://localhost:3000/category/add', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': 'Bearer ' + token 
                },
                body: JSON.stringify(data)
            })
            .then(response => {
                if (!response.ok) {
                    throw new Error(`HTTP error! status: ${response.status}`);
                }
                console.log('New category added:', data);
                return response.json();
            })
            .then(data => {
                console.log('New category added:', data);
                Swal.fire({
                    title: 'Shop Added!',
                    text: 'Shop added successfully!',
                    icon: 'success',
                    confirmButtonText: 'Cool'
                }).then((result) => {
                    window.location.href = '/offlinefood/offlinefood.html';
                });
            })
            .catch(error => {
                console.error('Error:', error);
            });
        }
    });
}
