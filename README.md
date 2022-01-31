# 前提
## コードの記述箇所
基本的に、`src/main/java/frc/robot`下にある、[`Robot.java`](./src/main/java/frc/robot/Robot.java)にコードを記述していく。  
実行の本体はMain.javaに記述されているが、それは書き換えない。

## モードについて
[DriverStationソフト](https://docs.wpilib.org/en/stable/docs/software/driverstation/index.html)にて、ロボットをenableすることで[`Robot.java`](./src/main/java/frc/robot/Robot.java)に記述したコードが実行される。  
その実行には以下の4つのモードがある。
- TeleOperated
- Autonomous
- Practice
- Test

![](./readmesrc/ds-operation-tab.webp)

### モードに応じた実行
それぞれのモードに対してメソッドを記述することで実装していく。  
そのモードがenableされた瞬間、初期化処理が実行され、
それ以降は0.02秒ごとにPeriodic(定期的な)メソッドが繰り返し実行される。

| モード | 意味 | 初期化 | Periodic |
| :--: | :--: | :--: | :--: |
|TeleOperated|手動制御<br>試合の後半135秒間動く|`teleopInit()`|`teleopPeriodic()`|
|Autonomous|自動制御<br>試合の前半15秒間動く|`autonomousInit()`|`autonomousPeriodic()`|
|Practice|本番練習用<br>Practice Timingというものを設定するとそれに従ってTeleOpとAutoが切り替わる|なし|なし|
|Test|テスト用|`testInit()`|`testPeriodic()`|
|Disabled|enableにしていない状態|`disabledInit()`|`disabledPeriodic()`|

また、ロボットを起動した瞬間に`robotInit()`が実行され、
各モードに応じたPeriodicメソッドが実行されたのち、`robotPeriodic()`が実行される。  
`robotInit()`でフィールドの初期化やPIDゲインの設定などを行うと良い。

### モードの特徴
Autonomousモード中はコントローラの入力が一切入らない。

Practiceモードは特殊で、SetUpタブにて設定したPractice Timingに従ってモードが切り替わる。  
下の画像の例では、5秒のカウントダウンの後、15秒Autonomousモードになり、1秒のDelayを挟んだ後、120秒間TeleOperatedになる。(End Game中もTeleOperatedである。)

![](./readmesrc/ds-setup-tab.webp)

# SAKURA Tempestaロボットコードの設計解説
前述のように基本的には[`Robot.java`](./src/main/java/frc/robot/Robot.java)にコードを記述していく。  
しかし、Javaはオブジェクト指向言語であり、ロボットもいくつかのオブジェクトの集合体として表現できる。  
他にも、Stateというものを導入することで、コードの実装の分離も可能になる。

これらのことを踏まえ、後述するような設計とした。

## 各ファイルの意図
それぞれのオブジェクトに対応するクラスを[`component`](./src/main/java/frc/robot/component/)下に作成した。
そしてその各オブジェクトは[`Component`](./src/main/java/frc/robot/component/Component.java) Interfaceを継承することとしている。

また、ロボット操作時にいくつのモードが生じることが予想される。  
例えば、ドライブモード(移動やCARGO回収)、発射モード、クライムモードなどが挙げられる。  
それらに対してもクラスを作り、[`mode`](./src/main/java/frc/robot/mode/)下に配置した。  
`component`同様、各モードは[`Mode`](./src/main/java/frc/robot/mode/Mode.java)クラスを継承することとした。

また、SAKURA Tempestaでは[`State`](./src/main/java/frc/robot/State.java)クラスを導入することで実装の分離を実現している。  

他にも、定数用のクラスとしての[`Const`](src/main/java/frc/robot/subClass/Const.java)、外部センサーとしての[`ExternalSnesors`](src/main/java/frc/robot/subClass/ExternalSensors.java)、便利関数としての[`Util.java`](src/main/java/frc/robot/subClass/Util.java)などが`src/main/java/frc/roboot/subClass`下に配置されている。

## プログラムの流れ
### メソッド概要
TeleOperatedを想定する。

|実行タイミング|実行回数|実行されるメソッドなど|具体的な実行内容|
| :--: | :--: | :--: | :--: |
|ロボット起動直後|1回| `Robot`のインスタンス化|`Robot`にて宣言されたフィールドが作られる|
| |1回|`robotInit()`|フィールドの初期化や設定などを行う|
|teleopでenableした直後|teleopでenableする毎に1回ずつ| `teleopInit()`|teleop用の設定を行う<br>PIDのゲインや、手動操作特有のモータの設定など|
|teleopでenableしてる最中|teleop中0.02秒ごとに繰り返し|`teleopPeriodic()`|後述|
|enableからdisableにした瞬間|disableする毎に1回ずつ|`disableInit()`|disable時の設定を行う<br>ロボットによるが、機構のロックなど|
|disableの最中|disable中0.02秒ごとに繰り返し|`disablePeriodic()`|あんまり書くことは無いと思われる|

以上のものがモードに応じて切り替わりながら実行される。

### `teleopPeriodic()`について
SAKURA Tempestaは`teleopPeriodic()`をいくつかの工程に分けて考えている。 

簡単に説明すると、
まず、コントローラやセンサの値に応じて`State`を変化させ、
その後に`State`に応じて各`component`を動作させる、という方式である。

時系列に沿って書くと以下のようになる。

|呼び出される処理など|役割|説明|
| :--: | :--: | :--: |
|`State::stateInit()`|初期化|`State`をリセットする|
|`ExternalSensors::readSensors()`|外部のセンサの値を読み込む|センサの値を`State`に反映させる<br>ここでのセンサは、roboRIOなどに接続した(componentに接続されていない)センサを指す。<br>ロボットのジャイロや、CARGO認識のセンサなどが例として挙げられる|
|各componentの`Component::readSensors()`|componentにあるセンサの値を読み込む|センサの値をStateに反映させる<br>ここでのセンサはcomponentに接続されたセンサを指す。<br>モータコントローラに接続されたエンコーダや、リミットスイッチなどが例として挙げられる。|
|`Mode::changeMode()`|モードを変化させる|`State::modes`を変化させる<br>基本的にはモードとコントローラの入力に応じて変化させる|
|`Mode::changeState()`|Stateを変化させる|`State`のフィールド(modes以外)を変化させる<br>モードによって`State`の変化のさせ方を切り替える|
|各componentの`Component::applyState()`|Stateを適用する|`changeState()`で変化させた`State`に応じて各componentを動作させる|

以上のようなステップを取ることで、実装を要所要所で分離して行える。

`readSensors()`パートでは用法を気にせず、センサの値を読むだけでよい。  
`changeState()`パートではモードごとにクラスを作り、それぞれのクラスでこのメソッドの実装を行うので、そのモードのことだけ考えるだけで十分。また、componentがこう動作してほしいという気持ちを`State`のフィールドに反映するだけでいい。
例えば、componentの一つである`Arm`にPID制御で40度まで動かしてね！という気持ちで、`armState`を`k_PID`に`setAngle`を`40`に設定するだけで、実際の動作は`Arm`側に任せれば良い。  
`applyState()`パートで先述した実際の動作をさせればよく、どのようなときにその動きをすればよいのかなどは考慮しなくて良い。先程の例では、`armState`が`k_PID`のとき(`switch`文などで切り替える)、(アームのモータとして`TalonSRX`を使用しているのであれば、)`armMotor::set(ControlMode.Position, angleToPoint(State::setAngle))`を呼び出す、というように処理を実装していく。

## 実際の実装について
前述の意図を踏まえた上で、どのように実装していくかを述べる。

### `State`について
ロボットの「状態」を表すクラスである。  
センサーの値のみならず、この後ロボットをどう動かしたいか、
などの情報も持つ。

DriveBaseを例にして述べる。  
まず、`State`クラス内で
```java
enum DriveMode {
    k_manual, // 手動操作
    k_PID, // PID制御
    k_neutral // 何もしない
}
```
が定義されているとする。  
そして、DriveBase用のフィールドとして
```java
public DriveMode drivemode;
// k_manual用
public double driveXSpeed, driveZRotation;
// k_PID用
public double drivePIDSetPosition, drivePIDSetDirection;
```
を持たせる。

### `Component`について

### `Mode`について

### `Util`について

### `Const`について

# トラブルシューティング
トラブルには様々な種類がある。
- ロボットと接続できない
- Phoenix Tunerが使えない
- enableにした瞬間disableになる
- コントローラが効かない
- ロボットが想定と違う動きをする

