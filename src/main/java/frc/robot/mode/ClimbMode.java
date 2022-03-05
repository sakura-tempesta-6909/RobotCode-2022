package frc.robot.mode;

import frc.robot.State;
import frc.robot.mode.Mode;
import frc.robot.State.ConveyorState;
import frc.robot.State.Modes;

public class ClimbMode extends Mode {

  @Override
  public void changeMode() {
    if(driveController.getBackButton()){
      State.mode = Modes.k_conveyor;
    } else if(driveController.getStartButton()){
      State.mode = Modes.k_drive;
    }
}    
  

  @Override
  public void changeState() {
    if(driveController.getAButton()){
      State.is_solenoidFrontOpen = true;
    } else {
      State.is_solenoidFrontOpen = false;
    }

    if(driveController.getBButton()){
      State.is_solenoidBackOpen = true;
    } else {
      State.is_solenoidBackOpen = false;
    }
    if(driveController.getRightBumper() && driveController.getLeftBumper()){     
      State.is_climbSolenoidOpen = true;
    } else {
      State.is_climbSolenoidOpen = false;
    }
  }
  
}
