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
        driveRightFront = new WPI_TalonSRX(Const.DriveRightFrontPort);
        driveLeftFront = new WPI_TalonSRX(Const.DriveLeftFront);
        driveRightBack = new VictorSPX(Const.DriveRightBackPort);
        driveLeftBack = new VictorSPX(Const.DriveLeftBackPort);

        driveRightBack.follow(driveRightFront);
        driveLeftBack.follow(driveLeftFront);

        Ddrive = new DifferentialDrive(driveLeftFront, driveRightFront);

        driveRightFront.configAllSettings(Const.DriveRightConfig);
        driveLeftFront.configAllSettings(Const.DriveLeftConfig);
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
        // TODO Auto-generated method stub
    }
}
