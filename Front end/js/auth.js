document.addEventListener('DOMContentLoaded', () => {
    const formMedico = document.getElementById('formMedico');
    const mensagemDiv = document.getElementById('mensagem');
    const btnCadastrar = document.querySelector('.btn-cadastrar');

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
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(medicoData)
            });

            const result = await response.json();

            if (response.ok) {
                mensagemDiv.innerText = `Médico ${result.nome} cadastrado com sucesso!`;
                mensagemDiv.classList.add('success');

                setTimeout(() => {
                    window.location.href = "index.html";
                }, 2000);

            } else {
                mensagemDiv.innerText = result.message || "Erro ao cadastrar. Verifique os dados.";
                mensagemDiv.classList.add('error');
                btnCadastrar.innerText = "Finalizar Cadastro";
                btnCadastrar.disabled = false;
            }

        } catch (error) {
            console.error('Erro na requisição:', error);
            mensagemDiv.innerText = "Servidor offline. Verifique o seu Back-end Java.";
            mensagemDiv.classList.add('error');
            btnCadastrar.innerText = "Finalizar Cadastro";
            btnCadastrar.disabled = false;
        }
    });
});