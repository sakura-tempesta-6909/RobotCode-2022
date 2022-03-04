package frc.robot.mode;

import edu.wpi.first.wpilibj.XboxController;
import frc.robot.State;
import frc.robot.State.DriveSpeed;
import frc.robot.State.ConveyorState;
import frc.robot.State.Modes;;

public class DriveMode extends Mode {
    
    @Override
    public void changeMode() {
        if(driveController.getBackButton()){
            State.mode = Modes.k_conveyor;
        }
        if(driveController.getStartButton()){
            State.mode = Modes.k_climb;
        }
        
    }

    @Override
    public void changeState() {
        State.driveSpeed = DriveSpeed.s_fastDrive;
        State.driveXSpeed = driveController.getLeftY();
        State.driveZRotation = driveController.getRightX();

        if(driveController.getLeftBumper()){
            State.conveyorState = ConveyorState.s_outtakeConveyor;
        } else if(driveController.getRightBumper()){
            State.conveyorState = ConveyorState.s_intakeConveyor;
        }

        if(driveController.getAButton()){
            State.is_intakeExtendOpen = false;
        } else {
            State.is_intakeExtendOpen = true;
        }
    }
    
}
