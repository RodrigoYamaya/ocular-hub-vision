document.addEventListener('DOMContentLoaded', () => {

    const formCadastro = document.getElementById('uploadForm');
    const btnSalvar = document.getElementById('btnAnalisar');

    if (formCadastro) {
        formCadastro.addEventListener('submit', enviarExameComImagem);
    }

    async function enviarExameComImagem(event) {
        event.preventDefault();

        const inputImagem = document.getElementById('fileInput');
        const foto = inputImagem.files[0];

        if (!foto) {
            alert("Por favor, selecione uma imagem do exame!");
            return;
        }

        const dadosExame = {
            pacienteId: document.getElementById('pacienteId').value,
            titulo: document.getElementById('titulo').value,
            regiaoAnalisada: document.getElementById('regiao').value // Nota: no seu HTML o ID é 'regiao'
        };

        const formData = new FormData();
        formData.append("imagem", foto);

        const blobDados = new Blob([JSON.stringify(dadosExame)], {
            type: 'application/json'
        });
        formData.append("dados", blobDados);

        try {
            const textoOriginal = btnSalvar.innerText;
            btnSalvar.innerText = "Analisando com IA...";
            btnSalvar.disabled = true;

            const resposta = await fetch('http://localhost:8081/exames/com-imagem', {
                method: 'POST',
                body: formData
            });

            if (resposta.ok) {
                const exameSalvo = await resposta.json();

                // Redirecionamento para o relatório com o ID do exame
                window.location.href = `pages/relatorio.html?id=${exameSalvo.id}`;
            } else {
                alert("Erro ao salvar o exame.");
                btnSalvar.innerText = textoOriginal;
                btnSalvar.disabled = false;
            }

        } catch (erro) {
            console.error("Erro:", erro);
            alert("Erro de conexão com o servidor.");
            btnSalvar.innerText = "Iniciar Análise";
            btnSalvar.disabled = false;
        }
    }
});