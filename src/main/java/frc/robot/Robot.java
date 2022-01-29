package frc.robot;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.TimedRobot;
import frc.robot.component.Component;
import frc.robot.component.Drive;
import frc.robot.subClass.ExternalSensors;

public class Robot extends TimedRobot {

  ArrayList<Component> components;

  ExternalSensors externalSensors;

  State state;

  @Override
  public void robotInit() {
    components = new ArrayList<>();
    components.add(new Drive());

    externalSensors = new ExternalSensors();
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
  public void autonomousPeriodic() {
    externalSensors.readExternalSensors(state);
    for (Component component : components) {
      component.readSensors(state);
    }
  }

  @Override
  public void teleopInit() {
    state.modes = State.Modes.k_drive;

    for (Component component : components) {
      component.teleopInit();
    }
  }

  @Override
  public void teleopPeriodic() {
    state.stateInit();

    externalSensors.readExternalSensors(state);
    for (Component component : components) {
      component.readSensors(state);
    }

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
  public void disabledPeriodic() {
    externalSensors.readExternalSensors(state);
    for (Component component : components) {
      component.readSensors(state);
    }
  }

  @Override
  public void testInit() {
    for (Component component : components) {
      component.testInit();
    }
  }

  @Override
  public void testPeriodic() {
    externalSensors.readExternalSensors(state);
    for (Component component : components) {
      component.readSensors(state);
    }
  }
}
