package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import frc.robot.mode.*;
import frc.robot.subClass.Const;

public class State {
    public static Modes mode;

    //Driveのスピード
    public static DriveSpeed driveSpeed;
    //xSpeedとzRotationのスピード(単位：：PerecntOutput)
    public static double driveXSpeed, driveZRotation;

    //ConveyorStateの変数を作る
    public static ConveyorState conveyorState;;
    
    //compressorがEnabledか
    public static boolean is_compressorEnabled;

    //intakeExtendのState
    public static IntakeExtendState intakeExtendState;
    //intakeExtendのスピード(単位：PercentOutput)
    public static double intakeExtendSpeed;

    //ClimbArmのState
    public static ClimbArmState climbArmState;
    //climbArmのスピード(単位：PercentOutput)
    public static double climbArmSpeed;
    public static double climbArmAngle;

    //firstSolenoidがopenしているか
    public static boolean is_firstSolenoidOpen;
    //secondSolenoidがopenしているか
    public static boolean is_secondSolenoidOpen;
    //climbSolenoidがopenしてるか
    public static boolean is_climbSolenoidOpen;
    
    //driveRightとdriveLeftがどれだけ進んでいるか(単位：cm)
    public static double driveRightFrontPositionCentimeter, driveLeftFrontPositionCentimeter;

    public static double gyroValue; // クランプの傾き用

    public static void StateInit() {
        XboxController driveController = new XboxController(Const.Ports.DriveController);
        XboxController operateController = new XboxController(Const.Ports.OperateController);
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

    /**
     * Driveの状態
     */
    public enum DriveSpeed {
        s_stopDrive,
        s_slowDrive,
        s_midDrive,
        s_fastDrive,

    }

    /**
     * Conveyorの状態
     */
    public enum ConveyorState {
        s_outtakeConveyor,
        s_intakeConveyor,
        s_shootConveyor,
        s_stopConveyor,

        s_beltIntake,
        s_beltOuttake,
        s_rollerIntake,
        s_rollerOuttake,
        s_shooterShoot,
        s_shooterOuttake,
    }

    /**
     * IntakeExtendの状態
     */
    public enum IntakeExtendState {
        s_manual,
        s_intakeExtendOpen,
        s_intakeExtendClose,
        s_intakeExtendNeutral,

    }

    /**
     * ClimbArmの状態
     */
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