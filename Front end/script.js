const form = document.querySelector('.upload-form');
const fileInput = document.querySelector('.file-input');
const dropZone = document.querySelector('.drop-zone');
const dropZoneText = document.querySelector('.drop-zone-text');


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
        fileInput.files = files; // Atribui o arquivo arrastado ao input real
        atualizarTextoDropzone(files[0].name);
    }
});

function atualizarTextoDropzone(nomeArquivo) {
    dropZoneText.innerText = `Arquivo selecionado: ${nomeArquivo}`;
}



form.addEventListener('submit', async (e) => {
    e.preventDefault();

    const file = fileInput.files[0];
    if (!file) {
        alert("Por favor, selecione uma imagem de retina!");
        return;
    }

    const formData = new FormData();
    formData.append('file', file);

    try {
        console.log("Enviando para o OcularHub Backend...");

        const response = await fetch('http://localhost:8081/exames/upload', {
            method: 'POST',
            body: formData
        });

        if (response.ok) {
            const data = await response.json();
            alert("Sucesso! Imagem salva na Camada Bronze.");
            console.log("ID do Registro:", data.id);
        } else {
            const errorText = await response.text();
            alert("Erro no servidor: " + errorText);
        }
    } catch (error) {
        console.error("Erro de conexão:", error);
        alert(" Erro: Verifique se o seu Spring Boot está rodando!");
    }
});