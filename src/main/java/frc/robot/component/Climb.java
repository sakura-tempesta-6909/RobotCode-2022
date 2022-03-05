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
  private Solenoid frontSolenoid, backSolenoid;
  private Solenoid clampSolenoid;
  private CANSparkMax climbArm;

   

  public Climb() {
    compressor = new Compressor(Const.CompressorPort, PneumaticsModuleType.CTREPCM);
    frontSolenoid = new Solenoid(PneumaticsModuleType.CTREPCM, Const.BackSolenoidPort);
    backSolenoid = new Solenoid(PneumaticsModuleType.CTREPCM, Const.BackSolenoidPort);
    clampSolenoid = new Solenoid(PneumaticsModuleType.CTREPCM, Const.ClampSolenoidPort);
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

 
  public void frontSolenoidOpen(){
    frontSolenoidControl(false);;
  }

  public void frontSolenoidClose(){
    frontSolenoidControl(true);
  }

   /**
   * @param frontSolenoid 縮んでいる状態をtrue
   */
  public void frontSolenoidControl(boolean frontSolenoidControl){
    frontSolenoid.set(frontSolenoidControl);
  }

 
  public void backSolenoidOpen(){
    backSolenoidControl(false);
  }

  public void backSolenoidClose(){
    backSolenoidControl(true);;
  }

   /**
   * @param backSoenoid 縮んでいる状態をtrue
   */
  public void backSolenoidControl(boolean backSolenoidControl){
    backSolenoid.set(backSolenoidControl);
  }

  
  public void clampSolenoidOpen(){
    clampSolenoidControl(false);
  }

  /**
   *  @param clampSolenoid 縮んでいる状態をtrue
   */
  public void clampSolenoidControl(boolean clampSolenoidControl){
    clampSolenoid.set(clampSolenoidControl);
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
      frontSolenoidOpen();
    } else {
      frontSolenoidClose();
    }
    if(State.is_solenoidBackOpen){
      backSolenoidOpen();
    } else {
      backSolenoidClose();
    }
    
    if(State.is_clampSolenoid){
      clampSolenoidOpen();
    } 
  }
  
}
