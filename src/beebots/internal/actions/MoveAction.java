package beebots.internal.actions;

import beebots.external.*;
import beebots.internal.arena.*;
import javafx.geometry.*;

public class MoveAction extends Action {

	public final beebots.external.ArenaObjectState target;

	private Point2D destination;

	public MoveAction(ArenaObjectState target) {
		this.target = target;
	}

	@Override
	public boolean prepareFor(Bee bee, Arena arena) {

		// Capture the target's position before moves are processed, to ensure order-independence.
		if (target != null) {
			destination = target.getPosition();
			return true;
		} else return false;
	}

	final double DISTANCE_PER_MOVE = 25;

	@Override
	public boolean executeFor(Bee bee, Arena arena) {

		// Move the bee toward the destination point.
		Point2D vector = destination.subtract(bee.position);
		double distance = vector.magnitude();

		if (distance > DISTANCE_PER_MOVE) {

			vector = vector.multiply(DISTANCE_PER_MOVE / distance);
			bee.position = bee.position.add(vector);

			// If the remaining distance is less than the target's radius, consider the move done. 
			completed = (distance - vector.magnitude()) < target.getRadius();
		} else {
			bee.position = destination;
			completed = true;
		}

		return completed;
	}

	@Override
	public String getDescription() {
		return "Flying";
	}

}
