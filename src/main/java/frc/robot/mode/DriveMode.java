package frc.robot.mode;

import edu.wpi.first.wpilibj.XboxController;
import frc.robot.State;
import frc.robot.State.DriveSpeed;
import frc.robot.State.ConveyorState;
import frc.robot.State.Modes;;

public class DriveMode extends Mode {
  
  @Override
  public void changeMode() {
    if(driveController.getBackButton()){
      State.mode = Modes.k_conveyor;
    }else if(driveController.getStartButton() && driveController.getBackButton()){
      State.mode = Modes.k_climb;
    }
  
  

    if(driveController.getAButton()){
      State.is_intakeExtendOpen = false;
    }else{
      State.is_intakeExtendOpen = true;
    }

  }

  @Override
  public void changeState() {
      // TODO Auto-generated method stub
      
  }
  
  
}
