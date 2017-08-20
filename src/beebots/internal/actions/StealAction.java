package beebots.internal.actions;

import beebots.internal.arena.*;

public class StealAction extends Action {

	public static final double MAXIMUM_STEALING_DISTANCE = 50;

	public final beebots.external.BeeState target;

	public StealAction(beebots.external.BeeState target) {
		this.target = target;
	}

	@Override
	public boolean prepareFor(Bee bee, Arena arena) {
		return bee.ID != target.ID && bee.position.distance(target.getPosition()) <= MAXIMUM_STEALING_DISTANCE;
	}

	@Override
	public boolean executeFor(Bee bee, Arena arena) {

		double average = (bee.getPollen() + target.getPollen()) / 2;
		bee.setPollen(average);
		arena.bees.get(target.ID).setPollen(average);

		return (completed = true);
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
