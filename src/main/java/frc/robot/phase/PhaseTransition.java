package frc.robot.phase;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.DoublePredicate;

public class PhaseTransition {
	private ArrayList<Phase> phaseList = new ArrayList<>();

	private Phase currentPhase;
	private Iterator<Phase> phaseIterator = phaseList.iterator();
	private boolean is_finished = false;

	public void registerPhase(Phase phase) {
		if(phaseList.size() == 0) {
			currentPhase = phase;
		}
		phaseList.add(phase);
	}

	public void run(double time) {
		if(is_finished) {
			System.out.println("All phases is finished.");
			return;
		}

		if(currentPhase.condition.test(time)) {
			System.out.println(currentPhase.toString() + " has been finished.");

			if(phaseIterator.hasNext()) {
				currentPhase = phaseIterator.next();
			} else {
				System.out.println("All phases have been finished!!");
				is_finished = true;
			}
		} else {
			currentPhase.action.run();
		}
	}


	public static class Phase {
		Runnable action;
		DoublePredicate condition;

		private int phaseID;
		private String phaseName;

		private static int phaseCounter = 0;
		public static void PhaseInit() {
			phaseCounter = 0;
		}

		public Phase(Runnable action, DoublePredicate condition) {
			this(action, condition, "");
		}

		public Phase(Runnable action, DoublePredicate condition, String phaseName) {
			this.action = action;
			this.condition = condition;
			this.phaseID = phaseCounter++;
			this.phaseName = phaseName;
		}

		public String toString() {
			return "phase " + this.phaseID + " (" + phaseName + ")";
		}
	}
}