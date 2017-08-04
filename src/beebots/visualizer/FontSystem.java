
package beebots.visualizer;

import java.awt.Font;

public class FontSystem {
	
	private static final String FONT_NAME = "Century Gothic";
	
	public static Font getFont(int size) {
		return new Font(FONT_NAME, Font.BOLD, size);
	}

}
