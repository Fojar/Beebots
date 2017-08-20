package beebots.internal.arena;

import javafx.geometry.Point2D;

public class Bee extends ArenaObject {

	public static final double RADIUS = 7;
	static final double POLLEN_CAPACITY = 5;

	public final Hive hive;

	public Point2D position;

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

	@Override
	public double getRadius() {
		return RADIUS;
	}

	public double getRemainingCapacity() {
		return POLLEN_CAPACITY - pollen;
	}

}
