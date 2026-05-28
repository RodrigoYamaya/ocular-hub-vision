document.addEventListener('DOMContentLoaded', () => {
    const formMedico = document.getElementById('formMedico');
    const formLogin = document.getElementById('formLogin');
    const mensagemDiv = document.getElementById('mensagem');

    const toggles = document.querySelectorAll('.btn-toggle, #toggleSenha');

    toggles.forEach(btn => {
        btn.addEventListener('click', function() {
            const container = this.closest('.password-wrapper') || this.closest('.password-container');
            const input = container.querySelector('input');

            const isPassword = input.getAttribute('type') === 'password';
            input.setAttribute('type', isPassword ? 'text' : 'password');

            this.innerText = isPassword ? '🔓' : '👁️';
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
                return;
            }

            btnCadastrar.innerText = "Processando...";
            btnCadastrar.disabled = true;
            mensagemDiv.className = "status-msg";
            mensagemDiv.innerText = "";

            const medicoData = {
                nome: document.getElementById('nome').value,
                crm: document.getElementById('crm').value,
                email: document.getElementById('email').value,
                password: senha,
                especialidade: document.getElementById('especialidade').value
            };

            try {
                const response = await fetch('http://localhost:8081/medicos', {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify(medicoData)
                });

                mensagemDiv.className = "status-msg";

                if (response.ok) {
                    const result = await response.json();
                    mensagemDiv.innerText = `Médico ${result.nome} cadastrado com sucesso!`;
                    mensagemDiv.classList.add('success');
                    setTimeout(() => { window.location.href = "login.html"; }, 2000);
                } else {
                    const result = await response.json().catch(() => ({}));

                    // BINGO: Agora lê 'userMessage' vindo da estrutura RFC 7807 do Spring
                    mensagemDiv.innerText = result.userMessage || "Erro ao cadastrar.";
                    mensagemDiv.classList.add('error');

                    btnCadastrar.innerText = "Finalizar Cadastro";
                    btnCadastrar.disabled = false;
                }
            } catch (error) {
                mensagemDiv.className = "status-msg error";
                mensagemDiv.innerText = "Servidor offline.";
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
                password: document.getElementById('senha').value
            };

            try {
                const response = await fetch('http://localhost:8081/login', {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify(loginData)
                });

                mensagemDiv.className = "status-msg";

                if (response.ok) {
                    const result = await response.json();
                    localStorage.setItem('medicoNome', result.nome);
                    if(result.token) {
                        localStorage.setItem('token', result.token);
                    }

                    mensagemDiv.innerText = "Login realizado! Entrando...";
                    mensagemDiv.classList.add('success');

                    setTimeout(() => {
                        window.location.href = "../index.html";
                    }, 1500);
                } else {
                    const result = await response.json().catch(() => ({}));
                    mensagemDiv.innerText = result.userMessage || "E-mail ou senha inválidos.";
                    mensagemDiv.classList.add('error');
                    btnEntrar.innerText = "Entrar no Sistema";
                    btnEntrar.disabled = false;
                }
            } catch (error) {
                mensagemDiv.className = "status-msg error";
                mensagemDiv.innerText = "Erro de conexão com o servidor.";
                btnEntrar.innerText = "Entrar no Sistema";
                btnEntrar.disabled = false;
            }
        });
    }    
});

function entrarComoAvaliador() {
    localStorage.setItem('medicoNome', 'Dr. Avaliador (Teste)');
    
    const mensagemDiv = document.getElementById('mensagem');
    if (mensagemDiv) {
        mensagemDiv.innerText = "Acesso de avaliação liberado! Entrando...";
        mensagemDiv.className = "status-msg success";
        mensagemDiv.style.display = "block";
        mensagemDiv.style.color = "green";
        mensagemDiv.style.marginTop = "15px";
        mensagemDiv.style.textAlign = "center";
    }

    setTimeout(() => {
        window.location.href = "../index.html";
    }, 1000);
}