document.addEventListener('DOMContentLoaded', async () => {
    document.getElementById('logoutButton').addEventListener('click', logout);
    const token = localStorage.getItem('jwtToken');
    const serviceId = localStorage.getItem('editServiceId');
    const form = document.getElementById('serviceForm');

    // Verifica se o usuário está autenticado
    if (!token) {
        alert('Você não está autenticado.');
        window.location.href = 'login/login.html';
        return;
    }

    // Carrega os dados do cliente se estiver em modo de edição
    if (serviceId) {
        try {
            const response = await fetch(`http://localhost:8080/service/${serviceId}`, {
                method: 'GET',
                headers: {
                    Authorization: token,
                    'Content-Type': 'application/json',
                },
            });

            if (!response.ok) {
                throw new Error('Erro ao carregar os dados do service.');
            }

            const service = await response.json();

            // Popula os campos do formulário
            document.getElementById('fullPrice').value = service.fullPrice || '';
            document.getElementById('serviceType').value = service.serviceType || 'BATH', 'GROOM', 'APPOINTMENT';
            document.getElementById('description').value = service.description || '';
            document.getElementById('client').value = service.client.id || '';
            document.getElementById('employee').value = service.employee.id || '';
        } catch (error) {
            console.error(error.message);
            alert('Erro ao carregar os dados do serviço.');
        }
    }

    // Manipula o envio do formulário
    form.addEventListener('submit', async event => {
        event.preventDefault(); // Impede o envio padrão do formulário

        const serviceData = {
            fullPrice: document.getElementById('fullPrice').value,
            serviceType: document.getElementById('serviceType').value,
            description: document.getElementById('description').value,
            client: document.getElementById('client').value,
            employee: document.getElementById('employee').value,
        };

        const url = serviceId
            ? `http://localhost:8080/service/${serviceId}`
            : 'http://localhost:8080/service';

        const method = serviceId ? 'PUT' : 'POST';

        try {
            const response = await fetch(url, {
                method,
                headers: {
                    Authorization: token,
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(serviceData),
            });

            if (!response.ok) {
                throw new Error('Erro ao salvar os dados do cliente.');
            }

            alert('animal salvo com sucesso!');
            window.location.href = 'service.html';
        } catch (error) {
            console.error(error.message);
            alert('Erro ao salvar os dados do animal.');
        }
    });
    function logout() {
        localStorage.removeItem('jwtToken'); // Remove o token armazenado
        alert('Logout realizado com sucesso.');
        window.location.href = 'login.html';
    }
});
