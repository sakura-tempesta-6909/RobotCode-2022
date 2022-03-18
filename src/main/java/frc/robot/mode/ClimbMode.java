package frc.robot.mode;

import frc.robot.State;
import frc.robot.State.ClimbArmState;
import frc.robot.State.Modes;
import frc.robot.State.DriveSpeed;

public class ClimbMode extends Mode {

  @Override
  public void changeMode() {
    if(driveController.getPOV() == 90){
      State.mode = Modes.k_conveyor;
    } else if(driveController.getPOV() == 270){
      State.mode = Modes.k_drive;
    } 
}    
  

  @Override
  public void changeState() {
    State.driveSpeed = DriveSpeed.s_midDrive;
    State.driveXSpeed = -driveController.getLeftY();
    State.driveZRotation = driveController.getRightX();

    State.climbArmSpeed = driveController.getRightTriggerAxis() - driveController.getLeftTriggerAxis();
    
    if(driveController.getAButton()){
      State.climbArmState = ClimbArmState.s_fastClimbArmSpin;
    } else {
      State.climbArmState = ClimbArmState.s_midClimbArmSpin;
    }


    if(driveController.getRightBumper()){
      State.is_firstSolenoidOpen = true;
    } else {
      State.is_firstSolenoidOpen = false;
    }

    if(driveController.getLeftBumper()){
      State.is_secondSolenoidOpen = true;
    } else {
      State.is_secondSolenoidOpen = false;
    }
    if(driveController.getRightStickButton() && driveController.getLeftStickButton() && driveController.getPOV() == 180){  
      State.is_climbSolenoidOpen = true;
    } else {
      State.is_climbSolenoidOpen = false;
    }
  }
}
