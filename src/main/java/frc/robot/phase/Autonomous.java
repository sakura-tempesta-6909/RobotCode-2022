package frc.robot.phase;

import frc.robot.State;

public class Autonomous {
	private static PhaseTransition phaseTransition;

	public static void autonomousInit() {
		phaseTransition = new PhaseTransition();
		PhaseTransition.Phase.PhaseInit();

		/**
		 * autonomousの流れ
		 * 1.conveyorModeにする
		 * 2.ボールを打つ＆＆調整
		 * 3.driveModeに切り替え
		 * 4.ボールの近くまで移動
		 * 5.ボールの回収
		 * これを繰り返す
		 */

		// Phaseの登録
		phaseTransition.registerPhase(
			new PhaseTransition.Phase(
				() -> {
					State.is_compressorEnabled = true;
					State.conveyorState = State.ConveyorState.s_intakeConveyor;
					return;
				},
				(double time) -> {
					return time > 0.5;
				},
				"intake"
			),
			new PhaseTransition.Phase(
				() -> {
					State.is_compressorEnabled = false;
					return;
				},
				(double time) -> {
					return time > 2;
				},
				"wait for 2 sec"
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
