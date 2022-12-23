package frc.robot.component;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class Limelight implements Component{

    private NetworkTable table;
    private NetworkTableEntry entry;

    public Limelight(){
        table =  NetworkTableInstance.getDefault().getTable("limelight");
        entry = table.getEntry("ty");
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

        double targetOffsetAngle_Vertical = entry.getDouble(0.0);  
        // how many degrees back is your limelight rotated from perfectly vertical?
        double limelightMountAngleDegrees = 25.0;
        
        // distance from the center of the Limelight lens to the floor
        double limelightLensHeightInches = 20.0;
        
        // distance from the target to the floor
        double goalHeightInches = 60.0;
        
        double angleToGoalDegrees = limelightMountAngleDegrees + targetOffsetAngle_Vertical;
        double angleToGoalRadians = angleToGoalDegrees * (3.14159 / 180.0);
        
        //calculate distance
        double distanceFromLimelightToGoalInches = (goalHeightInches - limelightLensHeightInches)/Math.tan(angleToGoalRadians);
        System.out.println(distanceFromLimelightToGoalInches);
    }
    @Override
    public void applyState() {
       
    }
    
}
