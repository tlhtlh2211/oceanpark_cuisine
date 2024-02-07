var auth_token = getCookie('auth_token');

const params = new URLSearchParams(window.location.search);
const shopId = params.get('shopId');

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
            getName(auth_token, shopId);
            getFoods(auth_token, shopId);
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

function getFoods(token, shopId) {
    fetch('http://localhost:3000/product/getProductByCategoryId/' + shopId, {
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
        const table = document.getElementById('foodTable');

        while (table.rows.length > 1) {
            table.deleteRow(1);
        }
        i = 0;

        data.forEach(food => {
            i = i+1;
            const row = table.insertRow();
            const Cell = row.insertCell(0);
            const nameCell = row.insertCell(1);
            const descriptionCell = row.insertCell(2);
            const priceCell = row.insertCell(3);
            const statusCell = row.insertCell(4);
            const foodId = food.id;

            Cell.textContent = i; 
            descriptionCell.textContent = food.description;
            nameCell.textContent = food.name;
            nameCell.innerHTML = `<a href="/review/review.html?productId=${food.id}">${food.name}</a>`;
            statusCell.textContent = food.status;
            priceCell.textContent = food.price;
        });
    })
    .catch(error => console.error('Error fetching data:', error));
}

function getName(token, shopId) {
    fetch('http://localhost:3000/category/getCategoryById/' + shopId, {
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
        document.getElementById('tableCaption').innerHTML = (data.name || 'Food List') + '<button id="addName" class="add-btn">+</button>';
        document.getElementById('contactNumber').innerHTML = '<strong>Contact Number:</strong> ' + '<span>' + (data.contactNumber || 'N/A') + '</span>';
        document.getElementById('socialMediaLink').innerHTML = '<strong>Social Media Link:</strong> ' + '<a href=' + (data.socialMediaLink) + ' target= "_blank">Facebook</a>';
        document.getElementById('addName').addEventListener('click', function() {
        addNewEntry(auth_token, shopId);
        }) 
    })
    .catch(error => console.error('Error fetching data:', error));
}

function addNewEntry(token, shopId) {
    const showInputModal = (preFilledName = '', preFilledDescription = '', preFilledPrice = '') => {
        Swal.fire({
            title: 'Enter Food Details',
            html:
            '<label for="swal-input1" style="display: block; text-align: left; font-weight: 500; font-size: 20px;">Food Name</label>' +
            '<input id="swal-input1" class="swal2-input" placeholder="Enter the food name">' +
            '<label for="swal-input2" style="display: block; text-align: left; font-weight: 500; font-size: 20px;">Description</label>' +
            '<textarea id="swal-input2" rows="4" class="swal2-textarea" placeholder="Write a short description"></textarea>' +
            '<label for="swal-input3" style="display: block; text-align: left; font-weight: 500; font-size: 20px;">Price</label>' +
            '<input id="swal-input3" class="swal2-input" placeholder="Price: xx.000 VND">',
        focusConfirm: false,
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
                var newName = result.value[0];
                var newDescription = result.value[1];
                var newPrice = result.value[2];

                if (newName.trim() === '' || newDescription.trim() === '') {
                    Swal.fire({
                        title: 'Missing Information',
                        text: 'You need to fill in both the name and the description of the food.',
                        icon: 'error',
                        confirmButtonText: 'Try again'
                    }).then(() => showInputModal(newName, newDescription, newPrice));
                    return;
                }

                if (isNaN(newPrice) || newPrice.trim() === '') {
                    Swal.fire({
                        title: 'Invalid Price',
                        text: 'Please enter a valid price (numbers only).',
                        icon: 'error',
                        confirmButtonText: 'Try again'
                    }).then(() => showInputModal(newName, newDescription, newPrice));
                    return;
                }

                var data = {
                    name: newName,
                    description: newDescription,
                    price: newPrice,
                    categoryId: shopId 
                };

                fetch('http://localhost:3000/product/add', {
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
                    return response.json();
                })
                .then(data => {
                    Swal.fire({
                        title: 'Food Added!',
                        text: 'Food added successfully!',
                        icon: 'success',
                        confirmButtonText: 'Cool'
                    }).then(() => {
                        window.location.href = '/product/product.html?shopId=' + shopId; 
                    });
                })
                .catch(error => {
                    console.error('Error:', error);
                    Swal.fire({
                        title: 'Error',
                        text: 'There was a problem adding the food item.',
                        icon: 'error',
                        confirmButtonText: 'OK'
                    });
                });
            }
        });
    };
    showInputModal();
}
