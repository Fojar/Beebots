package beebots.visualizer;

import java.awt.GraphicsConfiguration;

public interface ScreenManagerInterface {

	void pushScreen(Screen s);
	void popScreen();
	void popScreen(ReturnToken rt);

	boolean isKeyDown(int key);
	boolean keyPending();
	char getKey();
	char peekKey();
	void clearKeyBuffer();

	void setDrawing(boolean on);

	GraphicsConfiguration getGraphicsConfiguration();

}
