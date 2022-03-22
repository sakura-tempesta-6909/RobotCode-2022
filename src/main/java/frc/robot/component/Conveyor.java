package frc.robot.component;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.DigitalInput;
import frc.robot.subClass.Const;
import frc.robot.State;



public class Conveyor implements Component {

  private VictorSPX intakeRoller;
  private TalonSRX intakeBelt, launchMotor;
  private DigitalInput ballSensorIntake, ballSensorLaunch;
  private TalonSRX intakeExtend, backPlate;


  public Conveyor() {
    intakeRoller = new VictorSPX(Const.Ports.IntakeRoller);
    intakeBelt = new TalonSRX(Const.Ports.IntakeBeltMotor);
    launchMotor = new TalonSRX(Const.Ports.LaunchMotor);
    intakeExtend = new TalonSRX(Const.Ports.ConveyorExtend);
    backPlate = new TalonSRX(Const.Ports.BackPlate);
    intakeExtend.configAllSettings(Const.Configs.intakeExtend);
    launchMotor.configAllSettings(Const.Configs.LaunchMotor);
    

    /* バックプレート操作用のモーターのセット */

    ballSensorIntake = new DigitalInput(Const.Ports.BallSensorIntake);
    ballSensorLaunch = new DigitalInput(Const.Ports.BallSensorShooter);
    intakeRoller.setInverted(true);
    intakeExtend.setInverted(true);


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
   * trueの時センサーはボールを認識できていない。
   * falseの時センサーはボールを認識している。!がついている時
  */
  public void intakeConveyor(){
    if(ballDetectionIntake() && ballDetectionShoot() && State.conveyorState == State.ConveyorState.s_ballquantity0){
      beltRollerIntake();
    }else if(!ballDetectionIntake() && ballDetectionShoot() && State.conveyorState == State.ConveyorState.s_ballquantity0){
      beltRollerIntake();
      State.conveyorState = State.ConveyorState.s_ballquantity1;
    }else if(!ballDetectionIntake() && ballDetectionShoot() && State.conveyorState == State.ConveyorState.s_ballquantity1){
      beltRollerIntake();
    }else if(ballDetectionIntake() && ballDetectionShoot() && State.conveyorState == State.ConveyorState.s_ballquantity1){
      rollerIntake();
    }else if(!ballDetectionIntake() && !ballDetectionShoot() && State.conveyorState ==  State.ConveyorState.s_ballquantity1){
      conveyorNutral();
      State.conveyorState = State.ConveyorState.s_ballquantity2;
    }else if(ballDetectionIntake() && !ballDetectionShoot() && State.conveyorState == State.ConveyorState.s_ballquantity1) {
      conveyorNutral();
    }else if(!ballDetectionIntake() && !ballDetectionShoot() && State.conveyorState == State.ConveyorState.s_ballquantity2){
      conveyorNutral();
      //以下はボールが入った時の誤作動をなるべく防ぐためのもの
    }else if(!ballDetectionIntake() && !ballDetectionShoot() && State.conveyorState == State.ConveyorState.s_ballquantity0){
      State.conveyorState = State.ConveyorState.s_ballquantity2;
    }else if(ballDetectionIntake() && !ballDetectionShoot() && State.conveyorState == State.ConveyorState.s_ballquantity0){
      conveyorNutral();
    }
  }
  public boolean ballDetectionIntake(){
    return ballSensorIntake.get();
  }

  public boolean ballDetectionShoot(){
    return ballSensorLaunch.get();
  }

  public void outtakeConveyor(){
    conveyorControl(-Const.Speeds.RollerOuttake, -Const.Speeds.BeltOuttake, -Const.Speeds.ShooterOuttake);
  }

  public void shootConveyor(){
    conveyorControl(Const.Speeds.Neutral, Const.Speeds.BeltIntake, Const.Speeds.ShooterShoot);
    State.conveyorState = State.ConveyorState.s_ballquantity0;
  }

  public void stopConveyor(){
    conveyorControl(Const.Speeds.Neutral, Const.Speeds.Neutral, Const.Speeds.Neutral);
  }
  public void beltIntake(){
    conveyorControl(Const.Speeds.Neutral, Const.Speeds.BeltIntake, Const.Speeds.Neutral);
  }

  public void beltOuttake(){
    conveyorControl(Const.Speeds.Neutral, -Const.Speeds.BeltOuttake, Const.Speeds.Neutral);
  }

  public void rollerIntake(){
    conveyorControl(Const.Speeds.RollerIntake, Const.Speeds.Neutral, Const.Speeds.Neutral);
  }

  public void rollerOuttake(){
    conveyorControl(-Const.Speeds.RollerOuttake, Const.Speeds.Neutral, Const.Speeds.Neutral);
  }

  public void shooterShoot(){
    conveyorControl(Const.Speeds.Neutral, Const.Speeds.Neutral, Const.Speeds.ShooterShoot);
  }

  public void shooterOuttake(){
    conveyorControl(Const.Speeds.Neutral, Const.Speeds.Neutral, -Const.Speeds.ShooterOuttake);
  }

  public void beltRollerIntake(){
    conveyorControl(Const.Speeds.RollerIntake, Const.Speeds.BeltIntake, Const.Speeds.Neutral);
  }

  public void beltRollerOuttake(){
    conveyorControl(Const.Speeds.RollerOuttake, Const.Speeds.BeltOuttake, Const.Speeds.Neutral);
  }

  public void conveyorNutral(){
    conveyorControl(Const.Speeds.Neutral, Const.Speeds.Neutral, Const.Speeds.Neutral);

  }

  /**
   *
   * @param intakeRollerSpeed intakeを正
   * @param intakeBeltSpeed intakeを正
   * @param launchSpeed intakeを正
   */
  public void conveyorControl(double intakeRollerSpeed, double intakeBeltSpeed, double shooterSpeed){
    intakeRoller.set(ControlMode.PercentOutput, intakeRollerSpeed);
    intakeBelt.set(ControlMode.PercentOutput, intakeBeltSpeed);
    launchMotor.set(ControlMode.PercentOutput, shooterSpeed);
  }

  /**
   *
   * @param intakeExtendControl 展開するときを負
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

  public void intakeExtendOpen(){
    intakeExtendControl(-Const.Speeds.IntakeExtendOpen);
  }

  public void intakeExtendClose(){
    intakeExtendControl(Const.Speeds.IntakeExtendOpen);
  }

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
    // TODO Auto-generated method stub

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
