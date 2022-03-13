package frc.robot.mode;

import edu.wpi.first.wpilibj.XboxController;
import frc.robot.State;
import frc.robot.State.DriveSpeed;
import frc.robot.State.ConveyorState;
import frc.robot.State.IntakeExtendState;
import frc.robot.State.Modes;
import frc.robot.subClass.Const;
import frc.robot.subClass.Util;

public class DriveMode extends Mode {
  
  @Override
  public void changeMode() {
    if(driveController.getLeftTriggerAxis() > Const.TriggerValue){
      State.mode = Modes.k_conveyor;
    } else if(driveController.getStartButton() && driveController.getBackButton()){
      State.mode = Modes.k_climb;
    }
  
  }

  @Override
  public void changeState() {
    State.driveSpeed = DriveSpeed.s_fastDrive;
    State.driveXSpeed = driveController.getLeftY();
    State.driveZRotation = driveController.getRightX();
     
    if(driveController.getYButton()){
      State.intakeExtendSpeed = driveController.getLeftY(); 
      State.intakeExtendState = IntakeExtendState.s_manual;
      State.driveSpeed = DriveSpeed.s_stopDrive;
    }

    

		if(driveController.getPOV() == Const.POV90Degrees && driveController.getRightStickButton() && driveController.getLeftStickButton()){
			State.is_compressorEnabled = false;
		} else if(driveController.getPOV() == Const.POV180Degrees){
			State.is_compressorEnabled = true;
		}

		if(driveController.getLeftBumper()){
			State.conveyorState = ConveyorState.s_outtakeConveyor;
		} else if(driveController.getRightBumper()){
			State.conveyorState = ConveyorState.s_intakeConveyor;
		} else {
      Util.sendConsole("POV Value", driveController.getPOV());
        if(driveController.getPOV() == 45){
          State.conveyorState = ConveyorState.s_shooterOuttake;
        } else if(driveController.getPOV() == 90){
          State.conveyorState = ConveyorState.s_rollerOuttake;
        } else if(driveController.getPOV() == 135){
          State.conveyorState = ConveyorState.s_beltOuttake;
        } else if(driveController.getPOV() == 225){
          State.conveyorState = ConveyorState.s_shooterShoot;
        } else if(driveController.getPOV() == 270){
          State.conveyorState = ConveyorState.s_beltIntake;
        } else if(driveController.getPOV() == 315){
          State.conveyorState = ConveyorState.s_rollerIntake;
        } else {
          State.conveyorState = ConveyorState.s_stopConveyor;
        }
      }
      
      
  
    

		if(driveController.getAButton()){
			State.intakeExtendState = IntakeExtendState.s_intakeExtendOpen;
		} else if(driveController.getBButton()){
			State.intakeExtendState = IntakeExtendState.s_intakeExtendClose; 
		} else {
			State.intakeExtendState = IntakeExtendState.s_intakeExtendNeutral; 
		}
  
  }
      
}
  
  
