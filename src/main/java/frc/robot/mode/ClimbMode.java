package frc.robot.mode;

import com.revrobotics.CANSparkMax.IdleMode;

import frc.robot.State;
import frc.robot.State.ClimbArmState;
import frc.robot.State.Modes;
import frc.robot.subClass.Const;
import frc.robot.State.DriveState;


public class ClimbMode extends Mode {

  @Override
  public void changeMode() {

    // POV90: conveyorMode
    // POV270: driveMode
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
    State.driveXSpeed = -driveJoyStick.getY();
    State.driveZRotation = driveJoyStick.getX();

    // RT: 前, LT: 後ろ
    State.climbArmSpeed = driveJoyStick.getZ() * 0.8 - driveJoyStick.getZ();
    State.climbMotorIdleMode = IdleMode.kBrake;
    
    // A: climbArmを速くする
    if(Mode.Button2.get()){
      State.climbArmState = ClimbArmState.s_fastClimbArmSpin;
    } else {
      State.climbArmState = ClimbArmState.s_midClimbArmSpin;
    }

    // RB: firstSolenoidがOpen, LB: secondSolenoidがOpen

    State.is_firstSolenoidOpen = Mode.Button3.get();
    State.is_secondSolenoidOpen = Mode.Button4.get();

    // RS & LS & POV180: climbSolenoidがOpen
    if(driveJoyStick.getPOV() == 180) {
      State.is_climbSolenoidOpen = true;
    } else {
      State.is_climbSolenoidOpen = false;
    }

    if(Mode.Button5.get()){
      State.climbArmState = ClimbArmState.s_setClimbArmAngle;
      State.climbArmTargetAngle = Const.ClimbArm.MidRungCatchAngle;
    } else if(Mode.Button6.get()){
      State.climbArmState = ClimbArmState.s_setClimbArmAngle;
      State.climbArmTargetAngle = Const.ClimbArm.MidRungGetUnderAngle;
    }
  }
}
