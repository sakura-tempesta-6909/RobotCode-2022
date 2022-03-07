package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import frc.robot.mode.*;
import frc.robot.subClass.Const;

public class State {
    public static Modes mode;

    public static DriveSpeed driveSpeed;
    public static double driveXSpeed, driveZRotation;

    public static ConveyorState conveyorState;

    public static IntakeExtendState intakeExtendState;

    public static boolean is_solenoidFrontOpen;
    public static boolean is_solenoidBackOpen;
    public static boolean is_clampSolenoid;

    public static double gyroValue; // クランプの傾き用

    public static void StateInit() {
        XboxController driveController = new XboxController(Const.DriveControllerPort);
        XboxController operateController = new XboxController(Const.OperateControllerPort);
        Mode.addController(driveController, operateController);
        mode = Modes.k_drive;
        
        stateReset();
    }

    public static void stateReset() {
        driveSpeed = DriveSpeed.s_stopDrive;
        conveyorState = ConveyorState.s_stopConveyor;
        intakeExtendState = IntakeExtendState.s_intakeExtendStop;
        is_solenoidFrontOpen = true;
        is_solenoidBackOpen = true;
        is_clampSolenoid = true;
    }

    public enum DriveSpeed {
        s_stopDrive,
        s_slowDrive,
        s_midDrive,
        s_fastDrive,

    }
    public enum ConveyorState {
        s_outtakeConveyor,
        s_intakeConveyor,
        s_shootConveyor,
        s_stopConveyor,
    }

    public enum IntakeExtendState {
        s_intakeExtendOpen,
        s_intakeExtendClose,
        s_intakeExtendStop,
    }

    public enum Modes {
        k_drive(new DriveMode()),
        k_conveyor(new ConveyorMode()),
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