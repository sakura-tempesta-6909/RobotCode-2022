package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import frc.robot.mode.*;
import frc.robot.subClass.Const;

public class State {
    public static Modes mode;
    public static DriveSpeed driveSpeed;

    public static double gyroValue; // クランプの傾き用

    public static double driveXSpeed, driveZRotation;
    
    public static void StateInit() {
        XboxController driveController = new XboxController(Const.DriveControllerPort);
        XboxController operateController = new XboxController(Const.OperateControllerPort);
        Mode.addController(driveController, operateController);
        mode = Modes.k_drive;
        
        stateReset();
    }

    public static void stateReset() {
        driveSpeed = DriveSpeed.s_StopDrive;
    }

    public enum DriveSpeed {
        s_StopDrive,
        s_SlowDrive,
        s_MidDrive,
        s_FastDrive,

    }

    public enum Modes {
        k_drive(new DriveMode()),
        k_shooter(new ConveyorMode()),
        k_climb(new ClimbMode());

        private final Mode mode;
        Modes(Mode mode) {
           this.mode = mode;
        }

        public void changeMode() {
            this.mode.changeMode();
        }

        public void changeState() {
            this.mode.changeState();
        }
    }
    
}