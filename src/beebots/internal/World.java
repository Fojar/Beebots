package beebots.internal;

import java.util.*;
import java.util.stream.Collectors;
import javafx.geometry.Point2D;

public class World {

	final List<Flower> flowers;
	final List<Bee> bees = new ArrayList<>();
	final List<Hive> hives = new ArrayList<>();

	public World() {

		final double HIVE_OFFSET = 450;
		final double FLOWER_EXTENT = 450;

		List<Point2D> startingPositions = new ArrayList<>();

		for (int y = -1; y <= 1; y += 2) {
			for (int x = -1; x <= 1; x += 2) {
				startingPositions.add(new Point2D(x * HIVE_OFFSET, y * HIVE_OFFSET));
			}
		}

		assert (startingPositions.size() == 4);

		Collections.shuffle(startingPositions);

		for (int id = 0; id < 4; id++) {
			Hive hive = new Hive(id, startingPositions.get(id));
			hives.add(hive);
			bees.add(hive.bee);
		}

		// Create flowers.
		Random RNG = new Random();

		// Start with all the hive locations, so flowers aren't spawned near them.
		ArrayList<Point2D> flowerPositions = hives.stream().map(h -> h.location).collect(Collectors.toCollection(ArrayList<Point2D>::new));
		// There will always be a flower in the exact centre.
		flowerPositions.add(Point2D.ZERO);

		for (int i = 0; i < 100; i++) {
			Point2D candidate = new Point2D(RNG.nextDouble() * FLOWER_EXTENT, RNG.nextDouble() * FLOWER_EXTENT);

			if (!flowerPositions.stream().anyMatch(p -> p.distance(candidate) < 150)) {
				flowerPositions.add(candidate);
				flowerPositions.add(new Point2D(-candidate.getY(), candidate.getX()));
				flowerPositions.add(new Point2D(candidate.getY(), -candidate.getX()));
				flowerPositions.add(new Point2D(-candidate.getX(), -candidate.getY()));
			}
		}

		flowers = flowerPositions.stream().skip(hives.size()).map(pos -> new Flower(pos)).collect(Collectors.toList());

		for (int i = 0; i < flowers.size(); i++) {
			assert (flowers.get(i).ID == i);
		}
	}

}
