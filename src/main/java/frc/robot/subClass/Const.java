package frc.robot.subClass;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRXConfiguration;

public class Const {
    //Deadband
    public static final double Deadband = 0.2;
    public static final double Neutral = 0;

    //PORTS
    //ControllerPort(コントローラーのポート)
    public static final int DriveControllerPort = 0;
    public static final int OperateControllerPort = 1;

    //Motor, drive
    public static final int DriveRightFrontPort = 0;
    public static final int DriveRightBackPort = 1;
    public static final int DriveLeftFrontPort = 2;
    public static final int DriveLeftBackPort = 3;

    //Motor, Conveyor
    public static final int IntakeBeltMotorPort = 4;
    public static final int IntakeRollerPort = 5;
    public static final int LaunchMotorPort = 6;
    public static final int ConveyorExtendPort = 7;
    public static final int BackPlatePort = 8;
    public static final int LimitSwitchPort = 9;

    //sensor, conveyor
    public static final int BallSensorPort =0; //これはあるか分からない

    //pneumatics
    public static final int CompressorPort = 0;
    public static final int FirstSolenoidPort = 0;
    public static final int SecondSolenoidPort = 1;
    public static final int ClimbSolenoidPort = 2;

    //sparkMax
    public static final int ClimbArmPort = 0;
  
    //DriveSpeed
    public static final int FastDrive = 1;
    public static final double MidDrive = 0.5;
    public static final double SlowDrive = 0.25;
    public static final int StopDrive = 0;
    //ConveyorSpeed
    //ボールの発射(Shoot)
    public static final double BeltShoot = 1;
    public static final double ShooterShoot = 0.5;
    //ボールを出す(outtake)
    public static final double BeltOuttake = 1;
    public static final double RollerOuttake = 0.5;
    public static final double ShooterOuttake = 0.3;

    //ボールの回収(intake)
    public static final double BeltIntake = 1;
    public static final double RollerIntake = 0.5;

    //intakeExtend
    public static final double IntakeExtendOpen = 0.2;

    //Climb
    public static final double FastClimbArmSpin = 0.5;
    public static final double MidClimbArmSpin = 0.25;

    //Trigger
    public static final double LargeTriggerValue = 0.7;
    public static final double LittleTriggerValue = 0.35;

    public static final TalonSRXConfiguration DriveRightConfig = new TalonSRXConfiguration();
    public static final TalonSRXConfiguration DriveLeftConfig = new TalonSRXConfiguration();
    public static final TalonSRXConfiguration LaunchMotorConfig = new TalonSRXConfiguration();

    public static void ConstInit() {
        DriveRightConfig.primaryPID.selectedFeedbackSensor = FeedbackDevice.CTRE_MagEncoder_Relative;
        DriveLeftConfig.primaryPID.selectedFeedbackSensor = FeedbackDevice.CTRE_MagEncoder_Relative;

        LaunchMotorConfig.slot0.kP = 0;
        LaunchMotorConfig.slot0.kI = 0;
        LaunchMotorConfig.slot0.kD = 0;
        LaunchMotorConfig.primaryPID.selectedFeedbackSensor = FeedbackDevice.CTRE_MagEncoder_Relative;

    }
}
