package beebots.internal;

public class StealAction extends Action {

	final ArenaObject target;

	StealAction(ArenaObject target) {
		this.target = target;
	}

	final double DISTANCE_PER_MOVE = 100;

	@Override
	boolean executeFor(Bee bee, World world) {

		return completed;
	}

	@Override
	public boolean isCompleted() {
		return completed;
	}

	@Override
	public String getDescription() {
		return "Stealing";
	}

}
