document.addEventListener('DOMContentLoaded', () => {
    
    const inputBusca = document.getElementById('input-busca');
    const linhasPacientes = document.querySelectorAll('.linha-paciente');
    const mensagemVazia = document.getElementById('mensagem-vazia');

    if (!inputBusca || linhasPacientes.length === 0) return;

    inputBusca.addEventListener('keyup', function() {
        
        const termoBusca = inputBusca.value.trim().toLowerCase();
        let encontrouAlguem = false;

        linhasPacientes.forEach(linha => {
            const nomePaciente = linha.querySelector('.nome-paciente').textContent.toLowerCase();
            const idPaciente = linha.querySelector('.id-cell').textContent.toLowerCase();

            if (nomePaciente.includes(termoBusca) || idPaciente.includes(termoBusca)) {
                linha.style.display = ''; 
                encontrouAlguem = true;
            } else {
                linha.style.display = 'none'; 
            }
        });

        if (!encontrouAlguem) {
            mensagemVazia.style.display = 'block';
        } else {
            mensagemVazia.style.display = 'none';
        }
    });
});