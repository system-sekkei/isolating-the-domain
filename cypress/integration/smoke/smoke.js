context('isolating-the-domain', () => {
  beforeEach(() => {
    cy.visit('http://localhost:8080/')
  })
  it('Smoke test', () => {
    cy.title().should('eq', 'ダッシュボード')

    cy.get('a').contains('従業員').click()
    cy.title().should('contains', '従業員の一覧')

    // 従業員登録
    cy.contains('従業員の新規登録').click()
    cy.title().should('contains', '従業員情報の新規登録')
    //何も入力せずにバリデーションが走っていることを確認
    cy.get('.button').contains('確認').click()
    cy.get('div.error').should((errorDivs) =>{
        expect(errorDivs.length).to.not.equal(0)
    })
    cy.get('#mailAddress\\.value').type('test-taro@example.com')
    cy.get('#name\\.value').type('テスト太郎')
    cy.get('#phoneNumber\\.value').type('090-1234-5678')
    cy.get('.button').contains('確認').click()
    cy.title().should('contains', '確認')
    cy.get('.button').contains('登録').click()
    cy.title().should('contains', '完了')
    cy.get('a').contains('従業員の一覧').click()
    cy.title().should('eq', '従業員の一覧')

    // 従業員詳細
    cy.get('tbody > tr > td > a').contains('テスト太郎').click()
    cy.title().should('contains', '詳細')

    // 従業員変更
    cy.get('.button').contains('変更').click()
    cy.title().should('contains', '変更')
    cy.get('#name\\.value').clear().type('テスト次郎')
    cy.get('.button').contains('保存').click()
    cy.title().should('contains', '詳細')
    cy.get('.button').contains('ＯＫ').click()

    // 時給の変遷
    cy.get('.button').contains('時給の履歴').click()
    cy.title().should('contains', '時給の履歴')

    // 時給の登録
    cy.get('.button').contains('登録').click()
    cy.title().should('contains', '時給の登録')
    cy.get('.button').contains('戻る').click()
    cy.title().should('contains', '時給の履歴')
    cy.get('.button').contains('戻る').click()
    cy.title().should('contains', '詳細')

    cy.get('a').contains('ダッシュボード').click()
    cy.title().should('eq', 'ダッシュボード')

    // 勤務時間入力
    cy.get('a').contains('勤務時間の入力').click()
    cy.title().should('contains', '入力')
    cy.get('.button').contains('登録').click()
    cy.title().should('contains', '勤務時間の一覧')

    cy.get('a').contains('ダッシュボード').click()
    cy.title().should('eq', 'ダッシュボード')

    // 給与
    cy.get('a').contains('給与').click()
    cy.title().should('contains', '給与の一覧')
    cy.get('tbody > tr > td ').contains('テスト次郎').parent().within(() => {
        cy.get('.button').contains('勤務時間').click()
    })
    cy.title().should('contains', '一覧')
    cy.get('a').contains('給与の一覧').click()
    cy.title().should('contains', '給与の一覧')

    // 契約終了
    cy.get('a').contains('ダッシュボード').click()
    cy.title().should('eq', 'ダッシュボード')

    cy.get('a').contains('従業員').click()
    cy.title().should('contains', '従業員の一覧')

    //従業員削除
    cy.get('tbody > tr > td > a').contains('テスト次郎').click()
    cy.title().should('contains', '詳細')
    cy.get('#deleteConfirmButton').click()
    cy.get('#deleteButton').click()
    cy.title().should('contains', '従業員の一覧')
    cy.get('tbody > tr > td > a').contains('テスト次郎').should('not.exist')
})
})


