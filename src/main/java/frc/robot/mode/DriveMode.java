package frc.robot.mode;

import edu.wpi.first.wpilibj.XboxController;
import frc.robot.State;
import frc.robot.State.DriveSpeed;
import frc.robot.State.ConveyorState;
import frc.robot.State.IntakeExtendState;
import frc.robot.State.Modes;
import frc.robot.subClass.Const;

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
     
	State.intakeExtendSpeed = driveController.getRightTriggerAxis() - driveController.getLeftTriggerAxis();
    
	

		if(driveController.getPOV() == Const.POV90Degrees && driveController.getRightStickButton() && driveController.getLeftStickButton()){
			State.is_compressorEnabled = false;
		} else if(driveController.getPOV() == Const.POV180Degrees){
			State.is_compressorEnabled = true;
		}

		if(driveController.getLeftBumper()){
			State.conveyorState = ConveyorState.s_outtakeConveyor;
		} else if(driveController.getRightBumper()){
			State.conveyorState = ConveyorState.s_intakeConveyor;
		}
  
  }
      
}
  
  
