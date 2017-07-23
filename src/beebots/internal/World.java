package beebots.internal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.geometry.Point2D;

public class World {

	final List<Flower> flowers = new ArrayList<>();
	final List<Bee> bees = new ArrayList<>();
	final List<Hive> hives = new ArrayList<>();

	public World() {

		final int HIVE_OFFSET = 450;

		List<Point2D> startingPositions = new ArrayList<>();

		for (int y = -1; y <= 1; y += 2) {
			for (int x = -1; x <= 1; x += 2) {
				startingPositions.add(new Point2D(x * HIVE_OFFSET, y * HIVE_OFFSET));
			}
		}
		
		assert (startingPositions.size() == 4);

		Collections.shuffle(startingPositions);

		for (int id = 0; id < 4; id++) {
			hives.add(new Hive(id, startingPositions.get(id)));
			bees.add(new Bee(id, startingPositions.get(id)));
		}

	}

}
