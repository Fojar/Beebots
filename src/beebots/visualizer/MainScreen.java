package beebots.visualizer;

import beebots.bots.TestBot;
import beebots.bots.ThiefBot;
import beebots.internal.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.*;
import javafx.geometry.Point2D;

public class MainScreen extends Screen {

	double x = HALF_WIDTH, y = HALF_HEIGHT;
	Random RNG = new Random();

	double angle = RNG.nextFloat() * Math.PI;
	double angleVel = RNG.nextGaussian() * .1;
	double angleAcc;

	World world;

	Color hiveColor = new Color(.7f, .6f, .3f);
	Color grassColor = new Color(.1f, .5f, .1f);

	List<BotRunner> runners = new ArrayList<>();

	@Override
	public void initialize() {

		world = new World();

		runners.add(new BotRunner(world.bees.get(0), new TestBot(), world));
		runners.add(new BotRunner(world.bees.get(1), new TestBot(), world));
		runners.add(new BotRunner(world.bees.get(2), new TestBot(), world));

		runners.add(new BotRunner(world.bees.get(3), new ThiefBot(), world));
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

		for (Hive hive : world.hives) {
			g.setColor(hiveColor);
			fillCircle(g, hive.getPosition(), Hive.RADIUS);
			g.setColor(Color.BLACK);
			drawCircle(g, hive.getPosition(), Hive.RADIUS, .1f);
		}

		for (Flower flower : world.flowers) {

			g.setColor(Color.BLACK);
			drawCircle(g, flower.getPosition(), Flower.RADIUS, .1f);

			g.setColor(Color.GRAY);
			fillCircle(g, flower.getPosition(), Flower.RADIUS);

			g.setColor(Color.PINK);

			double pollenRadius = Flower.RADIUS * Math.sqrt(flower.getPollenFraction());
			fillCircle(g, flower.getPosition(), pollenRadius);

		}

		g.setColor(Color.YELLOW);
		for (Bee bee : world.bees) {
			fillCircle(g, bee.getPosition(), Bee.RADIUS);
		}
		g.setColor(Color.BLACK);
		for (Bee bee : world.bees) {
			drawCircle(g, bee.getPosition(), Bee.RADIUS, .25f);
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

		runners.stream()
				.filter(r -> r.getCurrentAction() instanceof MoveAction)
				.forEach(r -> {
					r.executeCurrentAction();
				});

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
