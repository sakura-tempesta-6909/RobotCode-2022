package frc.robot.mode;

import frc.robot.State;
import frc.robot.State.ClimbArmState;
import frc.robot.subClass.Const;

public class TestMode extends Mode{

    @Override
    public void changeMode() {}

    @Override
    public void changeState() {
    if(driveController.getBackButton()){
      State.climbArmState = ClimbArmState.s_setClimbArmAngle;
      State.climbArmTargetAngle = Const.Other.StoreClimbArmAngle;
    }
        
    if(driveController.getStartButton()){
        State.climbArmState = ClimbArmState.s_angleCalibration;
      } 
    }
    
}
