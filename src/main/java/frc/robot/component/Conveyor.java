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
public void conveyorControl(double intakeRollerSpeed, double intakeBeltSpeed, double launchSpeed){
    conveyorControl(intakeRollerSpeed, intakeBeltSpeed, launchSpeed);
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
        conveyorControl(Const.IntakeBeltOuttake * State.intakeBeltSpeed, Const.IntakeRolleOuttake, Const.LaunchOuttake);
        break;
      case s_intakeConveyor:
        conveyorControl(Const.IntakeRollerIntake * State.intakeRollerSpeed, Const.IntakeBeltIntake * State.intakeBeltSpeed, Const.LaunchStop);
        break;
      case s_shooting:
        conveyorControl(Const.IntakeRollerStop * State.intakeRollerSpeed, Const.IntakeBeltShoot * State.intakeBeltSpeed, Const.LaunchShoot * State.launchSpeed);
        break;
      case s_stopConveyor:
          conveyorControl(Const.IntakeRollerStop * State.intakeRollerSpeed, Const.IntakeBeltStop * State.intakeBeltSpeed, Const.LaunchStop * State.launchSpeed);
          break;

    }
  }
  
}
