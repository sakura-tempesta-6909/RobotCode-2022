package frc.robot.mode;

import frc.robot.State;

public class TestMode extends Mode{

    @Override
    public void changeMode() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void changeState() {
        // TODO Auto-generated method stub
        
    if(driveController.getStartButton()){
        State.calibration = true;
      } else{
        State.calibration = false;
      }
        
    }
    
}
