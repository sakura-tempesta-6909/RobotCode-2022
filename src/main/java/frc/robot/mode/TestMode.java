package frc.robot.mode;

import frc.robot.State;
import frc.robot.State.ClimbArmState;

public class TestMode extends Mode{

    @Override
    public void changeMode() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void changeState() {

    if(driveController.getAButton()) {
      State.climbArmState = ClimbArmState.s_setClimbArmAngle;
      State.climbArmTargetAngle = 0;
    } else if(driveController.getBButton()) {
      State.climbArmState = ClimbArmState.s_setClimbArmAngle;
      State.climbArmTargetAngle = 156;
    } else if(driveController.getYButton()) {
      State.climbArmState = ClimbArmState.s_setClimbArmAngle;
      State.climbArmTargetAngle = 336;
    } else if(driveController.getXButton()) {
      State.climbArmState = ClimbArmState.s_setClimbArmAngle;
      State.climbArmTargetAngle = 270;
    }
      

    if(driveController.getBackButton()){
      State.climbArmState = ClimbArmState.s_setClimbArmAngle;
      State.climbArmTargetAngle = 122.3;
    }
        // TODO Auto-generated method stub
        
    if(driveController.getStartButton()){
        State.climbArmState = ClimbArmState.s_angleCalibration;
      } 
    }
    
}
