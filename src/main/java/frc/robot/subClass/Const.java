package frc.robot.subClass;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.can.TalonSRXConfiguration;

public class Const {
   public static final class Ports{
        //PORTS                                          
        //ControllerPort(コントローラーのポート)
        public static final int DriveController = 0;
        public static final int OperateController = 1;

        //Motor, drive
        public static final int DriveRightFront = 0;
        public static final int DriveRightBack= 1;
        public static final int DriveLeftFront = 2;
        public static final int DriveLeftBack = 3;

        //Motor, Conveyor
        public static final int IntakeBeltMotor = 4;
        public static final int IntakeRoller = 5;
        public static final int LaunchMotor = 6;
        public static final int ConveyorExtend = 7;
        public static final int BackPlate = 8;
        public static final int LimitSwitch = 9;

        //sensor, conveyor
        public static final int BallSensorIntake = 0;
        public static final int BallSensorShooter = 1;

        //pneumatics
        public static final int Compressor = 0;
        public static final int FirstSolenoid = 0;
        public static final int SecondSolenoid = 1;
        public static final int ClimbSolenoid = 2;

        //sparkMax
        public static final int ClimbArm = 1;
    }

    public static final class Speeds{
        public static final double Neutral = 0;

        //DriveSpeed
        public static final double FastDrive = 0.8;
        public static final double MidDrive = 0.5;
        public static final double SlowDrive = 0.25;
        public static final double StopDrive = 0;

        //ConveyorSpeed
        //ボールの発射(Shoot)
        public static final double BeltShoot = 0.3;
        public static final double ShooterShoot = 1.0;

        //ボールを出す(outtake)
        public static final double BeltOuttake = 0.3;
        public static final double RollerOuttake = 0.5;
        public static final double ShooterOuttake = 0.5;

        //ボールの回収(intake)
        public static final double BeltIntake = 0.3;
        public static final double RollerIntake = 0.5;

        //intakeExtend
        public static final double IntakeExtendOpen = 0.2;

        //Climb
        public static final double FastClimbArmSpin = 1;
        public static final double MidClimbArmSpin = 0.5;
    }

    public static final class Point{
        // DrivePoint
        public static final double EncoderPointsPerRevolution = 4096;
        // DriveBaseのタイヤの直径 単位はセンチ
        public static final double DriveWheelDiameter = 15.24; 
        // タイヤの円周を求める
        public static final double DriveLengthPerWheelRevolution = DriveWheelDiameter * Math.PI;
        // 1cm進むとどのくらいPointが増えるか
        public static final double DrivePointsPerDriveLength = EncoderPointsPerRevolution / DriveLengthPerWheelRevolution; 
        
        //ClmbPoint 
        // 円の角度
        public static final double Round = 360;
        // ClimbArmのギア比
        public static final double ClimbArmGearRatio = 75 * 4.5;
        // ギアが一回転するとどのくらい角度が増えるか
        public static final double DegreesPerRevolution = Round / ClimbArmGearRatio;
        }

    public static final class Counts{

        // ClimbArmEncoderの１秒あたりのカウント数
        public static final int ClimbArmEncoderCount = 5;
    }

    public static final class Util{
        //Deadband
        public static final double Deadband = 0.2;
        public static final double TriggerValue = 0.5;
        public static final int ClimbArmCurrentLimit = 60;
    }

    public static final class Configs{
        public static final int ExtendPIDslot = 0;
        public static final int UpPIDslot = 1;

        public static final TalonSRXConfiguration DriveRight = new TalonSRXConfiguration();
        public static final TalonSRXConfiguration DriveLeft= new TalonSRXConfiguration();
        public static final TalonSRXConfiguration LaunchMotor = new TalonSRXConfiguration();
        public static final TalonSRXConfiguration intakeExtend = new TalonSRXConfiguration();
    }

    public static void ConstInit() {

        Configs.DriveRight.primaryPID.selectedFeedbackSensor = FeedbackDevice.CTRE_MagEncoder_Relative;
        Configs.DriveLeft.primaryPID.selectedFeedbackSensor = FeedbackDevice.CTRE_MagEncoder_Relative;

        Configs.LaunchMotor.slot0.kP = 0;
        Configs.LaunchMotor.slot0.kI = 0;
        Configs.LaunchMotor.slot0.kD = 0;
        Configs.LaunchMotor.primaryPID.selectedFeedbackSensor = FeedbackDevice.CTRE_MagEncoder_Relative;
        
        //intakeExtendを上げるとき１、展開するとき（下げるとき）０
        Configs.intakeExtend.slot0.kP = 0;
        Configs.intakeExtend.slot0.kI = 0;
        Configs.intakeExtend.slot0.kD = 0;
        
        Configs.intakeExtend.slot1.kP = 0;
        Configs.intakeExtend.slot1.kI = 0;
        Configs.intakeExtend.slot1.kD = 0;
        Configs.intakeExtend.primaryPID.selectedFeedbackSensor = FeedbackDevice.Analog;
        
        //LimitSwitch
        Configs.intakeExtend.forwardLimitSwitchNormal = LimitSwitchNormal.NormallyOpen;
        Configs.intakeExtend.forwardLimitSwitchSource = LimitSwitchSource.FeedbackConnector;

        Configs.intakeExtend.reverseLimitSwitchNormal = LimitSwitchNormal.NormallyOpen;
        Configs.intakeExtend.reverseLimitSwitchSource = LimitSwitchSource.FeedbackConnector;
    }
}
