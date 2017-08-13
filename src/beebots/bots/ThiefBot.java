package beebots.bots;

import beebots.internal.*;
import java.util.Random;

public class ThiefBot extends BeeBot {

	Bee myBee;

	Random RNG = new Random();

	@Override
	public String initalize(Bee bee) {
		this.myBee = bee;
		return "Thief " + bee.ID;
	}

	enum State {
		HUNTING,
		STEALING,
		RETURNING_TO_HIVE,
		DEPOSITING_POLLEN
	}

	State state = State.DEPOSITING_POLLEN;

	@Override
	public void computeNextAction(World world) {

		if (getCurrentAction() == Action.IDLE) {
			// Whenever the last action has finished, we select our next action.

			switch (state) {

			case STEALING:
				flyTo(myBee.hive);
				state = State.RETURNING_TO_HIVE;
				break;

			case RETURNING_TO_HIVE:
				depositPollen();
				state = State.DEPOSITING_POLLEN;
				break;

			case DEPOSITING_POLLEN:
			case HUNTING:
				flyTo(world.bees.get(RNG.nextInt(world.bees.size())));
				state = State.HUNTING;
				break;
			}

		}

		if (state == State.HUNTING) {
			for (Bee bee : world.bees) {
				if (bee == myBee) continue;

				if (bee.getPosition().distance(myBee.getPosition()) <= StealAction.MAXIMUM_STEALING_DISTANCE) {
					if (bee.getPollenCarried() > 0) {
						stealFrom(bee);
						state = State.STEALING;
						break;
					} else {
						flyTo(world.bees.get(RNG.nextInt(world.bees.size())));
					}
				}
			}

		}

	}

}
