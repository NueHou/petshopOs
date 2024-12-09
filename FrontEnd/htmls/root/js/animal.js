

async function fetchAnimals() {
    const token = localStorage.getItem('jwtToken'); // Recupera o token armazenado

    if (!token) {
        alert('Você não está autenticado. Faça login novamente.');
        window.location.href = 'login.html';
        return;
    }
    try {
        const response = await fetch('http://localhost:8080/animal', {
            method: 'GET',
            headers: {
                Authorization: token,
                'Content-Type': 'application/json',
            },
        });

        if (!response.ok) throw new Error('Erro ao carregar animais.');
        const animal = await response.json();

        animalList.innerHTML = animal
            .map(
                animal => `
                    <tr>
                        <td>${animal.name}</td>
                        <td>${animal.type}</td>
                        <td>${animal.race}</td>
                        <td>
                        <button class="editButton" onclick="editAnimal(${animal.id})">Editar</button>
                        <button class="deleteButton" onclick="deleteAnimal(${animal.id})">Excluir</button>
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
async function deleteAnimal(animalId) {
    const token = localStorage.getItem('jwtToken');
    try {
        const response = await fetch(`http://localhost:8080/animal/${animalId}`, {
            method: 'DELETE',
            headers: {
                'Authorization': token
            }
        });

        if (response.ok) {
            alert('Animal excluído com sucesso!');
            fetchAnimals(); // Atualiza a lista de clientes
        } else {
            alert('Erro ao excluir Animal!');
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
            const animalId = button.getAttribute('data-id');
            const confirmDelete = confirm('Tem certeza que deseja excluir este cliente?');
            if (confirmDelete) {
                deleteClient(animalId);
            }
        });
    });
}

window.editAnimal = id => {
    localStorage.setItem('editAnimalId', id);
    window.location.href = 'animal-form.html';
};

createAnimalButton.addEventListener('click', () => {
    localStorage.removeItem('editAnimalId');
    window.location.href = 'animal-form.html';
});

fetchAnimals();

// Função para realizar logout
function logout() {
    localStorage.removeItem('jwtToken'); // Remove o token armazenado
    alert('Logout realizado com sucesso.');
    window.location.href = 'login.html';
}

// Adiciona eventos ao carregar a página
document.addEventListener('DOMContentLoaded', () => {
    fetchAnimals();
    document.getElementById('logoutButton').addEventListener('click', logout);
});
