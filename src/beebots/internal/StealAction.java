package beebots.internal;

public class StealAction extends Action {

	public static final double MAXIMUM_STEALING_DISTANCE = 50;

	final Bee target;

	StealAction(Bee target) {
		this.target = target;
	}

	@Override
	boolean prepareFor(Bee bee, World world) {
		return bee.position.distance(target.position) <= MAXIMUM_STEALING_DISTANCE;
	}

	@Override
	boolean executeFor(Bee bee, World world) {

		double average = (bee.pollen + target.pollen) / 2;
		bee.pollen = average;
		target.pollen = average;

		System.out.println("theft completed.");

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
