package frc.robot.phase;

import frc.robot.State;

public class Autonomous {
	private static PhaseTransition phaseTransition;

	public static void autonomousInit() {
		phaseTransition = new PhaseTransition();
		PhaseTransition.Phase.PhaseInit();


	/**
	 * ボールの回収もしたかったら、autonomousのコードを3種類ほど書く必要がある
	 * (とれそうなボールは三つある、他のチームが同じこと考えてたらautonomous中 他のボールを取りに行かないといけない）
	 *  option 1
	 * phase 1
	 * 	shooter on
	 * phase 2 
	 * 	shooter off
	 * 	set(-34.07inch)
	 * phase 3
	 * 	turnto(157.5degrees)
	 * phase 4
	 *  set(80)
	 * phase 5 
	 * 	turnto(180)
	 * phase 6
	 * 	set(79.81)
	 * phase 7
	 * 	turnto(22.5)
	 * phase 8 
	 * 	set(34.07)
	 *
	 * 	option 2
	 * phase 1 
	 * 	shooter on 
	 * phase 2
	 *  shooter off
	 * 	set(-34.07inch)
	 * 	phase 3
	 * 	turnto(-157.5degrees)
	 * phase 4
	 *  set(80)
	 * phase 5 
	 * 	turnto(-180)
	 * phase 6
	 * 	set(79.81)
	 * phase 7
	 * 	turnto(-22.5)
	 * phase 8 
	 * 	set(34.07)
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
				() -> {
					System.out.println("on success");
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
