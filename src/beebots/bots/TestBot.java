package beebots.bots;

import beebots.internal.*;
import java.util.Random;

public class TestBot extends BeeBot {

	Bee bee;

	Random RNG = new Random();

	@Override
	public String initalize(Bee bee) {
		this.bee = bee;
		return "Collector " + bee.ID;
	}

	enum State {
		GOING_TO_FLOWER,
		COLLECTING_POLLEN,
		RETURNING_TO_HIVE,
		DEPOSITING_POLLEN
	}

	State state = State.DEPOSITING_POLLEN;

	@Override
	public void computeNextAction(World world) {

		if (getCurrentAction() == Action.IDLE) {
			// Whenever the last action has finished, we select our next action.

			switch (state) {
			case GOING_TO_FLOWER:
				collectPollen();
				state = State.COLLECTING_POLLEN;
				break;

			case COLLECTING_POLLEN:
				flyTo(bee.hive);
				state = State.RETURNING_TO_HIVE;
				break;

			case RETURNING_TO_HIVE:
				depositPollen();
				state = State.DEPOSITING_POLLEN;
				break;

			case DEPOSITING_POLLEN:
				flyTo(world.flowers.get(RNG.nextInt(world.flowers.size())));
				state = State.GOING_TO_FLOWER;
				break;
			}

		}

	}

}
