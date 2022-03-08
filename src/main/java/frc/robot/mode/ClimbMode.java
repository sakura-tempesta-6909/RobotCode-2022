package frc.robot.mode;

import frc.robot.State;
import frc.robot.mode.Mode;
import frc.robot.subClass.Const;
import frc.robot.State.ConveyorState;
import frc.robot.State.Modes;

public class ClimbMode extends Mode {

  @Override
  public void changeMode() {
    if(driveController.getPOV() == Const.POV90Degrees){
      State.mode = Modes.k_conveyor;
    } else if(driveController.getPOV() == Const.POV270Degrees){
      State.mode = Modes.k_drive;
    } 
}    
  

  @Override
  public void changeState() {
    if(driveController.getAButton()){
      State.is_firstSolenoidOpen = true;
    } else {
      State.is_firstSolenoidOpen = false;
    }

    if(driveController.getBButton()){
      State.is_secondSolenoidOpen = true;
    } else {
      State.is_secondSolenoidOpen = false;
    }
    if(driveController.getRightBumper() && driveController.getLeftBumper()){     
      State.is_climbSolenoidOpen = true;
    } else {
      State.is_climbSolenoidOpen = false;
    }
  }
  
}
