package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import frc.robot.component.Climb;
import frc.robot.component.Component;
import frc.robot.component.Conveyor;
import frc.robot.component.Drive;
import frc.robot.component.Limelight;
import frc.robot.phase.Autonomous;
import frc.robot.subClass.Const;
import frc.robot.subClass.ExternalSensors;
import frc.robot.subClass.MQTT;
import frc.robot.subClass.Util;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

public class Robot extends TimedRobot {

    ArrayList<Component> components;

    ExternalSensors externalSensors;
    MQTT mqtt = new MQTT();

    PrintStream defaultConsole = System.out;
    ByteArrayOutputStream newConsole = new ByteArrayOutputStream();

    @Override
    public void robotInit() {
        System.setOut(new PrintStream(newConsole));
        Const.ConstInit();
        Thread thread = new Thread(() -> {
            mqtt.connect();
        });
        thread.start();
        components = new ArrayList<>();
        components.add(new Drive());
        components.add(new Conveyor());
        components.add(new Climb());

        externalSensors = new ExternalSensors();

        State.StateInit();
        Util.sendSystemOut(defaultConsole, newConsole);
    }

    @Override
    public void robotPeriodic() {
        Util.sendSystemOut(defaultConsole, newConsole);
    }

    @Override
    public void autonomousInit() {
        for (Component component : components) {
            component.autonomousInit();
        }
        Autonomous.autonomousInit();
    }

    @Override
    public void autonomousPeriodic() {
        State.stateReset();
        externalSensors.readExternalSensors();
        for (Component component : components) {
            component.readSensors();
        }

        Autonomous.run();

        for (Component component : components) {
            component.applyState();
        }
        mqtt.publishState();
    }

    @Override
    public void teleopInit() {
        State.mode = State.Modes.k_drive;

        for (Component component : components) {
            component.teleopInit();
        }
    }

    @Override
    public void teleopPeriodic() {
        State.stateReset();
        externalSensors.readExternalSensors();
        for (Component component : components) {
            component.readSensors();
        }

        State.mode.changeMode();

        State.mode.changeState();

        for (Component component : components) {
            component.applyState();
        }
        Util.allSendConsole();
        
        mqtt.publishState();
    }

    

    @Override
    public void disabledInit() {
        for (Component component : components) {
            component.disabledInit();
        }
    }

    @Override
    public void disabledPeriodic() {
        externalSensors.readExternalSensors();
        for (Component component : components) {
            component.readSensors();
        }
        Util.allSendConsole();
    }

    @Override
    public void testInit() {
        State.mode = State.Modes.k_test;

        for (Component component : components) {
            component.testInit();
        }
    }

    @Override
    public void testPeriodic() {
        externalSensors.readExternalSensors();
        State.stateReset();
        for (Component component : components) {
            component.readSensors();
        }
        State.mode.changeState();

        for (Component component : components) {
            component.applyState();
        }
        mqtt.publishState();
    }
}
