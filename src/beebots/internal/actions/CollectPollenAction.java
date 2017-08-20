package beebots.internal.actions;

import beebots.internal.arena.*;

public class CollectPollenAction extends Action {

	Flower targetFlower;

	@Override
	public boolean prepareFor(Bee bee, Arena arena) {

		if (targetFlower == null) {

			// If the bee is on a flower, it can collect some pollen.
			Flower flower = arena.getFlowerNearestToPoint(bee.position);
			double distance = flower.location.distance(bee.position);

			if (distance < Flower.RADIUS) {
				targetFlower = flower;
			}
		}

		return targetFlower != null;
	}

	public Flower getTargetFlower() {
		return targetFlower;
	}

	@Override
	public String getDescription() {
		return "Collecting";
	}

	@Override
	public boolean executeFor(Bee bee, Arena arena) {
		return false;
	}

}
