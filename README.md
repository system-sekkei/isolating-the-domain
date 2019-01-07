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
[ドメインを隔離する](https://github.com/system-sekkei/isolating-the-domain/wiki)

[型による設計](https://github.com/masuda220/business-logic-patterns/wiki/%E8%A8%AD%E8%A8%88%E3%82%AC%E3%82%A4%E3%83%89%E3%83%A9%E3%82%A4%E3%83%B3)

## 起動方法

```sh
./gradlew clean bootRun
```

## 実行可能Jarのビルド

```sh
./gradlew clean build
```

## 動作検証

```
npm install
npm test
```

Cypressのヘッドレスブラウザでテストします。

## アーキテクチャ

![アーキテクチャ](architecture.png)
