package beebots.internal;

public class IdleAction extends Action {

	private IdleAction() {
	}

	private static final IdleAction idleSingleton = new IdleAction();

	public static Action getInstance() {
		return idleSingleton;
	}

	@Override
	boolean executeFor(Bee bee, World world) {
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
