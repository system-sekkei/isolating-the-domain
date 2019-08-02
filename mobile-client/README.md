# mobile SPA client (by Elm)

Elmによるmobile向けSPAクライアント

## 開発時起動方法

```sh
./gradlew :mobile-client:elmServe
```

open http://localhost:1234

http://localhost:8080 でwebappが起動している必要があります。

## デプロイ

```sh
./gradlew :mobile-client:elmBuild
```

`mobile-client/dist/` 配下のファイル一式をWebサーバで公開する。

## PWA対応

iOS/Androidともに `ホーム画面に追加` からPWAアプリとしてインストールできる。
