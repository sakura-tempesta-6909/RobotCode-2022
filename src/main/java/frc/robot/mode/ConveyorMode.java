package frc.robot.mode;

import frc.robot.State;
import frc.robot.subClass.Const;
import frc.robot.State.ConveyorState;
import frc.robot.State.Modes;
import frc.robot.State.DriveSpeed;

public class ConveyorMode extends Mode {

  @Override
  public void changeMode() {
    if(driveController.getRightBumper()){
      State.mode = Modes.k_drive;
    } else if(driveController.getStartButton() && driveController.getBackButton()){
      State.mode = Modes.k_climb;
    }

  }

  @Override
  public void changeState() {
    State.driveSpeed = DriveSpeed.s_midDrive;
    State.driveXSpeed = -driveController.getLeftY();
    State.driveZRotation = driveController.getRightX();

    if(driveController.getRightTriggerAxis() > Const.Xbox.TriggerValue){
      State.conveyorState = ConveyorState.s_shootConveyor;
    }
  }

}
