# ドメインを隔離する Isolating the Domain
gradle based Spring Boot &amp; MVC &amp; Thymeleaf &amp; Security &amp; MyBatis template project

## 2016-4-18 リリースノート

* MyBatisのJSR310 Date & Time API向けTypeHandlerを導入
* 自作TypeHandlerを削除

## 2016-4-14 リリースノート

* ダイレクトフィールドアクセスに切り替え
* ドメイン層のgetter/setter 禁止
* Sprng MVC の Session Attribute のライフサイクル管理の向上
* テーブルの　NOT NULL の徹底
* 画面レイアウトを 水平フォームへ

## 設計ガイド

https://github.com/system-sekkei/isolating-the-domain/wiki/%E3%83%89%E3%83%A1%E3%82%A4%E3%83%B3%E5%B1%A4%E3%81%AE%E8%A8%AD%E8%A8%88%E3%82%AC%E3%82%A4%E3%83%89

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
