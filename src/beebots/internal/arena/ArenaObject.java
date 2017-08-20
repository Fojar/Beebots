package beebots.internal.arena;

import javafx.geometry.Point2D;

/**
 * All objects that appear in the arena must derive from this class.
 */
public abstract class ArenaObject {

	/**
	 * This ID need only be unique across instances of a particular derived class.
	 */
	public final int ID;

	protected double pollen;

	protected ArenaObject(int ID) {
		this.ID = ID;
	}

	/**
	 * There can only be 4 players, so IDs for bees and hives must be limited to the range [0, 3].
	 */
	protected static int verifyIDforPlayers(int ID) {
		if (ID < 0 || ID > 3) throw new IllegalArgumentException("ID must be from 0 to 3.");
		return ID;
	}

	public abstract Point2D getPosition();

	public abstract double getRadius();

	public double getPollen() {
		return pollen;
	}

	public void setPollen(double value) {
		if (pollen >= 0) pollen = value;
	}

	public double addPollen(double value) {
		setPollen(pollen + value);
		return pollen;
	}
}
