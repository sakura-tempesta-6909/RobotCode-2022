package frc.robot.phase;

import frc.robot.State;
import frc.robot.State.ConveyorState;
import frc.robot.State.DriveState;
import frc.robot.component.Drive;
import edu.wpi.first.math.util.Units;

public class Autonomous {
	private static PhaseTransition phaseTransitionA;
	private static PhaseTransition phaseTransitionB;

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

		// Phaseの登録A
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
					State.drivePidSetMeter = Units.inchesToMeters(-34.1);
					return;
				},
				(double time) -> {
					return (State.driveLeftFrontPositionMeter == Units.inchesToMeters(-34.1)) && (State.driveRightFrontPositionMeter == Units.inchesToMeters(-34.1)); //これは多分岩井君がやってるのと被るからそのままにしておく
				},
				() -> {
					System.out.println("out of tarmac");
				},
				"out of tarmac"
			),
			new PhaseTransition.Phase(
				() -> {
					State.driveState = DriveState.s_turnTo;
					State.targetDirection = 157.5;
					return;
				},
				(double time) -> {
					return State.reachTurn;
				},
				"finish 1st turn"
			),
			new PhaseTransition.Phase(
				() -> {
					State.driveState = DriveState.s_pidDrive;
					State.drivePidSetMeter = Units.inchesToMeters(80);
					return;
				}, 
				(double time) -> {
					return (State.driveLeftFrontPositionMeter == Units.inchesToMeters(80)) && (State.driveRightFrontPositionMeter == Units.inchesToMeters(80));
				},
				"reach ball"
			),
			new PhaseTransition.Phase(
				() -> {
					State.driveState = DriveState.s_turnTo;
					State.targetDirection = 180;
					return;
				},
				(double time) -> {
					return State.reachTurn;
				},
				"turn to hub"
			),
			new PhaseTransition.Phase(
				() -> {
					State.driveState = DriveState.s_pidDrive;
					State.drivePidSetMeter = Units.inchesToMeters(79.8);
					return;
				},
				(double time) -> {
					return (State.driveLeftFrontPositionMeter == Units.inchesToMeters(79.8)) && (State.driveRightFrontPositionMeter == Units.inchesToMeters(79.8));
				},
				"towards the hub"
			),
			new PhaseTransition.Phase(
				() -> {
					State.driveState = DriveState.s_turnTo;
					State.targetDirection = 22.5;
					return;
				}, 
				(double time) -> {
					return State.reachTurn;
				},
				"final turn"
			),
			new PhaseTransition.Phase(
				() -> {
					State.driveState = DriveState.s_pidDrive;
					State.drivePidSetMeter = 34.1;
					return;
				},
				(double time) -> {
					return (State.driveLeftFrontPositionMeter == Units.inchesToMeters(34.1)) && (State.driveRightFrontPositionMeter == Units.inchesToMeters(34.1));
				},
				"bump into hub"
			),
			new PhaseTransition.Phase(
				() -> {
					State.driveState = DriveState.s_stopDrive;
					State.conveyorState = ConveyorState.s_shooterShoot;
					return;
				},
				(double time) -> {
					return (time > 1);
				},
				"finish autonomous"
			)	
		);


		//  Phaseの登録B
		phaseTransitionB.registerPhase(
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
					State.drivePidSetMeter = Units.inchesToMeters(-34.1);
					return;
				},
				(double time) -> {
					return (State.driveLeftFrontPositionMeter == Units.inchesToMeters(-34.1)) && (State.driveRightFrontPositionMeter == Units.inchesToMeters(-34.1)); //これは多分岩井君がやってるのと被るからそのままにしておく
				},
				() -> {
					System.out.println("out of tarmac");
				},
				"out of tarmac"
			),
			new PhaseTransition.Phase(
				() -> {
					State.driveState = DriveState.s_turnTo;
					State.targetDirection = -157.5;
					return;
				},
				(double time) -> {
					return State.reachTurn;
				},
				"first turn"
			),
			new PhaseTransition.Phase(
				() -> {
					State.driveState = DriveState.s_pidDrive;
					State.drivePidSetMeter = Units.inchesToMeters(80);
					return;
				}, 
				(double time) -> {
					return (State.driveLeftFrontPositionMeter == Units.inchesToMeters(80)) && (State.driveRightFrontPositionMeter == Units.inchesToMeters(80));
				},
				"reach ball"
			),
			new PhaseTransition.Phase(
				() -> {
					State.driveState = DriveState.s_turnTo;
					State.targetDirection = -180;
					return;
				},
				(double time) -> {
					return State.reachTurn;
				},
				"turn to hub"
			),
			new PhaseTransition.Phase(
				() -> {
					State.driveState = DriveState.s_pidDrive;
					State.drivePidSetMeter = Units.inchesToMeters(79.8);
					return;
				},
				(double time) -> {
					return (State.driveLeftFrontPositionMeter == Units.inchesToMeters(79.8)) && (State.driveRightFrontPositionMeter == Units.inchesToMeters(79.8));
				},
				"towards the hub"
			),
			new PhaseTransition.Phase(
				() -> {
					State.driveState = DriveState.s_turnTo;
					State.targetDirection = -22.5;
					return;
				}, 
				(double time) -> {
					return State.reachTurn;
				},
				"final turn"
			),
			new PhaseTransition.Phase(
				() -> {
					State.driveState = DriveState.s_pidDrive;
					State.drivePidSetMeter = 34;
					return;
				},
				(double time) -> {
					return (State.driveLeftFrontPositionMeter == Units.inchesToMeters(34.1)) && (State.driveRightFrontPositionMeter == Units.inchesToMeters(34.1));
				},
				"bump into hub"
			),
			new PhaseTransition.Phase(
				() -> {
					State.driveState = DriveState.s_stopDrive;
					State.conveyorState = ConveyorState.s_shooterShoot;
					return;
				},
				(double time) -> {
					return (time > 1);
				},
				"finish autonomous"
			)	
		);
	}

	public static void run() {
		if(State.gameSpecificMessage == "1"){
			phaseTransitionA.run();
		} else if(State.gameSpecificMessage == "2"){
			phaseTransitionB.run();
		}
	}
}
