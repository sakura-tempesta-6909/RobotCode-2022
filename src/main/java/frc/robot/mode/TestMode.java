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
        State.is_compressorEnabled = false;
        if(driveController.getAButton()){
            State.climbArmState = ClimbArmState.s_setClimbArmAngle;
            State.climbArmTaregetAngle = 0;
        }else if(driveController.getBButton()){
            State.climbArmState = ClimbArmState.s_setClimbArmAngle;
            State.climbArmTaregetAngle = 90;
        }else if(driveController.getXButton()){
            State.climbArmState = ClimbArmState.s_setClimbArmAngle;
            State.climbArmTaregetAngle = 180;
        }else if(driveController.getYButton()){
            State.climbArmState = ClimbArmState.s_setClimbArmAngle;
            State.climbArmTaregetAngle = 270;
        }
        // TODO Auto-generated method stub
        
    if(driveController.getStartButton()){
        State.climbArmState = ClimbArmState.s_angleCalibration;
      } else{
        State.climbArmState = ClimbArmState.s_climbArmNeutral;
      }
        
    }
    
}
