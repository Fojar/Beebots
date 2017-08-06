package beebots.internal;

import javafx.geometry.Point2D;

public class Bee extends ArenaObject {

	static final double BEE_RADIUS = 7;
	static final double POLLEN_CAPACITY = 5;

	public final Hive hive;

	Point2D position;
	double pollen;

	public Bee(int ID, Hive hive) {
		super(verifyIDforPlayers(ID), BEE_RADIUS);
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

}
