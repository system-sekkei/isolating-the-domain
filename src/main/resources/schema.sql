DROP SCHEMA IF EXISTS 給与;
CREATE SCHEMA 給与;

CREATE TABLE 給与.ユーザー (
  ユーザーID  INTEGER PRIMARY KEY,
  登録日時 TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
CREATE SEQUENCE 給与.ユーザーIDシーケンサー;

CREATE TABLE 給与.ユーザー名 (
  ユーザー名ID INTEGER PRIMARY KEY,
  ユーザーID  INTEGER NOT NULL,
  ユーザー名 VARCHAR(40) NOT NULL,
  登録日時 TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
    ,FOREIGN KEY (ユーザーID) REFERENCES 給与.ユーザー(ユーザーID)
);
CREATE SEQUENCE 給与.ユーザー名IDシーケンサー;

CREATE TABLE 給与.ユーザー名対応表 (
  ユーザーID  INTEGER NOT NULL,
  ユーザー名ID INTEGER NOT NULL,
    PRIMARY KEY (ユーザーID, ユーザー名ID)
    ,FOREIGN KEY (ユーザーID) REFERENCES  給与.ユーザー(ユーザーID)
    ,FOREIGN KEY (ユーザー名ID) 
      REFERENCES  給与.ユーザー名(ユーザー名ID)
);

CREATE TABLE 給与.ユーザー電話番号 (
  ユーザー電話番号ID INTEGER PRIMARY KEY,
  ユーザーID  INTEGER NOT NULL,
  電話番号 VARCHAR(13) NOT NULL,
  登録日時 TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
    ,FOREIGN KEY (ユーザーID) REFERENCES 給与.ユーザー(ユーザーID)
);
CREATE SEQUENCE 給与.ユーザー電話番号IDシーケンサー;

CREATE TABLE 給与.ユーザー電話番号対応表 (
  ユーザーID  INTEGER NOT NULL,
  ユーザー電話番号ID INTEGER NOT NULL,
    PRIMARY KEY (ユーザーID, ユーザー電話番号ID)
    ,FOREIGN KEY (ユーザーID) REFERENCES  給与.ユーザー(ユーザーID)
    ,FOREIGN KEY (ユーザー電話番号ID) 
      REFERENCES  給与.ユーザー電話番号対応表(ユーザー電話番号ID)
);


CREATE TABLE 給与.ユーザーメールアドレス (
  ユーザーメールアドレスID INTEGER PRIMARY KEY,
  ユーザーID  INTEGER NOT NULL,
  メールアドレス VARCHAR(255) NOT NULL,
  登録日時 TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
    ,FOREIGN KEY (ユーザーID) REFERENCES 給与.ユーザー(ユーザーID)
);
CREATE SEQUENCE 給与.ユーザーメールアドレスIDシーケンサー;

CREATE TABLE 給与.ユーザーメールアドレス対応表 (
  ユーザーID  INTEGER NOT NULL,
  ユーザーメールアドレスID INTEGER NOT NULL,
    PRIMARY KEY (ユーザーID, ユーザーメールアドレスID)
    ,FOREIGN KEY (ユーザーID) REFERENCES  給与.ユーザー(ユーザーID)
    ,FOREIGN KEY (ユーザーメールアドレスID) 
      REFERENCES  給与.ユーザーメールアドレス対応表(ユーザーメールアドレスID)
);

CREATE TABLE 給与.削除済みユーザー (
  ユーザーID  INTEGER PRIMARY KEY,
  登録日時 TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
    ,FOREIGN KEY (ユーザーID) REFERENCES 給与.ユーザー(ユーザーID)
);

CREATE TABLE 給与.就業時間 (
  就業時間ID INTEGER PRIMARY KEY,
  ユーザーID INTEGER NOT NULL,
  就業日 DATE NOT NULL,
  開始時刻 TIME NOT NULL,
  終了時刻 TIME NOT NULL,
  休憩時間 INTEGER NOT NULL,
  登録日時 TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
    ,FOREIGN KEY (ユーザーID) REFERENCES  給与.ユーザー(ユーザーID)
);

CREATE TABLE 給与.就業時間対応表 (
  ユーザーID INTEGER NOT NULL,
  就業日 DATE NOT NULL,
  就業時間ID INTEGER NOT NULL,
    PRIMARY KEY (ユーザーID, 就業日, 就業時間ID)
    ,FOREIGN KEY (ユーザーID) REFERENCES  給与.ユーザー(ユーザーID)
    ,FOREIGN KEY (就業時間ID)
      REFERENCES  給与.就業時間(就業時間ID)
);

CREATE SEQUENCE 給与.就業時間IDシーケンサー;
