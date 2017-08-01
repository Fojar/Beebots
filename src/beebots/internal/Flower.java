package beebots.internal;

import javafx.geometry.Point2D;

public class Flower extends StationaryArenaObject {

	static final double FLOWER_RADIUS = 15;
	
	private static int nextId = 0;
	
	private double pollen;

	public Flower(Point2D position) {
		super(nextId++, FLOWER_RADIUS, position);
		pollen = 100;
	}

	public double getPollen() {
		return pollen;
	}

	void setPollen(double value) {
		pollen = value;
	}

}
