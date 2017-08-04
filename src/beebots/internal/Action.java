package beebots.internal;

public abstract class Action {

	abstract boolean executeFor(Bee bee);

	public abstract String getDescription();

	public static final Action IDLE = IdleAction.getInstance();

	abstract boolean isCompleted();

}
