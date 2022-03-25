package frc.robot.mode;

import frc.robot.State;
import frc.robot.State.ClimbArmState;
import frc.robot.State.Modes;
import frc.robot.State.DriveState;

public class ClimbMode extends Mode {

  @Override
  public void changeMode() {

    //POV90: convyoerMode
    //POV270: driveMode
    if(driveController.getPOV() == 90){
      State.mode = Modes.k_conveyor;
    } else if(driveController.getPOV() == 270){
      State.mode = Modes.k_drive;
    }
  }


  @Override
  public void changeState() {

    // climbModeはmidDriveで走る
    // LY: 前後, RX: 左右
    State.driveState = DriveState.s_midDrive;
    State.driveXSpeed = -driveController.getLeftY();
    State.driveZRotation = driveController.getRightX();

    //RT:前, LT:後ろ
    State.climbArmSpeed = driveController.getRightTriggerAxis() - driveController.getLeftTriggerAxis();

    //A:climbArmを速くする
    if(driveController.getAButton()){
      State.climbArmState = ClimbArmState.s_fastClimbArmSpin;
    } else {
      State.climbArmState = ClimbArmState.s_midClimbArmSpin;
    }


    //RB: firstSolenoidがOpen, LB: secondSolenoidがOpen

    State.is_firstSolenoidOpen = driveController.getRightBumper();
    State.is_secondSolenoidOpen = driveController.getLeftBumper();

    // RS & LS & POV180: climbSolenoidがOpen
    if(driveController.getRightStickButton() && driveController.getLeftStickButton() && driveController.getPOV() == 180) {
      State.is_climbSolenoidOpen = true;
    } else {
      State.is_climbSolenoidOpen = false;
    }

  }
}
