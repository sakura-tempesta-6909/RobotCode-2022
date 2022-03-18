package frc.robot.component;

// import com.ctre.phoenix.motorcontrol.ControlMode;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
// import com.revrobotics.SparkMaxAlternateEncoder;

// import edu.wpi.first.hal.CTREPCMJNI;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import frc.robot.subClass.Const;
import frc.robot.State;

public class Climb implements Component {

  /**クライム機構展開
   * クライムが展開したことを確認する
   * ジャイロ？かセンサー使ってクランプの傾きを把握する(MagEncoder)
   * クランプの開閉
   *
   */

  private Compressor compressor;
  private Solenoid firstSolenoid, secondSolenoid;
  private Solenoid climbSolenoid;
  private CANSparkMax climbArm;



  public Climb() {
    compressor = new Compressor(Const.Ports.Compressor, PneumaticsModuleType.CTREPCM);
    firstSolenoid = new Solenoid(PneumaticsModuleType.CTREPCM, Const.Ports.FirstSolenoid);
    secondSolenoid = new Solenoid(PneumaticsModuleType.CTREPCM, Const.Ports.SecondSolenoid);
    climbSolenoid = new Solenoid(PneumaticsModuleType.CTREPCM, Const.Ports.ClimbSolenoid);
    //climbArm = new CANSparkMax(Const.Ports.ClimbArm, CANSparkMaxLowLevel.MotorType.kBrushless);
    climbArm = new CANSparkMax(Const.Ports.ClimbArm, CANSparkMaxLowLevel.MotorType.kBrushed);
  }

  /**
   *
   * @param climbSpinSpeed 前回りを正
   */
  public void climbControl(double climbSpinSpeed){
    climbArm.set(climbSpinSpeed);
  }

  /**
   * @param firstSolenoid falseで閉じている
   */
  public void firstSolenoidControl(boolean firstSolenoidOpen){
    firstSolenoid.set(firstSolenoidOpen);
  }

  public void firstSolenoidOpen(){
    firstSolenoidControl(true);
  }

  public void firstSolenoidClose(){
    firstSolenoidControl(false);
  }

  /**
   * @param secondSoenoid falseで閉じている
   */
  public void secondSolenoidControl(boolean secondSolenoidControl){
    secondSolenoid.set(secondSolenoidControl);
  }

  public void secondSolenoidOpen(){
    secondSolenoidControl(true);
  }

  public void secondSolenoidClose(){
    secondSolenoidControl(false);
  }

  /**
   *  @param climbSolenoidOwn trueで伸びている
   */
  public void climbSolenoidControl(boolean climbSolenoidControl){
    climbSolenoid.set(climbSolenoidControl);
  }

  public void climbSolenoidExtend(){
    climbSolenoidControl(true);
  }

  public void compressorDisable(){
    compressor.disable();
  }

  public void compressorEnable(){
    compressor.enableDigital();
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
    switch(State.climbArmState){
      case s_fastClimbArmSpin:
        climbControl(State.climbArmSpeed * Const.Speeds.FastClimbArmSpin);
        break;
      case s_midClimbArmSpin:
        climbControl(State.climbArmSpeed * Const.Speeds.MidClimbArmSpin);
        break;
      case s_climbArmNeutral:
        climbControl(Const.Speeds.Neutral);
        break;
    }

    if(State.is_firstSolenoidOpen){
      firstSolenoidOpen();
    } else {
      firstSolenoidClose();
    }

    if(State.is_secondSolenoidOpen){
      secondSolenoidOpen();
    } else {
      secondSolenoidClose();
    }

    if(State.is_climbSolenoidOpen){
      climbSolenoidExtend();
    }

    if(State.is_compressorEnabled){
      compressorEnable();
    } else {
      compressorDisable();
    }
  }

}
