package frc.robot.component;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
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
    private DifferentialDrive Ddrive;

    public Drive() {
        driveRightFront = new WPI_TalonSRX(Const.DriveRightFront);
        driveLeftFront = new WPI_TalonSRX(Const.DriveLeftFront);
        driveRightBack = new VictorSPX(Const.DriveRightBack);
        driveLeftBack = new VictorSPX(Const.DriveLeftBack);

        driveRightBack.follow(driveRightFront);
        driveLeftBack.follow(driveLeftFront);

        Ddrive = new DifferentialDrive(driveLeftFront, driveRightFront);

        driveRightFront.configAllSettings(Const.dRConfig);
        driveLeftFront.configAllSettings(Const.dLConfig);
        driveRightFront.setInverted(true);
        driveRightBack.setInverted(true);
    }
    public void arcadeDrive(double xSpeed, double zRotation){
        Ddrive.arcadeDrive(xSpeed, zRotation);
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
        // TODO Auto-generated method stub
        
    }

    @Override
    public void applyState() {
        switch(State.driveSpeed){
            case s_FastDrive:
                arcadeDrive(State.driveXSpeed, State.driveZRotation);
                break;
            case s_MidDrive:
                arcadeDrive(Const.MidDrive, Const.MidDrive);
                break;
            case s_SlowDrive:
                arcadeDrive(Const.SlowDrive, Const.SlowDrive);
                break;
            case s_StopDrive:
                arcadeDrive(Const.StopDrive, Const.StopDrive);
        }
    }
}
