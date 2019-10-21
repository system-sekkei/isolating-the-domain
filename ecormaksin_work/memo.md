# テーブル構造理解のためにdockerコンテナでDBサーバーを作成する

```
docker build -t postgres11-jp .
docker run -it -e "TZ=Asia/Tokyo" -e POSTGRES_PASSWORD=secret -h isolating-the-domain01 --name=isolating-the-domain01 --privileged -p 5432:5432 -v $(pwd)/sql:/docker-entrypoint-initdb.d postgres11-jp
```

作った後で、DBeaverからER図を生成する。

# 理解のためにビューを作成する

```
create or replace view 契約中の従業員 as
SELECT
    従業員.従業員ID,
    従業員の名前.従業員名,
    従業員のメールアドレス.メールアドレス,
    従業員の電話番号.電話番号
FROM 給与.従業員 as 従業員
INNER JOIN 給与.従業員の名前 as 従業員の名前 on (
従業員.従業員ID = 従業員の名前.従業員ID
) INNER JOIN 給与.従業員の電話番号 as 従業員の電話番号 on (
従業員.従業員ID = 従業員の電話番号.従業員ID
) INNER JOIN 給与.従業員のメールアドレス as 従業員のメールアドレス on (
従業員.従業員ID = 従業員のメールアドレス.従業員ID
) INNER JOIN 給与.契約中 as 契約中 on (
従業員.従業員ID = 契約中.従業員ID
);
```
