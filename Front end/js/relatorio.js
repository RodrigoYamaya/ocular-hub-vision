document.addEventListener('DOMContentLoaded', () => {
    
    const btnImprimir = document.getElementById('btn-imprimir');

    if (btnImprimir) {
        btnImprimir.addEventListener('click', () => {
            // Chama a função nativa de impressão do navegador
            window.print();
        });
    }

});