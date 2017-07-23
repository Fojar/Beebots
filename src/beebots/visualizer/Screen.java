package beebots.visualizer;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;

public abstract class Screen {

	static protected ScreenManagerInterface host = null;

	static protected final int WIDTH = 1280;
	static protected final int HEIGHT = 800;

	static protected final int HALF_WIDTH = WIDTH / 2;
	static protected final int HALF_HEIGHT = HEIGHT / 2;

	/**
	 * Initialization should happen here, rather than in constructor. In particular, other screens should be pushed from
	 * here. Doing so in the constructor would cause screens to be placed <em>under</em> the current screen in the stack
	 * instead of above!
	 */
	public void initialize() {
	}

	public void update() {
	}

	public void draw(Graphics2D g) {
	}

	public void acceptReturnToken(ReturnToken rt) {
	}

	public BufferedImage loadPNG(String pathname) {

		try {
			if (!pathname.startsWith("/")) pathname = "images/" + pathname;

			InputStream inputStream = getClass().getResourceAsStream(pathname);
			if (inputStream == null) {
				System.err.println("PNG \"" + pathname + "\" not found.");
				return null;
			}

			BufferedImage loaded = ImageIO.read(new BufferedInputStream(inputStream));
			GraphicsConfiguration gc = host.getGraphicsConfiguration();
			BufferedImage temp = gc.createCompatibleImage(loaded.getWidth(), loaded.getHeight(), Transparency.TRANSLUCENT);
			temp.createGraphics().drawImage(loaded, null, 0, 0);
			return temp;

		} catch (IOException ex) {
			System.err.println("Error while reading PNG \"" + pathname + "\".");
			return null;
		}
	}

	public BufferedImage makeNewBufferedImage(int width, int height, int transparency) {
		GraphicsConfiguration gc = host.getGraphicsConfiguration();
		BufferedImage temp = gc.createCompatibleImage(width, height, transparency);
		return temp;
	}

	public BufferedImage makeCopy(BufferedImage img) {
		BufferedImage result = makeNewBufferedImage(img.getWidth(), img.getHeight(), img.getTransparency());
		result.createGraphics().drawImage(img, null, 0, 0);
		return result;
	}

}
