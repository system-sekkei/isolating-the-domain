INSERT INTO 給与.ユーザー
(user_id, gender)
VALUES
 ('fukawa_teruyoshi@example.com', '男性'),
 ('kuriyama_yuino@example.com', '女性'),
 ('fujimura_kaoru@example.com', '男性'),
 ('ijuuin_ken@example.com', '男性'),
 ('yamato_michiko@example.com', '女性'),
 ('miyake_yukiya@example.com', '女性');

 -- ユーザ名
 INSERT INTO 給与.ユーザー名
 (ユーザー名ID, ユーザーID, 登録日時, ユーザー名)
 values
 (1, 'fukawa_teruyoshi@example.com', now(), '布川 光良'),
 (2, 'kuriyama_yuino@example.com', now(), '栗山 友以乃'),
 (3, 'fujimura_kaoru@example.com', now(), '藤村 薫'),
 (4, 'ijuuin_ken@example.com', now(), '伊集院 建'),
 (5, 'yamato_michiko@example.com', now(), '大和　路子'),
 (6, 'miyake_yukiya@example.com', now(), '三宅 有起子')
 ;
 INSERT INTO 給与.ユーザー名
 (ユーザー名ID, ユーザーID, 登録日時, ユーザー名)
 values(7, 'fukawa_teruyoshi@example.com', now(), '布川 光義')
 ;
 
 INSERT INTO 給与.ユーザー名対応表
 (ユーザーID, ユーザー名ID)
values
 ('fukawa_teruyoshi@example.com', 7),
 ('kuriyama_yuino@example.com', 2),
 ('fujimura_kaoru@example.com', 3),
 ('ijuuin_ken@example.com', 4),
 ('yamato_michiko@example.com', 5),
 ('miyake_yukiya@example.com', 6)
 ;

 -- 電話番号
 INSERT INTO 給与.ユーザー電話番号
 (ユーザー電話番号ID, ユーザーID, 登録日時, 電話番号)
 values
 (1, 'fukawa_teruyoshi@example.com', now(), '03-1234-5678'),
 (2, 'kuriyama_yuino@example.com', now(), '03-1234-5678'),
 (3, 'fujimura_kaoru@example.com', now(), '03-1234-5678'),
 (4, 'ijuuin_ken@example.com', now(), '03-1234-5678'),
 (5, 'yamato_michiko@example.com', now(), '03-1234-5678'),
 (6, 'miyake_yukiya@example.com', now(), '03-1234-5678')
 ;
 INSERT INTO 給与.ユーザー電話番号
 (ユーザー電話番号ID, ユーザーID, 登録日時, 電話番号)
 values(7, 'fukawa_teruyoshi@example.com', now(), '03-1234-9999')
 ;
 
 INSERT INTO 給与.ユーザー電話番号対応表
 (ユーザーID, ユーザー電話番号ID)
values
 ('fukawa_teruyoshi@example.com', 7),
 ('kuriyama_yuino@example.com', 2),
 ('fujimura_kaoru@example.com', 3),
 ('ijuuin_ken@example.com', 4),
 ('yamato_michiko@example.com', 5),
 ('miyake_yukiya@example.com', 6)
 ;
 
 -- 誕生日
 INSERT INTO 給与.ユーザー誕生日
 (ユーザー誕生日ID, ユーザーID, 登録日時, 誕生日)
 values
 (1, 'fukawa_teruyoshi@example.com', now(), '1988-01-01'),
 (2, 'kuriyama_yuino@example.com', now(), '1988-03-03'),
 (3, 'fujimura_kaoru@example.com', now(), '1988-05-05'),
 (4, 'ijuuin_ken@example.com', now(), '1988-07-07'),
 (5, 'yamato_michiko@example.com', now(), '1988-09-09'),
 (6, 'miyake_yukiya@example.com', now(), '1988-12-31')
 ;
 INSERT INTO 給与.ユーザー誕生日
 (ユーザー誕生日ID, ユーザーID, 登録日時, 誕生日)
 values(7, 'fukawa_teruyoshi@example.com', now(), '1988-02-29')
 ;
 
 INSERT INTO 給与.ユーザー誕生日対応表
 (ユーザーID, ユーザー誕生日ID)
values
 ('fukawa_teruyoshi@example.com', 7),
 ('kuriyama_yuino@example.com', 2),
 ('fujimura_kaoru@example.com', 3),
 ('ijuuin_ken@example.com', 4),
 ('yamato_michiko@example.com', 5),
 ('miyake_yukiya@example.com', 6)
 ;
 
 -- メールアドレス
 INSERT INTO 給与.ユーザーメールアドレス
 (ユーザーメールアドレスID, ユーザーID, 登録日時, メールアドレス)
 values
 (1, 'fukawa_teruyoshi@example.com', now(), 'fukawa_teruyoshi@example.com'),
 (2, 'kuriyama_yuino@example.com', now(), 'kuriyama_yuino@example.com'),
 (3, 'fujimura_kaoru@example.com', now(), 'fujimura_kaoru@example.com'),
 (4, 'ijuuin_ken@example.com', now(), 'ijuuin_ken@example.com'),
 (5, 'yamato_michiko@example.com', now(), 'yamato_michiko@example.com'),
 (6, 'miyake_yukiya@example.com', now(), 'miyake_yukiya@example.com')
 ;
 INSERT INTO 給与.ユーザーメールアドレス
 (ユーザーメールアドレスID, ユーザーID, 登録日時, メールアドレス)
 values(7, 'fukawa_teruyoshi@example.com', now(), 'fukawa_teruyoshi_new@example.com')
 ;
 
 INSERT INTO 給与.ユーザーメールアドレス対応表
 (ユーザーID, ユーザーメールアドレスID)
values
 ('fukawa_teruyoshi@example.com', 7),
 ('kuriyama_yuino@example.com', 2),
 ('fujimura_kaoru@example.com', 3),
 ('ijuuin_ken@example.com', 4),
 ('yamato_michiko@example.com', 5),
 ('miyake_yukiya@example.com', 6)
 ;
