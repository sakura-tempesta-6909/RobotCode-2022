package frc.robot.component;


import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.RelativeEncoder;
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
  private static RelativeEncoder climbArmEncoder;
  public static boolean is_climbArmMotorNEO;


   
  /**
   * Motorの初期化、Motor・センサーの反転
   */
  public Climb() {
    compressor = new Compressor(Const.Ports.Compressor, PneumaticsModuleType.CTREPCM);
    firstSolenoid = new Solenoid(PneumaticsModuleType.CTREPCM, Const.Ports.FirstSolenoid);
    secondSolenoid = new Solenoid(PneumaticsModuleType.CTREPCM, Const.Ports.SecondSolenoid);
    climbSolenoid = new Solenoid(PneumaticsModuleType.CTREPCM, Const.Ports.ClimbSolenoid);
    is_climbArmMotorNEO = false;
    if(is_climbArmMotorNEO){
      climbArm =  new CANSparkMax(Const.Ports.ClimbArm, CANSparkMaxLowLevel.MotorType.kBrushless);
      climbArmEncoder = climbArm.getEncoder();
    } else {
      climbArm =  new CANSparkMax(Const.Ports.ClimbArm, CANSparkMaxLowLevel.MotorType.kBrushed);
      climbArmEncoder = climbArm.getAlternateEncoder(Const.Counts.ClimbArmEncoderCount);
    }
    
  }

  public static double spinToAngle(double spin){
    return spin / Const.Point.DegreesPerRevolution;
  }

  public double angleToSpin(double angle){
    return Const.Point.DegreesPerRevolution * angle;
  }

  public static double getClimbArmAngle(){
    return spinToAngle(climbArmEncoder.getPosition());
  }

  /**
   * ClimbArmを動かす
   * @param climbSpinSpeed 前回りを正
   */
  public void climbControl(double climbSpinSpeed){
    climbArm.set(climbSpinSpeed);
  }

  

  /**
   *  firstSolenoidを動かす
   * @param firstSolenoid falseで閉じている
   */
  public void firstSolenoidControl(boolean firstSolenoidOpen){
    firstSolenoid.set(firstSolenoidOpen);
  }
  
  /**
   * firstSolenoidをopenする
   */
  public void firstSolenoidOpen(){
    firstSolenoidControl(true);
  }

  /**
   * firstSolenoidをcloseする
   */
  public void firstSolenoidClose(){
    firstSolenoidControl(false);
  }

   /**
    * secondSolnoidを動かす
   * @param secondSoenoid falseで閉じている
   */
  public void secondSolenoidControl(boolean secondSolenoidControl){
    secondSolenoid.set(secondSolenoidControl);
  }

  /**
   * secondSolenoidをopenする
   */
  public void secondSolenoidOpen(){
    secondSolenoidControl(true);
  }

  /**
   * secondSolenoidをcloseする
   */
  public void secondSolenoidClose(){
    secondSolenoidControl(false);
  }

  /**
   * climbSolenoidを動かす
   *  @param climbSolenoid trueで伸びている
   */
  public void climbSolenoidControl(boolean climbSolenoidControl){
    climbSolenoid.set(climbSolenoidControl);
  }

  public void climbSolenoidExtend(){
    climbSolenoidControl(true);
  }

  /**
   * compressorをdisableにする
   */
  public void compressorDisable(){
    compressor.disable();
  }

  /**
   * compressorをenableにする
   */
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
    State.climbArmAngle = getClimbArmAngle();
    
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
