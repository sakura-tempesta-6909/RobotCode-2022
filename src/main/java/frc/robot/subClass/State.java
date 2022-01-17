package frc.robot.subClass;

import edu.wpi.first.wpilibj.XboxController;
import frc.robot.mode.*;

public class State {
    public Modes modes;
    
    public State() {
        XboxController driveController = new XboxController(Const.DriveControllerPort);
        XboxController operateController = new XboxController(Const.OperateControllerPort);
        for (Modes modes : Modes.values()) {
            modes.mode.addController(driveController, operateController);
        }

        stateInit();
    }

    public void stateInit() {
    }

    public enum Modes {
        k_drive(new DriveMode());

        public final Mode mode;
        Modes(Mode mode) {
           this.mode = mode;
        }
    }
    
}