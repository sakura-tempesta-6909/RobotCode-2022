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

        driveRightFront.configAllSettings(Const.Configs.DriveRight);
        driveLeftFront.configAllSettings(Const.Configs.DriveLeft);
        driveRightFront.setInverted(true);
        driveRightBack.setInverted(true);
        driveRightFront.setSensorPhase(true);
        driveLeftFront.setSensorPhase(true);

    }

    /**
     * driveを動かす 
     * @param xSpeed 前を正
     * @param zRotation 右を正
     * @param xSpeed driveの縦方向の値
     * @param zRotation　driveの回転方向の値
     */
    public void arcadeDrive(double xSpeed, double zRotation){
        differntialDrive.arcadeDrive(xSpeed, zRotation);
    }

    /**
<<<<<<< HEAD
     * PositionのPointをcmに変換する、
     * @param drivePoint Positionの値
     * @return PositionのdrivePointをcmにする 返り値はcm
=======
     *
     * @param drivePoint PositionのPointをセンチに変換する
>>>>>>> main
     */
    public double drivePointToCm(double drivePoint){
        return drivePoint / Const.Point.DrivePointsPerDriveLength;
    }

    /**
     * 
     * @return　右がどれだけ進んだか(単位：cm)
     */
    public double getDriveRightCM(){
        return drivePointToCm(driveRightFront.getSelectedSensorPosition());
    }

    /** 
     * @return 左がどれだけ進んだか(単位：cm)
     */
    public double getDriveLeftCM(){
        return drivePointToCm(driveLeftFront.getSelectedSensorPosition());
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
        State.driveRightFrontPositionCentimeter = getDriveRightCM();
        State.driveLeftFrontPositionCentimeter = getDriveLeftCM();
    }

    @Override
    public void applyState() {
        switch(State.driveSpeed){
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
