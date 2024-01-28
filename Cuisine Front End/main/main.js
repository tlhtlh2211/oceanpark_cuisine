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
