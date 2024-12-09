document.addEventListener('DOMContentLoaded', async () => {
    document.getElementById('logoutButton').addEventListener('click', logout);
    const token = localStorage.getItem('jwtToken');
    const animalId = localStorage.getItem('editAnimalId');
    const form = document.getElementById('animalForm');

    // Verifica se o usuário está autenticado
    if (!token) {
        alert('Você não está autenticado.');
        window.location.href = 'login.html';
        return;
    }

    // Carrega os dados do cliente se estiver em modo de edição
    if (animalId) {
        try {
            const response = await fetch(`http://localhost:8080/animal/${animalId}`, {
                method: 'GET',
                headers: {
                    Authorization: token,
                    'Content-Type': 'application/json',
                },
            });

            if (!response.ok) {
                throw new Error('Erro ao carregar os dados do animal.');
            }

            const animal = await response.json();

            // Popula os campos do formulário
            document.getElementById('name').value = animal.name || '';
            document.getElementById('type').value = animal.type || '';
            document.getElementById('race').value = animal.race || '';
        } catch (error) {
            console.error(error.message);
            alert('Erro ao carregar os dados do animal.');
        }
    }

    // Manipula o envio do formulário
    form.addEventListener('submit', async event => {
        event.preventDefault(); // Impede o envio padrão do formulário

        const animalData = {
            name: document.getElementById('name').value,
            type: document.getElementById('type').value,
            race: document.getElementById('race').value,
        };

        const url = animalId
            ? `http://localhost:8080/animal/${animalId}`
            : 'http://localhost:8080/animal';

        const method = animalId ? 'PUT' : 'POST';

        try {
            const response = await fetch(url, {
                
                method,
                headers: {
                    Authorization: token,
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(animalData),
            });

            if (!response.ok) {
                throw new Error('Erro ao salvar os dados do cliente.');
            }

            alert('animal salvo com sucesso!');
            window.location.href = 'animal.html';
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
