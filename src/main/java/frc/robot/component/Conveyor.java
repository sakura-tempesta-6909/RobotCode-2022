package frc.robot.component;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.SparkMaxPIDController;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import frc.robot.State;
import frc.robot.subClass.Const;



public class Conveyor implements Component {

  private VictorSPX intakeRoller;
  private TalonSRX intakeBelt;
  private CANSparkMax shooter;
  private DigitalInput ballSensor;
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

    ballSensor = new DigitalInput(Const.Ports.BallSensor);
    intakeRoller.setInverted(true);

  }
  
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
    shooterPIDController.setReference(shooterSpeed * Const.Other.shooterMaxOutput,CANSparkMax.ControlType.kVelocity);
  }

  /**
   * intakeExtendを動かす
   * @param intakeExtendControl 展開するときをtrue
   */
  public void intakeExtendControl(boolean intakeExtendControl){
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
    State.shooterMotorSpeed = shooter.getEncoder().getVelocity();
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
