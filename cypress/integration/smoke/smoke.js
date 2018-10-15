context('isolating-the-domain', () => {
  beforeEach(() => {
    cy.visit('http://localhost:8080/')
  })
  it('Smoke test', () => {
	//利用者一覧が表示されている
	cy.get('.header').should('text', '利用者一覧')
	//件数退避して追加後の件数と比較したかったけど退避方法不明
//	var lineCount = cy.get('tbody > tr')
//	assert.equal(lineCount, 6, '行数が合ってません')
	//利用者登録に遷移
	cy.get('a[href="/user/register"]').click()
	cy.get('.header').should('text', '利用者の新規登録')
	//何も入力せずにバリデーションが走っていることを確認
	cy.get('button').should('text', '確認する').click()
	cy.get('div.error').should((errorDivs) =>{
		expect(errorDivs.length).to.not.equal(0)
	})
	cy.get('#identifier\\.value').type('hoge@example.com')
	cy.get('#name\\.value').type('テスト太郎')
	cy.get('#dateOfBirth\\.value').type('1974-12-31')
	cy.get('#phoneNumber\\.value').type('090-1234-5678')
	cy.get('#gender\\.value1').click()
	cy.get('button').should('text', '確認する').click()
	cy.get('.header').should('text', '利用者登録の確認')
	cy.get('a[href="/user/register/register"]').click()
	cy.get('.header').should('text', '利用者登録の完了')
	cy.get('a[href="/"]').click()
	cy.get('.header').should('text', '利用者一覧')
  })
})


