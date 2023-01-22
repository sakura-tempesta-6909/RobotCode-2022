package frc.robot.component;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.State;


public class Limelight implements Component {

    private NetworkTable table;
    private NetworkTableEntry txEntry, tyEntry;

    private double Kp;

    public Limelight(){
        table =  NetworkTableInstance.getDefault().getTable("limelight");
        txEntry = table.getEntry("ty");
        Kp = -0.01;
        tyEntry = table.getEntry("tx");

    }

    public void autonomousInit(){

    }
    public void teleopInit(){

    }
    public void disabledInit(){

    }
    public void testInit(){

    }
    public void readSensors(){
        // ターゲットの角度
        double targetOffsetAngle_Vertical = -tyEntry.getDouble(0.0);  
        // how many degrees back is your limelight rotated from perfectly vertical?
        // limelightの角度
        double limelightMountAngleDegrees = 36.0;
        
        // distance from the center of the Limelight lens to the floor
        // limelightの高さ
        double limelightLensHeightCentis = 83;
        
        // distance from the target to the floor
        // ターゲットの高さ
        double goalHeightCentis = 164;
        
        double angleToGoalDegrees = limelightMountAngleDegrees + targetOffsetAngle_Vertical;
        double angleToGoalRadians = angleToGoalDegrees * (3.14159 / 180.0);
        
        // calculate distance
        // ターゲットまでの距離
        State.distanceFromLimelightToGoalCentis = (goalHeightCentis - limelightLensHeightCentis)/Math.tan(angleToGoalRadians);
        System.out.println(State.distanceFromLimelightToGoalCentis);

        double targetAngle = (goalHeightCentis - limelightLensHeightCentis/100) - Math.tan(limelightMountAngleDegrees);
        System.out.println(Math.tan(targetAngle));

        double tx;
        double ty;
        tx = txEntry.getDouble(0);
        ty = tyEntry.getDouble(0);
        
        State.heading_error = tx;
        State.steering_adjust = Kp * tx;

        if(Math.signum(State.steering_adjust) > 0) {
            State.steering_adjust += 0.2;
        } else if(Math.signum(State.steering_adjust) < 0) {
            State.steering_adjust += -0.2;
        }

        
        State.limelight.put("tx", txEntry.getDouble(0));
        State.limelight.put("ty", tyEntry.getDouble(0));
        State.limelight.put("limelightdistance",State.distanceFromLimelightToGoalCentis);
        State.limelight.put("heading", State.heading_error);
        State.limelight.put("steering_adjust", State.steering_adjust);
        State.limelight.put("distance", State.distanceFromLimelightToGoalCentis);
        System.out.println("limelight" + State.limelight.getOrDefault("tx", txEntry.getDouble(0)));
        SmartDashboard.putNumber("distance", State.distanceFromLimelightToGoalCentis);
        SmartDashboard.putNumber("ty", tyEntry.getDouble(0));
        
    }
    public void applyState() {

    }
    
}