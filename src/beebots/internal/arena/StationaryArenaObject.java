package beebots.internal.arena;

import beebots.internal.arena.Arena;
import beebots.internal.arena.ArenaObject;
import javafx.geometry.Point2D;

/**
 * Objects that remain in a fixed position must derive from this class.
 */
public abstract class StationaryArenaObject extends ArenaObject {

	public final Point2D location;

	protected StationaryArenaObject(int ID, Point2D location) {
		super(ID);
		if (!Arena.inRange(location)) throw new IllegalArgumentException("location is out of range.");

		this.location = location;
	}

	@Override
	public Point2D getPosition() {
		return location;
	}

}
