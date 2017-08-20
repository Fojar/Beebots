package beebots.internal.actions;

import beebots.internal.arena.*;

public class IdleAction extends Action {

	private IdleAction() {
	}

	private static final IdleAction idleSingleton = new IdleAction();

	public static Action getInstance() {
		return idleSingleton;
	}

	@Override
	public boolean executeFor(Bee bee, Arena arena) {
		return true;
	}

	@Override
	public String getDescription() {
		return "Idle";
	}

	@Override
	public boolean isCompleted() {
		return false;
	}

}
