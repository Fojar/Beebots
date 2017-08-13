package beebots.internal;

public class DepositPollenAction extends Action {

	@Override
	boolean prepareFor(Bee bee, World world) {

		// Depositing is possible when the bee is on its hive.
		return bee.hive.location.distance(bee.position) < Hive.RADIUS;
	}

	@Override
	public String getDescription() {
		return "Depositing";
	}

	@Override
	boolean executeFor(Bee bee, World world) {
		bee.hive.pollen += bee.pollen;
		bee.pollen = 0;
		return true;
	}

}
