package frc.robot.subClass;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.State;
import frc.robot.State.IntakeExtendState;

public class Util {

    //不感帯処理
    public static double deadbandProcessing(double value) {
        return Math.abs(value) > Const.Deadband ? value : 0;
    }

    public static boolean deadbandCheck(double value) {
        return Math.abs(value) > Const.Deadband;
    }


    public static void sendConsole(String key, String text) {
        System.out.println(key + ":" + text);
        SmartDashboard.putString(key, text);
    }

    public static void sendConsole(String key, double number) {
        System.out.println(key + ":" +number);
        SmartDashboard.putNumber(key, number);
    }
    public static void sendConsole(String key, Boolean which){
        System.out.println(key + ":" +which);
        SmartDashboard.putBoolean(key, which);
    }

    public static void allsendConsole(){
        sendConsole( "Mode", State.mode.toString());
        sendConsole("DriveSpeed", State.driveSpeed.toString());
        sendConsole("ClimbState", State.climbArmState.toString());
        sendConsole("ConveyerState", State.conveyorState.toString());
        sendConsole("driveXSpeed", State.driveXSpeed);
        sendConsole("driveZRotation", State.driveZRotation);
        sendConsole("intakeExtendSpeed", State.intakeExtendSpeed);
        sendConsole("fristSolenoidOpen", State.is_firstSolenoidOpen);
        sendConsole("secondsolenoidOpen", State.is_secondSolenoidOpen);
        sendConsole("climbSolenoidOpen", State.is_climbSolenoidOpen);
        sendConsole("driveRightPosition", State.driveRightFrontPosition);
        sendConsole("driveLeftPosition", State.driveLeftFrontPosition);
        sendConsole("climbArmSpeed", State.climbArmSpeed);
        sendConsole("compressorEnable", State.is_compressorEnabled);
        sendConsole("intakeExtendOpen", State.intakeExtendState.toString());
        sendConsole("driveRightCM", State.driveRightFrontPosition);
        sendConsole("driveLeftCM", State.driveLeftFrontPosition);
     
    
    }
}
