package beebots.visualizer;

import beebots.internal.*;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.util.Random;
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
	Color grassColor = new Color(.1f, .6f, .1f);

	@Override
	public void initialize() {

		world = new World();
		worldState = new WorldState(world);
	}

	@Override
	public void update() {
		char key = host.getKey();
		if (key == KeyEvent.VK_ESCAPE) host.popScreen();

	}

	@Override
	public void draw(Graphics2D g) {
		
		g.translate(HALF_HEIGHT, HALF_HEIGHT);
		double scale = (double)HEIGHT / Arena.SIZE;
		g.scale(scale, scale);
		
		g.setColor(grassColor);
		g.fillRect(-Arena.MAX_COORD, -Arena.MAX_COORD, Arena.SIZE, Arena.SIZE);
		
		
		g.setColor(hiveColor);
		for (Hive hive : worldState.hives) {

			Point2D position = hive.getPosition();

			AffineTransform transform = g.getTransform();

			g.translate(position.getX(), position.getY());
			scale = hive.radius;
			g.scale(scale, scale);
			g.fillOval(-1, -1, 2, 2);

			g.setTransform(transform);

		}

	}

}
