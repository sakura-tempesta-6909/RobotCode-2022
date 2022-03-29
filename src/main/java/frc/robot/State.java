package frc.robot;
import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.mode.ClimbMode;
import frc.robot.mode.ConveyorMode;
import frc.robot.mode.DriveMode;
import frc.robot.mode.TestMode;
import frc.robot.mode.Mode;
import frc.robot.subClass.Const;

public class State {
    public static Modes mode;
    public static double drivePidSetMeter;
    public static boolean driveAccumulateReset;
    //DriveStateの変数を作る
    public static DriveState driveState;
    //xSpeedとzRotationのスピード(単位：：PerecntOutput)
    public static double driveXSpeed, driveZRotation;

    //ConveyorStateの変数を作る
    public static ConveyorState conveyorState;;
    
    //compressorがEnabledか
    public static boolean is_compressorEnabled;

    //intakeExtendのスピード(単位：PercentOutput)
    public static double intakeExtendSpeed;
    public static boolean is_intakeExtendOpen;

    //ClimbのMotorがNEOか
    public static final boolean is_climbArmMotorNEO = true;
    //ClimbArmのState
    public static ClimbArmState climbArmState;
    // climbのidleMode
    public static IdleMode climbMotorIdleMode;
    //climbArmのスピード(単位：PercentOutput)
    public static double climbArmSpeed;
    public static double climbArmAngle;
    public static double climbArmTargetAngle;

    //firstSolenoidがopenしているか
    public static boolean is_firstSolenoidOpen;
    //secondSolenoidがopenしているか
    public static boolean is_secondSolenoidOpen;
    //climbSolenoidがopenしてるか
    public static boolean is_climbSolenoidOpen;
   
    //driveRightとdriveLeftがどれだけ進んでいるか(単位：Meter)
    public static double driveRightFrontPositionMeter, driveLeftFrontPositionMeter;
    //DriverStationのAlliance
    public static DriverStation.Alliance alliance;
    //StringのgameMessage
    public static String gameSpecificMessage;
    public static boolean calibration;

    //shooterのspeed
    public static double shooterMotorSpeed;
    //gyro
    public static double currentDirection;
    public static double targetDirection;
    public static boolean gyroReset;
    // 
    public static boolean reachTurn;

    public static boolean isDrivePidFinished;

    public static void StateInit() {
        XboxController driveController = new XboxController(Const.Ports.DriveController);
        XboxController operateController = new XboxController(Const.Ports.OperateController);
        Mode.addController(driveController, operateController);
        mode = Modes.k_drive;
        is_compressorEnabled = true;
        alliance = DriverStation.getAlliance();
        gameSpecificMessage = DriverStation.getGameSpecificMessage();
        climbMotorIdleMode = IdleMode.kCoast;
        
        stateReset();
    }

    public static void stateReset() {
        climbMotorIdleMode = IdleMode.kCoast;
        driveState = DriveState.s_stopDrive;
        conveyorState = ConveyorState.s_stopConveyor;
        climbArmState = ClimbArmState.s_climbArmNeutral;
        is_firstSolenoidOpen = false;
        is_secondSolenoidOpen = false;
        is_climbSolenoidOpen = false;
    }

    /**
     * Driveの状態
     */
    public enum DriveState {
        s_stopDrive,
        s_slowDrive,
        s_midDrive,
        s_fastDrive,
        s_turnTo,
        s_pidDrive,
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
     * ClimbArmの状態
     */
    public enum ClimbArmState {
        s_fastClimbArmSpin,
        s_midClimbArmSpin,
        s_setClimbArmAngle,  
        s_climbArmNeutral,
        s_angleCalibration,
    }

    public enum Modes {
        k_drive(new DriveMode()),
        k_conveyor(new ConveyorMode()),
        k_climb(new ClimbMode()),
        k_test(new TestMode());

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