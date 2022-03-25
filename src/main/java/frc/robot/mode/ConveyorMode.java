package frc.robot.mode;

import frc.robot.State;
import frc.robot.subClass.Const;
import frc.robot.State.ConveyorState;
import frc.robot.State.Modes;
import frc.robot.State.DriveState;

public class ConveyorMode extends Mode {

  @Override
  public void changeMode() {
    /**
     * RightBumperを押したら、driveModeになる
     * StartButtonとBackButtonを押したら、climbModeになる
     */
    if(driveController.getRightBumper()){
      State.mode = Modes.k_drive;
    } else if(driveController.getStartButton() && driveController.getBackButton()){
      State.mode = Modes.k_climb;
    }

  }

  @Override
  public void changeState() {
    /**
     * conveyorModeはmidDrive(0.5)の速度で走る
     * Leftを倒すと前後、Rightを倒すと左右に動く
     */
    State.driveState = DriveState.s_midDrive;
    State.driveXSpeed = -driveController.getLeftY();
    State.driveZRotation = driveController.getRightX();

    /**
     * RightTriggerを押すとボールを発射する
     */
    if(driveController.getRightTriggerAxis() > Const.Other.TriggerValue){
      State.conveyorState = ConveyorState.s_shootConveyor;
    }
  }

}
