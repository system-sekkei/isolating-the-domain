context('isolating-the-domain', () => {
  beforeEach(() => {
    cy.visit('http://localhost:8080/')
  })
  it('Smoke test', () => {
	cy.title().should('eq', 'ダッシュボード')

    cy.get('.button').contains('従業員').click()
    cy.title().should('contains', '従業員一覧')

    // 従業員登録
	cy.contains('従業員の新規登録').click()
	cy.title().should('contains', '従業員の新規登録')
	//何も入力せずにバリデーションが走っていることを確認
	cy.get('.button').contains('確認する').click()
	cy.get('div.error').should((errorDivs) =>{
		expect(errorDivs.length).to.not.equal(0)
	})
	cy.get('#mailAddress\\.value').type('test-taro@example.com')
	cy.get('#name\\.value').type('テスト太郎')
	cy.get('#phoneNumber\\.value').type('090-1234-5678')
	cy.get('.button').contains('確認する').click()
	cy.title().should('contains', '確認')
	cy.get('.button').contains('登録する').click()
	cy.title().should('contains', '完了')
	cy.get('a').contains('従業員一覧へ').click()
    cy.title().should('eq', '従業員一覧')

    // 従業員更新
	cy.get('tbody > tr > td').contains('テスト太郎').parent().within(() => {
		cy.get('.button').contains('変更').click()
	})
	cy.title().should('contains', '変更')
	cy.get('#name\\.value').clear().type('テスト次郎')
	cy.get('.button').contains('編集の確認').click()
	cy.title().should('contains', '確認')
	cy.get('.button').contains('保存する').click()
	cy.get('a').contains('従業員一覧へ').click()
    cy.title().should('eq', '従業員一覧')

    cy.get('.button').contains('ダッシュボード').click()
    cy.title().should('eq', 'ダッシュボード')

	// 勤務時間入力
	cy.get('.button').contains('勤務時間入力').click()
	cy.title().should('contains', '入力')
	cy.get('#startHour\\.value').type('9')
	cy.get('#startMinute\\.value').type('30')
	cy.get('#endHour\\.value').type('18')
	cy.get('#endMinute\\.value').type('00')
	cy.get('#breaks\\.value').type('90')
	cy.get('.button').contains('登録する').click()

	// TODO 登録後の画面遷移

    //cy.get('.button').contains('ダッシュボード').click()
    //cy.title().should('eq', 'ダッシュボード')

    // 給与計算
    cy.get('.button').contains('給与計算').click()
    cy.title().should('contains', '従業員一覧')
	cy.get('tbody > tr > td').contains('テスト次郎').parent().within(() => {
		cy.get('.button').contains('勤務時間').click()
	})
    cy.title().should('contains', '一覧')
	cy.get('a').contains('一覧に戻る').click()
    cy.title().should('contains', '従業員一覧')

    // 契約終了
    cy.get('.button').contains('ダッシュボード').click()
    cy.title().should('eq', 'ダッシュボード')

    cy.get('.button').contains('従業員').click()
    cy.title().should('contains', '従業員一覧')

	cy.get('tbody > tr > td').contains('テスト次郎').parent().within(() => {
		cy.get('.button').contains('変更').click()
	})
    cy.title().should('contains', '変更')
	cy.get('.button').contains('削除の確認').click()
    cy.title().should('contains', '確認')
	cy.get('.button').contains('削除').click()
	cy.contains('従業員一覧へ').click()
    cy.title().should('contains', '従業員一覧')
	})
})


