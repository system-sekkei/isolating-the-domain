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
 (従業員ID, 従業員名ID, 従業員名)
values
 (1, 7, '布川 光義'),
 (2, 2, '栗山 友以乃'),
 (3, 3, '藤村 薫'),
 (4, 4, '伊集院 建'),
 (5, 5, '大和　路子'),
 (6, 6, '三宅 有起子')
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
 (従業員ID, 従業員電話番号ID, 電話番号)
values
 (1, 7, '03-1234-9999'),
 (2, 2, '03-1234-5678'),
 (3, 3, '03-1234-5678'),
 (4, 4, '03-1234-5678'),
 (5, 5, '03-1234-5678'),
 (6, 6, '03-1234-5678')
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
 (従業員ID, 従業員メールアドレスID, メールアドレス)
values
 (1, 7, 'fukawa_teruyoshi_new@example.com'),
 (2, 2, 'kuriyama_yuino@example.com'),
 (3, 3, 'fujimura_kaoru@example.com'),
 (4, 4, 'ijuuin_ken@example.com'),
 (5, 5, 'yamato_michiko@example.com'),
 (6, 6, 'miyake_yukiya@example.com')
 ;

INSERT INTO 給与.契約中(従業員ID) values(1),(2),(3),(4),(5),(6);

-- 時給
INSERT INTO 給与.時給契約履歴(時給ID, 従業員ID, 時給, 適用開始日, 時間外割増率, 深夜割増率) VALUES
(1, 1, 950, DATEADD('DAY', -60, CURRENT_DATE), 25, 35),
(2, 2, 950, DATEADD('DAY', -60, CURRENT_DATE), 25, 35),
(3, 3, 950, DATEADD('DAY', -60, CURRENT_DATE), 25, 35),
-- 時給登録なし
-- (4, 4, 950, DATEADD('DAY', -60, CURRENT_DATE), 25, 35),
-- (5, 5, 950, DATEADD('DAY', -60, CURRENT_DATE), 25, 35),
(6, 6, 950, DATEADD('DAY', -60, CURRENT_DATE), 25, 35);

INSERT INTO 給与.時給契約(従業員ID, 適用開始日, 時給, 時間外割増率, 深夜割増率) VALUES
(1, DATEADD('DAY', -60, CURRENT_DATE), 950, 25, 35),
(2, DATEADD('DAY', -60, CURRENT_DATE), 950, 25, 35),
(3, DATEADD('DAY', -60, CURRENT_DATE), 950, 25, 35),
-- 時給登録なし
-- (4, DATEADD('DAY', -60, CURRENT_DATE), 950, 25, 35),
-- (5, DATEADD('DAY', -60, CURRENT_DATE), 950, 25, 35),
(6, DATEADD('DAY', -60, CURRENT_DATE), 950, 25, 35)
;

-- 時給変遷を確認するためのデータ
INSERT INTO 給与.時給契約履歴(時給ID, 従業員ID, 時給, 適用開始日, 時間外割増率, 深夜割増率) VALUES
(7, 6, 955, DATEADD('DAY', -30, CURRENT_DATE), 25, 35);
INSERT INTO 給与.時給契約(従業員ID, 適用開始日, 時給, 時間外割増率, 深夜割増率) VALUES
(6, DATEADD('DAY', -30, CURRENT_DATE), 955, 25, 35);

-- 作業時間
INSERT INTO 給与.就業時間履歴(就業時間ID, 従業員ID, 就業日, 開始時刻, 終了時刻, 休憩時間, 深夜休憩時間) VALUES
(1, 1, DATEADD('DAY', -1, CURRENT_DATE), '9:00', '17:00', 60, 0),
(2, 1, DATEADD('DAY', -2, CURRENT_DATE), '9:00', '17:00', 60, 0),
(3, 1, DATEADD('DAY', -3, CURRENT_DATE), '9:00', '17:00', 60, 0),
(4, 1, DATEADD('DAY', -4, CURRENT_DATE), '9:00', '17:00', 60, 0),
(5, 1, DATEADD('DAY', -5, CURRENT_DATE), '9:00', '17:00', 60, 0),
(6, 1, DATEADD('DAY', -8, CURRENT_DATE), '9:00', '17:00', 60, 0),
(7, 1, DATEADD('DAY', -9, CURRENT_DATE), '9:00', '17:00', 60, 0),
(8, 1, DATEADD('DAY', -10, CURRENT_DATE), '9:00', '17:00', 60, 0),
(9, 1, DATEADD('DAY', -11, CURRENT_DATE), '9:00', '17:00', 60, 0),
(10, 1, DATEADD('DAY', -12, CURRENT_DATE), '9:00', '17:00', 60, 0),
(11, 1, DATEADD('DAY', -15, CURRENT_DATE), '9:00', '17:00', 60, 0),
(12, 1, DATEADD('DAY', -16, CURRENT_DATE), '9:00', '17:00', 60, 0),
(13, 1, DATEADD('DAY', -17, CURRENT_DATE), '9:00', '17:00', 60, 0),
(14, 1, DATEADD('DAY', -18, CURRENT_DATE), '9:00', '17:00', 60, 0),
(15, 1, DATEADD('DAY', -19, CURRENT_DATE), '9:00', '17:00', 60, 0),
(16, 1, DATEADD('DAY', -22, CURRENT_DATE), '9:00', '17:00', 60, 0),
(17, 1, DATEADD('DAY', -23, CURRENT_DATE), '9:00', '17:00', 60, 0),
(18, 1, DATEADD('DAY', -24, CURRENT_DATE), '9:00', '17:00', 60, 0),
(19, 1, DATEADD('DAY', -25, CURRENT_DATE), '9:00', '17:00', 60, 0),
(20, 1, DATEADD('DAY', -26, CURRENT_DATE), '9:00', '17:00', 60, 0),
(21, 1, DATEADD('DAY', -29, CURRENT_DATE), '9:00', '17:00', 60, 0),
(22, 1, DATEADD('DAY', -30, CURRENT_DATE), '9:00', '17:00', 60, 0),
(23, 1, DATEADD('DAY', -31, CURRENT_DATE), '9:00', '17:00', 60, 0),
(24, 1, DATEADD('DAY', -32, CURRENT_DATE), '9:00', '17:00', 60, 0),
(25, 1, DATEADD('DAY', -33, CURRENT_DATE), '9:00', '17:00', 60, 0),
(26, 5, DATEADD('DAY', -1, CURRENT_DATE), '9:00', '17:00', 60, 0);

INSERT INTO 給与.就業時間(就業時間ID, 従業員ID, 就業日, 開始時刻, 終了時刻, 休憩時間, 深夜休憩時間) VALUES
(1, 1, DATEADD('DAY', -1, CURRENT_DATE), '9:00', '17:00', 60, 0),
(2, 1, DATEADD('DAY', -2, CURRENT_DATE), '9:00', '17:00', 60, 0),
(3, 1, DATEADD('DAY', -3, CURRENT_DATE), '9:00', '17:00', 60, 0),
(4, 1, DATEADD('DAY', -4, CURRENT_DATE), '9:00', '17:00', 60, 0),
(5, 1, DATEADD('DAY', -5, CURRENT_DATE), '9:00', '17:00', 60, 0),
(6, 1, DATEADD('DAY', -8, CURRENT_DATE), '9:00', '17:00', 60, 0),
(7, 1, DATEADD('DAY', -9, CURRENT_DATE), '9:00', '17:00', 60, 0),
(8, 1, DATEADD('DAY', -10, CURRENT_DATE), '9:00', '17:00', 60, 0),
(9, 1, DATEADD('DAY', -11, CURRENT_DATE), '9:00', '17:00', 60, 0),
(10, 1, DATEADD('DAY', -12, CURRENT_DATE), '9:00', '17:00', 60, 0),
(11, 1, DATEADD('DAY', -15, CURRENT_DATE), '9:00', '17:00', 60, 0),
(12, 1, DATEADD('DAY', -16, CURRENT_DATE), '9:00', '17:00', 60, 0),
(13, 1, DATEADD('DAY', -17, CURRENT_DATE), '9:00', '17:00', 60, 0),
(14, 1, DATEADD('DAY', -18, CURRENT_DATE), '9:00', '17:00', 60, 0),
(15, 1, DATEADD('DAY', -19, CURRENT_DATE), '9:00', '17:00', 60, 0),
(16, 1, DATEADD('DAY', -22, CURRENT_DATE), '9:00', '17:00', 60, 0),
(17, 1, DATEADD('DAY', -23, CURRENT_DATE), '9:00', '17:00', 60, 0),
(18, 1, DATEADD('DAY', -24, CURRENT_DATE), '9:00', '17:00', 60, 0),
(19, 1, DATEADD('DAY', -25, CURRENT_DATE), '9:00', '17:00', 60, 0),
(20, 1, DATEADD('DAY', -26, CURRENT_DATE), '9:00', '17:00', 60, 0),
(21, 1, DATEADD('DAY', -29, CURRENT_DATE), '9:00', '17:00', 60, 0),
(22, 1, DATEADD('DAY', -30, CURRENT_DATE), '9:00', '17:00', 60, 0),
(23, 1, DATEADD('DAY', -31, CURRENT_DATE), '9:00', '17:00', 60, 0),
(24, 1, DATEADD('DAY', -32, CURRENT_DATE), '9:00', '17:00', 60, 0),
(25, 1, DATEADD('DAY', -33, CURRENT_DATE), '9:00', '17:00', 60, 0),
(26, 5, DATEADD('DAY', -1, CURRENT_DATE), '9:00', '17:00', 60, 0);

ALTER SEQUENCE 給与.従業員ID RESTART WITH 100;
ALTER SEQUENCE 給与.従業員名ID RESTART WITH 100;
ALTER SEQUENCE 給与.従業員電話番号ID RESTART WITH 100;
ALTER SEQUENCE 給与.従業員メールアドレスID RESTART WITH 100;
ALTER SEQUENCE 給与.時給ID RESTART WITH 100;
ALTER SEQUENCE 給与.就業時間ID RESTART WITH 1000;

