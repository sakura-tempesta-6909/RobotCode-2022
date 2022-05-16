package frc.robot.subClass;

import com.revrobotics.SparkMaxPIDController;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRXConfiguration;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.util.Units;

public class PIDTuner {
    public static void shooterSet(String key, double shooterP, double shooterI, double shooterD, SparkMaxPIDController shooterPid){
        SmartDashboard.getNumber("ShooterP", shooterP);
        SmartDashboard.getNumber("ShooterI", shooterI);
        SmartDashboard.getNumber("ShooterD", shooterD);
        shooterPid.setP(shooterP);
        shooterPid.setI(shooterI);
        shooterPid.setD(shooterD);

    }

    public static void driveRightSet(String key, double driveRightP, double driveRightI, double driveRightD) {

        SmartDashboard.getNumber("DriveRightP", driveRightP);
        SmartDashboard.getNumber("DriveRightI", driveRightI);
        SmartDashboard.getNumber("DriveRightD", driveRightD);

        MotorConfigs.DriveRight.slot0.kP = driveRightP;
        MotorConfigs.DriveRight.slot0.kI = driveRightI;
        MotorConfigs.DriveRight.slot0.kD = driveRightD;
        MotorConfigs.DriveRight.slot0.maxIntegralAccumulator = 1023*0.014/MotorConfigs.DriveRight.slot0.kI;
    }

    public static void driveLeftSet(String key, double driveLeftP, double driveLeftI, double driveLeftD) {

        SmartDashboard.getNumber("DriveLeftP", driveLeftP);
        SmartDashboard.getNumber("DriveLeftI", driveLeftI);
        SmartDashboard.getNumber("DriveLeftD", driveLeftD);

        MotorConfigs.DriveLeft.slot0.kP = driveLeftP;
        MotorConfigs.DriveLeft.slot0.kI = driveLeftI;
        MotorConfigs.DriveLeft.slot0.kD = driveLeftD;
        MotorConfigs.DriveLeft.slot0.maxIntegralAccumulator = 1023*0.014/MotorConfigs.DriveLeft.slot0.kI;

    }

    public static void gyroSet(String key, double gyro) {

        SmartDashboard.getNumber("gyro", gyro);

        MotorConfigs.DriveRight.primaryPID.selectedFeedbackSensor = FeedbackDevice.CTRE_MagEncoder_Relative;
        MotorConfigs.DriveLeft.primaryPID.selectedFeedbackSensor = FeedbackDevice.CTRE_MagEncoder_Relative;   
        MotorConfigs.gyroPidController = new PIDController(0.01, gyro, 0);
        MotorConfigs.gyroPidController.setIntegratorRange(-0.1/gyro, 0.1/gyro);
        MotorConfigs.gyroPidController.setTolerance(3);

    }

    public static final class MotorConfigs {
        public static final TalonSRXConfiguration DriveRight = new TalonSRXConfiguration();
        public static final TalonSRXConfiguration DriveLeft= new TalonSRXConfiguration();
        public static PIDController gyroPidController;
    }
}