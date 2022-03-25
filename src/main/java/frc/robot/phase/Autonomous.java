package frc.robot.phase;

import frc.robot.State;

public class Autonomous {
	private static PhaseTransition phaseTransition;

	public static void autonomousInit() {
		phaseTransition = new PhaseTransition();
		PhaseTransition.Phase.PhaseInit();


	/**
	 * ボールの回収もしたかったら、autonomousのコードを3種類ほど書く必要がある
	 （とれそうなボールは三つある、他のチームが同じこと考えてたらautonomous中 他のボールを取りに行かないといけない）
	 * phase1
	 * 	compressor起動
	 * 	driveBase.set(forward 60cm )
	 * phase 2
	 *  shooter on (中に入ってるボールを発射)  1秒間
	 * phase 3 (option 1)
	 * 	driveBase.set(forward -160cm)
	 * 	driveBase.turn(-129 degrees)
	 * 	driveBase.set(forward 85cm)
	 * phase 4
	 * 	driveBase.turn(145 又は 146 degrees)
	 * 	driveBase.set(forward 223cm)
	 *  driveBase.turn(-17 degrees)
	 * phase 5
	 * 	shooter on (中に入ってるボールを発射)  1秒間
	 * 
	 * phase 3
	 * 	drivebase.set
	 * 	
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
