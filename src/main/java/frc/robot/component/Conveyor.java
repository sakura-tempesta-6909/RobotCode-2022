package frc.robot.component;

import com.ctre.phoenix.motorcontrol.SensorCollection;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import frc.robot.subClass.Const;

public class Conveyor implements Component {

  private VictorSPX intakeRoller;
  private WPI_TalonSRX intakeBelt, LaunchMotor;
  private DigitalInput ballSensor;

  public Conveyor() {
    intakeRoller = new VictorSPX(Const.IntakeRoller);
    intakeBelt = new WPI_TalonSRX(Const.IntakeBeltMotor);
    LaunchMotor = new WPI_TalonSRX(Const.LaunchMotor);
    
    LaunchMotor.configAllSettings(Const.launchMotorConfig);

    /**バックプレート操作用のモーターのセット */

    ballSensor = new DigitalInput(Const.BallSensor);

  }
  /**  バックプレートのそうさ
 * シューターの速さ（距離に応じて）
 * インテークベルトのそうさ（センサー類を使って詰まらないようにする）
 * シューターモーターの上下（クライム中は上がっている等）
 * シューターモーターはモードによって動きが変わるはず
 * ボールが詰まったときの対処
 * 他にもあった方がよさそうな機能
*/

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
    // TODO Auto-generated method stub
    
  }
  
}
