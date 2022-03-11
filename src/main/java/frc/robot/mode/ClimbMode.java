package frc.robot.mode;

import frc.robot.State;
import frc.robot.mode.Mode;
import frc.robot.subClass.Const;
import frc.robot.State.ClimbArmState;
import frc.robot.State.ConveyorState;
import frc.robot.State.Modes;
import frc.robot.State.DriveSpeed;

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
    State.driveSpeed = DriveSpeed.s_midDrive;
    State.driveXSpeed = driveController.getLeftY();
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
    if(driveController.getRightStickButton() && driveController.getLeftStickButton() && driveController.getPOV() == Const.POV180Degrees){  
      State.is_climbSolenoidOpen = true;
    } else {
      State.is_climbSolenoidOpen = false;
    }
  }
}
