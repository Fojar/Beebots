package beebots.internal;

public class BeeController {

	public final Bee bee;
	public final BeeBot beeBot;

	public BeeController(Bee bee, BeeBot beeBot) {
		this.bee = bee;
		this.beeBot = beeBot;
	}

	public void doTurn(WorldState worldState) {

		beeBot.doAction(worldState);
		Action action = beeBot.currentAction;

		if (action != Action.IDLE) {
			if (action.executeFor(bee)) {
				beeBot.previousAction = action;
				beeBot.currentAction = Action.IDLE;
			}
		}

	}

}
