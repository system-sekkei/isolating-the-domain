# mobile SPA client (by Elm)

## 起動方法

開発時
```sh
./gradlew mobile-client:elmServe
```

```localhost:1234``` でダッシュボード画面を表示

## ビルド方法

```sh
./gradlew mobile-client:elmBuild
```

`mobile-client/dist/` 配下のファイル一式をWebサーバで公開する

## PWA対応

iOS/Androidともに `ホーム画面に追加` からPWAアプリとしてインストールできる