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
		phaseTransition.run();
	}
}
