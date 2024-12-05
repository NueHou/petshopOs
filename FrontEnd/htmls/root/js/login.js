const apiUrl = 'http://localhost:8080/auth/login'; // Substitua pela URL correta da sua API

document.getElementById('loginForm').addEventListener('submit', async (event) => {
    event.preventDefault();

    // Capturar valores do formulário
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;

    try {
        // Enviar a requisição para o endpoint /auth/login
        const response = await fetch(apiUrl, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ username, password }),
        });

        if (response.ok) {
            const data = await response.json();
            localStorage.setItem('jwtToken', data.token); // Salva o token no armazenamento local
            window.location.href = 'home.html'; // Redireciona para a tela inicial
        } 

               

        // Obter o token da resposta
        const data = await response.json();
        const token = data.token;

        // Armazenar o token no localStorage
        localStorage.setItem('jwtToken', token);

        // Redirecionar para outra página ou informar sucesso
        alert('Login realizado com sucesso!');
        console.log('Token JWT:', token);
        
        // window.location.href = "/home.html"; // Exemplo de redirecionamento
    } catch (error) {
        console.error('Erro durante o login:', error.message);
        document.getElementById('errorMessage').textContent = error.message;
    }
});
