context('isolating-the-domain', () => {
  beforeEach(() => {
    cy.visit('http://localhost:8080/')
  })
  it('Smoke test', () => {
	cy.get('.header').should('text', '利用者一覧')
  })
})


