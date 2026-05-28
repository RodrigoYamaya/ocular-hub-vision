document.addEventListener('DOMContentLoaded', () => {
    
    const btnAbrirModal = document.getElementById('btn-abrir-modal');
    const btnFecharModal = document.getElementById('btn-fechar-modal');
    const modalCadastro = document.getElementById('modal-cadastro');
    const btnSalvar = document.getElementById('btn-salvar-paciente');

    if (btnAbrirModal && modalCadastro) {
        btnAbrirModal.addEventListener('click', () => {
            modalCadastro.style.display = 'flex';
        });
    }

    if (btnFecharModal && modalCadastro) {
        btnFecharModal.addEventListener('click', () => {
            modalCadastro.style.display = 'none';
        });
    }

    window.addEventListener('click', (event) => {
        if (event.target === modalCadastro) {
            modalCadastro.style.display = 'none';
        }
    });

    if (btnSalvar) {
        btnSalvar.addEventListener('click', () => {
            alert('Paciente cadastrado com sucesso!');
            if (modalCadastro) modalCadastro.style.display = 'none';
        });
    }

    
    const inputBusca = document.getElementById('input-busca');
    const linhasPacientes = document.querySelectorAll('.linha-paciente');
    const mensagemVazia = document.getElementById('mensagem-vazia');

    if (inputBusca && linhasPacientes.length > 0) {
        
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
    }
});