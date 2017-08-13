package beebots.internal;

import beebots.visualizer.FontSystem;
import beebots.visualizer.Screen;
import java.awt.*;
import java.awt.geom.AffineTransform;

public class BotRunner {

	public final Bee bee;
	public final BeeBot beeBot;
	public final World world;

	public final String name;

	final Font actionFont = FontSystem.getFont(20);

	final Color backgroundColor = new Color(240, 240, 240);

	public BotRunner(Bee bee, BeeBot beeBot, World world) {
		this.bee = bee;
		this.beeBot = beeBot;
		this.world = world;

		name = beeBot.initalize(bee);
	}

	public void computeNextAction() {
		beeBot.computeNextAction(world);
	}

	public Action getCurrentAction() {
		return beeBot.currentAction;
	}

	public void drawStatus(Graphics2D g) {

		AffineTransform transform = g.getTransform();

		Point offset = bee.hive.offset;

		int x = Screen.HALF_WIDTH - 120 + 520 * offset.x;
		int y = Screen.HALF_HEIGHT - 120 + 280 * offset.y;

		g.translate(x, y);

		g.setColor(backgroundColor);
		g.fillRect(0, 0, 240, 240);

		g.setColor(Color.BLACK);
		g.setFont(actionFont);

		g.drawString(name, 20, 40);
		g.drawString(beeBot.getCurrentAction().getDescription(), 20, 70);

		g.drawString(String.format("Carrying %.1f", bee.pollen), 20, 100);
		g.drawString(String.format("Stored %.1f", bee.hive.pollen), 20, 130);

		g.setTransform(transform);

	}

	public void executeCurrentAction() {
		if (beeBot.currentAction.executeFor(bee, world)) {
			beeBot.previousAction = beeBot.currentAction;
			beeBot.currentAction = Action.IDLE;
		};

	}

	public boolean prepareCurrentAction() {
		boolean ready = beeBot.currentAction.prepareFor(bee, world);
		if (!ready) finishCurrentAction();
		return ready;
	}

	void finishCurrentAction() {
		beeBot.currentAction.finish();
		beeBot.previousAction = beeBot.currentAction;
		beeBot.currentAction = Action.IDLE;
	}

}
