# ドメインを隔離する Isolating the Domain
gradle based Spring Boot &amp; MVC &amp; Thymeleaf &amp; Security &amp; MyBatis template project

## 2016-4-14 リリースノート

* ダイレクトフィールドアクセスに切り替え
* ドメイン層のgetter/setter 禁止
* Sprng MVC の Session Attribute のライフサイクル管理の向上
* テーブルの　NOT NULL の徹底
* 画面レイアウトを 水平フォームへ

## 起動方法

```sh
./gradlew clean bootRun
```

## 実行可能Jarのビルド

```sh
./gradlew clean build
```

## アーキテクチャ

![アーキテクチャ](architecture.png)
