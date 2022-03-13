package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import frc.robot.mode.*;
import frc.robot.subClass.Const;

public class State {
    public static Modes mode;

    public static DriveSpeed driveSpeed;
    public static double driveXSpeed, driveZRotation;

    public static ConveyorState conveyorState;;
    
    public static boolean is_compressorEnabled;

    public static IntakeExtendState intakeExtendState;

    public static ClimbArmState climbArmState;
    public static double climbArmSpeed;

    public static boolean is_firstSolenoidOpen;
    public static boolean is_secondSolenoidOpen;
    public static boolean is_climbSolenoidOpen;

    public static double driveRightFrontVelocity, driveRightBackVelocity, driveLeftFrontVelocity, driveLeftBackVelocity;

    public static double gyroValue; // クランプの傾き用

    public static void StateInit() {
        XboxController driveController = new XboxController(Const.DriveControllerPort);
        XboxController operateController = new XboxController(Const.OperateControllerPort);
        Mode.addController(driveController, operateController);
        mode = Modes.k_drive;
        is_compressorEnabled = true;
        
        stateReset();
    }

    public static void stateReset() {
        driveSpeed = DriveSpeed.s_stopDrive;
        conveyorState = ConveyorState.s_stopConveyor;
        intakeExtendState = IntakeExtendState.s_intakeExtendNeutral;
        climbArmState = ClimbArmState.s_climbArmNeutral;
        is_firstSolenoidOpen = false;
        is_secondSolenoidOpen = false;
        is_climbSolenoidOpen = false;
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
        s_intakeExtendNeutral,
    
    }

    public enum ClimbArmState {
        s_fastClimbArmSpin,
        s_midClimbArmSpin,
        s_climbArmNeutral,
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