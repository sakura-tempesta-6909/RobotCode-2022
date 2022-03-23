package frc.robot.mode;

import frc.robot.State;
import frc.robot.State.DriveState;
import frc.robot.State.ConveyorState;
import frc.robot.State.Modes;
import frc.robot.subClass.Const;
import frc.robot.subClass.Util;

public class DriveMode extends Mode {

    @Override
    public void changeMode() {
        if(driveController.getLeftBumper()){
            State.mode = Modes.k_conveyor;
        } else if(driveController.getStartButton() && driveController.getBackButton()){
            State.mode = Modes.k_climb;
        }

    }

    @Override
    public void changeState() {
        State.driveXSpeed =  -driveController.getLeftY();
        State.driveZRotation = driveController.getRightX();

        if(driveController.getYButton()){
            State.driveState = DriveState.s_midDrive;
        }else{
            State.driveState = DriveState.s_fastDrive;
        }

        if(driveController.getPOV() == 90 && driveController.getRightStickButton() && driveController.getLeftStickButton()){
            State.is_compressorEnabled = false;
        } else if(driveController.getPOV() == 180){
            State.is_compressorEnabled = true;
        }

        if(driveController.getLeftTriggerAxis() > Const.Other.TriggerValue){
            State.conveyorState = ConveyorState.s_outtakeConveyor;
        } else if(driveController.getRightTriggerAxis() > Const.Other.TriggerValue){
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

        State.is_intakeExtendOpen = driveController.getAButton();
    }

}
  
  
