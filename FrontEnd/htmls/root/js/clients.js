
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

        if (!response.ok) throw new Error('Erro ao carregar animais.');
        const client = await response.json();

        clientsList.innerHTML = client
            .map(
                client => `
                    <tr>
                        <td>${client.name}</td>
                        <td>${client.cpf}</td>
                        <td>${client.email}</td>
                        <td>${client.animal || 'Pet'}</td>
                        <td>
                        <button class="editButton" onclick="editClient(${client.id})">Editar</button>
                        <button class="deleteButton" onclick="deleteClient(${client.id})">Excluir</button>
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


// Função para deletar cliente
async function deleteClient(clientId) {
    const token = localStorage.getItem('jwtToken');
    try {
        const response = await fetch(`http://localhost:8080/clients/${clientId}`, {
            method: 'DELETE',
            headers: {
                'Authorization': token
            }
        });

        if (response.ok) {
            alert('Cliente excluído com sucesso!');
            fetchClients(); // Atualiza a lista de clientes
        } else {
            alert('Erro ao excluir cliente!');
            console.error(await response.text());
        }
    } catch (error) {
        console.error('Erro ao conectar ao servidor:', error);
    }
}

// Adiciona evento aos botões de excluir
function attachDeleteEvents() {
    const deleteButtons = document.querySelectorAll('.deleteButton');
    deleteButtons.forEach((button) => {
        button.addEventListener('click', () => {
            const clientId = button.getAttribute('data-id');
            const confirmDelete = confirm('Tem certeza que deseja excluir este cliente?');
            if (confirmDelete) {
                deleteClient(clientId);
            }
        });
    });
}

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
