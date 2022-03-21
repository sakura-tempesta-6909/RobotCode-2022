package frc.robot.mode;

import frc.robot.State;
import frc.robot.State.DriveSpeed;

public class TestMode extends Mode{

    @Override
    public void changeMode() {
        // TODO Auto-generated method stub
        
        
    }

    @Override
    public void changeState() {

        if(driveController.getAButton()){
            State.driveSpeed = DriveSpeed.s_pidDrive;
            State.drivePidsSetPoint = 20000;
        }else if(driveController.getBButton()){
            State.driveSpeed = DriveSpeed.s_pidDrive;
            State.drivePidsSetPoint = -20000;
        }else if(driveController.getXButton()){
            State.driveSpeed = DriveSpeed.s_pidDrive;
            State.drivePidsSetPoint = 0;
        }
        
    }
    
}
