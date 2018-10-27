DROP SCHEMA IF EXISTS 給与;
CREATE SCHEMA 給与;

CREATE TABLE 給与.ユーザー (
  ユーザーID  BIGINT PRIMARY KEY,
  登録日時 TIMESTAMP NOT NULL
);

CREATE TABLE 給与.ユーザー名 (
  ユーザー名ID BIGINT PRIMARY KEY,
  ユーザーID  BIGINT NOT NULL,
  ユーザー名 VARCHAR(40) NOT NULL,
  登録日時 TIMESTAMP NOT NULL
    ,FOREIGN KEY (ユーザーID) REFERENCES 給与.ユーザー(ユーザーID)
);

CREATE TABLE 給与.ユーザー名対応表 (
  ユーザーID  BIGINT NOT NULL,
  ユーザー名ID BIGINT NOT NULL,
    PRIMARY KEY (ユーザーID, ユーザー名ID)
    ,FOREIGN KEY (ユーザーID) REFERENCES  給与.ユーザー(ユーザーID)
    ,FOREIGN KEY (ユーザー名ID) 
      REFERENCES  給与.ユーザー名(ユーザー名ID)
);

CREATE TABLE 給与.ユーザー誕生日 (
  ユーザー誕生日ID BIGINT PRIMARY KEY,
  ユーザーID  BIGINT NOT NULL,
  誕生日 DATE NOT NULL,
  登録日時 TIMESTAMP NOT NULL
    ,FOREIGN KEY (ユーザーID) REFERENCES 給与.ユーザー(ユーザーID)
);

CREATE TABLE 給与.ユーザー誕生日対応表 (
  ユーザーID  BIGINT NOT NULL,
  ユーザー誕生日ID BIGINT NOT NULL,
    PRIMARY KEY (ユーザーID, ユーザー誕生日ID)
    ,FOREIGN KEY (ユーザーID) REFERENCES  給与.ユーザー(ユーザーID)
    ,FOREIGN KEY (ユーザー誕生日ID) 
      REFERENCES  給与.ユーザー誕生日対応表(ユーザー誕生日ID)
);

CREATE TABLE 給与.ユーザー電話番号 (
  ユーザー電話番号ID BIGINT PRIMARY KEY,
  ユーザーID  BIGINT NOT NULL,
  電話番号 VARCHAR(13) NOT NULL,
  登録日時 TIMESTAMP NOT NULL
    ,FOREIGN KEY (ユーザーID) REFERENCES 給与.ユーザー(ユーザーID)
);

CREATE TABLE 給与.ユーザー電話番号対応表 (
  ユーザーID  BIGINT NOT NULL,
  ユーザー電話番号ID BIGINT NOT NULL,
    PRIMARY KEY (ユーザーID, ユーザー電話番号ID)
    ,FOREIGN KEY (ユーザーID) REFERENCES  給与.ユーザー(ユーザーID)
    ,FOREIGN KEY (ユーザー電話番号ID) 
      REFERENCES  給与.ユーザー電話番号対応表(ユーザー電話番号ID)
);


CREATE TABLE 給与.ユーザーメールアドレス (
  ユーザーメールアドレスID BIGINT PRIMARY KEY,
  ユーザーID  BIGINT NOT NULL,
  メールアドレス VARCHAR(255) NOT NULL,
  登録日時 TIMESTAMP NOT NULL
    ,FOREIGN KEY (ユーザーID) REFERENCES 給与.ユーザー(ユーザーID)
);

CREATE TABLE 給与.ユーザーメールアドレス対応表 (
  ユーザーID  BIGINT NOT NULL,
  ユーザーメールアドレスID BIGINT NOT NULL,
    PRIMARY KEY (ユーザーID, ユーザーメールアドレスID)
    ,FOREIGN KEY (ユーザーID) REFERENCES  給与.ユーザー(ユーザーID)
    ,FOREIGN KEY (ユーザーメールアドレスID) 
      REFERENCES  給与.ユーザーメールアドレス対応表(ユーザーメールアドレスID)
);

CREATE TABLE 給与.ユーザー性別 (
  ユーザー性別ID BIGINT PRIMARY KEY,
  ユーザーID  BIGINT NOT NULL,
  性別 CHAR(2) NOT NULL,
  登録日時 TIMESTAMP NOT NULL
    ,FOREIGN KEY (ユーザーID) REFERENCES 給与.ユーザー(ユーザーID)
);

CREATE TABLE 給与.ユーザー性別対応表 (
  ユーザーID BIGINT NOT NULL,
  ユーザー性別ID BIGINT NOT NULL,
    PRIMARY KEY (ユーザーID, ユーザー性別ID)
    ,FOREIGN KEY (ユーザーID) REFERENCES  給与.ユーザー(ユーザーID)
    ,FOREIGN KEY (ユーザー性別ID) 
      REFERENCES  給与.ユーザー性別対応表(ユーザー性別ID)
);

CREATE TABLE 給与.削除済みユーザー (
  ユーザーID  BIGINT PRIMARY KEY,
  登録日時 TIMESTAMP NOT NULL
    ,FOREIGN KEY (ユーザーID) REFERENCES 給与.ユーザー(ユーザーID)
);

CREATE SEQUENCE 給与.シーケンサー START WITH 100000;
