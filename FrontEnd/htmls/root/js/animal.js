

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
                        <button class="edit-btn" onclick="editAnimal(${animal.id})">Editar</button>
                        </td>
                    </tr>
                `
            )
            .join('');
    } catch (error) {
        console.error(error.message);
    }
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
