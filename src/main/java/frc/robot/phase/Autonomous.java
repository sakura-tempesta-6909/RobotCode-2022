package frc.robot.phase;

public class Autonomous {
    private static PhaseTransition phaseTransition;

    public static void autonomousInit() {
        phaseTransition = new PhaseTransition();
        PhaseTransition.Phase.PhaseInit();

        // Phaseの登録
        phaseTransition.registerPhase(
            new PhaseTransition.Phase(
                () -> {
                    return;
                },
                (double time) -> {
                    return time > 3;
                },
                "3 second wait"
            ),
            new PhaseTransition.Phase(
                () -> {
                    return;
                },
                (double time) -> {
                    return time > 4;
                },
                "4 second wait"
            )
        );
    }

    public static void run() {
        phaseTransition.run();
    }
}
