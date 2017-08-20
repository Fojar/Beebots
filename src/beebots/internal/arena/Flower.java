package beebots.internal.arena;

import beebots.internal.BotRunner;
import java.util.List;
import javafx.geometry.Point2D;

public class Flower extends StationaryArenaObject {

	public static final double RADIUS = 20;
	static final double INITIAL_POLLEN = 20;

	private static int nextId = 0;

	public Flower(Point2D position) {
		super(nextId++, position);
		pollen = INITIAL_POLLEN;
	}

	public double getPollenFraction() {
		return pollen / INITIAL_POLLEN;
	}

	public void distributePollenTo(List<BotRunner> runners) {

		final double POLLEN_PER_COLLECTION = .2;

		if (pollen == 0) {
			runners.forEach(r -> r.finishCurrentAction());
			return;
		}

		// For now, if a flower has any pollen remaining, it can provide a full turn's worth of pollen to all bees collecting.
		for (BotRunner runner : runners) {

			double capacity = runner.bee.getRemainingCapacity();
			if (capacity > 0) {

				double amount = Math.min(POLLEN_PER_COLLECTION, capacity);
				runner.bee.pollen += amount;
				pollen -= amount;

			} else {
				runner.finishCurrentAction();
			}

		}

		if (pollen < 0) pollen = 0;
	}

	@Override
	public double getRadius() {
		return RADIUS;
	}

}
