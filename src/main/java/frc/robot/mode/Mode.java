package frc.robot.mode;

import edu.wpi.first.wpilibj.XboxController;
import frc.robot.subClass.State;

public abstract class Mode {
    XboxController driveController, operateController;
    public void addController(XboxController driveController, XboxController operateController) {
        this.driveController = driveController;
        this.operateController = operateController;
    }

    /**
     * Modeを変化させる。
     * @param state
     * @param driver
     * @param operator
     */
    abstract public void changeMode(final State state);

    /**
     * Stateを変化させる
     * @param state
     * @param driver
     * @param operator
     */
    abstract public void changeState(final State state);
}
