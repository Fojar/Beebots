package beebots.internal;

import javafx.geometry.Point2D;

public class Hive extends StationaryArenaObject {

	static final double HIVE_RADIUS = 30;
	
	double pollen;
	
	/* Each hive is associated with one bee having the same ID. */
	public final Bee bee;

	public Hive(int ID, Point2D position) {
		super(verifyIDforPlayers(ID), HIVE_RADIUS, position);
		
		bee = new Bee(ID, this);
	}

	public double getPollenStored() {
		return pollen;
	}

}
