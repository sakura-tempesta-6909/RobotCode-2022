package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import frc.robot.mode.*;
import frc.robot.subClass.Const;

public class State {
    public static Modes mode;
    
    public static void StateInit() {
        XboxController driveController = new XboxController(Const.DriveControllerPort);
        XboxController operateController = new XboxController(Const.OperateControllerPort);
        for (Modes modes : Modes.values()) {
            modes.mode.addController(driveController, operateController);
        }

        stateReset();
    }

    public static void stateReset() {
    }

    public enum Modes {
        k_drive(new DriveMode());

        private final Mode mode;
        Modes(Mode mode) {
           this.mode = mode;
        }

        public void changeMode() {
            this.mode.changeMode();;
        }

        public void changeState() {
            this.mode.changeState();
        }
    }
    
}