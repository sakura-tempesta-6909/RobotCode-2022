package frc.robot.phase;

import frc.robot.State;

public class Autonomous {
    private static PhaseTransition phaseTransition;

    public static void autonomousInit() {
        phaseTransition = new PhaseTransition();
        PhaseTransition.Phase.PhaseInit();

        // Phaseの登録
        phaseTransition.registerPhase(
            new PhaseTransition.Phase(
                () -> {
                    State.is_compressorEnabled = false;
                    return;
                },
                (double time) -> {
                    return time > 1;
                },
                "compressor stop"
            ),
            new PhaseTransition.Phase(
                () -> {
                    State.is_compressorEnabled = true;
                    State.conveyorState = State.ConveyorState.s_intakeConveyor;
                    return;
                },
                (double time) -> {
                    return time > 1;
                },
                "intake"
            ),
            new PhaseTransition.Phase(
                () -> {
                    State.is_compressorEnabled = false;
                    State.conveyorState = State.ConveyorState.s_shootConveyor;
                    return;
                },
                (double time) -> {
                    return time > 2;
                },
                "shoot"
            )
        );
    }

    public static void run() {
        phaseTransition.run();
    }
}
