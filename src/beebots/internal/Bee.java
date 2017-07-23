package beebots.internal;

import javafx.geometry.Point2D;

public class Bee extends ArenaObject {

	Point2D position;

	double pollen;

	public Bee(int ID, Point2D position) {

		super(verifyIDforPlayers(ID), 5);
		
		
		if (!Arena.inRange(position)) throw new IllegalArgumentException("position is out of range.");
		this.position = position;
	}

	@Override
	public Point2D getPosition() {
		return position;
	}

	public double getPollenCarried() {
		return pollen;
	}

}
