package beebots.external;

import beebots.internal.arena.*;
import java.util.*;
import java.util.stream.*;
import javafx.geometry.*;

public class ArenaState {

	public final List<FlowerState> flowers;
	public final List<BeeState> bees;
	public final List<HiveState> hives;

	public ArenaState(Arena arena) {

		flowers = arena.flowers.stream().map(f -> new FlowerState(f)).collect(ImmutableCollector.toList());
		hives = arena.bees.stream().map(b -> new HiveState(b.hive)).collect(ImmutableCollector.toList());
		bees = hives.stream().map(h -> h.beeState).collect(ImmutableCollector.toList());

		for (int i = 0; i < flowers.size(); i++) assert (flowers.get(i).ID == i);
		for (int i = 0; i < bees.size(); i++) assert (bees.get(i).ID == i);
		for (int i = 0; i < hives.size(); i++) assert (hives.get(i).ID == i);
	}

	FlowerState getFlowerNearestToPoint(Point2D point) {
		return flowers.stream()
				.collect(Collectors.minBy(Comparator.comparing(f -> f.getPosition().distance(point)))).get();
	}

}

class ImmutableCollector {

	public static <t> Collector<t, List<t>, List<t>> toList() {
		return Collector.of(ArrayList::new, List::add, (left, right) -> {
			left.addAll(right);
			return left;
		}, Collections::unmodifiableList);
	}
}
