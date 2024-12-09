
// Função para exibir os clientes na página
async function fetchEmployees() {
    const token = localStorage.getItem('jwtToken'); // Recupera o token armazenado

    if (!token) {
        alert('Você não está autenticado. Faça login novamente.');
        window.location.href = 'login.html';
        return;
    }
    const personTypeMap = {
        0: 'Empregado'
    }
    try {
        const response = await fetch('http://localhost:8080/employees', {
            method: 'GET',
            headers: {
                Authorization: token,
                'Content-Type': 'application/json',
            },
        });

        if (!response.ok) throw new Error('Erro ao carregar employee.');
        const employee = await response.json();

        employeeList.innerHTML = employee
            .map(
                employee => `
                    <tr>
                        <td>${employee.name}</td>
                        <td>${employee.cpf}</td>
                        <td>${employee.email}</td>
                        <td>${personTypeMap[employee.personType] || 'Empregado'}</td>
                        <td>
                        <button class="editButton" onclick="editEmployee(${employee.id})">Editar</button>
                        <button class="deleteButton" onclick="deleteEmployee(${employee.id})">Excluir</button>
                        </td>
                    </tr>
                `
            )
            .join('');
    } catch (error) {
        console.error(error.message);
    }
}

async function deleteEmployee(employeeId) {
    const token = localStorage.getItem('jwtToken');
    try {
        const response = await fetch(`http://localhost:8080/employees/${employeeId}`, {
            method: 'DELETE',
            headers: {
                'Authorization': token
            }
        });

        if (response.ok) {
            alert('employee excluído com sucesso!');
            fetchEmployees(); // Atualiza a lista de clientes
        } else {
            alert('Erro ao excluir employee!');
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
            const employeeId = button.getAttribute('data-id');
            const confirmDelete = confirm('Tem certeza que deseja excluir este cliente?');
            if (confirmDelete) {
                deleteClient(employeeId);
            }
        });
    });
}


window.editEmployee = id => {
    localStorage.setItem('editEmployeeId', id);
    window.location.href = 'employee-form.html';
};

createEmployeeButton.addEventListener('click', () => {
    localStorage.removeItem('editEmployeeId');
    window.location.href = 'employee-form.html';
});

fetchEmployees();

// Função para realizar logout
function logout() {
    localStorage.removeItem('jwtToken'); // Remove o token armazenado
    alert('Logout realizado com sucesso.');
    window.location.href = 'login.html';
}

// Adiciona eventos ao carregar a página
document.addEventListener('DOMContentLoaded', () => {
    fetchEmployees();
    document.getElementById('logoutButton').addEventListener('click', logout);
});
