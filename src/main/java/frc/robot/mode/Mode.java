package frc.robot.mode;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;

public abstract class Mode {
    static XboxController driveController, operateController;
    public static void addController(XboxController driveController, XboxController operateController) {
        Mode.driveController = driveController;
        Mode.operateController = operateController;

    }

    static Joystick driveJoystick, operateJoystick;
    public static void addJoyStick(Joystick driveJoystick, Joystick operateJoystick) {
        Mode.driveJoystick = driveJoystick;
        Mode.operateJoystick = operateJoystick;
    }

    /**
     * Modeを変化させる。
     */
    abstract public void changeMode();

    /**
     * Stateを変化させる
     */
    abstract public void changeState();
}
