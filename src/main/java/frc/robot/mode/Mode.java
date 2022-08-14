package frc.robot.mode;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

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

    public static final Joystick m_Joystick = new Joystick(0);
    public static final JoystickButton Button1 = new JoystickButton(m_Joystick, 1);
    public static final JoystickButton Button2 = new JoystickButton(m_Joystick, 2);
    public static final JoystickButton Button3 = new JoystickButton(m_Joystick, 3);
    public static final JoystickButton Button4 = new JoystickButton(m_Joystick, 4);
    public static final JoystickButton Button5 = new JoystickButton(m_Joystick, 5);
    public static final JoystickButton Button6 = new JoystickButton(m_Joystick, 6);
    public static final JoystickButton Button7 = new JoystickButton(m_Joystick, 7);
    public static final JoystickButton Button8 = new JoystickButton(m_Joystick, 8);
    public static final JoystickButton Button9 = new JoystickButton(m_Joystick, 9);
    public static final JoystickButton Button10 = new JoystickButton(m_Joystick, 10);
    public static final JoystickButton Button11 = new JoystickButton(m_Joystick, 11);
    public static final JoystickButton Button12 = new JoystickButton(m_Joystick, 12);


    /**
     * Modeを変化させる。
     */
    abstract public void changeMode();

    /**
     * Stateを変化させる
     */
    abstract public void changeState();
}
