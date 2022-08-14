package frc.robot.mode;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;

public abstract class Mode {
    static XboxController driveController, operateController;
    public static void addController(XboxController driveController, XboxController operateController) {
        Mode.driveController = driveController;
        Mode.operateController = operateController;

    }

    static Joystick driveJoyStick;
    public static void addJoyStick(Joystick driveJoyStick) {
        Mode.driveJoyStick = driveJoyStick;
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
