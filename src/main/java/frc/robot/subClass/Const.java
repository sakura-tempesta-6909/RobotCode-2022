package frc.robot.subClass;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.can.SlotConfiguration;
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
        public static final int BallSensor =0; //これはあるか分からない

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

    public static final class Xbox{
        //Deadband
        public static final double Deadband = 0.2;

        //Trigger
        public static final double TriggerValue = 0.7;

        //Stick
        public static final double RightXValue = 0.5;

        //POV
        public static final double POV90Degrees = 90;
        public static final double POV180Degrees = 180;
        public static final double POV270Degrees = 270;
    }

    public static final class Slot{
        public static final int ExtendPIDslot = 0;
        public static final int UpPIDslot = 1;
    }

    public static final TalonSRXConfiguration DriveRightConfig = new TalonSRXConfiguration();
    public static final TalonSRXConfiguration DriveLeftConfig = new TalonSRXConfiguration();
    public static final TalonSRXConfiguration LaunchMotorConfig = new TalonSRXConfiguration();
    public static final TalonSRXConfiguration intakeExtendConfig = new TalonSRXConfiguration();

    public static void ConstInit() {

        DriveRightConfig.primaryPID.selectedFeedbackSensor = FeedbackDevice.CTRE_MagEncoder_Relative;
        DriveLeftConfig.primaryPID.selectedFeedbackSensor = FeedbackDevice.CTRE_MagEncoder_Relative;

        LaunchMotorConfig.slot0.kP = 0;
        LaunchMotorConfig.slot0.kI = 0;
        LaunchMotorConfig.slot0.kD = 0;
        LaunchMotorConfig.primaryPID.selectedFeedbackSensor = FeedbackDevice.CTRE_MagEncoder_Relative;
        
        //intakeExtendを上げるとき１、展開するとき（下げるとき）０
        intakeExtendConfig.slot0.kP = 0;
        intakeExtendConfig.slot0.kI = 0;
        intakeExtendConfig.slot0.kD = 0;
        
        intakeExtendConfig.slot1.kP = 0;
        intakeExtendConfig.slot1.kI = 0;
        intakeExtendConfig.slot1.kD = 0;
        intakeExtendConfig.primaryPID.selectedFeedbackSensor = FeedbackDevice.Analog;
        
        //LimitSwitch
        intakeExtendConfig.forwardLimitSwitchNormal = LimitSwitchNormal.NormallyOpen;
        intakeExtendConfig.forwardLimitSwitchSource = LimitSwitchSource.FeedbackConnector;

        intakeExtendConfig.reverseLimitSwitchNormal = LimitSwitchNormal.NormallyOpen;
        intakeExtendConfig.reverseLimitSwitchSource = LimitSwitchSource.FeedbackConnector;
    }
}
