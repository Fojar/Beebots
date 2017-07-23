package beebots.internal;

import javafx.geometry.Point2D;

public class Flower extends StationaryArenaObject {

	static final double FLOWER_RADIUS = 5;
	
	private double pollen;

	public Flower(double pollen, int ID, Point2D position, double radius) {
		super(verifyIDforPlayers(ID), FLOWER_RADIUS, position);
	}

	public double getPollen() {
		return pollen;
	}

	void setPollen(double value) {
		pollen = value;
	}

}
