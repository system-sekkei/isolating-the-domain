context('isolating-the-domain', () => {
  beforeEach(() => {
    cy.visit('http://localhost:8080/')
  })
  it('Smoke test', () => {
	//利用者一覧が表示されている
	cy.get('.header').should('text', '利用者一覧')
	const id = 'hoge-' +  new Date().getTime() + '@example.com'
	//利用者登録に遷移
	cy.contains('利用者の新規登録').click()
	cy.get('.header').should('text', '利用者の新規登録')
	//何も入力せずにバリデーションが走っていることを確認
	cy.get('.button').contains('確認する').click()
	cy.get('div.error').should((errorDivs) =>{
		expect(errorDivs.length).to.not.equal(0)
	})
	cy.get('#mailAddress\\.value').type(id)
	cy.get('#name\\.value').type('テスト太郎')
	cy.get('#dateOfBirth\\.value').type('1974-12-31')
	cy.get('#phoneNumber\\.value').type('090-1234-5678')
	cy.get('#gender\\.value1').click()
	cy.get('.button').contains('確認する').click()
	cy.get('.header').should('text', '利用者登録の確認')
	cy.get('.button').contains('登録する').click()
	cy.get('.header').should('text', '利用者登録の完了')
	cy.get('a').contains('利用者一覧へ').click()
	cy.get('.header').should('text', '利用者一覧')
	//更新処理
	cy.get('tbody > tr > td').contains(id).parent().within(() => {
		cy.get('.button').contains('変更').click()
	})
	cy.get('.header').should('text', '利用者情報の変更')
	cy.get('#name\\.value').clear().type('テスト次郎')
	cy.get('.button').contains('編集の確認').click()
	cy.get('.header').should('text', '利用者情報の変更の確認')
	cy.get('.button').contains('保存する').click()
	cy.get('a').contains('利用者一覧へ').click()
	cy.get('.header').should('text', '利用者一覧')

	// 勤務時間入力
	cy.get('.button').contains('勤務時間入力').click()
	cy.get('.header').should('text', '勤務時間の入力')
	cy.get('#startHour\\.value').type('9')
	cy.get('#startMinute\\.value').type('30')
	cy.get('#endHour\\.value').type('18')
	cy.get('#endMinute\\.value').type('00')
	cy.get('#breaks\\.value').type('90')
	cy.get('.button').contains('登録する').click()
	cy.get('.header').should('text', '利用者一覧')

	// 勤務時間一覧
	cy.get('tbody > tr > td').contains(id).parent().within(() => {
		cy.get('.button').get('.calendar').click()
	})
	cy.get('a').contains('一覧に戻る').click()
	cy.get('.header').should('text', '利用者一覧')

	//削除
	cy.get('tbody > tr > td').contains(id).parent().within(() => {
		cy.get('.button').contains('変更').click()
	})
	cy.get('.header').should('text', '利用者情報の変更')
	cy.get('.button').contains('削除の確認').click()
	cy.get('.header').should('text', '削除の確認')
	cy.get('.button').contains('削除').click()
	cy.contains('利用者一覧へ').click()
	cy.get('.header').should('text', '利用者一覧')
	})
})


