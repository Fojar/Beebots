package beebots.internal;

import java.util.Collections;
import java.util.List;

/**
 * An immutable view of the world.
 */
public class WorldState {

	public final List<Flower> flowers;
	public final List<Bee> bees;
	public final List<Hive> hives;

	public WorldState(World world) {
		this.flowers = Collections.unmodifiableList(world.flowers);
		this.bees = Collections.unmodifiableList(world.bees);
		this.hives = Collections.unmodifiableList(world.hives);
	}

}
