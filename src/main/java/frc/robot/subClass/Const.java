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

    //sensor, conveyor
    public static final int BallSensorPort =0; //これはあるか分からない

    //pneumatics
    public static final int CompressorPort = 0;
    public static final int FrontSolenoidPort = 0;
    public static final int BackSolenoidPort = 1;
    public static final int ClampSolenoidPort = 2;

    //sparkMax
    public static final int ClimbArmPort = 0;
  
    //DriveSpeed
    public static final int FastDrive = 1;
    public static final double MidDrive = 0.5;
    public static final double SlowDrive = 0.25;
    public static final int StopDrive = 0;
    //ConveyorSpeed
    //ボールの発射(Shoot)
    public static final double IntakeBeltShoot = 1;
    public static final double LaunchShoot = 0.5;
    //ボールを出す(outtake)
    public static final double IntakeBeltOuttake = 1;
    public static final double IntakeRollerOuttake = 0.5;

    //ボールの回収(intake)
    public static final double IntakeBeltIntake = 1;
    public static final double IntakeRollerIntake = 0.5;

    //Climb
    public static final double ClimbArmFrontSpin = 0.5;
    public static final double ClimbArmBackSpin = 0.5;

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
