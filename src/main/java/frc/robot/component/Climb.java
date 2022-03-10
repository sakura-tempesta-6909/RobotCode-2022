package frc.robot.component;

// import com.ctre.phoenix.motorcontrol.ControlMode;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
// import com.revrobotics.SparkMaxAlternateEncoder;

// import edu.wpi.first.hal.CTREPCMJNI;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedController;
import frc.robot.subClass.Const;
import frc.robot.State;

public class Climb implements Component {

  /**クライム機構展開
   * クライムが展開したことを確認する
   * ジャイロ？かセンサー使ってクランプの傾きを把握する(MagEncoder)
   * クランプの開閉
   * 
   */

  /**
   * 
   */
 

  private Compressor compressor; 
  private Solenoid firstSolenoid, secondSolenoid;
  private Solenoid climbSolenoid;
  private CANSparkMax climbArm;

   

  public Climb() {
    compressor = new Compressor(Const.CompressorPort, PneumaticsModuleType.CTREPCM);
    firstSolenoid = new Solenoid(PneumaticsModuleType.CTREPCM, Const.FirstSolenoidPort);
    secondSolenoid = new Solenoid(PneumaticsModuleType.CTREPCM, Const.SecondSolenoidPort);
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

  public void compressorDisable(){
    compressor.disable();
  }

  public void compressorEnable(){
    compressor.enableDigital();
    boolean enabled = compressor.enabled();
  }


  public void climbSolenoidExtend(){
    climbSolenoidControl(true);
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
