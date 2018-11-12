INSERT INTO 給与.ユーザー(ユーザーID)
VALUES
 (1),
 (2),
 (3),
 (4),
 (5),
 (6)
 ;

 -- ユーザ名
 INSERT INTO 給与.ユーザー名
 (ユーザー名ID, ユーザーID, ユーザー名)
 values
 (1, 1, '布川 光良'),
 (2, 2, '栗山 友以乃'),
 (3, 3, '藤村 薫'),
 (4, 4, '伊集院 建'),
 (5, 5, '大和　路子'),
 (6, 6, '三宅 有起子')
 ;
 INSERT INTO 給与.ユーザー名
 (ユーザー名ID, ユーザーID, ユーザー名)
 values(7, 1, '布川 光義')
 ;
 
 INSERT INTO 給与.ユーザー名対応表
 (ユーザーID, ユーザー名ID)
values
 (1, 7),
 (2, 2),
 (3, 3),
 (4, 4),
 (5, 5),
 (6, 6)
 ;

 -- 電話番号
 INSERT INTO 給与.ユーザー電話番号
 (ユーザー電話番号ID, ユーザーID, 電話番号)
 values
 (1, 1, '03-1234-5678'),
 (2, 2, '03-1234-5678'),
 (3, 3, '03-1234-5678'),
 (4, 4, '03-1234-5678'),
 (5, 5, '03-1234-5678'),
 (6, 6, '03-1234-5678')
 ;
 INSERT INTO 給与.ユーザー電話番号
 (ユーザー電話番号ID, ユーザーID, 電話番号)
 values(7, 1, '03-1234-9999')
 ;
 
 INSERT INTO 給与.ユーザー電話番号対応表
 (ユーザーID, ユーザー電話番号ID)
values
 (1, 7),
 (2, 2),
 (3, 3),
 (4, 4),
 (5, 5),
 (6, 6)
 ;
 
 -- メールアドレス
 INSERT INTO 給与.ユーザーメールアドレス
 (ユーザーメールアドレスID, ユーザーID, メールアドレス)
 values
 (1, 1, 'fukawa_teruyoshi@example.com'),
 (2, 2, 'kuriyama_yuino@example.com'),
 (3, 3, 'fujimura_kaoru@example.com'),
 (4, 4, 'ijuuin_ken@example.com'),
 (5, 5, 'yamato_michiko@example.com'),
 (6, 6, 'miyake_yukiya@example.com')
 ;
 INSERT INTO 給与.ユーザーメールアドレス
 (ユーザーメールアドレスID, ユーザーID, メールアドレス)
 values(7, 1, 'fukawa_teruyoshi_new@example.com')
 ;
 
 INSERT INTO 給与.ユーザーメールアドレス対応表
 (ユーザーID, ユーザーメールアドレスID)
values
 (1, 7),
 (2, 2),
 (3, 3),
 (4, 4),
 (5, 5),
 (6, 6)
 ;

ALTER SEQUENCE 給与.ユーザーID RESTART WITH 100;
ALTER SEQUENCE 給与.ユーザー名ID RESTART WITH 100;
ALTER SEQUENCE 給与.ユーザー電話番号ID RESTART WITH 100;
ALTER SEQUENCE 給与.ユーザーメールアドレスID RESTART WITH 100;
