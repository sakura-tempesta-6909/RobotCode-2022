package frc.robot.mode;

import frc.robot.State;
import frc.robot.mode.Mode;
import frc.robot.State.ConveyorState;
import frc.robot.State.Modes;

public class ClimbMode extends Mode {

  @Override
  public void changeMode() {
    if(driveController.getBackButton()){
      State.mode = Modes.k_drive;
    }
}    
  

  @Override
  public void changeState() {
    if(driveController.getAButton()){
      State.is_solenoidFrontOpen = false;
    } else {
      State.is_solenoidFrontOpen = true;
    }

    if(driveController.getBButton()){
      State.is_solenoidBackOpen = false;
    } else {
      State.is_solenoidBackOpen = true;
    }
    if(driveController.getRightBumper() && driveController.getLeftBumper()){     
      State.is_clampSolenoid = false;
    } 
  }
  
}
