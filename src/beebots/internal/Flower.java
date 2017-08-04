package beebots.internal;

import javafx.geometry.Point2D;

public class Flower extends StationaryArenaObject {

	static final double FLOWER_RADIUS = 15;

	static final double INITIAL_POLLEN = 10;

	private static int nextId = 0;

	private double pollen;

	public Flower(Point2D position) {
		super(nextId++, FLOWER_RADIUS, position);
		pollen = INITIAL_POLLEN;
	}

	public double getPollen() {
		return pollen;
	}

	public double getPollenFraction() {
		return pollen / INITIAL_POLLEN;
	}

	void setPollen(double value) {
		pollen = value;
	}

}
