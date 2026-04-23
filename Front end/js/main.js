const form = document.getElementById('uploadForm');
const fileInput = document.getElementById('fileInput');
const dropZone = document.getElementById('dropZone');
const dropZoneText = document.querySelector('.drop-zone-text');
const statusMsg = document.getElementById('uploadStatus');
const btnAnalisar = document.getElementById('btnAnalisar');

dropZone.addEventListener('click', () => fileInput.click());

fileInput.addEventListener('change', () => {
    if (fileInput.files.length > 0) {
        atualizarTextoDropzone(fileInput.files[0].name);
    }
});

['dragover', 'dragleave', 'drop'].forEach(eventName => {
    dropZone.addEventListener(eventName, (e) => {
        e.preventDefault();
        e.stopPropagation();
    });
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
    statusMsg.innerText = "Enviando para triagem inteligente...";
    statusMsg.className = "status-msg";

    const formData = new FormData();
    formData.append('file', file);

    try {
        const response = await fetch('http://localhost:8081/exames/upload', {
            method: 'POST',
            body: formData
        });

        if (response.ok) {
            alert("Sucesso! O exame foi enviado para a base de dados.");
            statusMsg.innerText = "Análise concluída com sucesso.";
            statusMsg.className = "status-msg success";

            form.reset();
            dropZoneText.innerText = "Arraste a imagem ou clique aqui";
        } else {
            const errorText = await response.text();
            statusMsg.innerText = "Erro no Servidor: " + errorText;
            statusMsg.className = "status-msg error";
        }
    } catch (error) {
        statusMsg.innerText = "Erro de conexão: O Back-end está desligado.";
        statusMsg.className = "status-msg error";
    } finally {
        btnAnalisar.innerText = originalBtnText;
        btnAnalisar.disabled = false;
    }
});