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
    if(Mode.Button2.get()){
      State.mode = Modes.k_drive;
    } else if(Mode.Button7.get() && Mode.Button8.get()){
      State.mode = Modes.k_climb;
    } else if(Mode.Button11.get() && Mode.Button12.get()){
      State.mode = Modes.k_climb;
  }

  }

  @Override
  public void changeState() {
    // conveyorModeはmidDriveで走る
    // LY: 前後、RX: 左右
     
    State.driveState = DriveState.s_midDrive;
    State.driveXSpeed = -driveJoyStick.getY();
    State.driveZRotation = driveJoyStick.getX();

    // RT: ボール発射
    if(driveJoyStick.getZ() > Const.Other.JoyStickRight){
      State.conveyorState = ConveyorState.s_shootConveyor;
    }

    if(Mode.Button3.get()){
      State.driveState = DriveState.s_pidDrive;
      State.drivePidSetMeter = -Const.AutonomousConst.ShootLengthFromFender;
      
    }

    if(Mode.Button4.get()){
      State.driveAccumulateReset = true;
      
    }
  }

}
