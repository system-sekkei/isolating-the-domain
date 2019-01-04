# ドメインを隔離する Isolating the Domain
Spring Boot
Spring MVC (direct field access)
Thymeleaf
MyBatis SQL Mapper

## 起動方法

```sh
./gradlew clean bootRun
```

## 設計ガイド
https://github.com/system-sekkei/isolating-the-domain/wiki
https://github.com/masuda220/business-logic-patterns/wiki/%E8%A8%AD%E8%A8%88%E3%82%AC%E3%82%A4%E3%83%89%E3%83%A9%E3%82%A4%E3%83%B3

## 2017-10-20 リリースノート

* 依存ライブラリを最新化
    * Spring Boot
    * MyBatis
    * Thymeleaf

## 2017-7-30 リリースノート

* Spring MVC direct field accessの使い方をより明確に
* フレームワークのバージョンアップ
* 設計ガイドの加筆・修正

## 2016-5-1 リリースノート

* パッケージの意図を明確に
* パッケージ構造の整理と package-info.java の加筆修正
* 未使用でも、標準的なパッケージを追加

### パッケージ構成

* application
  * service //サービス部品
  * usecase //サービスを組み合わせたユースケース定義
* domain
  * fundamentals //モデル記述の基礎部品
  * model //中核の関心事の記述
* infrastructure
  * datasource //データベースアクセスの実装
  * transfer //通信の実装
* presetation
  * controller //コントローラ
  * view //ビュー定義

## 2016-4-30 リリースノート

* MyBatis-spring-boot-starter 導入(mybatis config の自動化)
* mybatis JSR310 ハンドラー導入（自動configされる）
* boot devtools 導入 ( h2-console を有効にする)

localhost:8080/h2-console で h2 コンソールに接続
( jdbc:h2:mem:testdb)

## 2016-4-18 リリースノート

* Spring Bootを1.3.3.RELEASERへ
* MyBatisを3.3.1.RELEASEへ
* MyBatisのJSR310 Date & Time API向けTypeHandlerを導入
* 自作TypeHandlerを削除

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
