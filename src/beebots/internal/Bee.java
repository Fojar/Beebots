package beebots.internal;

import javafx.geometry.Point2D;

public class Bee extends ArenaObject {

	static final double BEE_RADIUS = 5;
	
	Point2D position;

	double pollen;

	public Bee(int ID, Hive hive) {
		super(verifyIDforPlayers(ID), BEE_RADIUS);
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
