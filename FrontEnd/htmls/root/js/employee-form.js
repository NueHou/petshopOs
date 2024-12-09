document.addEventListener('DOMContentLoaded', async () => {
    document.getElementById('logoutButton').addEventListener('click', logout);
    const token = localStorage.getItem('jwtToken');
    const employeesId = localStorage.getItem('editEmployeeId');
    const form = document.getElementById('employeeForm');

    // Verifica se o usuário está autenticado
    if (!token) {
        alert('Você não está autenticado.');
        window.location.href = 'login.html';
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

            const employees = await response.json();

            // Popula os campos do formulário
            document.getElementById('name').value = employees.name || '';
            document.getElementById('cpf').value = employees.cpf || '';
            document.getElementById('email').value = employees.email || '';
            document.getElementById('password').value = employees.password || '';
            document.getElementById('personType').value = employees.personType || '';
        } catch (error) {
            console.error(error.message);
            alert('Erro ao carregar os dados do employee.');
        }
    }

    // Manipula o envio do formulário
    form.addEventListener('submit', async event => {
        event.preventDefault(); // Impede o envio padrão do formulário

        const personTypeEnumMap = {
            0: 'EMPLOYEE'
        }

        const employeesData = {
            name: document.getElementById('name').value,
            cpf: document.getElementById('cpf').value,
            password: document.getElementById('password').value,
            email: document.getElementById('email').value,
            personType: personTypeEnumMap['EMPLOYEE'],
        };
        console.log(employeesData);
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
                body: JSON.stringify(employeesData),
            });

            if (!response.ok) {
                throw new Error('Erro ao salvar os dados do empregado.');
            }

            alert('empregado salvo com sucesso!');
            window.location.href = 'employee.html';
        } catch (error) {
            console.error(error.message);
            alert('Erro ao salvar os dados do empregado.');
        }
    });
    function logout() {
        localStorage.removeItem('jwtToken'); // Remove o token armazenado
        alert('Logout realizado com sucesso.');
        window.location.href = 'login.html';
    }
});
