document.addEventListener('DOMContentLoaded', () => {
    const formMedico = document.getElementById('formMedico'); // Formulário de Cadastro
    const formLogin = document.getElementById('formLogin');   // Formulário de Login
    const mensagemDiv = document.getElementById('mensagem');

    const btnToggle = document.getElementById('toggleSenha');
    const inputSenha = document.getElementById('senha');

    if (btnToggle && inputSenha) {
        btnToggle.addEventListener('click', () => {
            const type = inputSenha.getAttribute('type') === 'password' ? 'text' : 'password';
            inputSenha.setAttribute('type', type);

            btnToggle.innerText = type === 'password' ? '👁️' : '🙈';
        });
    }

    if (formMedico) {
        const btnCadastrar = formMedico.querySelector('.btn-cadastrar');

        formMedico.addEventListener('submit', async (event) => {
            event.preventDefault();

            btnCadastrar.innerText = "Processando...";
            btnCadastrar.disabled = true;
            mensagemDiv.className = "status-msg";
            mensagemDiv.innerText = "";

            const medicoData = {
                nome: document.getElementById('nome').value,
                crm: document.getElementById('crm').value,
                email: document.getElementById('email').value,
                senha: document.getElementById('senha').value,
                especialidade: document.getElementById('especialidade').value
            };

            try {
                const response = await fetch('http://localhost:8081/medicos', {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify(medicoData)
                });

                const result = await response.json();

                if (response.ok) {
                    mensagemDiv.innerText = `Médico ${result.nome} cadastrado com sucesso!`;
                    mensagemDiv.classList.add('success');
                    setTimeout(() => { window.location.href = "login.html"; }, 2000);
                } else {
                    mensagemDiv.innerText = result.message || "Erro ao cadastrar.";
                    mensagemDiv.classList.add('error');
                    btnCadastrar.innerText = "Finalizar Cadastro";
                    btnCadastrar.disabled = false;
                }
            } catch (error) {
                mensagemDiv.innerText = "Servidor offline.";
                mensagemDiv.classList.add('error');
                btnCadastrar.disabled = false;
            }
        });
    }


    if (formLogin) {
        const btnEntrar = formLogin.querySelector('.btn-cadastrar');

        formLogin.addEventListener('submit', async (event) => {
            event.preventDefault();

            btnEntrar.innerText = "Autenticando...";
            btnEntrar.disabled = true;
            mensagemDiv.className = "status-msg";
            mensagemDiv.innerText = "";

            const loginData = {
                email: document.getElementById('email').value,
                senha: document.getElementById('senha').value
            };

            try {
                const response = await fetch('http://localhost:8081/auth/login', {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify(loginData)
                });

                if (response.ok) {
                    const result = await response.json();

                    localStorage.setItem('medicoNome', result.nome);

                    mensagemDiv.innerText = "Login realizado! Entrando...";
                    mensagemDiv.classList.add('success');

                    setTimeout(() => {
                        window.location.href = "../index.html";
                    }, 1500);
                } else {
                    mensagemDiv.innerText = "E-mail ou senha inválidos.";
                    mensagemDiv.classList.add('error');
                    btnEntrar.innerText = "Entrar no Sistema";
                    btnEntrar.disabled = false;
                }
            } catch (error) {
                mensagemDiv.innerText = "Erro de conexão com o servidor.";
                mensagemDiv.classList.add('error');
                btnEntrar.innerText = "Entrar no Sistema";
                btnEntrar.disabled = false;
            }
        });
    }
});