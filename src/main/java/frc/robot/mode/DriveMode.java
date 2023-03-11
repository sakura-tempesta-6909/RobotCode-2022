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

        // LB: conveyorMode
        // Start & Back: climbMode
        if(Mode.Button1.get()){
            State.mode = Modes.k_conveyor;
        } else if(Mode.Button7.get() && Mode.Button8.get()){
            State.mode = Modes.k_climb;
        } else if(Mode.Button11.get() && Mode.Button12.get()){
            State.mode = Modes.k_climb;
        }

    }

    @Override
    public void changeState() {
        State.driveXSpeed =  -driveJoyStick.getY();
        State.driveZRotation = driveJoyStick.getX();

        // Y: midDriveで走る
        if(Mode.Button5.get()){
            State.driveState = DriveState.s_midDrive;
        }else{
            State.driveState = DriveState.s_fastDrive;
        }

        // POV90 & RS & LS: compressorをオフにする
        if(Mode.Button3.get() && Mode.Button4.get()){
            State.is_compressorEnabled = false;
        } else if(driveJoyStick.getPOV() == 180){
            State.is_compressorEnabled = true;
        }

        // LT: outtake, RT: intake
        if(driveJoyStick.getZ() > Const.Other.JoyStickRight){
            State.conveyorState = ConveyorState.s_outtakeConveyor;
        } else if(driveJoyStick.getZ() < Const.Other.JoyStickLeft){
            State.conveyorState = ConveyorState.s_intakeConveyor;
        } else {
            Util.sendConsole("POV Value", driveController.getPOV());
            if(driveJoyStick.getPOV() == 45){
                State.conveyorState = ConveyorState.s_shooterOuttake;
            } else if(driveJoyStick.getPOV() == 90){
                State.conveyorState = ConveyorState.s_rollerOuttake;
            } else if(driveJoyStick.getPOV() == 135){
                State.conveyorState = ConveyorState.s_beltOuttake;
            } else if(driveJoyStick.getPOV() == 225){
                State.conveyorState = ConveyorState.s_shooterShoot;
            } else if(driveJoyStick.getPOV() == 270){
                State.conveyorState = ConveyorState.s_beltIntake;
            } else if(driveJoyStick.getPOV() == 315){
                State.conveyorState = ConveyorState.s_rollerIntake;
            } else {
                State.conveyorState = ConveyorState.s_stopConveyor;
            }
        }
 
        // A: intakeExtendをopen, B: intakeExtendをclose
        if(Mode.Button5.get()){
            State.is_intakeExtendOpen = true;
        } else if(Mode.Button6.get()){
            State.is_intakeExtendOpen = false;
        }
    }

}
  
  
