package frc.robot.phase;

import frc.robot.State;
import frc.robot.State.ConveyorState;
import frc.robot.State.DriveState;
import frc.robot.component.Drive;

public class Autonomous {
	private static PhaseTransition phaseTransition;

	public static void autonomousInit() {
		phaseTransition = new PhaseTransition();
		PhaseTransition.Phase.PhaseInit();

		// Phaseの登録
		phaseTransition.registerPhase(
			new PhaseTransition.Phase(
				() -> {
					State.is_compressorEnabled = true;
					State.conveyorState = State.ConveyorState.s_intakeConveyor;
					State.conveyorState = ConveyorState.s_shooterShoot;
					return;
				},
				(double time) -> {
					return time > 0.5;
				},
				() -> {
					System.out.println("on success");
					State.driveState = DriveState.s_pidDrive;
					State.drivePidSetMeter = 2;
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
