package beebots.external;

import javafx.geometry.*;

public class ArenaObjectState<A extends beebots.internal.arena.ArenaObject> {

	public final int ID;

	protected final A internalObject;

	public ArenaObjectState(A arenaObject) {
		this.internalObject = arenaObject;
		this.ID = arenaObject.ID;
	}

	public Point2D getPosition() {
		return internalObject.getPosition();
	}

	public double getRadius() {
		return internalObject.getRadius();
	}

	public double getPollen() {
		return internalObject.getPollen();
	}

}
