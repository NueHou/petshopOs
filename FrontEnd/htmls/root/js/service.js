
// Função para exibir os clientes na página
async function fetchService() {
    const token = localStorage.getItem('jwtToken'); // Recupera o token armazenado

    if (!token) {
        alert('Você não está autenticado. Faça login novamente.');
        window.location.href = 'login.html';
        return;
    }
    try {
        const response = await fetch('http://localhost:8080/service', {
            method: 'GET',
            headers: {
                Authorization: token,
                'Content-Type': 'application/json',
            },
        });

        if (!response.ok) throw new Error('Erro ao carregar serviços.');
        const service = await response.json();

        serviceList.innerHTML = service
            .map(
                service => `
                    <tr>
                        <td>${service.fullPrice}</td>
                        <td>${service.description}</td>
                        <td>${service.serviceType}</td>
                        <td>${service.employee}</td>
                        <td>${service.client}</td>
                        <td>
                        <button class="editButton" onclick="editService(${service.id})">Editar</button>
                        <button class="deleteButton" onclick="deleteService(${service.id})">Excluir</button>
                        </td>
                    </tr>
                `
            )
            .join('');
    } catch (error) {
        console.error(error.message);
    }
    
}

// Função para deletar cliente
async function deleteService(serviceId) {
    const token = localStorage.getItem('jwtToken');
    try {
        const response = await fetch(`http://localhost:8080/service/${serviceId}`, {
            method: 'DELETE',
            headers: {
                'Authorization': token
            }
        });

        if (response.ok) {
            alert('Service excluído com sucesso!');
            fetchService(); // Atualiza a lista de service
        } else {
            alert('Erro ao excluir service!');
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
            const serviceId = button.getAttribute('data-id');
            const confirmDelete = confirm('Tem certeza que deseja excluir este service?');
            if (confirmDelete) {
                deleteService(serviceId);
            }
        });
    });
}

window.editService = id => {
    localStorage.setItem('editServiceId', id);
    window.location.href = 'service-form.html';
};

createServiceButton.addEventListener('click', () => {
    localStorage.removeItem('editServiceId');
    window.location.href = 'service-form.html';
});

fetchService();

// Função para realizar logout
function logout() {
    localStorage.removeItem('jwtToken'); // Remove o token armazenado
    alert('Logout realizado com sucesso.');
    window.location.href = 'login.html';
}

// Adiciona eventos ao carregar a página
document.addEventListener('DOMContentLoaded', () => {
    fetchService();
    document.getElementById('logoutButton').addEventListener('click', logout);
});
