

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

import org.opencv.core.Point;
import org.opencv.core.Size;

public class Line extends Line2D.Double {
	private static final double RANGE = Math.PI / 8;
	private double theta;
 
	// private Point pt1;
	// private Point pt2;
	// private double slope;
	// private LineType lineType;

//	public Line(LineType lineType) {
//		super();
//		// slope = 0;
//		// this.lineType = lineType;
//	}

//	public Line(int x1, int y1, int x2, int y2) {
//		super(x1, y1, x2, y2);
//		// slope = (double) (y1 - y2) / (x1 - x2);
//		// lineType = LineType.LINE_UNKNOWN;
//	}

	public Line(double rho, double theta) {
		this.theta=theta;
		double a = Math.cos(theta);
		double b = Math.sin(theta);
		double x0 = a * rho;
		double y0 = b * rho;
		x1 = Math.round(x0 + 1000 * (-b));
		y1 = Math.round(y0 + 1000 * (a));
		x2 = Math.round(x0 - 1000 * (-b));
		y2 = Math.round(y0 - 1000 * (a));
		// slope = (pt1.y - pt2.y) / (pt1.x - pt2.x);
		// if (isInRange(theta, 0, RANGE)
		// || isInRange(theta, Math.PI - RANGE, Math.PI)) {
		// orderEndpointsY();
		// lineType = LineType.LINE_VERTICAL;
		// } else if (isInRange(theta, Math.PI / 2 - RANGE, Math.PI / 2 +
		// RANGE)) {
		// orderEndpointsX();
		// lineType = LineType.LINE_HORIZONTAL;
		// } else if (isInRange(theta, Math.PI / 4 - RANGE, Math.PI / 4 + RANGE)
		// || isInRange(theta, 3 * Math.PI / 4 - RANGE, 3 * Math.PI / 4
		// + RANGE)) {
		// lineType = LineType.LINE_DIAGONAL;
		// } else {
		// lineType = LineType.LINE_UNKNOWN;
		// }
	}

	// private void orderEndpointsX() {
	// if (pt2.x < pt1.x) {
	// Point temp = pt1;
	// pt1 = pt2;
	// pt2 = temp;
	// }
	// }

	// private void orderEndpointsY() {
	// if (pt2.y < pt1.y) {
	// Point temp = pt1;
	// pt1 = pt2;
	// pt2 = temp;
	// }
	// }

	// public void addLine(Line line) {
	// pt1.x += line.pt1.x;
	// pt1.y += line.pt1.y;
	// pt2.x += line.pt2.x;
	// pt2.y += line.pt2.y;
	// }

	// public void divideLineBy(int n) {
	// pt1.x /= n;
	// pt1.y /= n;
	// pt2.x /= n;
	// pt2.y /= n;
	// }

	public Point getPt1() {
		return new Point(x1, y1);
	}

	public Point getPt2() {
		return new Point(x2, y2);
	}

	// public LineType getLineType() {
	// return lineType;
	// }

	// public double getSlope() {
	// return slope;
	// }

	// private boolean isInRange(double val, double lowerBound, double
	// upperBound) {
	// return (val >= lowerBound && val < upperBound);
	// }

	// public String toString() {
	// return pt1.toString() + " " + pt2.toString();
	// }

	public Boolean similarTheta(Line line){
		return (Math.abs(theta-line.theta)<Math.PI/8);
	}
	
	public Point getIntersectionPoint(Line line) {
		if (!intersectsLine(line))
			return null;
		double px = x1, py = y1, rx = x2 - px, ry = y2 - py;
		double qx = line.getX1(), qy = line.getY1(), sx = line.getX2() - qx, sy = line
				.getY2() - qy;

		double det = sx * ry - sy * rx;
		if (det == 0) {
			return null;
		} else {
			double z = (sx * (qy - py) + sy * (px - qx)) / det;
			if (z == 0 || z == 1)
				return null;
			return new Point((float) (px + z * rx), (float) (py + z * ry));
		}
	}

	// public int compareTo(Object object) {
	// Line line = (Line) object;
	// if (line.lineType == lineType) {
	// switch (lineType) {
	// case LINE_VERTICAL:
	// if ((pt1.x + pt2.x) < (line.pt1.x + line.pt2.x)) {
	// return -1;
	// } else {
	// return 1;
	// }
	// case LINE_HORIZONTAL:
	// if ((pt1.y + pt2.y) < (line.pt1.y + line.pt2.y)) {
	// return -1;
	// } else {
	// return 1;
	// }
	// default:
	// return 0;
	// }
	// } else {
	// return line.lineType.compareTo(lineType);
	// }
	// }
}
