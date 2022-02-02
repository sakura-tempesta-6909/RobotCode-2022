package frc.robot.mode;

import edu.wpi.first.wpilibj.XboxController;

public abstract class Mode {
    XboxController driveController, operateController;
    public void addController(XboxController driveController, XboxController operateController) {
        this.driveController = driveController;
        this.operateController = operateController;
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
