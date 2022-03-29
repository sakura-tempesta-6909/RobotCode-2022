package frc.robot.phase;

import frc.robot.State;
import frc.robot.State.ConveyorState;
import frc.robot.State.DriveState;
import frc.robot.component.Drive;

public class Autonomous {
	private static PhaseTransition phaseTransitionA;

	public static void autonomousInit() {
		phaseTransitionA = new PhaseTransition();
		phaseTransitionB = new PhaseTransition();
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
		phaseTransitionA.registerPhase(
			new PhaseTransition.Phase(
				() -> {
					State.conveyorState = ConveyorState.s_shooterShoot;
					return;
					
				},
				(double time) -> {
					return time > 1.0; //ここは調整
				},
				() -> {
					System.out.println("initial shot fired");
				},
				"initial shot"
			),
			new PhaseTransition.Phase(
				() -> {
					State.conveyorState = ConveyorState.s_intakeConveyor;
					State.driveState = DriveState.s_pidDrive;
					State.drivePidSetMeter = 2;
					return;
				},
				(double time) -> {
					return (State.driveLeftFrontPositionMeter == 2) && (State.driveRightFrontPositionMeter == 2); //これは多分岩井君がやってるのと被るからそのままにしておく
				},
				() -> {
					System.out.println("out of tarmac");
				},
				"out of tarmac"
			),
			new PhaseTransition.Phase(
				() -> {
					
					return;
				},
				(double time) -> {
					return (true);
				},
				""
			)
		);
	}

	public static void run() {
		phaseTransitionA.run();
	}
}
