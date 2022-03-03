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
  private DigitalInput ballSensor;
  private TalonSRX conveyorExtend;

  public Conveyor() {
    intakeRoller = new VictorSPX(Const.IntakeRollerPort);
    intakeBelt = new TalonSRX(Const.IntakeBeltMotorPort);
    launchMotor = new TalonSRX(Const.LaunchMotorPort);
    conveyorExtend =new TalonSRX(Const.ConveyorExtendPort);
    
    launchMotor.configAllSettings(Const.launchMotorConfig);

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
public void intakeConveyor(double intakeRollerIntakeSpeed, double intakeBeltIntakeSpeed, double launchIntakeSpeed){
    intakeRoller.set(ControlMode.PercentOutput, intakeRollerIntakeSpeed * Const.IntakeRollerIntake);
    intakeBelt.set(ControlMode.PercentOutput, intakeBeltIntakeSpeed * Const.IntakeBeltIntake);
}
public void outtakeConveyor(double intakeRollerOuttakeSpeed, double intakeBeltOuttakeSpeed, double launchOuttakeSpeed){
    intakeRoller.set(ControlMode.PercentOutput, intakeRollerOuttakeSpeed * Const.IntakeRolleOuttake);
    intakeBelt.set(ControlMode.PercentOutput, intakeBeltOuttakeSpeed * Const.IntakeBeltOuttake);
    launchMotor.set(ControlMode.PercentOutput, launchOuttakeSpeed * Const.LaunchOuttake);
}
public void shootConveyor(double intakeRollerShootSpeed, double intakeBeltShootSpeed, double launchShootSpeed){
    intakeRoller.set(ControlMode.PercentOutput, intakeRollerShootSpeed * Const.IntakeBeltShoot);
    intakeBelt.set(ControlMode.PercentOutput, intakeRollerShootSpeed * Const.IntakeBeltShoot);
    launchMotor.set(ControlMode.PercentOutput, launchShootSpeed * Const.LaunchShoot);
}
public void stopConveyor(double intakeRollerStop, double intakeBeltStop, double launchStop){
    intakeRoller.set(ControlMode.PercentOutput, intakeRollerStop * Const.IntakeRollerStop);
    intakeBelt.set(ControlMode.PercentOutput, intakeRollerStop * Const.IntakeBeltStop);
    launchMotor.set(ControlMode.PercentOutput, launchStop * Const.LaunchStop);
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
        outtakeConveyor(Const.IntakeBeltOuttake , Const.IntakeRolleOuttake, Const.LaunchOuttake);
        break;
      case s_intakeConveyor:
        intakeConveyor(Const.IntakeRollerIntake, Const.IntakeBeltIntake, Const.LaunchStop);
        break;
      case s_shootConveyor:
        shootConveyor(Const.IntakeRollerStop , Const.IntakeBeltShoot, Const.LaunchShoot);
        break;
      case s_stopConveyor:
          stopConveyor(Const.IntakeRollerStop,Const.IntakeBeltStop,Const.LaunchStop);
          break;

    }
  }
  
}
