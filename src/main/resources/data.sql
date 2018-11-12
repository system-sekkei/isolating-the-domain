INSERT INTO 給与.ユーザー(ユーザーID, 登録日時)
VALUES
 (1, now()),
 (2, now()),
 (3, now()),
 (4, now()),
 (5, now()),
 (6, now())
 ;

 -- ユーザ名
 INSERT INTO 給与.ユーザー名
 (ユーザー名ID, ユーザーID, 登録日時, ユーザー名)
 values
 (1, 1, now(), '布川 光良'),
 (2, 2, now(), '栗山 友以乃'),
 (3, 3, now(), '藤村 薫'),
 (4, 4, now(), '伊集院 建'),
 (5, 5, now(), '大和　路子'),
 (6, 6, now(), '三宅 有起子')
 ;
 INSERT INTO 給与.ユーザー名
 (ユーザー名ID, ユーザーID, 登録日時, ユーザー名)
 values(7, 1, now(), '布川 光義')
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
 (ユーザー電話番号ID, ユーザーID, 登録日時, 電話番号)
 values
 (1, 1, now(), '03-1234-5678'),
 (2, 2, now(), '03-1234-5678'),
 (3, 3, now(), '03-1234-5678'),
 (4, 4, now(), '03-1234-5678'),
 (5, 5, now(), '03-1234-5678'),
 (6, 6, now(), '03-1234-5678')
 ;
 INSERT INTO 給与.ユーザー電話番号
 (ユーザー電話番号ID, ユーザーID, 登録日時, 電話番号)
 values(7, 1, now(), '03-1234-9999')
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
 (ユーザーメールアドレスID, ユーザーID, 登録日時, メールアドレス)
 values
 (1, 1, now(), 'fukawa_teruyoshi@example.com'),
 (2, 2, now(), 'kuriyama_yuino@example.com'),
 (3, 3, now(), 'fujimura_kaoru@example.com'),
 (4, 4, now(), 'ijuuin_ken@example.com'),
 (5, 5, now(), 'yamato_michiko@example.com'),
 (6, 6, now(), 'miyake_yukiya@example.com')
 ;
 INSERT INTO 給与.ユーザーメールアドレス
 (ユーザーメールアドレスID, ユーザーID, 登録日時, メールアドレス)
 values(7, 1, now(), 'fukawa_teruyoshi_new@example.com')
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
