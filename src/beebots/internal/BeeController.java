package beebots.internal;

import beebots.visualizer.FontSystem;
import beebots.visualizer.Screen;
import java.awt.*;
import java.awt.geom.AffineTransform;

public class BeeController {

	public final Bee bee;
	public final BeeBot beeBot;

	final Font actionFont = FontSystem.getFont(20);

	final Color backgroundColor = new Color(240, 240, 240);

	public BeeController(Bee bee, BeeBot beeBot) {
		this.bee = bee;
		this.beeBot = beeBot;
	}

	public void doTurn(WorldState worldState) {

		beeBot.doAction(worldState);
		Action action = beeBot.currentAction;

		if (action != Action.IDLE) {
			if (action.executeFor(bee)) {
				beeBot.previousAction = action;
				beeBot.currentAction = Action.IDLE;
			}
		}

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

		g.drawString(beeBot.getCurrentAction().getDescription(), 20, 40);

		g.setTransform(transform);

	}

}
