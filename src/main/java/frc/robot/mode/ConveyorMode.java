package frc.robot.mode;

import frc.robot.State;
import frc.robot.subClass.Const;
import frc.robot.State.ConveyorState;
import frc.robot.State.Modes;
import frc.robot.State.DriveState;

public class ConveyorMode extends Mode {

  @Override
  public void changeMode() {

    // RB: driveMode
    // Start & Back: climbModeになる
    if(driveController.getRightBumper()){
      State.mode = Modes.k_drive;
    } else if(driveController.getStartButton() && driveController.getBackButton()){
      State.mode = Modes.k_climb;
    }

  }

  @Override
  public void changeState() {
    // conveyorModeはmidDriveで走る
    // LY: 前後、RX: 左右
     
    State.driveState = DriveState.s_midDrive;
    State.driveXSpeed = -driveController.getLeftY();
    State.driveZRotation = driveController.getRightX();

    // RT: ボール発射
    if(driveController.getRightTriggerAxis() > Const.Other.TriggerValue){
      State.conveyorState = ConveyorState.s_shootConveyor;
    }
  }

}
