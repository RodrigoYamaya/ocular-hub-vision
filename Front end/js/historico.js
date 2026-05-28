document.addEventListener('DOMContentLoaded', () => {
    
    const inputBusca = document.getElementById('input-busca-historico');
    const selectStatus = document.getElementById('filtro-status');
    const linhasHistorico = document.querySelectorAll('.linha-historico');
    const mensagemVazia = document.getElementById('mensagem-vazia-historico');

    if (!inputBusca || !selectStatus || linhasHistorico.length === 0) return;

    // Função central que aplica os dois filtros simultaneamente
    function aplicarFiltros() {
        const termoBusca = inputBusca.value.trim().toLowerCase();
        const statusSelecionado = selectStatus.value;
        let encontrouAlgum = false;

        linhasHistorico.forEach(linha => {
            // Coleta todo o texto da linha para uma busca profunda
            const textoLinha = linha.textContent.toLowerCase();
            // Pega a gravidade escondida no data-attribute do HTML
            const statusLinha = linha.getAttribute('data-status');

            // Verifica se o texto bate
            const matchTexto = textoLinha.includes(termoBusca);
            // Verifica se o dropdown bate
            const matchStatus = (statusSelecionado === 'todos') || (statusSelecionado === statusLinha);

            // Só mostra a linha se passar nos DOIS testes
            if (matchTexto && matchStatus) {
                linha.style.display = ''; 
                encontrouAlgum = true;
            } else {
                linha.style.display = 'none'; 
            }
        });

        // Controle da mensagem vazia
        if (!encontrouAlgum) {
            mensagemVazia.style.display = 'block';
        } else {
            mensagemVazia.style.display = 'none';
        }
    }

    // Escuta tanto a digitação quanto a mudança no menu de diagnóstico
    inputBusca.addEventListener('keyup', aplicarFiltros);
    selectStatus.addEventListener('change', aplicarFiltros);
});