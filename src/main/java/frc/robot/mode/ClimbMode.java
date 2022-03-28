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
    State.driveXSpeed = -operateController.getLeftY();
    State.driveZRotation = operateController.getRightX();

    // RT: 前, LT: 後ろ
    State.climbArmSpeed = operateController.getRightTriggerAxis() - operateController.getLeftTriggerAxis();
    State.climbMotorIdleMode = IdleMode.kBrake;
    
    // A: climbArmを速くする
    if(operateController.getAButton()){
      State.climbArmState = ClimbArmState.s_fastClimbArmSpin;
    } else {
      State.climbArmState = ClimbArmState.s_midClimbArmSpin;
    }

    // RB: firstSolenoidがOpen, LB: secondSolenoidがOpen

    State.is_firstSolenoidOpen = operateController.getRightBumper();
    State.is_secondSolenoidOpen = operateController.getLeftBumper();

    // RS & LS & POV180: climbSolenoidがOpen
    if(operateController.getRightStickButton() && operateController.getLeftStickButton() && operateController.getPOV() == 180) {
      State.is_climbSolenoidOpen = true;
    } else {
      State.is_climbSolenoidOpen = false;
    }

    if(operateController.getBButton()){
      State.climbArmState = ClimbArmState.s_setClimbArmAngle;
      State.climbArmTargetAngle = Const.Other.MidRungCatchAngle;
    } else if(operateController.getYButton()){
      State.climbArmState = ClimbArmState.s_setClimbArmAngle;
      State.climbArmTargetAngle = Const.Other.MidRungGetUnderAngle;
    }
  }
}
