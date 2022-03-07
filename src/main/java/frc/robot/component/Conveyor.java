package frc.robot.component;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.SensorCollection;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.subClass.Const;
import frc.robot.State;
import edu.wpi.first.wpilibj.PneumaticsModuleType;

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

    launchMotor.configAllSettings(Const.LaunchMotorConfig);

    /**バックプレート操作用のモーターのセット */

    ballSensor = new DigitalInput(Const.BallSensorPort);
    //intakeExtend.configForwardLimitSwitchSource(LimitSwitchSource.RemoteTalonSRX, LimitSwitchNormal.NormallyOpen);
    //intakeExtend.configReverseLimitSwitchSource(LimitSwitchSource.RemoteTalonSRX, LimitSwitchNormal.NormallyOpen);

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
    intakeExtend.set(ControlMode.PercentOutput, intakeExtendControl);
  }

  public void intakeExtendOpen(){
    intakeExtendControl(Const.IntakeExtendOpen);
  }
  
  public void intakeExtendClose(){
    intakeExtendControl(-Const.IntakeExtendOpen);
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
    }
    
    if(State.is_intakeExtendOpen){
      intakeExtendOpen();
    } else {
      intakeExtendClose();
    }
  }
  
}
