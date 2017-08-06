package beebots.visualizer;

import beebots.bots.TestBot;
import beebots.internal.*;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.util.*;
import java.util.stream.Collectors;
import javafx.geometry.Point2D;

public class MainScreen extends Screen {

	double x = HALF_WIDTH, y = HALF_HEIGHT;
	Random RNG = new Random();

	double angle = RNG.nextFloat() * Math.PI;
	double angleVel = RNG.nextGaussian() * .1;
	double angleAcc;

	World world;
	WorldState worldState;

	Color hiveColor = new Color(.7f, .6f, .3f);
	Color grassColor = new Color(.1f, .5f, .1f);

	List<BeeController> controllers = new ArrayList<>();

	@Override
	public void initialize() {

		world = new World();
		worldState = new WorldState(world);

		controllers = world.bees.stream()
				.map(b -> new BeeController(b, new TestBot()))
				.collect(Collectors.toList());
	}

	int ticks;

	@Override
	public void update() {
		char key = host.getKey();
		if (key == KeyEvent.VK_ESCAPE) host.popScreen();

		if (++ticks == 15) {
			controllers.forEach(c -> c.doTurn(worldState));
			ticks = 0;
		}

	}

	@Override
	public void draw(Graphics2D g) {

		controllers.forEach(c -> c.drawStatus(g));

		AffineTransform transform = g.getTransform();

		g.translate(HALF_WIDTH, HALF_HEIGHT);
		double scale = (double) HEIGHT / Arena.SIZE;
		g.scale(scale, scale);

		g.setColor(grassColor);
		g.fillRect(-Arena.MAX_COORD, -Arena.MAX_COORD, Arena.SIZE, Arena.SIZE);

		for (Hive hive : worldState.hives) {
			g.setColor(hiveColor);
			fillCircle(g, hive.getPosition(), hive.radius);
			g.setColor(Color.BLACK);
			drawCircle(g, hive.getPosition(), hive.radius, .1f);
		}

		g.setColor(Color.PINK);
		for (Flower flower : worldState.flowers) {
			fillCircle(g, flower.getPosition(), flower.radius);
		}

		g.setColor(Color.YELLOW);
		for (Bee bee : worldState.bees) {
			fillCircle(g, bee.getPosition(), bee.radius);
		}
		g.setColor(Color.BLACK);
		for (Bee bee : worldState.bees) {
			drawCircle(g, bee.getPosition(), bee.radius, .3f);
		}

		g.setTransform(transform);

	}

	private void fillCircle(Graphics2D g, Point2D centre, double radius) {

		AffineTransform transform = g.getTransform();

		g.translate(centre.getX(), centre.getY());
		double scale = radius;
		g.scale(scale, scale);
		g.fillOval(-1, -1, 2, 2);

		g.setTransform(transform);
	}

	private void drawCircle(Graphics2D g, Point2D centre, double radius, float strokeWidth) {

		AffineTransform transform = g.getTransform();

		g.translate(centre.getX(), centre.getY());
		double scale = radius;
		g.scale(scale, scale);

		g.setStroke(new BasicStroke(strokeWidth));
		g.drawOval(-1, -1, 2, 2);

		g.setTransform(transform);
	}

}
