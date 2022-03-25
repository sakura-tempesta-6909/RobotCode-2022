package frc.robot.mode;

import frc.robot.State;
import frc.robot.State.ClimbArmState;
import frc.robot.State.Modes;
import frc.robot.State.DriveState;

public class ClimbMode extends Mode {

  @Override
  public void changeMode() {
    if(driveController.getPOV() == 90){
      State.mode = Modes.k_conveyor;
    } else if(driveController.getPOV() == 270){
      State.mode = Modes.k_drive;
    }
  }


  @Override
  public void changeState() {

    // climbModeはmidDrive(0.5)の速度で走る
    // Leftを倒すと前後、Rightを倒すと左右に動く
    State.driveState = DriveState.s_midDrive;
    State.driveXSpeed = -driveController.getLeftY();
    State.driveZRotation = driveController.getRightX();

    //RightTriggerを押すと前に回り、LeftTriggerを押すと後ろに回る
    State.climbArmSpeed = driveController.getRightTriggerAxis() - driveController.getLeftTriggerAxis();

    //どちらかのTriggerが押された状態でAButtonを押すとclimbArmが早く回る
    if(driveController.getAButton()){
      State.climbArmState = ClimbArmState.s_fastClimbArmSpin;
    } else {
      State.climbArmState = ClimbArmState.s_midClimbArmSpin;
    }


    //RightBumperを押すとfirstSolenoidがOpenし、LeftBumperを押すとsecondSolenoidがOpenする

    State.is_firstSolenoidOpen = driveController.getRightBumper();
    State.is_secondSolenoidOpen = driveController.getLeftBumper();

    // RightSticとkLeftStickとPOV180を押すとclimbSolenoidがOpenする
    if(driveController.getRightStickButton() && driveController.getLeftStickButton() && driveController.getPOV() == 180) {
      State.is_climbSolenoidOpen = true;
    } else {
      State.is_climbSolenoidOpen = false;
    }

  }
}
