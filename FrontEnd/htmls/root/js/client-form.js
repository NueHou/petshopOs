document.addEventListener('DOMContentLoaded', async () => {
    const token = localStorage.getItem('jwtToken');
    const clientId = localStorage.getItem('editClientId');
    const form = document.getElementById('clientForm');

    // Verifica se o usuário está autenticado
    if (!token) {
        alert('Você não está autenticado.');
        window.location.href = 'login.html';
        return;
    }

    // Chama a função para carregar os animais
    await loadAnimals();

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
            document.getElementById('password').value = client.password || '';
            document.getElementById('animal-id').value = client.animal?.id || ''; // Seleciona o animal relacionado
        } catch (error) {
            console.error(error.message);
            alert('Erro ao carregar os dados do cliente.');
        }
    }

    // Manipula o envio do formulário
    form.addEventListener('submit', async event => {
        event.preventDefault();

        const clientData = {
            name: document.getElementById('name').value,
            cpf: document.getElementById('cpf').value,
            password: document.getElementById('password').value,
            email: document.getElementById('email').value,
            animalId: document.getElementById('animal-id').value,
        };
        console.log(clientData);
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
            window.location.href = 'clients.html';
        } catch (error) {
            console.error(error.message);
            alert('Erro ao salvar os dados do cliente.');
        }
    });

    document.getElementById('logoutButton').addEventListener('click', logout);
});

    

async function loadAnimals() {
    const token = localStorage.getItem('jwtToken');
    const animalEndpoint = 'http://localhost:8080/animal';
    try {
        const response = await fetch(animalEndpoint, {
            headers: {
                Authorization: token,
                'Content-Type': 'application/json',
            },
        });

        if (!response.ok) {
            throw new Error('Erro ao carregar os animais.');
        }

        const animals = await response.json();

        // Preenche o dropdown com os animais
        const animalSelect = document.getElementById('animal-id');
        animalSelect.innerHTML = '<option value="">Selecione um animal</option>';
        animals.forEach(animal => {
            const option = document.createElement('option');
            option.value = animal.id;
            option.textContent = `${animal.name} (${animal.type}) (${animal.race})`;
            animalSelect.appendChild(option);
        });
    } catch (error) {
        console.error(error.message);
        alert('Erro ao carregar os animais.');
    }
}

function logout() {
    localStorage.removeItem('jwtToken');
    alert('Logout realizado com sucesso.');
    window.location.href = 'login.html';
}
