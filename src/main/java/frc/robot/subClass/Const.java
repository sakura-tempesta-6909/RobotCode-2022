package frc.robot.subClass;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRXConfiguration;

import edu.wpi.first.math.util.Units;

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
        public static final int ShooterMotor = 6;
        public static final int LimitSwitch = 9;

        //sensor, conveyor
        public static final int BallSensorIntake = 0;
        public static final int BallSensorShooter = 1;

        //pneumatics
        public static final int Compressor = 0;
        public static final int FirstSolenoid = 0;
        public static final int SecondSolenoid = 1;
        public static final int ClimbSolenoid = 2;
        public static final int ConveyorExtend = 3;

        //sparkMax
        public static final int ClimbArm = 1;
    }

    public static final class Speeds{
        public static final double Neutral = 0;

        //DriveSpeed
        //fastDriveの時のスピード
        public static final double FastDrive = 0.8;
        //midDriveの時のスピード
        public static final double MidDrive = 0.5;
        //slowDriveの時のスピード
        public static final double SlowDrive = 0.25;

        //ConveyorSpeed
        //ボールの発射(Shoot)
        //CARGOを発射するときのbeltのスピード
        public static final double BeltShoot = 0.3;
        //CARGOを発射するときのshooterのスピード
        public static final double ShooterShoot = 1.0;

        //ボールを出す(outtake)
        //outtakeするときのbeltのスピード
        public static final double BeltOuttake = 0.3;
        //outtakeするときのrollerのスピード
        public static final double RollerOuttake = 0.5;
        //outtakeするときのshooterのスピード
        public static final double ShooterOuttake = 0.5;

        //ボールの回収(intake)
        //intakeするときのbeltのスピード
        public static final double BeltIntake = 0.3;
        //intakeするときのbeltのスピード
        public static final double RollerIntake = 0.5;

        //Climb
        //fastClimbArmのスピード
        public static final double FastClimbArmSpin = 1;
        //midClimbArmのスピード
        public static final double MidClimbArmSpin = 0.5;
    }

    public static final class Calculation{
        // DrivePoint
        public static final double EncoderPointsPerRevolution = 4096;

        // タイヤの直径を求める 単位はメートル
        public static final double DriveWheelDiameter = Units.inchesToMeters(6.0);
        // タイヤの円周のを求める　単位はメートル
        public static final double DriveLengthPerWheelRevolution = DriveWheelDiameter * Math.PI;

        // 1m進むとどのくらいPointが増えるか
        public static final double DrivePointsPerDriveLength = EncoderPointsPerRevolution / DriveLengthPerWheelRevolution;

        //ClimbPoint
        // 円の角度
        public static final double Round = 360;
        // ClimbArmのギア比
        public static final double ClimbArmGearRatio = 75 * 4.5;
        // ギアが一回転するとどのくらい角度が増えるか
        public static final double DegreesPerRevolution = Round / ClimbArmGearRatio;
        // ClimbArmEncoderの１秒あたりのカウント数
        public static final int ClimbArmEncoderCount = 5;
    }

    public static final class Other{
        //シューターのモーターの最大速度
        public static final int shooterMotorMaxOutput = 100000;

        //Deadband
        public static final double Deadband = 0.2;
        public static final double TriggerValue = 0.5;

        //ClimbArmのモーターのAmpの制限値
        public static final int ClimbArmCurrentLimit = 60;
    }

    public static final class MotorConfigs {
        public static final TalonSRXConfiguration DriveRight = new TalonSRXConfiguration();
        public static final TalonSRXConfiguration DriveLeft= new TalonSRXConfiguration();
        public static final TalonSRXConfiguration ShooterMotor = new TalonSRXConfiguration();
    }

    public static void ConstInit() {

        MotorConfigs.DriveRight.primaryPID.selectedFeedbackSensor = FeedbackDevice.CTRE_MagEncoder_Relative;
        MotorConfigs.DriveLeft.primaryPID.selectedFeedbackSensor = FeedbackDevice.CTRE_MagEncoder_Relative;

        MotorConfigs.ShooterMotor.slot0.kP = 0.003;
        MotorConfigs.ShooterMotor.slot0.kI = 0.000025;
        MotorConfigs.ShooterMotor.slot0.kD = 0.003;
        MotorConfigs.ShooterMotor.primaryPID.selectedFeedbackSensor = FeedbackDevice.CTRE_MagEncoder_Relative;
    }
}
