package beebots.bots;

import beebots.external.*;
import beebots.internal.actions.*;
import java.util.*;

public class ThiefBot extends BeeBot {

	BeeState myBee;

	Random RNG = new Random();

	@Override
	public String initalize(BeeState bee) {
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
	public void computeNextAction(ArenaState arena) {

		if (currentAction == Action.IDLE) {
			// Whenever the last action has finished, we select our next action.

			switch (state) {

			case STEALING:
				flyTo(myBee.hiveState);
				state = State.RETURNING_TO_HIVE;
				break;

			case RETURNING_TO_HIVE:
				depositPollen();
				state = State.DEPOSITING_POLLEN;
				break;

			case DEPOSITING_POLLEN:
			case HUNTING:
				flyTo(arena.bees.get(RNG.nextInt(arena.bees.size())));
				state = State.HUNTING;
				break;
			}

		}

		if (state == State.HUNTING) {
			for (BeeState bee : arena.bees) {
				if (bee == myBee) continue;

				if (bee.getPosition().distance(myBee.getPosition()) <= StealAction.MAXIMUM_STEALING_DISTANCE) {
					if (bee.getPollen() > 0) {
						stealFrom(bee);
						state = State.STEALING;
						break;
					} else {
						flyTo(arena.bees.get(RNG.nextInt(arena.bees.size())));
					}
				}
			}

		}

	}

}
