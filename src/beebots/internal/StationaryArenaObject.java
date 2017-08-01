package beebots.internal;

import javafx.geometry.Point2D;

/**
 * Objects that remain in a fixed position must derive from this class.
 */
public abstract class StationaryArenaObject extends ArenaObject {

	public final Point2D location;

	protected StationaryArenaObject(int ID, double radius, Point2D location) {
		super(ID, radius);
		if (!Arena.inRange(location)) throw new IllegalArgumentException("location is out of range.");

		this.location = location;
	}

	@Override
	public Point2D getPosition() {
		return location;
	}

}
