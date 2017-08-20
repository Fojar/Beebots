package beebots.internal;

import beebots.internal.visualizer.FontSystem;
import beebots.internal.visualizer.Screen;
import beebots.external.*;
import beebots.internal.actions.*;
import beebots.internal.arena.*;
import java.awt.*;
import java.awt.geom.*;

public class BotRunner {

	public final Bee bee;
	public final BeeBot beeBot;

	final Arena arena;
	final ArenaState arenaState;

	public final String name;

	final Font actionFont = FontSystem.getFont(20);
	final Color backgroundColor = new Color(240, 240, 240);

	public BotRunner(Bee bee, BeeBot beeBot, Arena arena, ArenaState arenaState) {
		this.bee = bee;
		this.beeBot = beeBot;
		this.arena = arena;
		this.arenaState = arenaState;

		name = beeBot.initalize(arenaState.bees.get(bee.ID));
	}

	public void computeNextAction() {
		if (beeBot.currentAction.isCompleted()) {
			beeBot.previousAction = beeBot.currentAction;
			beeBot.currentAction = Action.IDLE;
		}
		beeBot.computeNextAction(arenaState);
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
		g.drawString(beeBot.currentAction.getDescription(), 20, 70);

		g.drawString(String.format("Carrying %.1f", bee.getPollen()), 20, 100);
		g.drawString(String.format("Stored %.1f", bee.hive.getPollen()), 20, 130);

		g.setTransform(transform);

	}

	public void executeCurrentAction() {
		beeBot.currentAction.executeFor(bee, arena);
	}

	public boolean prepareCurrentAction() {
		boolean ready = beeBot.currentAction.prepareFor(bee, arena);
		if (!ready) finishCurrentAction();
		return ready;
	}

	public void finishCurrentAction() {
		beeBot.currentAction.finish();
	}

}
