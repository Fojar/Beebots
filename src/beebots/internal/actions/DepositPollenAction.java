package beebots.internal.actions;

import beebots.internal.arena.*;

public class DepositPollenAction extends Action {

	@Override
	public boolean prepareFor(Bee bee, Arena arena) {

		// Depositing is possible when the bee is on its hive.
		return bee.hive.location.distance(bee.position) < Hive.RADIUS;
	}

	@Override
	public String getDescription() {
		return "Depositing";
	}

	@Override
	public boolean executeFor(Bee bee, Arena arena) {
		bee.hive.addPollen(bee.getPollen());
		bee.setPollen(0);
		return (completed = true);
	}

}
