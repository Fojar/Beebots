package beebots.external;

import beebots.internal.arena.*;

public final class BeeState extends ArenaObjectState<beebots.internal.arena.Bee> {

	public final HiveState hiveState;

	BeeState(Bee bee, HiveState hiveState) {
		super(bee);
		this.hiveState = hiveState;
	}

}
