package frc.robot.subClass;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRXConfiguration;

public class Const {
    //Deadband
    public static final double Deadband = 0.2;

    //PORTS
    //ControllerPort(コントローラーのポート)
    public static final int DriveControllerPort = 0;
    public static final int OperateControllerPort = 1;

    //Motor, drive
    public static final int DriveRightFrontPort = 0;
    public static final int DriveRightBackPort = 1;
    public static final int DriveLeftFront = 2;
    public static final int DriveLeftBackPort = 3;

    //Motor, Conveyor
    public static final int IntakeBeltMotorPort = 4;
    public static final int IntakeRollerPort = 5;
    public static final int LaunchMotorPort = 6;

    //sensor, conveyor
    public static final int BallSensorPort =0; //これはあるか分からない

    //DriveSpeed
    public static final int FastDrive = 1;
    public static final double MidDrive = 0.5;
    public static final double SlowDrive = 0.25;
    public static final int StopDrive = 0;

    //pneumatics
    public static final int CompressorPort = 0;
    public static final int SolenoidRightPort = 0;
    public static final int SolenoidLeftPort = 1;
    public static final int SolenoidClampPort = 2;

    public static final TalonSRXConfiguration dRConfig = new TalonSRXConfiguration();
    public static final TalonSRXConfiguration dLConfig = new TalonSRXConfiguration();
    public static final TalonSRXConfiguration DriveRightConfig = new TalonSRXConfiguration();
    public static final TalonSRXConfiguration DriveLeftConfig = new TalonSRXConfiguration();
    public static final TalonSRXConfiguration launchMotorConfig = new TalonSRXConfiguration();

    public static void ConstInit() {
        DriveRightConfig.primaryPID.selectedFeedbackSensor = FeedbackDevice.CTRE_MagEncoder_Relative;
        DriveLeftConfig.primaryPID.selectedFeedbackSensor = FeedbackDevice.CTRE_MagEncoder_Relative;

        launchMotorConfig.slot0.kP = 0;
        launchMotorConfig.slot0.kI = 0;
        launchMotorConfig.slot0.kD = 0;
        launchMotorConfig.primaryPID.selectedFeedbackSensor = FeedbackDevice.CTRE_MagEncoder_Relative;
    }
}
