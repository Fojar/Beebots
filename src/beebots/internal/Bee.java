package beebots.internal;

import javafx.geometry.Point2D;

public class Bee extends ArenaObject {

	public static final double RADIUS = 7;
	static final double POLLEN_CAPACITY = 5;

	public final Hive hive;

	Point2D position;
	double pollen;

	public Bee(int ID, Hive hive) {
		super(verifyIDforPlayers(ID));
		if (hive == null) {
			throw new IllegalArgumentException();
		}
		this.hive = hive;
		this.position = hive.getPosition();
	}

	@Override
	public Point2D getPosition() {
		return position;
	}

	public double getPollenCarried() {
		return pollen;
	}

	public double getRemainingCapacity() {
		return POLLEN_CAPACITY - pollen;
	}

	@Override
	public double getRadius() {
		return RADIUS;
	}

}
