document.addEventListener('DOMContentLoaded', async () => {
    document.getElementById('logoutButton').addEventListener('click', logout);
    const token = localStorage.getItem('jwtToken');
    const employeesId = localStorage.getItem('editEmployeeId');
    const form = document.getElementById('employeeForm');

    // Verifica se o usuário está autenticado
    if (!token) {
        alert('Você não está autenticado.');
        window.location.href = 'login/login.html';
        return;
    }

    // Carrega os dados do cliente se estiver em modo de edição
    if (employeesId) {
        try {
            const response = await fetch(`http://localhost:8080/employees/${employeesId}`, {
                method: 'GET',
                headers: {
                    Authorization: token,
                    'Content-Type': 'application/json',
                },
            });

            if (!response.ok) {
                throw new Error('Erro ao carregar os dados do cliente.');
            }

            const employee = await response.json();

            // Popula os campos do formulário
            document.getElementById('name').value = employee.name || '';
            document.getElementById('cpf').value = employee.cpf || '';
            document.getElementById('email').value = employee.email || '';
            document.getElementById('password').value = employee.password || '';
            document.getElementById('personType').value = employee.personType || 'EMPLOYEE';
        } catch (error) {
            console.error(error.message);
            alert('Erro ao carregar os dados do employee.');
        }
    }

    // Manipula o envio do formulário
    form.addEventListener('submit', async event => {
        event.preventDefault(); // Impede o envio padrão do formulário

        const employeeData = {
            cpf: document.getElementById('cpf').value,
            password: document.getElementById('password').value,
            email: document.getElementById('email').value,
            personType: document.getElementById('personType').value,
        };

        const url = employeesId
            ? `http://localhost:8080/employees/${employeesId}`
            : 'http://localhost:8080/employees';

        const method = employeesId ? 'PUT' : 'POST';

        try {
            const response = await fetch(url, {
                method,
                headers: {
                    Authorization: token,
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(employeeData),
            });

            if (!response.ok) {
                throw new Error('Erro ao salvar os dados do cliente.');
            }

            alert('Cliente salvo com sucesso!');
            window.location.href = 'employee.html';
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
