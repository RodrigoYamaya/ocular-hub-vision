document.addEventListener('DOMContentLoaded', async () => {

    const btnImprimir = document.getElementById('btn-imprimir');
    if (btnImprimir) {
        btnImprimir.addEventListener('click', () => {
            window.print();
        });
    }

    const params = new URLSearchParams(window.location.search);
    const idExame = params.get('id');

    if (idExame) {
        try {
            const resposta = await fetch(`http://localhost:8080/exames/${idExame}`);

            if (!resposta.ok) throw new Error("Exame não encontrado");

            const exame = await resposta.json();

            document.getElementById('titulo-laudo').innerText = exame.titulo;
            document.getElementById('diagnostico-ia').innerText = exame.diagnosticoIa;
            document.getElementById('precisao-ia').innerText = exame.precisaoIa + "%";
            document.getElementById('data-registro').innerText = new Date(exame.dataRegistro).toLocaleDateString();


        } catch (error) {
            console.error("Erro ao carregar dados:", error);
            alert("Não foi possível carregar os dados do relatório.");
        }
    }
});