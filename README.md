# CutTrees

CutTrees は、木を簡単にきるための Spigot Plugin です。

[![](http://img.youtube.com/vi/nFoT79u2AEs/0.jpg)](http://www.youtube.com/watch?v=nFoT79u2AEs)

斧を使って原木を破壊すると、その原木でできた木を破壊することができます。

破壊したブロックの数だけ斧の耐久値を消費します。

斧の耐久値がなくなった場合は破壊されます。

斧が耐久エンチャントを持っていれば、エンチャントレベルによって耐久値の消費が減ります。

最後に、壊したブロックの数だけプレイヤーの統計データを更新します。

## 特徴

- 統計データに対応
- 耐久エンチャントに対応
- 設定が日本語に対応
- 拡張機能を開発可能

## ダウンロード

| CutTrees Version | Spigot Version |
| :--------------: | :------------: |
| [1.2](https://bintray.com/masahirosaito-repo/cuttrees/download_file?file_path=com%2FMasahiroSaito%2FSpigot%2FCutTrees%2F1.2%2FCutTrees-1.2.jar) | 1.11.* |
| [1.2](https://bintray.com/masahirosaito-repo/cuttrees/download_file?file_path=com%2FMasahiroSaito%2FSpigot%2FCutTrees%2F1.2%2FCutTrees-1.2.jar) | 1.10.* |

## 使い方

使い方はとても簡単です！

上記のリンクからこのプラグインをダウンロードして、プラグインを導入したい Spigot サーバーの `plugin` フォルダに配置し、サーバーを起動します。

あとは、斧を持って木をきるだけです！

## 設定

このプラグインをダウンロードして、`plugin` フォルダに配置しサーバーを起動すると、 `plugin/CutTrees/configs.json` ファイルが生成されます。このファイルをテキストエディタ等で編集することで設定を変更することができます。

- 設定を ON にする場合は、`true`
- 設定を OFF にする場合は、 `false`

```json
{
  "スニーキング状態の時に木をきる": false,
  "統計を増やす": true,
  "クリエイティブモード時に木をきる": false,
  "クリエイティブモード時に道具の耐久値を減らす": false,
  "クリエイティブモード時に統計を増やす": false,
  "デバッグメッセージを表示する": false
}
```

## 拡張機能を開発

Maven または Gradle の依存関係にこのプラグインを追加することで開発を行います。

Bintray : [https://bintray.com/masahirosaito-repo/cuttrees/CutTrees](https://bintray.com/masahirosaito-repo/cuttrees/CutTrees)

### Maven

```xml
<repository>
  <name>cuttrees-repo</name>
  <url>https://dl.bintray.com/masahirosaito-repo/cuttrees/</url>
</repository>
```

```xml
<dependency>
  <groupId>com.MasahiroSaito.Spigot</groupId>
  <artifactId>CutTrees</artifactId>
  <version>1.2</version>
  <type>pom</type>
</dependency>
```

### Gradle

```gradle
maven {
    name = "cuttrees-repo"
    url = "https://dl.bintray.com/masahirosaito-repo/cuttrees/"
}
```

```gradle
compile 'com.MasahiroSaito.Spigot:CutTrees:1.2'
```
