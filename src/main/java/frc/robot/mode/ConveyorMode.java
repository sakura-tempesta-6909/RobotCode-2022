package frc.robot.mode;

import frc.robot.State;
import frc.robot.mode.Mode;
import frc.robot.State.ConveyorState;;

public class ConveyorMode extends Mode {

  @Override
  public void changeMode() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void changeState() {
    if(driveController.getBButton()){
      State.conveyorState = ConveyorState.s_shooting;
    } else {
      State.conveyorState = ConveyorState.s_stopConveyor;
    }
    
  }
  
}
