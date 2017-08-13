package beebots.internal;

public class CollectPollenAction extends Action {

	Flower targetFlower;

	@Override
	boolean prepareFor(Bee bee, World world) {

		if (targetFlower == null) {

			// If the bee is on a flower, it can collect some pollen.
			Flower flower = world.getFlowerNearestToPoint(bee.position);
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
	boolean executeFor(Bee bee, World world) {
		return false;
	}

}
