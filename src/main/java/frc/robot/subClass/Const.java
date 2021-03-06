package frc.robot.subClass;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRXConfiguration;
import com.revrobotics.SparkMaxPIDController;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.util.Units;

public class Const {
    public static final class Ports{
        // PORTS                                          
        // ControllerPort(コントローラーのポート)
        public static final int DriveController = 0;
        public static final int OperateController = 1;

        // Motor, drive
        public static final int DriveRightFront = 0;
        public static final int DriveRightBack= 1;
        public static final int DriveLeftFront = 2;
        public static final int DriveLeftBack = 3;

        // Motor, Conveyor
        public static final int IntakeBeltMotor = 4;
        public static final int IntakeRoller = 5;

        //sensor, conveyor
        public static final int BallSensorIntake = 1;
        public static final int BallSensorShooter = 2;

        // pneumatics
        public static final int Compressor = 0;
        public static final int FirstSolenoid = 0;
        public static final int SecondSolenoid = 1;
        public static final int ClimbSolenoid = 2;
        public static final int ConveyorExtend = 3;

        // sparkMax
        public static final int ClimbArm = 1;
        public static final int Shooter = 2;

        // dio port
        public static final int hallsensorPort = 0;
    }

    
    
         
        
    public static final class Speeds{
        public static final double Neutral = 0;

        // DriveSpeed
        // fastDriveの時のスピード
        public static final double FastDrive = 0.8;
        // midDriveの時のスピード
        public static final double MidDrive = 0.5;
        // slowDriveの時のスピード
        public static final double SlowDrive = 0.25;

        // ConveyorSpeed
        // ボールの発射(Shoot)
        // CARGOを発射するときのbeltのスピード
        public static final double BeltShoot = 0.5;
        // CARGOを発射するときのshooterのスピード
        public static final double ShooterShoot = 1.0;

        // ボールを出す(outtake)
        // outtakeするときのbeltのスピード
        public static final double BeltOuttake = 0.5;
        // outtakeするときのrollerのスピード
        public static final double RollerOuttake = 0.5;
        // outtakeするときのshooterのスピード
        public static final double ShooterOuttake = 0.5;

        // ボールの回収(intake)
        // intakeするときのbeltのスピード
        public static final double BeltIntake = 0.5;
        //intakeするときのbeltのスピード
        public static final double RollerIntake = 0.5;

        // Climb
        // fastClimbArmのスピード
        public static final double FastClimbArmSpin = 1;
        // slowClimbArmのスピード
        public static final double SlowClimbArmSpin = 0.2;
        // midClimbArmのスピード
        public static final double MidClimbArmSpin = 0.8;

        // シューターのモーターの最大速度
        public static final int shooterMaxOutput = 5300;
        public static final int ShooterShootThresholdSpeed = 4900;


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

        // ClimbPoint
        // 円の角度
        public static final double FullTurnAngle = 360;
        // ClimbArmのギア比
        public static final double ClimbArmGearRatio = 75 * 4.5;
        // ギアが一回転するとどのくらい角度が増えるか
        public static final double DegreesPerRevolution = FullTurnAngle / ClimbArmGearRatio;
        // ClimbArmEncoderの１秒あたりのカウント数
        public static final int ClimbArmEncoderCount = 5;

    }

    public static final class Pid{
        /** drivePID 長距離用のSlot */
        public static final int DrivePidLongSlot = 0;
        /** drivePID 短距離用のSlot */
        public static final int DrivePidShortSlot = 1;
        /** drivePID 長短距離の判定のしきい値 */
        public static final double DrivePidShortThreshold = 0.5;
      
        //gyroのPID
        public static final double PIDControllerkP = 1;
        public static final double PIDControllerkI = 0.001;
        public static final double PIDControllerkD = 0.6;

        public static void shooterPidSet(SparkMaxPIDController shooterPid){
            shooterPid.setP(0.0008);
            shooterPid.setI(6e-7);
            shooterPid.setD(0);
        }
        
    }

    public static final class ClimbArm{
        // ClimbArmのモーターのArmの制限値
        public static final int ClimbArmCurrentLimit = 60;

        
        // ClimbArmの位置合わせ用
        public static final double ClimbArmFastThreshold = 20;
        public static final double ClimbArmSetAngleThreshold = 3;

        //MidRungを掴む角度
        public static final double MidRungCatchAngle = 150.8;
        //MidRungをの下を通るようにする
        public static final double MidRungGetUnderAngle = 90;

        
        public static final double StoreClimbArmAngle = 122.3;
        
    }
    
    public static final class Other{

        // Deadband
        public static final double Deadband = 0.2;
        // Triggerの押し込み具合
        public static final double TriggerValue = 0.5;

        public static final double TestTurnDirection = 90;
        
        //DrivePIDの目標値と現在の値の誤差の許容範囲(単位メートル)
        public static final double DrivePidTolerance = 0.1;
    }

    public static final class MotorConfigs {
        public static final TalonSRXConfiguration DriveRight = new TalonSRXConfiguration();
        public static final TalonSRXConfiguration DriveLeft= new TalonSRXConfiguration();
        public static PIDController gyroPidController;
    }

    public static final class AutonomousConst {
        /** shoot時に下がるときの距離 */
        public static final double ShootLengthFromFender = 0.3;

        // angles
        // travel distance
    }
    
    public static void ConstInit() {
        MotorConfigs.DriveRight.slot0.kP = 0.051;
        MotorConfigs.DriveRight.slot0.kI = 0.000006;
        MotorConfigs.DriveRight.slot0.kD = 0.00054;
        MotorConfigs.DriveRight.slot0.maxIntegralAccumulator = 1023*0.014/MotorConfigs.DriveRight.slot0.kI;
        
        MotorConfigs.DriveLeft.slot0.kP = 0.048;
        MotorConfigs.DriveLeft.slot0.kI = 0.000009;
        MotorConfigs.DriveLeft.slot0.kD = 0.00054;
        MotorConfigs.DriveLeft.slot0.maxIntegralAccumulator =  1023*0.014/MotorConfigs.DriveLeft.slot0.kI;

        MotorConfigs.DriveRight.slot1.kP = 0.2;
        MotorConfigs.DriveRight.slot1.kI = 0.004;
        MotorConfigs.DriveRight.slot1.kD = 0.000;
        MotorConfigs.DriveRight.slot1.maxIntegralAccumulator = 1023*0.1/MotorConfigs.DriveRight.slot1.kI;
        
        MotorConfigs.DriveLeft.slot1.kP = 0.2;
        MotorConfigs.DriveLeft.slot1.kI = 0.0004;
        MotorConfigs.DriveLeft.slot1.kD = 0.000;
        MotorConfigs.DriveLeft.slot1.maxIntegralAccumulator =  1023*0.1/MotorConfigs.DriveLeft.slot1.kI;


        MotorConfigs.DriveRight.primaryPID.selectedFeedbackSensor = FeedbackDevice.CTRE_MagEncoder_Relative;
        MotorConfigs.DriveLeft.primaryPID.selectedFeedbackSensor = FeedbackDevice.CTRE_MagEncoder_Relative;   
        MotorConfigs.gyroPidController = new PIDController(0.01, gyrokI, 0);
        MotorConfigs.gyroPidController.setIntegratorRange(-0.1/gyrokI, 0.1/gyrokI);
        MotorConfigs.gyroPidController.setTolerance(3);
    }
    public static final double gyrokI = 0.0021;
}
