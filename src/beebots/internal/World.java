package beebots.internal;

import java.awt.Point;
import java.util.*;
import java.util.stream.Collectors;
import javafx.geometry.Point2D;

public class World {

	public final List<Flower> flowers;
	public final List<Bee> bees;
	public final List<Hive> hives;

	public World() {

		List<Point> startingPositions = new ArrayList<>();

		for (int y = -1; y <= 1; y += 2) {
			for (int x = -1; x <= 1; x += 2) {
				startingPositions.add(new Point(x, y));
			}
		}

		assert (startingPositions.size() == 4);

		Collections.shuffle(startingPositions);

		List<Hive> tempHives = new ArrayList<>();
		List<Bee> tempBees = new ArrayList<>();

		for (int id = 0; id < 4; id++) {
			Hive hive = new Hive(id, startingPositions.get(id));
			tempHives.add(hive);
			tempBees.add(hive.bee);
		}

		hives = Collections.unmodifiableList(tempHives);
		bees = Collections.unmodifiableList(tempBees);

		flowers = createFlowers();

	}

	private List<Flower> createFlowers() {
		Random RNG = new Random();

		// Start with all the hive locations, so flowers aren't spawned near them.
		ArrayList<Point2D> flowerPositions = hives.stream()
				.map(h -> h.location)
				.collect(Collectors.toCollection(ArrayList<Point2D>::new));

		// There will always be a flower in the exact centre.
		flowerPositions.add(Point2D.ZERO);

		final double FLOWER_EXTENT = 460;
		final double MINIMUM_FLOWER_SEPARATION = 150;

		for (int i = 0; i < 100; i++) {
			Point2D candidate = new Point2D(RNG.nextDouble() * FLOWER_EXTENT, RNG.nextDouble() * FLOWER_EXTENT);

			if (!flowerPositions.stream().anyMatch(p -> p.distance(candidate) < MINIMUM_FLOWER_SEPARATION)) {
				flowerPositions.add(candidate);
				flowerPositions.add(new Point2D(-candidate.getY(), candidate.getX()));
				flowerPositions.add(new Point2D(candidate.getY(), -candidate.getX()));
				flowerPositions.add(new Point2D(-candidate.getX(), -candidate.getY()));
			}
		}

		List<Flower> tempFlowers = flowerPositions.stream()
				.skip(hives.size()) // The first positions were for the hives.
				.map(pos -> new Flower(pos))
				.collect(Collectors.toList());

		for (int i = 0; i < tempFlowers.size(); i++) {
			assert (tempFlowers.get(i).ID == i);
		}

		System.out.println(tempFlowers.size() + " flowers created.");

		return Collections.unmodifiableList(tempFlowers);
	}

	Flower getFlowerNearestToPoint(Point2D point) {

		return flowers.stream()
				.collect(Collectors.minBy(Comparator.comparing(f -> f.location.distance(point)))).get();
	}

}
