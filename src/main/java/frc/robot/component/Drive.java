package frc.robot.component;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import frc.robot.State;
import frc.robot.subClass.Const;

public class Drive implements Component{

    /**モードごとにドライブのスピードを変える
     * differential driveの設定
     * センサー類の読み取り
     */
    private WPI_TalonSRX driveRightFront, driveLeftFront;
    private VictorSPX driveRightBack, driveLeftBack;
    private DifferentialDrive differntialDrive;


    /**
     * Motorの初期化、Motor・センサーの反転 
     */
    public Drive() {
        driveRightFront = new WPI_TalonSRX(Const.Ports.DriveRightFront);
        driveLeftFront = new WPI_TalonSRX(Const.Ports.DriveLeftFront);
        driveRightBack = new VictorSPX(Const.Ports.DriveRightBack);
        driveLeftBack = new VictorSPX(Const.Ports.DriveLeftBack);

        driveRightBack.follow(driveRightFront);
        driveLeftBack.follow(driveLeftFront);

        differntialDrive = new DifferentialDrive(driveLeftFront, driveRightFront);

        driveRightFront.configAllSettings(Const.MotorConfigs.DriveRight);
        driveLeftFront.configAllSettings(Const.MotorConfigs.DriveLeft);
        driveRightFront.setInverted(true);
        driveRightBack.setInverted(true);
        driveRightFront.setSensorPhase(true);
        driveLeftFront.setSensorPhase(true);

    }

    /**
     * driveを動かす 
     * @param xSpeed driveの縦方向の値
     * @param zRotation　driveの回転方向の値
     */
    public void arcadeDrive(double xSpeed, double zRotation){
        // differntialDrive.arcadeDrive(xSpeed, zRotation);
    }

    /**
     * PositionのPointをMeterに変換する、
     * @param drivePoint Positionの値
     * @return PositionのdrivePointをMeterにする 返り値はMeter
     */
    public double drivePointToMeter(double drivePoint){
        return drivePoint / Const.Calculation.DrivePointsPerDriveLength;
    }

    public double getDriveRightMeter(){
        return drivePointToMeter(driveRightFront.getSelectedSensorPosition());
    }

    public double getDriveLeftMeter(){
        return drivePointToMeter(driveLeftFront.getSelectedSensorPosition());
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
    public void testInit() {}

    @Override
    public void readSensors() {
        State.driveRightFrontPositionMeter = getDriveRightMeter();
        State.driveLeftFrontPositionMeter = getDriveLeftMeter();
    }

    @Override
    public void applyState() {
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
            case s_stopDrive:
                arcadeDrive(Const.Speeds.Neutral * State.driveXSpeed, Const.Speeds.Neutral * State.driveZRotation);
        }
    }
}
