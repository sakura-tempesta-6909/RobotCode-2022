package frc.robot.autonomous;

import frc.robot.subClass.State;

public class Autonomous {

    Phase phase;

    enum Phase {
        phase_0,
    }

    public void changeState(final State state) {
        switch(phase) {
            case phase_0:
                break;
        }
    }
}
