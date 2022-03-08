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
                    return time > 5;
                },
                "5 second wait"
            ),
            new PhaseTransition.Phase(
                () -> {
                    return;
                },
                (double time) -> {
                    return time > 7;
                },
                "10 second wait"
            )
        );
    }

    public static void run() {
        phaseTransition.run();
    }
}
