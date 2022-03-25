package frc.robot.mode;

import com.revrobotics.CANSparkMax.IdleMode;

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
    State.driveState = DriveState.s_midDrive;
    State.driveXSpeed = -driveController.getLeftY();
    State.driveZRotation = driveController.getRightX();
    State.climbArmSpeed = driveController.getRightTriggerAxis() - driveController.getLeftTriggerAxis();
    State.climbMotorIdleMode = IdleMode.kBrake;
    if(driveController.getAButton()){
      State.climbArmState = ClimbArmState.s_fastClimbArmSpin;
    } else {
      State.climbArmState = ClimbArmState.s_midClimbArmSpin;
    }



    State.is_firstSolenoidOpen = driveController.getRightBumper();
    State.is_secondSolenoidOpen = driveController.getLeftBumper();

    if(driveController.getRightStickButton() && driveController.getLeftStickButton() && driveController.getPOV() == 180) {
      State.is_climbSolenoidOpen = true;
    } else {
      State.is_climbSolenoidOpen = false;
    }

  }
}
