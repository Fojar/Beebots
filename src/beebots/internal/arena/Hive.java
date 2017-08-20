package beebots.internal.arena;

import java.awt.Point;
import javafx.geometry.Point2D;

public class Hive extends StationaryArenaObject {

	public static final double RADIUS = 35;
	static final double OFFSET_MULTIPLIER = 460;

	/* Each hive is associated with one bee having the same ID. */
	public final Bee bee;

	/**
	 * A point having x and y equal to either 1 or -1. This identifies the quadrant in which the hive is located.
	 */
	public final Point offset;

	Hive(int ID, Point offset) {
		super(verifyIDforPlayers(ID), getPositionFromOffset(offset));
		this.offset = offset;
		bee = new Bee(ID, this);
	}

	public double getPollenStored() {
		return pollen;
	}

	private static Point2D getPositionFromOffset(Point offset) {
		return new Point2D(offset.x * OFFSET_MULTIPLIER, offset.y * OFFSET_MULTIPLIER);
	}

	@Override
	public double getRadius() {
		return RADIUS;
	}

}
