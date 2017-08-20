package beebots.external;

import beebots.internal.actions.*;

public abstract class BeeBot {

	public Action currentAction = Action.IDLE;
	public Action previousAction = Action.IDLE;

	/**
	 * This will be called once at the start of the round.
	 *
	 * @param bee The bee that this program will operate.
	 * @return The name for this bot.
	 */
	public abstract String initalize(BeeState bee);

	/**
	 * This will be called once for each turn in the simulation.
	 *
	 * @param arenaState The complete state of the arena at the beginning of the turn.
	 */
	public abstract void computeNextAction(ArenaState arenaState);

	protected final void flyTo(ArenaObjectState target) {
		currentAction = target == null ? Action.IDLE : new MoveAction(target);
	}

	protected final void collectPollen() {
		currentAction = new CollectPollenAction();
	}

	protected final void depositPollen() {
		currentAction = new DepositPollenAction();
	}

	protected final void stealFrom(BeeState bee) {
		currentAction = bee == null ? Action.IDLE : new StealAction(bee);
	}

}
