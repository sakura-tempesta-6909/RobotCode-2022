package frc.robot.component;


import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.RelativeEncoder;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import frc.robot.State;
import frc.robot.subClass.Const;

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
  private DigitalInput hallSensor;
  
  private static RelativeEncoder climbArmEncoder;



   
  /**
   * Motorの初期化、Motor・センサーの反転
   */
  public Climb() {

    hallSensor = new DigitalInput(Const.Ports.hallsensorPort);
    compressor = new Compressor(Const.Ports.Compressor, PneumaticsModuleType.CTREPCM);
    firstSolenoid = new Solenoid(PneumaticsModuleType.CTREPCM, Const.Ports.FirstSolenoid);
    secondSolenoid = new Solenoid(PneumaticsModuleType.CTREPCM, Const.Ports.SecondSolenoid);
    climbSolenoid = new Solenoid(PneumaticsModuleType.CTREPCM, Const.Ports.ClimbSolenoid);
    if(State.is_climbArmMotorNEO){
      climbArm =  new CANSparkMax(Const.Ports.ClimbArm, CANSparkMaxLowLevel.MotorType.kBrushless);
      climbArmEncoder = climbArm.getEncoder();
    } else {
      climbArm =  new CANSparkMax(Const.Ports.ClimbArm, CANSparkMaxLowLevel.MotorType.kBrushed);
      climbArmEncoder = climbArm.getAlternateEncoder(Const.Calculation.ClimbArmEncoderCount);
    }
  }


  /**
   * 
   * @param angle climbArmの角度
   * @return
   */
  public double angleToRevolution(double angle){
    return angle / Const.Calculation.DegreesPerRevolution;
  }

  /**
   * 
   * @param revolution climbArmの回転数
   * @return 回転数から角度に変換する
   */
  public double revolutionToAngle(double revolution){
    return Const.Calculation.DegreesPerRevolution * revolution;
  }

  /**
   * climbArmの角度を取得する
   * @return 回転数を使って角度を求める
   */
  public double getClimbArmAngle(){
    return revolutionToAngle(climbArmEncoder.getPosition()) % Const.Calculation.FullTurnAngle;
  }

  /**
   * ClimbArmを動かす
   * @param climbSpinSpeed 前回りを正
   */
  public void climbControl(double climbSpinSpeed){
    climbArm.set(climbSpinSpeed);
  }

  public boolean gethallSensor(){
    return !hallSensor.get();
  }

  public void resetAngle(){
    if(gethallSensor()){
      climbArmEncoder.setPosition(0);
    } else{
      return;
    }
  }

  public void startCalibration(){
    if(gethallSensor()){
      climbArm.set(0);
      resetAngle();
    } else{
      climbArm.set(0.2);
    }
  }

  /**
   *  firstSolenoidを動かす
   * @param firstSolenoidOpen falseで閉じている
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
    * secondSolenoidを動かす
   * @param secondSolenoidControl falseで閉じている
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
   *  @param climbSolenoidControl trueで伸びている
   */
  public void climbSolenoidControl(boolean climbSolenoidControl){
    climbSolenoid.set(climbSolenoidControl);
  }

  /**
   * climbSolenoidをopenする
   */
  public void climbSolenoidOpen(){
    climbSolenoidControl(true);
  }

  /**
   * climbSolenoidをcloseする
   */
  public void climbSolenoidClose(){
    climbSolenoidControl(false);
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
      case s_angleCalibration:
        startCalibration();
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
      climbSolenoidOpen();
    } else {
      climbSolenoidClose();
    }

    if(State.is_compressorEnabled){
      compressorEnable();
    } else {
      compressorDisable();
    }

  }
}
