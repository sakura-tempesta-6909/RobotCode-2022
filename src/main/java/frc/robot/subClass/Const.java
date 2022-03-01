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
    public static final int DriveRightFront = 0;
    public static final int DriveRightBack = 0;
    public static final int DriveLeftFront = 0;
    public static final int DriveLeftBack = 0;

    //Motor, Conveyor
    public static final int IntakeBeltMotor = 0;
    public static final int IntakeRoller = 0;
    public static final int LaunchMotor = 0;

    //sensor, conveyor
    public static final int BallSensor = 0; //これはあるか分からない

    //pneumatics
    public static final int Compressor = 0;
    public static final int SolenoidRight = 0;
    public static final int SolenoidLeft = 0;
    public static final int SolenoidClamp = 0;

    public static final TalonSRXConfiguration dRConfig = new TalonSRXConfiguration();
    public static final TalonSRXConfiguration dLConfig = new TalonSRXConfiguration();
    public static final TalonSRXConfiguration launchMotorConfig = new TalonSRXConfiguration();

    //DriveSpeed
    public static final int FastDrive = 1;
    public static final double MidDrive = 0.5;
    public static final double SlowDrive = 0.25;
    public static final int StopDrive = 0;


    public static void ConstInit() {
        dRConfig.primaryPID.selectedFeedbackSensor = FeedbackDevice.CTRE_MagEncoder_Relative;
        dLConfig.primaryPID.selectedFeedbackSensor = FeedbackDevice.CTRE_MagEncoder_Relative;

        launchMotorConfig.slot0.kP = 0;
        launchMotorConfig.slot0.kI = 0;
        launchMotorConfig.slot0.kD = 0;
        launchMotorConfig.primaryPID.selectedFeedbackSensor = FeedbackDevice.CTRE_MagEncoder_Relative;
    }
}
