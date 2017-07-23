package beebots.internal;

import javafx.geometry.Point2D;

/**
 * Objects that remain in a fixed position must derive from this class.
 */
public abstract class StationaryArenaObject extends ArenaObject {

	public final Point2D position;

	protected StationaryArenaObject(int ID, double radius, Point2D location) {
		super(ID, radius);
		this.position = location;
	}

	@Override
	public Point2D getPosition() {
		return position;
	}

}
