package beebots.internal;

import javafx.geometry.Point2D;

public class Hive extends StationaryArenaObject {

	static final double HIVE_RADIUS = 50;
	
	double pollen;

	public Hive(int ID, Point2D position) {
		super(verifyIDforPlayers(ID), HIVE_RADIUS, position);
	}

	public double getPollenStored() {
		return pollen;
	}

}
