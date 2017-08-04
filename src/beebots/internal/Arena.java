package beebots.internal;

import javafx.geometry.Point2D;

public class Arena {

	public static final int MAX_COORD = 500;
	public static final int SIZE = 1000;

	public static boolean inRange(Point2D point) {

		double x = point.getX();
		double y = point.getY();
		
		return (x >= -MAX_COORD && x <= MAX_COORD && y >= -MAX_COORD && y <= MAX_COORD);
	}

}
