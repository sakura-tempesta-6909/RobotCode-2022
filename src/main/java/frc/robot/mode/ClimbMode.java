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
    if(driveController.getBackButton()){
      State.mode = Modes.k_conveyor;
    } else if(driveController.getStartButton()){
      State.mode = Modes.k_drive;
    } 
}    
  

  @Override
  public void changeState() {
    State.driveSpeed = DriveSpeed.s_midDrive;
    State.driveXSpeed = driveController.getLeftY();
    State.driveZRotation = driveController.getRightX();

    State.climbArmState = ClimbArmState.s_midClimbArmFrontSpin;
    State.climbArmFrontSpeed = driveController.getRightTriggerAxis();
    
    State.climbArmState = ClimbArmState.s_midClimbArmBackSpin;
    State.climbArmBackSpeed = driveController.getLeftTriggerAxis();

    State.climbArmState = ClimbArmState.s_fastClimbArmFrontSpin;
    State.climbArmFrontSpeed = driveController.getRightTriggerAxis(); driveController.getAButton();

    State.climbArmState = ClimbArmState.s_fastClimbArmBackSpin;
    State.climbArmBackSpeed = driveController.getLeftTriggerAxis(); driveController.getAButton();
    
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
