package frc.robot.component;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.SparkMaxPIDController;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import frc.robot.State;
import frc.robot.subClass.Const;



public class Conveyor implements Component {

  private VictorSPX intakeRoller;
  private DigitalInput ballSensorIntake, ballSensorShooter;;
  private TalonSRX intakeBelt;
  private CANSparkMax shooter;
  private SparkMaxPIDController shooterPIDController;
  private Solenoid intakeExtend;
  
  /**
   * モーターの初期化、モーター・センサーの反転
   */
  public Conveyor() {
    intakeRoller = new VictorSPX(Const.Ports.IntakeRoller);
    intakeBelt = new TalonSRX(Const.Ports.IntakeBeltMotor);
    shooter = new CANSparkMax(Const.Ports.Shooter, CANSparkMaxLowLevel.MotorType.kBrushless);
    shooterPIDController = shooter.getPIDController();


    /* ShooterのPIDの設定 */
    Const.Pid.shooterPidSet(shooterPIDController);
   
    intakeExtend = new Solenoid(PneumaticsModuleType.CTREPCM, Const.Ports.ConveyorExtend);


    ballSensorIntake = new DigitalInput(Const.Ports.BallSensorIntake);
    ballSensorShooter = new DigitalInput(Const.Ports.BallSensorShooter);
    intakeRoller.setInverted(true);
    shooter.setInverted(true);
    shooter.getEncoder().setVelocityConversionFactor(-1);

  }
  
   /**
   * CARGOを回収する
   */

  public void intakeConveyor(){
    switch(State.ballQuantity){
      case s_ballQuantity0:
      if(ballDetectionIntake()){
        if(ballDetectionShoot()){
          conveyorNutral();
          State.ballQuantity = State.BallQuantity.s_ballQuantity2;
        }else{
          beltRollerIntake();
          State.ballQuantity = State.BallQuantity.s_ballQuantity1;
        }
      }else{
        if(ballDetectionShoot()){
          conveyorNutral();
          State.ballQuantity = State.BallQuantity.s_ballQuantity1;
        }else{
          beltRollerIntake();
        }
      }
      break;
      case s_ballQuantity1:
        if(ballDetectionIntake()){
          if(ballDetectionShoot()){
            conveyorNutral();
            State.ballQuantity = State.BallQuantity.s_ballQuantity2;
          }else{
            beltRollerIntake();
          }
        }else{
          if(ballDetectionShoot()){
            conveyorNutral();
          }else{
            rollerIntake();
          }
        }
      break;
      case s_ballQuantity2:
        if (ballDetectionIntake()){
          if(ballDetectionShoot()){
            conveyorNutral();
          }else{
            beltRollerIntake();
          }
        }else{
          if(ballDetectionShoot()){
            conveyorNutral();
          }else{
            conveyorNutral();
            State.ballQuantity = State.BallQuantity.s_ballQuantity0;
          }
        }
      break;
    }
  } 

    //センサーが認識しているときballDetection*** methodはtrue
  public boolean ballDetectionIntake(){
    return !ballSensorIntake.get();
  }

  public boolean ballDetectionShoot(){
    return !ballSensorShooter.get();
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
    State.ballQuantity = State.BallQuantity.s_ballQuantity0;

    if(State.shooterSpeed > Const.Speeds.ShooterShootThresholdSpeed){
      conveyorControl(Const.Speeds.Neutral, Const.Speeds.Neutral, Const.Speeds.ShooterShoot);
    } else {
      conveyorControl(Const.Speeds.Neutral, Const.Speeds.Neutral, Const.Speeds.ShooterShoot);
    }
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
   * conveyor関係のモーターを動かす
   * @param intakeRollerSpeed intakeを正
   * @param intakeBeltSpeed intakeを正
   * @param shooterSpeed intakeを正
   */
  public void conveyorControl(double intakeRollerSpeed, double intakeBeltSpeed, double shooterSpeed){
    intakeRoller.set(ControlMode.PercentOutput, intakeRollerSpeed);
    intakeBelt.set(ControlMode.PercentOutput, intakeBeltSpeed);
    if(shooterSpeed == Const.Speeds.Neutral){
      shooter.stopMotor();
    } else if(shooterSpeed <= Const.Speeds.ShooterOuttake){
      shooter.set(shooterSpeed);
    } else {
      shooterPIDController.setReference(shooterSpeed * Const.Speeds.shooterMaxOutput,CANSparkMax.ControlType.kVelocity);
    }
  }

  /**
   * intakeExtendを動かす
   * @param intakeExtendControl 展開するときをtrue
   */
  public void intakeExtendControl(boolean intakeExtendControl){
    if(State.ballQuantity == State.BallQuantity.s_ballQuantity2){
      intakeExtendControl = false;
    }
    intakeExtend.set(intakeExtendControl);
  }

  /**
   * intakeExtendをopenする
   */
  public void intakeExtendOpen(){
    intakeExtendControl(true);
  }

  /**
   * intakeExtendをcloseする
   */
  public void intakeExtendClose(){
    intakeExtendControl(false);
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
    State.shooterSpeed = shooter.getEncoder().getVelocity();
    State.intakeSensorJudge = ballDetectionIntake();
    State.shooterSensorJudge = ballDetectionShoot();
    State.voltage.put("Intake",intakeRoller.getBusVoltage());
    State.voltage.put("IntakeBelt",intakeBelt.getBusVoltage());
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

  if(State.is_intakeExtendOpen){
    intakeExtendOpen();
  } else {
    intakeExtendClose();
  }
  }

}
