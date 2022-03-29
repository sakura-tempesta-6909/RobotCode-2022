package frc.robot.phase;

import frc.robot.State;
import frc.robot.State.ConveyorState;
import frc.robot.State.DriveState;
import frc.robot.component.Drive;
import frc.robot.mode.ConveyorMode;
import frc.robot.phase.PhaseTransition.Phase;

import edu.wpi.first.math.util.Units;

public class Autonomous {
	private static PhaseTransition phaseTransitionA;
	private static PhaseTransition phaseTransitionB;
	private static PhaseTransition phaseTransitionC;

	private static PhaseTransition.Phase straightPidDrive(double inch, String phaseName) {
		return new PhaseTransition.Phase(
			() -> {
				State.driveState = DriveState.s_pidDrive;
				State.drivePidSetMeter = Units.inchesToMeters(inch);
				return;
			},
			(double time) -> {
				return State.isDrivePidFinished;
			},
			() -> {
				State.driveAccumulateReset = true;
			},
			phaseName
		);
	}

	private static PhaseTransition.Phase stationary(double period, String phaseName) {
		return new PhaseTransition.Phase(
			() -> {
				return;
			},
			(double time) -> {
				return time > period;
			},
			phaseName
		);
	}

	private static PhaseTransition.Phase turnTo(double angle, String phaseName) {
		return new PhaseTransition.Phase(
			() -> {
				State.driveState = DriveState.s_turnTo;
				State.targetDirection = angle;
				return;
			},
			(double time) -> {
				return State.reachTurn;
			},
			() -> {
				State.gyroReset = true;
			},
			phaseName
		);
	}

	private static PhaseTransition.Phase conveyorMode(double waiter, ConveyorState mode, String phaseName) {
		return new PhaseTransition.Phase(
				() -> {
					State.conveyorState = mode;
					return;
				},
				(double time) -> {
					return time > waiter;
				},
				phaseName
			);
	}

	private static PhaseTransition.Phase intakeExtend(double waiter, boolean isExtendOpen, String phaseName) {
		return new PhaseTransition.Phase(
			() -> {
				State.is_intakeExtendOpen = isExtendOpen;
				return;
			},
			(double time) -> {
				return time > waiter;
			},
			phaseName
		);
	}


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

		State.is_intakeExtendOpen = false;

		// Phaseの登録A
		phaseTransitionA.registerPhase(

			conveyorMode(1.0, ConveyorState.s_shootConveyor, "initialShot"),

			straightPidDrive(-34.1, "out of tarmac"),

			turnTo(157.5, "first turn"),

			intakeExtend(0.3, true, "open intake"),

			new PhaseTransition.Phase(
				() -> {
					State.conveyorState = ConveyorState.s_intakeConveyor;
					State.driveState = DriveState.s_pidDrive;
					State.drivePidSetMeter = Units.inchesToMeters(80);
					return;
				}, 
				(double time) -> {
					return State.isDrivePidFinished;
				},
				"move towards ball"
			),

			//stationary(3, "wait for ball to enter"), //これはいるか分からん

			turnTo(180, "u-turn"),

			intakeExtend(0.3, false, "close intake"),

			straightPidDrive(79.8, "towards the hub"),

			turnTo(22.5, "turn to hub"), 

			straightPidDrive(34.1, "bump into hub"),

			conveyorMode(1.0, ConveyorState.s_shooterShoot, "end of autonomous")
		);


		//  Phaseの登録B
		phaseTransitionB.registerPhase(

			conveyorMode(1.0, ConveyorState.s_shootConveyor, "initialShot"),

			straightPidDrive(-34.1, "out of tarmac"),

			turnTo(-157.5, "first turn"),

			intakeExtend(0.3, true, "open intake"),

			new PhaseTransition.Phase(
				() -> {
					State.conveyorState = ConveyorState.s_intakeConveyor;
					State.driveState = DriveState.s_pidDrive;
					State.drivePidSetMeter = Units.inchesToMeters(80);
					return;
				}, 
				(double time) -> {
					return State.isDrivePidFinished;
				},
				"move towards ball"
			),

			turnTo(-180, "u-turn"),

			intakeExtend(0.3, false, "close intake"),

			straightPidDrive(79.8, "towards the hub"),

			turnTo(-22.5, "turn to hub"), 

			straightPidDrive(34.1, "bump into hub"),

			conveyorMode(1.0, ConveyorState.s_shooterShoot, "end of autonomous")
		);

		// Phaseの登録C
		phaseTransitionC.registerPhase(

			conveyorMode(5.0, ConveyorState.s_shootConveyor, "initialShot"),

			straightPidDrive(-34.1, "out of tarmac")
		);
	}

	public static void run() {
		if(State.gameSpecificMessage == "A"){
			phaseTransitionA.run();
		} else if(State.gameSpecificMessage == "B"){
			phaseTransitionB.run();
		} else{
			phaseTransitionC.run();
		}
	}
}
