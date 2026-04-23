document.addEventListener('DOMContentLoaded', () => {
    const formMedico = document.getElementById('formMedico');
    const formLogin = document.getElementById('formLogin');
    const mensagemDiv = document.getElementById('mensagem');

    const toggles = document.querySelectorAll('.btn-toggle');
    toggles.forEach(btn => {
        btn.addEventListener('click', function() {
            // Pega o input que está logo antes do botão ou dentro do mesmo container
            const input = this.previousElementSibling;
            const type = input.getAttribute('type') === 'password' ? 'text' : 'password';
            input.setAttribute('type', type);
            this.innerText = type === 'password' ? '👁️' : '🙈';
        });
    });

    if (formMedico) {
        const btnCadastrar = formMedico.querySelector('.btn-cadastrar');

        formMedico.addEventListener('submit', async (event) => {
            event.preventDefault();

            const senha = document.getElementById('senha').value;
            const confirmarSenha = document.getElementById('confirmarSenha')?.value;

            if (confirmarSenha && senha !== confirmarSenha) {
                mensagemDiv.innerText = "As senhas não coincidem!";
                mensagemDiv.className = "status-msg error";
                return; // Para o envio aqui mesmo
            }

            btnCadastrar.innerText = "Processando...";
            btnCadastrar.disabled = true;
            mensagemDiv.className = "status-msg";
            mensagemDiv.innerText = "";

            const medicoData = {
                nome: document.getElementById('nome').value,
                crm: document.getElementById('crm').value,
                email: document.getElementById('email').value,
                senha: senha,
                especialidade: document.getElementById('especialidade').value
            };

            try {
                const response = await fetch('http://localhost:8081/medicos', {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify(medicoData)
                });

                if (response.ok) {
                    const result = await response.json();
                    mensagemDiv.innerText = `Médico ${result.nome} cadastrado com sucesso!`;
                    mensagemDiv.classList.add('success');
                    setTimeout(() => { window.location.href = "login.html"; }, 2000);
                } else {
                    const result = await response.json().catch(() => ({}));
                    mensagemDiv.innerText = result.message || "Erro ao cadastrar.";
                    mensagemDiv.classList.add('error');
                    btnCadastrar.innerText = "Finalizar Cadastro";
                    btnCadastrar.disabled = false;
                }
            } catch (error) {
                mensagemDiv.innerText = "Servidor offline.";
                mensagemDiv.classList.add('error');
                btnCadastrar.innerText = "Finalizar Cadastro";
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