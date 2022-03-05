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
    compressor = new Compressor(Const.CompressorPort, PneumaticsModuleType.CTREPCM);
    firstSolenoid = new Solenoid(PneumaticsModuleType.CTREPCM, Const.BackSolenoidPort);
    secondSolenoid = new Solenoid(PneumaticsModuleType.CTREPCM, Const.BackSolenoidPort);
    climbSolenoid = new Solenoid(PneumaticsModuleType.CTREPCM, Const.ClimbSolenoidPort);
    climbArm = new CANSparkMax(Const.ClimbArmPort, CANSparkMaxLowLevel.MotorType.kBrushless);
  }

  public void frontSpin(){
    climbControl(Const.ClimbArmFrontSpin);
  }   
  public void backSpin(){
    climbControl(-Const.ClimbArmBackSpin);
  }
  /**
   * 
   * @param climbSpinSpeed 時計回りを正
   */
  public void climbControl(double climbSpinSpeed){
    climbArm.set(climbSpinSpeed);
  }

  /**
   * @param firstSolenoid falseで伸びている
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
   * @param secondSoenoid falseで伸びている
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
   *  @param climbSolenoid trueで伸びている
   */
  public void climbSolenoidControl(boolean climbSolenoidControl){
    climbSolenoid.set(climbSolenoidControl);
  }
  public void climbSolenoidExtend(){
    climbSolenoidControl(true);
  }
  public void climbSolenoidShrink(){
    climbSolenoidControl(false);
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
    if(State.is_solenoidFrontOpen){
      firstSolenoidOpen();
    } else {
      firstSolenoidClose();
    }
    if(State.is_solenoidBackOpen){
      secondSolenoidClose();
    } else {
      secondSolenoidOpen();
    }
    if(State.is_climbSolenoidOpen){
      climbSolenoidExtend();
    } else {
      climbSolenoidShrink();
    }
  }
  
}
