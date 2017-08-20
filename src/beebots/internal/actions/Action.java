package beebots.internal.actions;

import beebots.internal.arena.*;

public abstract class Action {

	public static final Action IDLE = IdleAction.getInstance();

	protected boolean completed;

	/**
	 * @return true if the preparation succeeds and the action can be executed.
	 */
	public boolean prepareFor(Bee bee, Arena arena) {
		return true;
	}

	/**
	 * @return true if the action is finished.
	 */
	public abstract boolean executeFor(Bee bee, Arena world);

	public abstract String getDescription();

	public boolean isCompleted() {
		return completed;
	}

	public void finish() {
		completed = true;
	}

}
