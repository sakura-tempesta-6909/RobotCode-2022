package frc.robot.mode;

import frc.robot.State;
import frc.robot.State.DriveState;
import frc.robot.subClass.Const;
import frc.robot.State.ClimbArmState;

public class TestMode extends Mode{

  @Override
  public void changeMode() {}

  @Override
  public void changeState() {
    if(driveController.getBackButton()){
      State.climbArmState = ClimbArmState.s_setClimbArmAngle;
      State.climbArmTargetAngle = Const.ClimbArm.StoreClimbArmAngle;
    }
        
    if(driveController.getStartButton()){
        State.climbArmState = ClimbArmState.s_angleCalibration;
      } 

    if(driveController.getAButton()){
      State.driveState = DriveState.s_turnTo;
      State.targetDirection = Const.ClimbArm.TestTurnDirection;
      
    }
  }
}
