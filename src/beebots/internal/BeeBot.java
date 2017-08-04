package beebots.internal;

public abstract class BeeBot {

	Action currentAction = Action.IDLE;
	Action previousAction = Action.IDLE;

	public abstract void doAction(WorldState worldState);

	public Action getCurrentAction() {
		return currentAction;
	}
	
	public Action getPreviousAction() {
		return previousAction;
	}

	protected void move(ArenaObject target) {
		currentAction = new MoveAction(target);
	}

}
