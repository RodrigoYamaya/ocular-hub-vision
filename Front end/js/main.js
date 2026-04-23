document.addEventListener('DOMContentLoaded', () => {
    const medicoNome = localStorage.getItem('medicoNome');

    if (!medicoNome) {
        window.location.href = "pages/login.html";
        return;
    } else {
        const displayNome = document.getElementById('userName');
        if (displayNome) displayNome.innerText = `Dr. ${medicoNome}`;
    }

    const form = document.getElementById('uploadForm');
    const fileInput = document.getElementById('fileInput');
    const dropZone = document.getElementById('dropZone');
    const dropZoneText = document.querySelector('.drop-zone-text');
    const statusMsg = document.getElementById('uploadStatus');
    const btnAnalisar = document.getElementById('btnAnalisar');

    dropZone.addEventListener('click', () => fileInput.click());
    fileInput.addEventListener('change', () => {
        if (fileInput.files.length > 0) atualizarTextoDropzone(fileInput.files[0].name);
    });

    ['dragover', 'dragleave', 'drop'].forEach(eventName => {
        dropZone.addEventListener(eventName, (e) => { e.preventDefault(); e.stopPropagation(); });
    });

    dropZone.addEventListener('dragover', () => dropZone.classList.add('drop-zone--over'));
    dropZone.addEventListener('dragleave', () => dropZone.classList.remove('drop-zone--over'));

    dropZone.addEventListener('drop', (e) => {
        dropZone.classList.remove('drop-zone--over');
        const files = e.dataTransfer.files;
        if (files.length > 0) {
            fileInput.files = files;
            atualizarTextoDropzone(files[0].name);
        }
    });

    function atualizarTextoDropzone(nomeArquivo) {
        dropZoneText.innerText = `Arquivo selecionado: ${nomeArquivo}`;
        statusMsg.innerText = "Imagem carregada com sucesso.";
        statusMsg.className = "status-msg success";
    }

    form.addEventListener('submit', async (e) => {
        e.preventDefault();
        const file = fileInput.files[0];

        if (!file) {
            statusMsg.innerText = "Atenção: Selecione uma imagem de retina primeiro.";
            statusMsg.className = "status-msg error";
            return;
        }

        const originalBtnText = btnAnalisar.innerText;
        btnAnalisar.innerText = "Analisando Imagem...";
        btnAnalisar.disabled = true;

        const formData = new FormData();
        formData.append('file', file);
        formData.append('medicoResponsavel', medicoNome);

        try {
            const response = await fetch('http://localhost:8081/exames/upload', {
                method: 'POST',
                body: formData
            });

            if (response.ok) {
                const resultado = await response.json();

                alert("Sucesso! Análise concluída.");
                statusMsg.innerText = "Exame processado pela IA.";
                statusMsg.className = "status-msg success";

               localStorage.setItem('ultimoResultado', JSON.stringify(resultado));

                form.reset();
                dropZoneText.innerText = "Arraste a imagem ou clique aqui";



            } else {
                statusMsg.innerText = "Erro ao processar imagem no servidor.";
                statusMsg.className = "status-msg error";
            }
        } catch (error) {
            statusMsg.innerText = "Erro de conexão: Verifique o Back-end.";
            statusMsg.className = "status-msg error";
        } finally {
            btnAnalisar.innerText = originalBtnText;
            btnAnalisar.disabled = false;
        }
    });
});