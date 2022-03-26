package frc.robot.component;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import frc.robot.State;
import frc.robot.subClass.Const;
import frc.robot.subClass.Util;

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
    climbArm.setSmartCurrentLimit(Const.Other.ClimbArmCurrentLimit);
    
  }


  public double angleToRevolution(double angle){
    return angle / Const.Calculation.DegreesPerRevolution;
  }

  public double revolutionToAngle(double revolution){
    return Const.Calculation.DegreesPerRevolution * revolution;

  }

  public double getClimbArmAngle(){
    return Util.mod(revolutionToAngle(climbArmEncoder.getPosition()), Const.Calculation.FullTurnAngle);
  }

  public void setClimbArmAngle(double climbArmTaregetAngle){
    double angle = getClimbArmAngle();
    double fast_angle = 20;
    if(Util.is_angleInRange(climbArmTaregetAngle - 3, climbArmTaregetAngle + 3, angle)){
      climbControl(Const.Speeds.Neutral);
    }else if(Util.is_angleInRange(climbArmTaregetAngle + fast_angle, climbArmTaregetAngle + 180, angle)){
      climbControl(-Const.Speeds.MidClimbArmSpin);
    } else if(Util.is_angleInRange(climbArmTaregetAngle - 180, climbArmTaregetAngle - fast_angle, angle)) {
      climbControl(Const.Speeds.MidClimbArmSpin);
    } else if(Util.is_angleInRange(climbArmTaregetAngle, climbArmTaregetAngle + fast_angle, angle)){
      climbControl(-Const.Speeds.SlowClimbArmSpin);
    } else if(Util.is_angleInRange(climbArmTaregetAngle - fast_angle, climbArmTaregetAngle, angle)) {
      climbControl(Const.Speeds.SlowClimbArmSpin);
    } else {
      climbControl(Const.Speeds.MidClimbArmSpin);
    }
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

  public void climbSolenoidOpen(){
    climbSolenoidControl(true);
  }

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
    State.climbMotorIdleMode = IdleMode.kCoast;
    climbArm.setIdleMode(State.climbMotorIdleMode);
    // TODO Auto-generated method stub

  }

  @Override
  public void testInit() {
    // TODO Auto-generated method stub

  }

  @Override
  public void readSensors() {
    State.climbArmAngle = getClimbArmAngle();
    Util.sendConsole("ClimbCurrent", climbArm.getOutputCurrent());
  }

  @Override
  public void applyState() {
    climbArm.setIdleMode(State.climbMotorIdleMode);
    switch(State.climbArmState){
      case s_fastClimbArmSpin:
        climbControl(State.climbArmSpeed * Const.Speeds.FastClimbArmSpin);
        break;
      case s_midClimbArmSpin:
        climbControl(State.climbArmSpeed * Const.Speeds.MidClimbArmSpin);
        break;
      case s_setClimbArmAngle:
        setClimbArmAngle(State.climbArmTargetAngle);
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
