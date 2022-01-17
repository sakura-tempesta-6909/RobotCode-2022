package frc.robot;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.TimedRobot;
import frc.robot.component.Component;
import frc.robot.component.Drive;
import frc.robot.subClass.State;

public class Robot extends TimedRobot {

  ArrayList<Component> components;

  State state;

  @Override
  public void robotInit() {
    components.add(new Drive());
    state = new State();
  }

  @Override
  public void robotPeriodic() {}

  @Override
  public void autonomousInit() {
    for (Component component : components) {
      component.autonomousInit();
    }
  }

  @Override
  public void autonomousPeriodic() {}

  @Override
  public void teleopInit() {
    for (Component component : components) {
      component.teleopInit();
    }
  }

  @Override
  public void teleopPeriodic() {
    state.stateInit();

    state.modes.mode.changeMode(state);

    state.modes.mode.changeState(state);

    for (Component component : components) {
      component.applyState(state);
    }
  }

  @Override
  public void disabledInit() {
    for (Component component : components) {
      component.disabledInit();
    }
  }

  @Override
  public void disabledPeriodic() {}

  @Override
  public void testInit() {
    for (Component component : components) {
      component.testInit();
    }
  }

  @Override
  public void testPeriodic() {}
}
