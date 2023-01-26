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

    private double Kp, tx, ty, tv;

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
        // limelightから見たターゲットの角度
        double targetOffsetAngle_Vertical = -tyEntry.getDouble(0.0);  
        
        double angleToGoalDegrees = Const.Calculation.LimelightMountAngleDegrees + targetOffsetAngle_Vertical;
        double angleToGoalRadians = angleToGoalDegrees * (Math.toRadians(180.0));
        
        // calculate distance
        // ターゲットまでの距離
        State.distanceFromLimelightToGoalCentis = (Const.Calculation.GoalHeightCentis - Const.Calculation.LImelightLensHeightCentis)/Math.tan(angleToGoalRadians);
        System.out.println(State.distanceFromLimelightToGoalCentis);

        tx = txEntry.getDouble(0);
        ty = tyEntry.getDouble(0);
        tv = tvEntry.getDouble(0);

        //ターゲットを追いかける
        State.heading_error = tx;
        State.steering_adjust = Kp * tx;

        if(Math.signum(tx) > 0) {
            State.limelightZRotation = tx / -27 * 0.8;
            if(tx < 9){
                State.limelightZRotation = -0.5;
            }
        } else if(Math.signum(tx) < 0) {
            State.limelightZRotation = tx / -27 * 0.8;
            if(tx < -9) {
                State.limelightZRotation = 0.5;
            }
        }
        

        //シーク
        if(tv == 0.0) {
            State.steering_adjust = 0.3;
            State.steering_adjust += 0.2;
        } else {
            State.heading_error = tx;
            State.steering_adjust = Kp * tx;
            State.steering_adjust += -0.2;
        }
       
        //ターゲットに近づく
        State.driving_adjust = Kp *  State.distance_error;
        if(Math.signum(ty) > 0) {
            State.limelightXSpeed += 0.2;
        } else if (Math.signum(ty) < 0) {
            State.limelightXSpeed -= 0.2;
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