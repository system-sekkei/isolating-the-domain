# mobile SPA client (by Elm)

## 情報源

* official: https://elm-lang.org/
    * official導入ガイド: https://elm-lang.org/docs
    * chat(slack): http://elmlang.herokuapp.com/
    * フォーラム: https://discourse.elm-lang.org/
* 日本コミュニティ: https://elm-lang.jp/
    * official導入ガイド日本語訳: https://guide.elm-lang.jp/
    * chat(discord): https://discordapp.com/invite/4j2MxCg
* 本
    * 基礎からわかる Elm（v0.19対応）: http://www.c-r.com/book/detail/1299
* 人
    * [@jinjor](https://twitter.com/jinjor)  日本語Elm本著者
        * [ジンジャー研究室](http://jinjor-labo.hatenablog.com/)
        * [jinjor - Qiita](https://qiita.com/jinjor)
     * [@arowM_](https://twitter.com/arowM_)
        * [arowM - Qiita](https://qiita.com/arowM)
    *  [@miyamo_madoka](https://twitter.com/miyamo_madoka) 
        * [miyamo_madoka - Qiita](https://qiita.com/miyamo_madoka)

## CSSの取り扱い

* 選択肢
    1.  CSS in Elm (独自実装)
        * [CSS in JS(Elm)したら想像以上に良かった - ジンジャー研究室](http://jinjor-labo.hatenablog.com/entry/2016/05/30/165816)
        * [CSS in Elm 方式を導入してから１年半以上たった感想 - ジンジャー研究室](http://jinjor-labo.hatenablog.com/entry/2017/12/08/080435)
    2. CSS in Elm (elm-css):  [GitHub - rtfeldman/elm-css: Typed CSS in Elm.](https://github.com/rtfeldman/elm-css/)
        * [elm-css で PostCSS も使える CSS in Elm - Qiita](https://qiita.com/arowM/items/ce20b08a65f03e2ec44b)
    3. UIコンポーネント集: [GitHub - gdotdesign/elm-ui: UI library for making web applications with Elm](https://github.com/gdotdesign/elm-ui)
    4. 既存CSSフレームワークのelmラッパ（bootstrap, bluma）: 
        * [GitHub - surprisetalk/elm-bulma: Elm library for the Bulma front-end framework](https://github.com/surprisetalk/elm-bulma/) 
        * [GitHub - rundis/elm-bootstrap: Responsive and reliable web apps with Elm and Twitter Bootstrap](https://github.com/rundis/elm-bootstrap)
        * その他semantic-uiなどのラッパについてはメンテされていない感じがする
    5. 既存のフロントエンド開発の蓄積を使う
        * npm + webpack + scss + elm + (semantic-uiなどのCSSフレームワーク)
    6. [Elm いろいろためしたけど、もう見た目をあらわすクラス名をつけても全然いいよね？ - Qiita](https://qiita.com/arowM/items/50f40b4e78133b7ef00f)
* メモ
    * 基本は1で貫くが、無理な部分（css疑似クラス等）は必ずあるので4で補う？ CSSをすべて独自実装することになる？部分的に既存のCSSフレームワークを使いたい場合はどうする？
    * CSSに関しては既存のフロントエンド開発の蓄積(scssで記述してwebpackでcssに変換)を使った方がわかりやすい？
    * Vue.jsにあるようなscoped CSSを実現できる方法はあるのか？
    * 2, 3, 4に関しては組織やコミュニティが提供しているものではなさそうなので、導入には勇気がいる。特に3, 4はマークアップでレイアウトを組むので、html = 論理的な構造の定義、css = レイアウトの定義という役割の分離がなくなってしまい、最終的に出力されるhtmlが装飾で汚れるので個人的に好みではない。2については使ってみないとよくわからない感じがする

## 開発環境

* ツール
    * plugin対応IDE: intellij, vscode
* エコシステム
    * パッケージリポジトリ: https://package.elm-lang.org/
      * rtfeldman, NoRedInk, elm-communityが提供しているパッケージは信頼性が高そう
    * `elm` コマンドはnpmでインストールできるので、[node-gradle/gradle-node-plugin](https://github.com/node-gradle/gradle-node-plugin) を使って容易にgradleのタスクに乗せることができる。開発環境/CI環境にはjavaさえ入っていれば良いという状況は作れる
    * bundlerの選択肢としてはwebpackの他にParcelがある [Parcel でまとめる Elm と css と js のススメ - Qiita](https://qiita.com/kyasu1/items/44e67b8755f1adcfef67)

## モジュールの考え方

* 基本は各Page単位でモジュールを作り、Mainモジュールを親にして、URLによって各ページへ処理を振り分ける構成がベストプラクティスになっているらしい
* 参考実装
    * [GitHub - elm/package.elm-lang.org: website for browsing packages and exploring documentation](https://github.com/elm/package.elm-lang.org)
    * [GitHub - rtfeldman/elm-spa-example: A Single Page Application written in Elm](https://github.com/rtfeldman/elm-spa-example)
* CustomTypeの使い方に関しては
    * 参考になる実装は見つけられず
    * ひとまずは極端に、ページごとに1field=1typeで、全ページから共通して使う型は最小限にする、という方針で実装してみる
    * 集約的なtypeを作るのは取り回しがかなり晦渋な感じ（階層が深くなって、親から子への値の引き渡しや親への結果の戻しが逆に辛い）
    * value object的な1つの値を表現する型をpageモジュール内で組み合わせるのが、階層も浅くて済んでよさそう
    * 一覧系など表示だけのページのfieldにtypeを定義するのは冗長な感じはあるが、各関数のシグニチャは明確になるので好みではある
    * FormのFieldはユーザビリティ向上のためのvalidationロジックをまとめられるのですっきり感がある
* Page間で共有すべき値の取り回し
    * elm/package.elm-lang.orgでは `session` というtype aliasに共有データを突っ込んで各ページを引き回す感じ
    * vuex的な「どのモジュールからもアクセスできるデータストア」の仕組み/ライブラリは無さそう
    * 共有データは認証トークンやページ間のナビゲーションのためのkeyぐらいに留めて、必要なデータは都度サーバに問い合わせるのがシンプルではある
    * セッションが切れたあとに永続化したい場合（ex. 1ヶ月ぐらいは認証トークンを保持してログインのステップを飛ばすとか）portの仕組みを使ってJS側から/へ状態を取得/保存する形になりそう
        * refs. [複数のElmアプリで小さなデータを共有する - Local storage - Qiita](https://qiita.com/sand/items/3767d263f98b3dad264e)

## MISC

### PWA対応

* マニフェストファイルの作成支援 refs. https://app-manifest.firebaseapp.com/
* iOSの場合は設定が手間なのでgoogleが公開しているPWACompatというツールを使うと楽 refs. https://developers.google.com/web/updates/2018/07/pwacompat
* ServiceWorkerを使わない場合は、エントリポイントになるhtmlでmanifestファイルを読み込むだけなので、特に問題はない
* ServiceWorkerの登録はJSで。中の処理はelmでかける？
    * refs. https://discourse.elm-lang.org/t/psa-elm-http-works-with-serviceworker/2562
    * refs. https://notes.eellson.com/2018/02/26/offline-post-requests-with-elm-and-service-worker/
