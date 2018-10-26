DROP SCHEMA IF EXISTS 給与;
CREATE SCHEMA 給与;

DROP TABLE IF EXISTS 給与.ユーザー;
CREATE TABLE 給与.ユーザー (
  USER_ID  VARCHAR(255) PRIMARY KEY,
  PHONE_NUMBER VARCHAR(13) NOT NULL,
  DATE_OF_BIRTH DATE NOT NULL,
  GENDER CHAR(2) NOT NULL
);

COMMENT ON COLUMN 給与.ユーザー.USER_ID IS '利用者ID';
COMMENT ON COLUMN 給与.ユーザー.PHONE_NUMBER IS '電話番号';
COMMENT ON COLUMN 給与.ユーザー.DATE_OF_BIRTH IS '生年月日';
COMMENT ON COLUMN 給与.ユーザー.GENDER IS '性別';

DROP TABLE IF EXISTS 給与.ユーザー名;
CREATE TABLE 給与.ユーザー名 (
  ユーザー名ID BIGINT PRIMARY KEY,
  ユーザーID  VARCHAR(255) NOT NULL,
  ユーザー名 VARCHAR(40) NOT NULL,
  登録日時 TIMESTAMP NOT NULL
--    ,FOREIGN KEY (ユーザーID) REFERENCES 給与.ユーザー(USER_ID)
);

DROP TABLE IF EXISTS 給与.ユーザー名対応表;
CREATE TABLE 給与.ユーザー名対応表 (
  ユーザーID  VARCHAR(255) NOT NULL,
  ユーザー名ID BIGINT NOT NULL,
    PRIMARY KEY (ユーザーID, ユーザー名ID)
--    ,FOREIGN KEY (ユーザーID) REFERENCES  給与.ユーザー(USER_ID)
--    ,FOREIGN KEY (ユーザーメールアドレスID) 
--      REFERENCES  給与.ユーザーメールアドレス対応表(ユーザーメールアドレスID)
);


DROP TABLE IF EXISTS 給与.ユーザーメールアドレス;
CREATE TABLE 給与.ユーザーメールアドレス (
  ユーザーメールアドレスID BIGINT PRIMARY KEY,
  ユーザーID  VARCHAR(255) NOT NULL,
  メールアドレス VARCHAR(255) NOT NULL,
  登録日時 TIMESTAMP NOT NULL
--    ,FOREIGN KEY (ユーザーID) REFERENCES 給与.ユーザー(USER_ID)
);

DROP TABLE IF EXISTS 給与.ユーザーメールアドレス対応表;
CREATE TABLE 給与.ユーザーメールアドレス対応表 (
  ユーザーID  VARCHAR(255) NOT NULL,
  ユーザーメールアドレスID BIGINT NOT NULL,
    PRIMARY KEY (ユーザーID, ユーザーメールアドレスID)
--    ,FOREIGN KEY (ユーザーID) REFERENCES  給与.ユーザー(USER_ID)
--    ,FOREIGN KEY (ユーザーメールアドレスID) 
--      REFERENCES  給与.ユーザーメールアドレス対応表(ユーザーメールアドレスID)
);

DROP SEQUENCE IF EXISTS 給与.シーケンサー;
CREATE SEQUENCE 給与.シーケンサー START WITH 100000;
