
// Função para exibir os clientes na página
async function fetchClients() {
    const token = localStorage.getItem('jwtToken'); // Recupera o token armazenado

    if (!token) {
        alert('Você não está autenticado. Faça login novamente.');
        window.location.href = 'login.html';
        return;
    }
    try {
        const response = await fetch('http://localhost:8080/clients', {
            method: 'GET',
            headers: {
                Authorization: token,
                'Content-Type': 'application/json',
            },
        });

        if (!response.ok) throw new Error('Erro ao carregar clientes.');
        const clients = await response.json();

        clientList.innerHTML = clients
            .map(
                client => `
                    <tr>
                        <td>${client.id}</td>
                        <td>${client.name}</td>
                        <td>${client.cpf}</td>
                        <td>${client.email}</td>
                        <td>${client.animal}</td>
                        <td>${client.personType}</td>
                        <td>
                            <button onclick="editClient(${client.id})">Editar</button>
                        </td>
                    </tr>
                `
            )
            .join('');
    } catch (error) {
        console.error(error.message);
    }
}

window.editClient = id => {
    localStorage.setItem('editClientId', id);
    window.location.href = 'client-form.html';
};

createClientButton.addEventListener('click', () => {
    localStorage.removeItem('editClientId');
    window.location.href = 'client-form.html';
});

fetchClients();

// Função para realizar logout
function logout() {
    localStorage.removeItem('jwtToken'); // Remove o token armazenado
    alert('Logout realizado com sucesso.');
    window.location.href = 'login.html';
}

// Adiciona eventos ao carregar a página
document.addEventListener('DOMContentLoaded', () => {
    fetchClients();
    document.getElementById('logoutButton').addEventListener('click', logout);
});
