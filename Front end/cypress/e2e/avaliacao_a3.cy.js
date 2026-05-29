describe('Garantia de Qualidade - OcularHub Vision (A3)', () => {

    beforeEach(() => {
        // O caminho que cypress precisa achar minha landing page.
        cy.visit('http://127.0.0.1:5500/Front%20end/pages/home.html'); 
    });

    it('Requisito 2: Valida Responsividade na Landing Page (Mobile)', () => {
        cy.viewport('iphone-x');

        cy.get('.video-responsivo')
          .should('be.visible')
          .and(($video) => {
              expect($video.width()).to.be.at.most(375); 
          });

        cy.get('header.navbar').should('be.visible');
        cy.get('.btn-main').first().should('be.visible');
    });

    it('Requisito 4: Valida Interação JavaScript no Fluxo de Avaliação', () => {
        cy.viewport(1280, 720);

        cy.get('header nav a').contains('Acessar Sistema').click();
        cy.url().should('include', 'login.html');

        cy.contains('button', 'Acesso Rápido').click();

        cy.get('#mensagem')
          .should('be.visible')
          .and('contain.text', 'Acesso de avaliação liberado');

        // aqui vai passar direto pelo login com entrada automatica e vai para index.html.
        cy.url().should('include', 'index.html');
    });

    it('Requisito 2: Valida Responsividade da Tabela de Pacientes (Mobile)', () => {
        // Aqui vamos  Ajustar o caminho da tela de pacientes.
        cy.visit('http://127.0.0.1:5500/Front%20end/pages/pacientes.html');
        
        cy.viewport('iphone-x');

        cy.get('#input-busca').should('be.visible');
        cy.get('.tabela-dados').should('be.visible');
    });

    it('Requisito de Usabilidade: Valida o retorno à Landing Page clicando no Logo', () => {
        cy.visit('http://127.0.0.1:5500/Front%20end/pages/pacientes.html');
        
        cy.get('.logo a').click();

        cy.url().should('include', 'home.html');
    });


    it('Requisito 4: Valida Fluxo de Acesso e Funcionalidade do Botão Principal', () => {
        cy.visit('http://127.0.0.1:5500/Front%20end/pages/home.html'); 

        cy.get('header nav a').contains('Acessar Sistema').click();

        cy.contains('button', 'Acesso Rápido').click();

        cy.url().should('include', 'index.html');

        cy.get('#btnAnalisar')
          .should('be.visible')
          .and('contain.text', 'Iniciar Análise'); 
    });
});