package frc.robot.mode;

import frc.robot.State;
import frc.robot.mode.Mode;
import frc.robot.State.ConveyorState;
import frc.robot.State.Modes;

public class ConveyorMode extends Mode {

  @Override
  public void changeMode() {
    if(driveController.getRightTriggerAxis() == 1){
      State.mode = Modes.k_drive;
    } else if(driveController.getStartButton() && driveController.getBackButton()){
      State.mode = Modes.k_climb;
    }
    
  }

  @Override
  public void changeState() {
    if(driveController.getBButton()){
      State.conveyorState = ConveyorState.s_shootConveyor;
    } 
  }
  
}
