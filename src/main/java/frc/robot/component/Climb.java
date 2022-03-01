package frc.robot.component;

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

   Compressor compressor; 
   Solenoid solenoidRight, solenoidLeft;
   Solenoid solenoidClamp;

   public Climb() {
     compressor = new Compressor(Const.CompressorPort, PneumaticsModuleType.CTREPCM);
     solenoidRight = new Solenoid(PneumaticsModuleType.CTREPCM, Const.SolenoidRightPort);
     solenoidLeft = new Solenoid(PneumaticsModuleType.CTREPCM, Const.SolenoidLeftPort);
     solenoidClamp = new Solenoid(PneumaticsModuleType.CTREPCM, Const.SolenoidClampPort);

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
    // TODO Auto-generated method stub
  }
  
}
