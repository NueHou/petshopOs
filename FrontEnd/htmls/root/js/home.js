document.getElementById('logoutButton').addEventListener('click', () => {
    localStorage.removeItem('jwtToken');
    alert('Logout realizado.');
    window.location.href = 'login.html';
});

function navigateTo(page) {
    window.location.href = page;
}
