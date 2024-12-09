document.addEventListener('DOMContentLoaded', async () => {
    document.getElementById('logoutButton').addEventListener('click', logout);
    const token = localStorage.getItem('jwtToken');
    const serviceId = localStorage.getItem('editServiceId');
    const form = document.getElementById('serviceForm');

    if (!token) {
        alert('Você não está autenticado.');
        window.location.href = 'login.html';
        return;
    }

    const serviceTypeMap = {
        0: 'BATH',
        1: 'GROOM',
        2: 'APPOINTMENT',
    };

    // Função para carregar a lista de clientes
    

    // Carrega os dados do serviço se estiver em modo de edição
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
                throw new Error('Erro ao carregar os dados do serviço.');
            }

            const service = await response.json();
            document.getElementById('fullPrice').value = service.fullPrice || '';
            document.getElementById('serviceType').value = service.serviceType || '';
            document.getElementById('description').value = service.description || '';
            document.getElementById('client').value = service.client?.id || '';
            document.getElementById('employee').value = service.employee?.id || '';
        } catch (error) {
            console.error(error.message);
            alert('Erro ao carregar os dados do serviço.');
        }
    }

    // Carrega listas de clientes e funcionários
    await loadClients();
    await loadEmployees();

    form.addEventListener('submit', async event => {
        event.preventDefault();

        const serviceData = {
            fullPrice: (document.getElementById('fullPrice').value),
            serviceType: document.getElementById('serviceType').value,
            description: document.getElementById('description').value,
            client: document.getElementById('client').value,
            employee: document.getElementById('employee').value,
        };
        console.log(serviceData);
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
                throw new Error('Erro ao salvar os dados do serviço.');
            }

            alert('Serviço salvo com sucesso!');
            window.location.href = 'service.html';
        } catch (error) {
            console.error(error.message);
            alert('Erro ao salvar os dados do serviço.');
        }
    });
    async function loadClients() {
        try {
            const response = await fetch('http://localhost:8080/clients', {
                method: 'GET',
                headers: {
                    Authorization: token,
                    'Content-Type': 'application/json',
                },
            });

            if (!response.ok) {
                throw new Error('Erro ao carregar clientes.');
            }

            const clients = await response.json();
            const clientSelect = document.getElementById('client');
            clients.forEach(client => {
                const option = document.createElement('option');
                option.value = client.id;
                option.textContent = `${client.name} (CPF: ${client.cpf})`;
                clientSelect.appendChild(option);
            });
        } catch (error) {
            console.error(error.message);
        }
    }

    // Função para carregar a lista de funcionários
    async function loadEmployees() {
        try {
            const response = await fetch('http://localhost:8080/employees', {
                method: 'GET',
                headers: {
                    Authorization: token,
                    'Content-Type': 'application/json',
                },
            });

            if (!response.ok) {
                throw new Error('Erro ao carregar funcionários.');
            }

            const employees = await response.json();
            const employeeSelect = document.getElementById('employee');
            employees.forEach(employee => {
                const option = document.createElement('option');
                option.value = employee.id;
                option.textContent = `${employee.name} (Email: ${employee.email})`;
                employeeSelect.appendChild(option);
            });
        } catch (error) {
            console.error(error.message);
        }
    }
    function logout() {
        localStorage.removeItem('jwtToken');
        alert('Logout realizado com sucesso.');
        window.location.href = 'login.html';
    }
});
