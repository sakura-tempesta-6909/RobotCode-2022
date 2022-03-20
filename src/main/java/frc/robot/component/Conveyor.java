package frc.robot.component;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.DigitalInput;
import frc.robot.subClass.Const;
import frc.robot.State;



public class Conveyor implements Component {

  private VictorSPX intakeRoller;
  private TalonSRX intakeBelt, shooterMotor;
  private DigitalInput ballSensor;
  private TalonSRX intakeExtend, backPlate;
  
  /**
   * モーターの初期化、モーター・センサーの反転
   */
  public Conveyor() {
    intakeRoller = new VictorSPX(Const.Ports.IntakeRoller);
    intakeBelt = new TalonSRX(Const.Ports.IntakeBeltMotor);
    shooterMotor = new TalonSRX(Const.Ports.ShooterMotor);
    intakeExtend = new TalonSRX(Const.Ports.ConveyorExtend);
    backPlate = new TalonSRX(Const.Ports.BackPlate);
    intakeExtend.configAllSettings(Const.Configs.intakeExtend);
    shooterMotor.configAllSettings(Const.Configs.ShooterMotor);

    /* バックプレート操作用のモーターのセット */

    ballSensor = new DigitalInput(Const.Ports.BallSensor);
    intakeRoller.setInverted(true);
    intakeExtend.setInverted(true);
    intakeExtend.setNeutralMode(NeutralMode.Brake);


  }
  /**  バックプレートのそうさ
   * シューターの速さ（距離に応じて）
   * インテークベルトのそうさ（センサー類を使って詰まらないようにする）
   * シューターモーターの上下（クライム中は上がっている等）
   * シューターモーターはモードによって動きが変わるはず
   * ボールが詰まったときの対処
   * 他にもあった方がよさそうな機能
   */

  /**
   * CARGOを回収する
   */
  public void intakeConveyor(){
    conveyorControl(Const.Speeds.RollerIntake, Const.Speeds.BeltIntake, Const.Speeds.Neutral);
  }
  
  /**
   * CARGOを出す
   */
  public void outtakeConveyor(){
    conveyorControl(-Const.Speeds.RollerOuttake, -Const.Speeds.BeltOuttake, -Const.Speeds.ShooterOuttake);
  }

  /**
   * CARGOを発射する
   */
  public void shootConveyor(){
    conveyorControl(Const.Speeds.Neutral, Const.Speeds.BeltIntake, Const.Speeds.ShooterShoot);
  }

  /**
   * conveyorを何も動かさない
   */
  public void stopConveyor(){
    conveyorControl(Const.Speeds.Neutral, Const.Speeds.Neutral, Const.Speeds.Neutral);
  }

  /**
   * beltだけintakeに回す
   */
  public void beltIntake(){
    conveyorControl(Const.Speeds.Neutral, Const.Speeds.BeltIntake, Const.Speeds.Neutral);
  }

  /**
   * beltだけouttakeに回す
   */
  public void beltOuttake(){
    conveyorControl(Const.Speeds.Neutral, -Const.Speeds.BeltOuttake, Const.Speeds.Neutral);
  }

  /**
   * rollerだけintakeに回す
   */
  public void rollerIntake(){
    conveyorControl(Const.Speeds.RollerIntake, Const.Speeds.Neutral, Const.Speeds.Neutral);
  }

  /**
   * rollerだけouttakeに回す
   */
  public void rollerOuttake(){
    conveyorControl(-Const.Speeds.RollerOuttake, Const.Speeds.Neutral, Const.Speeds.Neutral);
  }

  /**
   * shooterだけintakeに回す
   */
  public void shooterShoot(){
    conveyorControl(Const.Speeds.Neutral, Const.Speeds.Neutral, Const.Speeds.ShooterShoot);
  }

  /**
   * shooterだけouttakeに回す
   */
  public void shooterOuttake(){
    conveyorControl(Const.Speeds.Neutral, Const.Speeds.Neutral, -Const.Speeds.ShooterOuttake);
  }

  /**
   * conveyor関係のモーターを動かす
   * @param intakeRollerSpeed intakeを正
   * @param intakeBeltSpeed intakeを正
   * @param shooterSpeed intakeを正
   */
  public void conveyorControl(double intakeRollerSpeed, double intakeBeltSpeed, double shooterSpeed){
    intakeRoller.set(ControlMode.PercentOutput, intakeRollerSpeed);
    intakeBelt.set(ControlMode.PercentOutput, intakeBeltSpeed);
    shooterMotor.set(ControlMode.Velocity, Const.Other.shooterMotorMaxOutput * shooterSpeed);
  }

  /**
   * intakeExtendを動かす
   * @param intakeExtendControl 展開するときを正
   */
  public void intakeExtendControl(double intakeExtendControl){

    // if (intakeExtendControl > 0) {
    //   intakeExtend.selectProfileSlot(Const.UpPIDslot, 0);
    //   intakeExtend.set(ControlMode.Velocity, intakeExtendControl);
    //   } else if(intakeExtendControl < 0){
    //     intakeExtend.selectProfileSlot(Const.ExtendPIDslot, 0);
    //     intakeExtend.set(ControlMode.Velocity, intakeExtendControl);
    //   } else {
    //     intakeExtend.set(ControlMode.Velocity, Const.Neutral);
    //   }
    intakeExtend.set(ControlMode.PercentOutput, intakeExtendControl);
  }

  /**
   * intakeExtendをopenする
   */
  public void intakeExtendOpen(){
      intakeExtend.selectProfileSlot(Const.Configs.ExtendPIDslot, 0);
      intakeExtend.set(ControlMode.Position, Const.Pid.IntakeExtendOpenPosition);
  
  }

  /**
   * intakeExtendをcloseする
   */
  public void intakeExtendClose(){
      intakeExtend.selectProfileSlot(Const.Configs.ExtendPIDslot, 0);
      intakeExtend.set(ControlMode.Position, -Const.Pid.IntakeExtendClosePosition);
  }

  /**
   * intakeExtendを動かさない
   */
  public void intakeExtendNeutral(){
    intakeExtendControl(Const.Speeds.Neutral);
  }


  public void backPlateMove(double angle){

  }

  @Override
  public void autonomousInit() {
    // TODO Auto-generated method stub

  }

  @Override
  public void teleopInit() {
    // TODO Auto-generated method stub

  }

  @Override
  public void disabledInit() {
    // TODO Auto-generated method stub

  }

  @Override
  public void testInit() {
    // TODO Auto-generated method stub

  }

  @Override
  public void readSensors() {
    State.shooterMotorSpeed = shooterMotor.getSelectedSensorVelocity();
    State.is_fedLimitSwitchClose = intakeExtend.getSensorCollection().isFwdLimitSwitchClosed();
    State.is_revLimitSwitchClose = intakeExtend.getSensorCollection().isRevLimitSwitchClosed();
  }

  @Override
  public void applyState() {
    switch(State.conveyorState){
      case s_outtakeConveyor:
        outtakeConveyor();
        break;
      case s_intakeConveyor:
        intakeConveyor();
        break;
      case s_shootConveyor:
        shootConveyor();
        break;
      case s_stopConveyor:
        stopConveyor();
        break;

      case s_beltIntake:
        beltIntake();
        break;
      case s_beltOuttake:
        beltOuttake();
        break;
      case s_rollerIntake:
        rollerIntake();
        break;
      case s_rollerOuttake:
        rollerOuttake();
        break;
      case s_shooterOuttake:
        shooterOuttake();
        break;
      case s_shooterShoot:
        shooterShoot();
        break;
    }

    switch(State.intakeExtendState){
      case s_manual:
        intakeExtendControl(State.intakeExtendSpeed * Const.Speeds.IntakeExtendOpen);
        break;
      case s_intakeExtendOpen:
        intakeExtendOpen();
        break;
      case s_intakeExtendClose:
        intakeExtendClose();
        break;
      case s_intakeExtendNeutral:
        intakeExtendControl(State.intakeExtendSpeed * Const.Speeds.Neutral);
        break;
    }
  }

}
