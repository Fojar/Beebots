package beebots.external;

public final class HiveState extends ArenaObjectState<beebots.internal.arena.Hive> {

	public final BeeState beeState;

	public HiveState(beebots.internal.arena.Hive hive) {
		super(hive);
		beeState = new BeeState(hive.bee, this);
	}

}
