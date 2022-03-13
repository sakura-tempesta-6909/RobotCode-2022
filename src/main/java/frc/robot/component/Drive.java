package frc.robot.component;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.SensorCollection;

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

    public Drive() {
        driveRightFront = new WPI_TalonSRX(Const.DriveRightFrontPort);
        driveLeftFront = new WPI_TalonSRX(Const.DriveLeftFrontPort);
        driveRightBack = new VictorSPX(Const.DriveRightBackPort);
        driveLeftBack = new VictorSPX(Const.DriveLeftBackPort);

        driveRightBack.follow(driveRightFront);
        driveLeftBack.follow(driveLeftFront);

        differntialDrive = new DifferentialDrive(driveLeftFront, driveRightFront);

        driveRightFront.configAllSettings(Const.DriveRightConfig);
        driveLeftFront.configAllSettings(Const.DriveLeftConfig);
        driveRightFront.setInverted(true);
        driveRightBack.setInverted(true);
    }
    public void arcadeDrive(double xSpeed, double zRotation){
        differntialDrive.arcadeDrive(xSpeed, zRotation);
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
      State.driveRightFrontVelocity = driveRightFront.getSelectedSensorVelocity();
      State.driveRightBackVelocity = driveRightBack.getSelectedSensorVelocity();
      State.driveLeftFrontVelocity = driveLeftFront.getSelectedSensorVelocity();
      State.driveLeftBackVelocity = driveLeftBack.getSelectedSensorVelocity();
    }

    @Override
    public void applyState() {
        switch(State.driveSpeed){
            case s_fastDrive:
                arcadeDrive(Const.FastDrive * State.driveXSpeed, Const.FastDrive * State.driveZRotation);
                break;
            case s_midDrive:
                arcadeDrive(Const.MidDrive * State.driveXSpeed, Const.MidDrive * State.driveZRotation);
                break;
            case s_slowDrive:
                arcadeDrive(Const.SlowDrive * State.driveXSpeed, Const.SlowDrive * State.driveZRotation);
                break;
            case s_stopDrive:
                arcadeDrive(Const.Neutral * State.driveXSpeed, Const.Neutral * State.driveZRotation);
        }
    }
}
