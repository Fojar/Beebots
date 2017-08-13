package beebots.internal;

public abstract class BeeBot {

	Action currentAction = Action.IDLE;
	Action previousAction = Action.IDLE;

	/**
	 * This will be called once at the start of the round.
	 *
	 * @param bee The bee that this program will operate.
	 */
	public abstract void initalize(Bee bee);

	public abstract void computeNextAction(World world);

	public Action getCurrentAction() {
		return currentAction;
	}

	public Action getPreviousAction() {
		return previousAction;
	}

	protected void flyTo(ArenaObject target) {
		currentAction = new MoveAction(target);
	}

	protected void collectPollen() {
		currentAction = new CollectPollenAction();
	}

	protected void depositPollen() {
		currentAction = new DepositPollenAction();
	}

}
