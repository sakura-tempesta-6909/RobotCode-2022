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
        // TODO Auto-generated method stub
        
    if(driveController.getStartButton()){
        State.climbArmState = ClimbArmState.s_angleCalibration;
      } else{
        State.climbArmState = ClimbArmState.s_climbArmNeutral;
      }
        
    }
    
}
