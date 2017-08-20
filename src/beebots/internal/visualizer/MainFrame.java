package beebots.internal.visualizer;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import java.util.Deque;
import java.util.Stack;
import java.util.concurrent.ConcurrentLinkedDeque;

public final class MainFrame implements KeyListener, ScreenManagerInterface {

	private final boolean[] keyState = new boolean[256];
	private final Deque<Character> keyBuffer = new ConcurrentLinkedDeque<>();

	private final Frame frame;
	private final Canvas canvas;

	private final Stack<Screen> screens = new Stack<>();

	private volatile boolean exitRequested = false;
	private int totalUpdates;
	private int totalDraws;

	private int initializationTimeDebt;
	private boolean drawEnabled = true;
	private int pushNesting;

	public final GraphicsConfiguration graphicsConfiguration;

	public MainFrame() {

		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice gd = ge.getDefaultScreenDevice();
		graphicsConfiguration = gd.getDefaultConfiguration();

		Screen.host = this;

		frame = new Frame();

		canvas = new Canvas();
		canvas.setFocusable(false);
		canvas.setPreferredSize(new Dimension(Screen.WIDTH, Screen.HEIGHT));

		frame.add(canvas);
		frame.pack();

		frame.setResizable(false);
		frame.setIgnoreRepaint(true);
		frame.addKeyListener(this);

		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we) {
				exitRequested = true;
				super.windowClosing(we);
			}
		});

		frame.setVisible(true);

		pushScreen(new MainScreen());
		runGameLoop();

		frame.setVisible(false);
		frame.dispose();
	}

	private void runGameLoop() {

		canvas.createBufferStrategy(2);
		BufferStrategy strategy = canvas.getBufferStrategy();

		// Time used for initialization before the main loop is entered can be ignored.
		initializationTimeDebt = 0;

		boolean done = false;

		final long TICK_VALUE = 50000000;	// Since deltatime is multiplied by 3, this gives exactly 60 fps.
		long accumulator = 0;

		long frameStartTime = System.nanoTime();
		long totalFrameTime = 0;

		boolean lastUpdateDrawn = false;

		while (!done) {
			long newTime = System.nanoTime();
			long deltaTime = newTime - frameStartTime;
			frameStartTime = newTime;

			accumulator += deltaTime * 3;
			totalFrameTime += deltaTime;

			while (accumulator >= 0) {

				screens.peek().update();
				lastUpdateDrawn = false;
				totalUpdates++;

				if (screens.empty() || exitRequested) {
					done = true;
					break;
				}
				accumulator -= TICK_VALUE;
			}

			// Initialization time is not counted as part of the frame.
			frameStartTime += initializationTimeDebt;
			initializationTimeDebt = 0;

			if (done) break;

			if (drawEnabled && !lastUpdateDrawn) {

				Graphics2D g = (Graphics2D) strategy.getDrawGraphics();
				g.setClip(0, 0, Screen.WIDTH, Screen.HEIGHT);
				g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);

				screens.peek().draw(g);

				g.dispose();
				strategy.show();
				lastUpdateDrawn = true;
				totalDraws++;

			} else {
				// This serves to avoid saturating the CPU. Ideally we could sleep for exactly the amount of time needed
				// until next update.
				sleep(1);
			}

		}
		
		strategy.dispose();

		int ms = (int) (totalFrameTime / 1000000);
		System.out.println("runtime = " + ms);
		System.out.println(" frames = " + totalDraws);
		System.out.println("avg fps = " + 1000 * totalDraws / ms);
		System.out.println("updates = " + totalUpdates);
		System.out.println("avg ups = " + 1000 * totalUpdates / ms);

		
	}

	public void sleep(int ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException ex) {
		}
	}

	@Override
	public boolean isKeyDown(int key) {
		return (key >= 0 && key < keyState.length) ? keyState[key] : false;
	}

	@Override
	public boolean keyPending() {
		return !keyBuffer.isEmpty();
	}

	@Override
	public char getKey() {
		if (keyBuffer.isEmpty()) return 0;
		else return keyBuffer.pollFirst();
	}

	@Override
	public char peekKey() {
		if (keyBuffer.isEmpty()) return 0;
		else return keyBuffer.peekFirst();
	}

	@Override
	public void clearKeyBuffer() {
		keyBuffer.clear();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		char key = e.getKeyChar();
		keyBuffer.addLast(key);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int c = e.getKeyCode();
		if (c >= 0 && c < keyState.length) keyState[c] = true;

		if (keyState[KeyEvent.VK_ESCAPE] && keyState[KeyEvent.VK_BACK_SPACE]) {
			exitRequested = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int c = e.getKeyCode();
		if (c >= 0 && c < keyState.length) keyState[c] = false;
	}

	@Override
	public void pushScreen(Screen s) {
		screens.push(s);

		// Since initialization can take a long time, we note the amount of time taken in this call, and then count that
		// against the frame time. This will cause the framerate to dip whenver lengthy initializations happen, but
		// it produces smoother animation afterwards.
		pushNesting++;
		long start = System.nanoTime();
		s.initialize();
		long duration = System.nanoTime() - start;
		if (--pushNesting == 0) initializationTimeDebt += duration;
	}

	@Override
	public void popScreen() {
		popScreen(new ReturnToken());
	}

	@Override
	public void popScreen(ReturnToken rt) {
		screens.pop();
		if (!screens.empty()) screens.peek().acceptReturnToken(rt);
		setDrawing(true);
	}

	@Override
	public void setDrawing(boolean enabled) {
		drawEnabled = enabled;
	}

	@Override
	public GraphicsConfiguration getGraphicsConfiguration() {
		return graphicsConfiguration;
	}

}
