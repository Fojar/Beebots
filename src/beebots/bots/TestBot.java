package beebots.bots;

import beebots.internal.*;
import java.util.Random;

public class TestBot extends BeeBot {

	Random RNG = new Random();

	@Override
	public void doAction(WorldState worldState) {

		if (getCurrentAction() == Action.IDLE) {
			Flower f = worldState.flowers.get(RNG.nextInt(worldState.flowers.size()));
			move(f);
		}

	}
}
