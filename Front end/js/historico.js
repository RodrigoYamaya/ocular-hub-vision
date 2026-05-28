document.addEventListener('DOMContentLoaded', () => {
    
    const inputBusca = document.getElementById('input-busca-historico');
    const selectStatus = document.getElementById('filtro-status');
    const linhasHistorico = document.querySelectorAll('.linha-historico');
    const mensagemVazia = document.getElementById('mensagem-vazia-historico');

    if (!inputBusca || !selectStatus || linhasHistorico.length === 0) return;

    function aplicarFiltros() {
        const termoBusca = inputBusca.value.trim().toLowerCase();
        const statusSelecionado = selectStatus.value;
        let encontrouAlgum = false;

        linhasHistorico.forEach(linha => {
            const textoLinha = linha.textContent.toLowerCase();
            const statusLinha = linha.getAttribute('data-status');

            const matchTexto = textoLinha.includes(termoBusca);
            const matchStatus = (statusSelecionado === 'todos') || (statusSelecionado === statusLinha);

            if (matchTexto && matchStatus) {
                linha.style.display = ''; 
                encontrouAlgum = true;
            } else {
                linha.style.display = 'none'; 
            }
        });

        if (!encontrouAlgum) {
            mensagemVazia.style.display = 'block';
        } else {
            mensagemVazia.style.display = 'none';
        }
    }

    inputBusca.addEventListener('keyup', aplicarFiltros);
    selectStatus.addEventListener('change', aplicarFiltros);
});