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

    if(driveController.getRightTriggerAxis() > Const.LargeTriggerValue){
      State.climbArmState = ClimbArmState.s_fastClimbArmFrontSpin;
    } else if(driveController.getLeftTriggerAxis() > Const.LargeTriggerValue){
      State.climbArmState = ClimbArmState.s_fastClimbArmBackSpin;
    } else if(driveController.getRightTriggerAxis() > Const.LittleTriggerValue){
      State.climbArmState = ClimbArmState.s_midClimbArmFrontSpin;
    } else if(driveController.getLeftTriggerAxis() > Const.LittleTriggerValue){
      State.climbArmState = ClimbArmState.s_midClimbArmBackSpin;
    } else {
      State.climbArmState = ClimbArmState.s_climbArmNeutral;
    }
  }
}
