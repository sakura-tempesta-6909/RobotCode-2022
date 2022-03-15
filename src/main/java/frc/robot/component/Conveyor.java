package frc.robot.component;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.SensorCollection;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.TalonSRXConfiguration;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.subClass.Const;
import frc.robot.State;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;



public class Conveyor implements Component {

  private VictorSPX intakeRoller;
  private TalonSRX intakeBelt, launchMotor;
  private DigitalInput ballSensor;
  private TalonSRX intakeExtend, backPlate;
  

  public Conveyor() {
    intakeRoller = new VictorSPX(Const.IntakeRollerPort);
    intakeBelt = new TalonSRX(Const.IntakeBeltMotorPort);
    launchMotor = new TalonSRX(Const.LaunchMotorPort);
    intakeExtend = new TalonSRX(Const.ConveyorExtendPort);
    backPlate = new TalonSRX(Const.BackPlatePort);
    intakeExtend.configAllSettings(Const.intakeExtendConfig);
    launchMotor.configAllSettings(Const.LaunchMotorConfig);

    /**バックプレート操作用のモーターのセット */

    ballSensor = new DigitalInput(Const.BallSensorPort);
    intakeRoller.setInverted(true);
    intakeExtend.setInverted(false);
  

  }
  /**  バックプレートのそうさ
   * シューターの速さ（距離に応じて）
   * インテークベルトのそうさ（センサー類を使って詰まらないようにする）
   * シューターモーターの上下（クライム中は上がっている等）
   * シューターモーターはモードによって動きが変わるはず
   * ボールが詰まったときの対処
   * 他にもあった方がよさそうな機能
  */

  public void intakeConveyor(){
    conveyorControl(Const.RollerIntake, Const.BeltIntake, Const.Neutral);
  }
 
  public void outtakeConveyor(){
    conveyorControl(-Const.RollerOuttake, -Const.BeltOuttake, -Const.ShooterOuttake);
  }

  public void shootConveyor(){
    conveyorControl(Const.Neutral, Const.BeltIntake, Const.ShooterShoot);
  }

  public void stopConveyor(){
    conveyorControl(Const.Neutral, Const.Neutral, Const.Neutral);
  }
  public void beltIntake(){
    conveyorControl(Const.Neutral, Const.BeltIntake, Const.Neutral);
  }

  public void beltOuttake(){
    conveyorControl(Const.Neutral, -Const.BeltOuttake, Const.Neutral);
  }

  public void rollerIntake(){
    conveyorControl(Const.RollerIntake, Const.Neutral, Const.Neutral);
  }

  public void rollerOuttake(){
    conveyorControl(-Const.RollerOuttake, Const.Neutral, Const.Neutral);
  }

  public void shooterShoot(){
    conveyorControl(Const.Neutral, Const.Neutral, Const.ShooterShoot);
  }

  public void shooterOuttake(){
    conveyorControl(Const.Neutral, Const.Neutral, -Const.ShooterOuttake);
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
   * @param intakeExtendControl 展開するときを正
   */
  public void intakeExtendControl(double intakeExtendControl){
    // if (intakeExtendControl > 0) {
    //   intakeExtend.selectProfileSlot(Const.ExtendPIDslot, 0);
    //   intakeExtend.set(ControlMode.Velocity, intakeExtendControl);
    

    // } else if(intakeExtendControl < 0){
    //   intakeExtend.selectProfileSlot(Const.UpPIDslot, 0);
    //   intakeExtend.set(ControlMode.Velocity, intakeExtendControl);
    // } else {
    //   intakeExtend.set(ControlMode.Velocity, Const.Neutral);
    // }
    intakeExtend.set(ControlMode.PercentOutput, intakeExtendControl);
  }

  public void intakeExtendOpen(){
    intakeExtendControl(Const.IntakeExtendOpen);
  }

  public void intakeExtendClose(){
    intakeExtendControl(-Const.IntakeExtendOpen);
  }

  public void intakeExtendNeutral(){
    intakeExtendControl(Const.Neutral);
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
        intakeExtendControl(State.intakeExtendSpeed * Const.IntakeExtendOpen);
        break;
      case s_intakeExtendOpen:
        intakeExtendOpen();
        break;
      case s_intakeExtendClose:
        intakeExtendClose();
        break;
      case s_intakeExtendNeutral:
        intakeExtend.setNeutralMode(NeutralMode.Brake);
        break;  
    }
  }
  
}
