package frc.robot.component;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import java.util.function.ToIntFunction;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import frc.robot.State;
import frc.robot.subClass.Const;
import frc.robot.subClass.Util;

public class Drive implements Component{

    /**モードごとにドライブのスピードを変える
     * differential driveの設定
     * センサー類の読み取り
     */
    private WPI_TalonSRX driveRightFront, driveLeftFront;
    private VictorSPX driveRightBack, driveLeftBack;
    private DifferentialDrive differntialDrive;
    private ADXRS450_Gyro gyro;


    /**
     * Motorの初期化、Motor・センサーの反転 
     */
    public Drive() {
        driveRightFront = new WPI_TalonSRX(Const.Ports.DriveRightFront);
        driveLeftFront = new WPI_TalonSRX(Const.Ports.DriveLeftFront);
        driveRightBack = new VictorSPX(Const.Ports.DriveRightBack);
        driveLeftBack = new VictorSPX(Const.Ports.DriveLeftBack);
        gyro = new ADXRS450_Gyro();

        driveRightBack.follow(driveRightFront);
        driveLeftBack.follow(driveLeftFront);

        differntialDrive = new DifferentialDrive(driveLeftFront, driveRightFront);

        driveRightFront.configAllSettings(Const.MotorConfigs.DriveRight);
        driveLeftFront.configAllSettings(Const.MotorConfigs.DriveLeft);
        driveRightFront.setInverted(true);
        driveRightBack.setInverted(true);
        driveRightFront.setSensorPhase(true);
        driveLeftFront.setSensorPhase(true);
        driveRightFront.setNeutralMode(NeutralMode.Brake);
        driveLeftFront.setNeutralMode(NeutralMode.Brake);
        driveRightBack.setNeutralMode(NeutralMode.Brake);
        driveLeftBack.setNeutralMode(NeutralMode.Brake);
        gyroInit();
    }
    
    public double getCurrentDirection(){
        double c = gyro.getAngle();
        return Util.determineDirection(c);
    }

    public void turnTo(double direction) {
        arcadeDrive(0, Const.MotorConfigs.gyroPidController.calculate(getCurrentDirection(), direction));
    }

    public void gyroInit(){
        gyro.reset();
        gyro.calibrate();
    }

    public void gyroReset(){
        gyro.reset();
    }

    public boolean isDirectionAchieived(){
        return Const.MotorConfigs.gyroPidController.atSetpoint();
    }

    public boolean is_judgePIDPosition(){
        return is_judgePIDRightPosition() && is_judgePIDLeftPosition();
    }

    public boolean is_judgePIDRightPosition(){
        return Util.mathDiffNowPointToGoalPoint(driveMeterToPoint(getDriveRightMeter()), Const.Calculation.EncoderPointsPerRevolution) < driveMeterToPoint(Const.Other.DrivePIDMeasurmentError);
    }

    public boolean is_judgePIDLeftPosition(){
        return Util.mathDiffNowPointToGoalPoint(driveMeterToPoint(getDriveLeftMeter()), Const.Calculation.EncoderPointsPerRevolution) < driveMeterToPoint(Const.Other.DrivePIDMeasurmentError);
    }
    
    

    /**
     * driveを動かす 
     * @param xSpeed driveの縦方向の値
     * @param zRotation　driveの回転方向の値
     */
    public void arcadeDrive(double xSpeed, double zRotation){
        differntialDrive.feed();
        differntialDrive.arcadeDrive(xSpeed, zRotation);
    }

    /**
     * PositionのPointをMeterに変換する、
     * @param drivePoint Positionの値
     * @return PositionのdrivePointをMeterにする 返り値はMeter
     */
    public double drivePointToMeter(double drivePoint){
        return drivePoint / Const.Calculation.DrivePointsPerDriveLength;
    }
    public double driveMeterToPoint(double driveMeter){
        return driveMeter * Const.Calculation.DrivePointsPerDriveLength;
    }

    /**
     * @return Rightの進んだ距離を取得する(単位:Meter)
     */
    public double getDriveRightMeter(){
        return drivePointToMeter(driveRightFront.getSelectedSensorPosition());
    }

    /**
     * @return Leftの進んだ距離を取得する(単位:Meter)
     */
    public double getDriveLeftMeter(){
        return drivePointToMeter(driveLeftFront.getSelectedSensorPosition());
    }

    public void drivePosition(double pidposition){
        driveRightFront.selectProfileSlot(0, 0);
        driveLeftFront.selectProfileSlot(0, 0);
        driveRightFront.set(ControlMode.Position, driveMeterToPoint(pidposition));
        driveLeftFront.set(ControlMode.Position, driveMeterToPoint(pidposition));
    }

    public void drivePidAllReset(){
        driveRightFront.setSelectedSensorPosition(0);
        driveLeftFront.setSelectedSensorPosition(0);
        driveLeftFront.setIntegralAccumulator(0);
        driveRightFront.setIntegralAccumulator(0);
    }

    @Override
    public void autonomousInit() {
        // TODO Auto-generated method stub
        

    }

    @Override
    public void teleopInit() {
        // TODO Auto-generated method stub

    }

    @Override
    public void disabledInit() {}

    @Override
    public void testInit() {
        driveRightFront.setSelectedSensorPosition(0);
        driveLeftFront.setSelectedSensorPosition(0);
        driveLeftFront.setIntegralAccumulator(0);
        driveRightFront.setIntegralAccumulator(0);
    }

    @Override
    public void readSensors() {
        State.driveRightFrontPositionMeter = getDriveRightMeter();
        State.driveLeftFrontPositionMeter = getDriveLeftMeter();
        State.currentDirection = getCurrentDirection();
        State.reachTurn = isDirectionAchieived();
        State.is_judgePIDPosition = is_judgePIDPosition();
        
    }

    @Override
    public void applyState() {
        if(State.driveAccumulateReset){
            drivePidAllReset(); 
        }
        if(State.gyroReset){
            gyroReset();
        }
        
        switch(State.driveState){
            case s_fastDrive:
                arcadeDrive(Const.Speeds.FastDrive * State.driveXSpeed, Const.Speeds.FastDrive * State.driveZRotation);
                break;
            case s_midDrive:
                arcadeDrive(Const.Speeds.MidDrive * State.driveXSpeed, Const.Speeds.MidDrive * State.driveZRotation);
                break;
            case s_slowDrive:
                arcadeDrive(Const.Speeds.SlowDrive * State.driveXSpeed, Const.Speeds.SlowDrive * State.driveZRotation);
                break;
            case s_pidDrive:
                drivePosition(State.drivePidSetMeter); 
                break;
            case s_stopDrive:
                arcadeDrive(Const.Speeds.Neutral * State.driveXSpeed, Const.Speeds.Neutral * State.driveZRotation);
            case s_turnTo:
                turnTo(State.targetDirection); 
                break;
        }

      
    }
}
