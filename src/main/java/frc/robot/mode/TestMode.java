package frc.robot.mode;

import frc.robot.State;
import frc.robot.component.Climb;
import frc.robot.State.ClimbArmState;

public class TestMode extends Mode{

    @Override
    public void changeMode() {}

    @Override
    public void changeState() {
    Climb climb = new Climb();
    if(driveController.getBackButton()){
      State.climbArmState = ClimbArmState.s_setClimbArmAngle;
      State.climbArmTargetAngle = 122.3;
    }
        
    if(driveController.getStartButton()){
        State.climbArmState = ClimbArmState.s_angleCalibration;
      }
    if(driveController.getBackButton()){
      climb.storeArm();
    }
        
    }
    
}
