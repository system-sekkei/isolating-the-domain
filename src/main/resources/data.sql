INSERT INTO 給与.従業員(従業員ID)
VALUES
 (1),
 (2),
 (3),
 (4),
 (5),
 (6)
 ;

 -- ユーザ名
 INSERT INTO 給与.従業員の名前履歴
 (従業員名ID, 従業員ID, 従業員名)
 values
 (1, 1, '布川 光良'),
 (2, 2, '栗山 友以乃'),
 (3, 3, '藤村 薫'),
 (4, 4, '伊集院 建'),
 (5, 5, '大和　路子'),
 (6, 6, '三宅 有起子')
 ;
 INSERT INTO 給与.従業員の名前履歴
 (従業員名ID, 従業員ID, 従業員名)
 values(7, 1, '布川 光義')
 ;
 
 INSERT INTO 給与.従業員の名前
 (従業員ID, 従業員名)
values
 (1, '布川 光義'),
 (2, '栗山 友以乃'),
 (3, '藤村 薫'),
 (4, '伊集院 建'),
 (5, '大和　路子'),
 (6, '三宅 有起子')
 ;

 -- 電話番号
 INSERT INTO 給与.従業員の電話番号履歴
 (従業員電話番号ID, 従業員ID, 電話番号)
 values
 (1, 1, '03-1234-5678'),
 (2, 2, '03-1234-5678'),
 (3, 3, '03-1234-5678'),
 (4, 4, '03-1234-5678'),
 (5, 5, '03-1234-5678'),
 (6, 6, '03-1234-5678')
 ;
 INSERT INTO 給与.従業員の電話番号履歴
 (従業員電話番号ID, 従業員ID, 電話番号)
 values(7, 1, '03-1234-9999')
 ;
 
 INSERT INTO 給与.従業員の電話番号
 (従業員ID, 電話番号)
values
 (1, '03-1234-9999'),
 (2, '03-1234-5678'),
 (3, '03-1234-5678'),
 (4, '03-1234-5678'),
 (5, '03-1234-5678'),
 (6, '03-1234-5678')
 ;
 
 -- メールアドレス
 INSERT INTO 給与.従業員のメールアドレス履歴
 (従業員メールアドレスID, 従業員ID, メールアドレス)
 values
 (1, 1, 'fukawa_teruyoshi@example.com'),
 (2, 2, 'kuriyama_yuino@example.com'),
 (3, 3, 'fujimura_kaoru@example.com'),
 (4, 4, 'ijuuin_ken@example.com'),
 (5, 5, 'yamato_michiko@example.com'),
 (6, 6, 'miyake_yukiya@example.com')
 ;
 INSERT INTO 給与.従業員のメールアドレス履歴
 (従業員メールアドレスID, 従業員ID, メールアドレス)
 values(7, 1, 'fukawa_teruyoshi_new@example.com')
 ;
 
 INSERT INTO 給与.従業員のメールアドレス
 (従業員ID, メールアドレス)
values
 (1, 'fukawa_teruyoshi_new@example.com'),
 (2, 'kuriyama_yuino@example.com'),
 (3, 'fujimura_kaoru@example.com'),
 (4, 'ijuuin_ken@example.com'),
 (5, 'yamato_michiko@example.com'),
 (6, 'miyake_yukiya@example.com')
 ;

INSERT INTO 給与.契約中(従業員ID) values(1),(2),(3),(4),(5),(6);

ALTER SEQUENCE 給与.従業員ID RESTART WITH 100;
ALTER SEQUENCE 給与.従業員名ID RESTART WITH 100;
ALTER SEQUENCE 給与.従業員電話番号ID RESTART WITH 100;
ALTER SEQUENCE 給与.従業員メールアドレスID RESTART WITH 100;
