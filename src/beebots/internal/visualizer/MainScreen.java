package beebots.internal.visualizer;

import beebots.bots.*;
import beebots.external.*;
import beebots.internal.*;
import beebots.internal.actions.*;
import beebots.internal.arena.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.List;
import java.util.*;
import java.util.stream.*;
import static java.util.stream.Collectors.groupingBy;
import javafx.geometry.Point2D;

public class MainScreen extends Screen {

	double x = HALF_WIDTH, y = HALF_HEIGHT;
	Random RNG = new Random();

	Arena arena;
	ArenaState arenaState;

	Color hiveColor = new Color(.7f, .6f, .3f);
	Color grassColor = new Color(.1f, .5f, .1f);

	List<BotRunner> runners = new ArrayList<>();
	

	@Override
	public void initialize() {

		arena = new Arena();
		arenaState = new ArenaState(arena);

		runners.add(new BotRunner(arena.bees.get(0), new TestBot(), arena, arenaState));
		runners.add(new BotRunner(arena.bees.get(1), new TestBot(), arena, arenaState));
		runners.add(new BotRunner(arena.bees.get(2), new TestBot(), arena, arenaState));

		runners.add(new BotRunner(arena.bees.get(3), new ThiefBot(), arena, arenaState));
	}

	int ticks;

	@Override
	public void update() {
		char key = host.getKey();
		if (key == KeyEvent.VK_ESCAPE) host.popScreen();

		if (++ticks == 3) {
			doActions();
			ticks = 0;
		}
	}

	@Override
	public void draw(Graphics2D g) {

		runners.forEach(c -> c.drawStatus(g));

		AffineTransform transform = g.getTransform();

		g.translate(HALF_WIDTH, HALF_HEIGHT);
		double scale = (double) HEIGHT / Arena.SIZE;
		g.scale(scale, scale);

		g.setColor(grassColor);
		g.fillRect(-Arena.MAX_COORD, -Arena.MAX_COORD, Arena.SIZE, Arena.SIZE);

		for (Hive hive : arena.hives) {
			g.setColor(hiveColor);
			fillCircle(g, hive.getPosition(), Hive.RADIUS);
			g.setColor(Color.BLACK);
			drawCircle(g, hive.getPosition(), Hive.RADIUS, .1f);
		}

		for (Flower flower : arena.flowers) {

			g.setColor(Color.BLACK);
			drawCircle(g, flower.getPosition(), Flower.RADIUS, .1f);

			g.setColor(Color.GRAY);
			fillCircle(g, flower.getPosition(), Flower.RADIUS);

			g.setColor(Color.PINK);

			double pollenRadius = Flower.RADIUS * Math.sqrt(flower.getPollenFraction());
			fillCircle(g, flower.getPosition(), pollenRadius);

		}

		g.setColor(Color.YELLOW);
		for (Bee bee : arena.bees) {
			fillCircle(g, bee.getPosition(), Bee.RADIUS);
		}
		g.setColor(Color.BLACK);
		for (Bee bee : arena.bees) {
			drawCircle(g, bee.getPosition(), Bee.RADIUS, .25f);
		}

		g.setColor(Color.RED);
		for (BotRunner runner : runners) {
			if (runner.beeBot.currentAction instanceof StealAction) {

				StealAction a = (StealAction) runner.beeBot.currentAction;

				Point2D thief = runner.bee.getPosition();
				Point2D mark = a.target.getPosition();

				Shape line = new Line2D.Double(thief.getX(), thief.getY(), mark.getX(), mark.getY());
				g.setStroke(new BasicStroke(30));
				g.draw(line);
			}
		}

		g.setTransform(transform);

	}

	private void fillCircle(Graphics2D g, Point2D centre, double radius) {

		AffineTransform transform = g.getTransform();

		g.translate(centre.getX(), centre.getY());
		g.scale(radius, radius);
		g.fillOval(-1, -1, 2, 2);

		g.setTransform(transform);
	}

	private void drawCircle(Graphics2D g, Point2D centre, double radius, float strokeWidth) {

		AffineTransform transform = g.getTransform();

		g.translate(centre.getX(), centre.getY());
		g.scale(radius, radius);

		g.setStroke(new BasicStroke(strokeWidth));
		g.drawOval(-1, -1, 2, 2);

		g.setTransform(transform);
	}

	private void doActions() {

		// Let all the bots compute their next action.
		runners.forEach(r -> r.computeNextAction());

		doCollections();

		runners.stream()
				.filter(r -> r.getCurrentAction() instanceof DepositPollenAction)
				.forEach(r -> {
					r.executeCurrentAction();
				});

		runners.stream()
				.filter(r -> r.getCurrentAction() instanceof StealAction)
				.forEach(r -> {
					r.executeCurrentAction();
				});

		List<BotRunner> movers = runners.stream()
				.filter(r -> r.getCurrentAction() instanceof MoveAction)
				.collect(Collectors.toList());

		movers.forEach(m -> m.prepareCurrentAction());
		movers.forEach(m -> m.executeCurrentAction());

	}

	private static Flower getTargetFlower(BotRunner runner) {
		CollectPollenAction action = (CollectPollenAction) runner.getCurrentAction();
		return action.getTargetFlower();
	}

	private void doCollections() {

		List<BotRunner> collectingRunners = runners.stream()
				.filter(r -> r.getCurrentAction() instanceof CollectPollenAction && r.prepareCurrentAction())
				.collect(Collectors.toList());

		// produce a map from flowers to all runners collecting on them.
		Map<Flower, List<BotRunner>> flowersBeingCollected = collectingRunners.stream()
				.collect(groupingBy(MainScreen::getTargetFlower));

		flowersBeingCollected.forEach((flower, runners) -> {
			flower.distributePollenTo(runners);
		});
	}

}
