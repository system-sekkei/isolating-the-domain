**本リポジトリは2020年時点から更新されていません。近々アップデートを予定していますが、それまでは以下から差分を抽出してください。**

- https://speakerdeck.com/masuda220/
- [Software Design 2023年2月号](https://gihyo.jp/magazine/SD/archive/2023/202302)

また、技術要素としてはJava11&SpringBoot2.xとなっています。SpringBoot3.x対応の例は [system-sekkei/library](https://github.com/system-sekkei/library) を参照してください。
libraryはCCSR(isolating-the-domainにRDRA)のサンプルとなっています。

# ドメインを独立させる Isolating the Domain
- Spring Boot
- Spring MVC (direct field access)
- Thymeleaf
- MyBatis SQL Mapper

## 起動方法

```sh
./gradlew bootRun
```

http://localhost:8080 でダッシュボード画面を表示

## 実行可能Jarのビルドと実行

```sh
./gradlew clean build
java -jar webapp/build/libs/webapp.jar
```

## JIG 設計ドキュメントの自動生成

```sh
./gradlew jig
```

`webapp/build/jig` 以下にソースコードから自動生成したクラス一覧やクラスの関連図を出力

[JIG 設計ドキュメント](https://github.com/dddjava/jig)

## 設計ガイド

[ドメインを独立させる](https://github.com/system-sekkei/isolating-the-domain/wiki)

[ドメイン駆動設計本格入門](https://www.slideshare.net/masuda220/ss-137608652)

[型指向のプログラミング：設計ガイドライン](https://github.com/masuda220/business-logic-patterns/wiki/%E8%A8%AD%E8%A8%88%E3%82%AC%E3%82%A4%E3%83%89%E3%83%A9%E3%82%A4%E3%83%B3)

[書籍：現場で役立つシステム設計の原則](https://gihyo.jp/book/2017/978-4-7741-9087-7)

## アーキテクチャ

![アーキテクチャ](architecture.png)

## 参考資料

- [労働基準法](https://elaws.e-gov.go.jp/search/elawsSearch/elaws_search/lsg0500/detail?lawId=322AC0000000049#171)
- [労働基準関係法令のあらまし | 大阪労働局](https://jsite.mhlw.go.jp/osaka-roudoukyoku/hourei_seido_tetsuzuki/roudoukijun_keiyaku/hourei_seido/_122090.html)
  - 事業者向けに労働基準法をわかりやすくまとめたパンフレット。労働条件通知書の見本付き。
- [時間外労働の上限規制 わかりやすい解説](https://www.mhlw.go.jp/content/000463185.pdf)
- [労働基準法が改正されました](https://www.mhlw.go.jp/stf/seisakunitsuite/bunya/koyou_roudou/roudoukijun/roukikaitei/index.html)
- [改正労働基準法に係る質疑応答](https://www.mhlw.go.jp/topics/2008/12/dl/tp1216-1k.pdf)
- [モデル就業規則](https://www.mhlw.go.jp/bunya/roudoukijun/model/dl/model.pdf)
- [法定時間外労働 労働時間計算についてのメモ](./docs/overLegalWorkTime.md)