package frc.robot.mode;

import edu.wpi.first.wpilibj.XboxController;
import frc.robot.State;
import frc.robot.State.DriveSpeed;
import frc.robot.State.ConveyorState;;

public class DriveMode extends Mode {
    
    @Override
    public void changeMode() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void changeState() {
        State.driveSpeed = DriveSpeed.s_fastDrive;
        State.driveXSpeed = driveController.getLeftY();
        State.driveZRotation = driveController.getRightX();
        if(driveController.getLeftBumper()){
            State.conveyorState = ConveyorState.s_firingConveyor;
        } else if(driveController.getRightBumper()){
            State.conveyorState = ConveyorState.s_collectionConveyor;
        } else {
            State.conveyorState = ConveyorState.s_stopConveyor;
        }
    }
    
}
