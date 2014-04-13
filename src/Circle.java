
import org.opencv.core.Point;
import org.opencv.core.Rect;

public class Circle {
	private Point center;
	private int radius;

	public Circle(Point center, int radius) {
		this.center = center;
		this.radius = radius;

	}

	public Point getCenter() {
		return center;
	}

	public int getRadius() {
		return radius;
	}
	
	public boolean containsPoint(Point point){
		return (Math.pow(point.x-center.x, 2)+Math.pow(point.y-center.y, 2)<=Math.pow(radius, 2));
	}
}
