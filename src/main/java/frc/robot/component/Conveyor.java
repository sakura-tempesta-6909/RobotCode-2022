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
  private TalonSRX intakeBelt, LaunchMotor;
  private DigitalInput ballSensor;
  private TalonSRX conveyorExtend;

  public Conveyor() {
    intakeRoller = new VictorSPX(Const.IntakeRollerPort);
    intakeBelt = new TalonSRX(Const.IntakeBeltMotorPort);
    LaunchMotor = new TalonSRX(Const.LaunchMotorPort);
    
    LaunchMotor.configAllSettings(Const.launchMotorConfig);

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
public void firing(){
  intakeRoller.set(ControlMode.PercentOutput, Const.IntakeRollerFiring);
  intakeBelt.set(ControlMode.PercentOutput, Const.IntakeBeltFiringing);
  conveyorExtend.set(ControlMode.PercentOutput, Const.ConveyorExtendFiring);
}  
public void shootingConveyor(){
  intakeBelt.set(ControlMode.PercentOutput, Const.IntakeBeltShooting);
  conveyorExtend.set(ControlMode.PercentOutput, Const.ConveyorExtendShooting);
}
public void collection(){
  intakeRoller.set(ControlMode.PercentOutput, Const.intakeRollerCollection);
  intakeBelt.set(ControlMode.PercentOutput, Const.IntakeBeltCollection);
  conveyorExtend.set(ControlMode.PercentOutput, Const.ConveyorExtendCollection);
}
public void stopConveyor(){
  intakeRoller.set(ControlMode.PercentOutput, 0);
  intakeBelt.set(ControlMode.PercentOutput, 0);
  conveyorExtend.set(ControlMode.PercentOutput, 0);
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
      case s_firingConveyor:
        firing();
        break;
      case s_collectionConveyor:
        collection();
        break;
      case s_shooting:
        shootingConveyor();
        break;
      case s_stopConveyor:
          stopConveyor();
          break;

    }
  }
  
}
