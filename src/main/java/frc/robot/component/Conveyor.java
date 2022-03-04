package frc.robot.component;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.SensorCollection;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.DigitalInput;
import frc.robot.subClass.Const;
import frc.robot.State;

public class Conveyor implements Component {

  private VictorSPX intakeRoller;
  private TalonSRX intakeBelt, launchMotor;
  private DigitalInput ballSensor, limitSwitch;
  private TalonSRX intakeExtend, backPlate;

  public Conveyor() {
    intakeRoller = new VictorSPX(Const.IntakeRollerPort);
    intakeBelt = new TalonSRX(Const.IntakeBeltMotorPort);
    launchMotor = new TalonSRX(Const.LaunchMotorPort);
    intakeExtend = new TalonSRX(Const.ConveyorExtendPort);
    backPlate = new TalonSRX(Const.BackPlatePort);
    limitSwitch = new DigitalInput(Const.LimitSwitch);
    
    launchMotor.configAllSettings(Const.LaunchMotorConfig);

    /**バックプレート操作用のモーターのセット */

    ballSensor = new DigitalInput(Const.BallSensorPort);

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
    conveyorControl(Const.IntakeRollerIntake, Const.IntakeBeltIntake, 0);
  }

  public void outtakeConveyor(){
    conveyorControl(-Const.IntakeRolleOuttake, -Const.IntakeBeltOuttake, -Const.LaunchOuttake);
  }

  public void shootConveyor(){
    conveyorControl(0, Const.IntakeBeltShoot, Const.LaunchShoot);
  }

  public void stopConveyor(){
    conveyorControl(0, 0, 0);
  }

  /**
   * 
   * @param intakeRollerSpeed intakeを正
   * @param intakeBeltSpeed intakeを正
   * @param launchSpeed intakeを正
   */
  public void conveyorControl(double intakeRollerSpeed, double intakeBeltSpeed, double launchSpeed){
    intakeRoller.set(ControlMode.PercentOutput, intakeRollerSpeed);
    intakeBelt.set(ControlMode.PercentOutput, intakeBeltSpeed);
    launchMotor.set(ControlMode.PercentOutput, launchSpeed);
  }

  public void conveyorExtendOpen(){

  }
  
  public void conveyorExtendClose(){

  }

  public void backPlateMove(double angle){
  }

  public void setIntakeExtend(double intakeExtendSpeed){
    if(intakeExtendSpeed > 0){
      if(limitSwitch.get()){
        intakeExtend.set(ControlMode.PercentOutput, 0);
      } else {
        intakeExtend.set(ControlMode.PercentOutput, intakeExtendSpeed);
      }
    }
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
    }
    
    if(State.is_intakeExtendOpen){
      conveyorExtendOpen();
    } else {
      conveyorExtendClose();
    }
  }
  
}
