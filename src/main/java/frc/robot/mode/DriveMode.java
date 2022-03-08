package frc.robot.mode;

import edu.wpi.first.wpilibj.XboxController;
import frc.robot.State;
import frc.robot.State.DriveSpeed;
import frc.robot.State.ConveyorState;
import frc.robot.State.IntakeExtendState;
import frc.robot.State.Modes;

public class DriveMode extends Mode {
  
  @Override
  public void changeMode() {
    if(driveController.getLeftTriggerAxis() == 0.8){
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

		if(driveController.getPOV() == 90 && driveController.getRightStickButton() && driveController.getLeftStickButton()){
			State.is_compressorEnabled = false;
		} else if(driveController.getPOV() == 180){
			State.is_compressorEnabled = true;
		}

		if(driveController.getLeftBumper()){
			State.conveyorState = ConveyorState.s_outtakeConveyor;
		} else if(driveController.getRightBumper()){
			State.conveyorState = ConveyorState.s_intakeConveyor;
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
  
  
