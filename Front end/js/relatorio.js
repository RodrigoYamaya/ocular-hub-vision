document.addEventListener('DOMContentLoaded', () => {
    
    const btnImprimir = document.getElementById('btn-imprimir');

    if (btnImprimir) {
        btnImprimir.addEventListener('click', () => {
            window.print();
        });
    }

});