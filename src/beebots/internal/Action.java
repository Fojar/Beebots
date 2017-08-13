package beebots.internal;

public abstract class Action {

	public static final Action IDLE = IdleAction.getInstance();

	protected boolean completed;

	/**
	 * @return true if the preparation succeeds and the action can be executed.
	 */
	boolean prepareFor(Bee bee, World world) {
		return true;
	}

	/**
	 * @return true if the action is finished.
	 */
	abstract boolean executeFor(Bee bee, World world);

	public abstract String getDescription();

	public boolean isCompleted() {
		return completed;
	}

	void finish() {
		completed = true;
	}

}
