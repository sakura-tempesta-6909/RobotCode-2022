package frc.robot.component;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import frc.robot.State;

public class Drive extends DifferentialDrive implements Component{

    /**モードによりドライブのスピードを変える
     * differential driveの設定
     * センサー類の読み取り
     */
    
    public Drive() {
        super(new Spark(0), new Spark(1)); //エラー対処用に仮書きされているだけ、実際はTalon,Victorが使われる
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
