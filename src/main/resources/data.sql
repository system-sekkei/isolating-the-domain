INSERT INTO 給与.ユーザー
(user_id, name, phone_number, date_of_birth, gender)
VALUES
 ('fukawa_teruyoshi@example.com', '布川 光良', '03-1234-5678','1988-01-01','男性'),
 ('kuriyama_yuino@example.com', '栗山 友以乃', '03-1234-5678','1988-03-03','女性'),
 ('fujimura_kaoru@example.com', '藤村 薫', '03-1234-5678','1988-05-05','男性'),
 ('ijuuin_ken@example.com', '伊集院 建', '03-1234-5678','1988-07-07','男性'),
 ('yamato_michiko@example.com', '大和 路子', '03-1234-5678','1988-09-09','女性'),
 ('miyake_yukiya@example.com', '三宅 有起子', '03-1234-5678','1988-12-31','女性');

 INSERT INTO 給与.users_mail_address
 (user_id, register_date, mail_address)
 values
 ('fukawa_teruyoshi@example.com', now(), 'fukawa_teruyoshi@example.com'),
 ('kuriyama_yuino@example.com', now(), 'kuriyama_yuino@example.com'),
 ('fujimura_kaoru@example.com', now(), 'fujimura_kaoru@example.com'),
 ('ijuuin_ken@example.com', now(), 'ijuuin_ken@example.com'),
 ('yamato_michiko@example.com', now(), 'yamato_michiko@example.com'),
 ('miyake_yukiya@example.com', now(), 'miyake_yukiya@example.com')
 ;
  INSERT INTO 給与.users_mail_address
 (user_id, register_date, mail_address)
 values('fukawa_teruyoshi@example.com', now(), 'fukawa_teruyoshi_new@example.com')
 ;
