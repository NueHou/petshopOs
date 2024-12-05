document.addEventListener('DOMContentLoaded', async () => {
    document.getElementById('logoutButton').addEventListener('click', logout);
    const token = localStorage.getItem('jwtToken');
    const clientId = localStorage.getItem('editClientId');
    const form = document.getElementById('clientForm');

    // Verifica se o usuário está autenticado
    if (!token) {
        alert('Você não está autenticado.');
        window.location.href = 'login/login.html';
        return;
    }

    // Carrega os dados do cliente se estiver em modo de edição
    if (clientId) {
        try {
            const response = await fetch(`http://localhost:8080/clients/${clientId}`, {
                method: 'GET',
                headers: {
                    Authorization: token,
                    'Content-Type': 'application/json',
                },
            });

            if (!response.ok) {
                throw new Error('Erro ao carregar os dados do cliente.');
            }

            const client = await response.json();

            // Popula os campos do formulário
            document.getElementById('name').value = client.name || '';
            document.getElementById('cpf').value = client.cpf || '';
            document.getElementById('email').value = client.email || '';
            document.getElementById('animal').value = client.animal.name || '';
            document.getElementById('personType').value = client.personType || 'CLIENT', 'EMPLOYEE';
        } catch (error) {
            console.error(error.message);
            alert('Erro ao carregar os dados do cliente.');
        }
    }

    // Manipula o envio do formulário
    form.addEventListener('submit', async event => {
        event.preventDefault(); // Impede o envio padrão do formulário

        const clientData = {
            name: document.getElementById('name').value,
            cpf: document.getElementById('cpf').value,
            email: document.getElementById('email').value,
            personType: document.getElementById('personType').value,
            animal: document.getElementById('animal').value
        };

        const url = clientId
            ? `http://localhost:8080/clients/${clientId}`
            : 'http://localhost:8080/clients';

        const method = clientId ? 'PUT' : 'POST';

        try {
            const response = await fetch(url, {
                method,
                headers: {
                    Authorization: token,
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(clientData),
            });

            if (!response.ok) {
                throw new Error('Erro ao salvar os dados do cliente.');
            }

            alert('Cliente salvo com sucesso!');
            window.location.href = 'client/clients.html';
        } catch (error) {
            console.error(error.message);
            alert('Erro ao salvar os dados do cliente.');
        }
    });
    function logout() {
        localStorage.removeItem('jwtToken'); // Remove o token armazenado
        alert('Logout realizado com sucesso.');
        window.location.href = 'login.html';
    }
});
