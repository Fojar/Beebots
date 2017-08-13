package beebots.internal;

import javafx.geometry.Point2D;

public class MoveAction extends Action {

	final ArenaObject target;

	MoveAction(ArenaObject target) {
		this.target = target;
	}

	final double DISTANCE_PER_MOVE = 50;

	@Override
	boolean executeFor(Bee bee, World world) {

		// Move the bee toward its target.
		Point2D vector = target.getPosition().subtract(bee.position);
		double distance = vector.magnitude();

		if (distance > DISTANCE_PER_MOVE) {

			vector = vector.multiply(DISTANCE_PER_MOVE / distance);
			bee.position = bee.position.add(vector);

			// If the remaining distance is less than the target's radius, consider the move done. 
			completed = (distance - vector.magnitude()) < target.getRadius();
		} else {
			bee.position = target.getPosition();
			completed = true;
		}

		return completed;
	}

	@Override
	public String getDescription() {
		return "Flying";
	}

}
