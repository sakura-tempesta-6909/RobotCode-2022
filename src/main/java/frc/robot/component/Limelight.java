package frc.robot.component;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.State;
import frc.robot.subClass.Const;


public class Limelight implements Component {

    private NetworkTable table;
    private NetworkTableEntry txEntry, tyEntry, tvEntry;

    private double Kp, distance_error;

    public Limelight(){
        table =  NetworkTableInstance.getDefault().getTable("limelight");
        txEntry = table.getEntry("tx");
        Kp = -0.01;
        tyEntry = table.getEntry("ty");
        tvEntry = table.getEntry("tv");
        
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
        
        double angleToGoalDegrees = Const.Calculation.LimelightMountAngleDegrees + targetOffsetAngle_Vertical;
        double angleToGoalRadians = angleToGoalDegrees * (Math.PI / 180.0);
        
        // calculate distance
        // ターゲットまでの距離
        State.distanceFromLimelightToGoalCentis = (Const.Calculation.GoalHeightCentis - Const.Calculation.LImelightLensHeightCentis)/Math.tan(angleToGoalRadians);
        System.out.println(State.distanceFromLimelightToGoalCentis);

        State.tx = txEntry.getDouble(0);
        State.ty = tyEntry.getDouble(0);
        State.tv = tvEntry.getDouble(0);
        State.distance_error = tyEntry.getDouble(0);
        
        State.heading_error = State.tx;
        State.steering_adjust = Kp * State.tx;

        if(Math.signum(State.steering_adjust) > 0) {
            State.steering_adjust += 0.2;
        } else if(Math.signum(State.steering_adjust) < 0) {
            State.steering_adjust += -0.2;
        }

        if(State.tv == 0.0) {
            State.steering_adjust = 0.3;
        } else {
            State.heading_error= State.tx;
            State.steering_adjust = Kp * State.tx;
        }
        State.steering_adjust += 0.2;
        State.steering_adjust -= 0.2;

        State.driving_adjust = Kp * distance_error;
        if(Math.signum(State.driving_adjust) > 0) {
            State.driving_adjust += 0.2;
        } else if (Math.signum(State.driving_adjust) < 0) {
            State.driving_adjust -= 0.2;
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