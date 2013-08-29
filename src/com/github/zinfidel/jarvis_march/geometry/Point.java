package com.github.zinfidel.jarvis_march.geometry;

import static java.lang.Math.sqrt;

/**
 * Represents a 2D point as on a Cartesian plane.
 * 
 * @author Zach Friedland
 */
public class Point implements Comparable<Point> {

    public static final Point Origin = new Point(0, 0);

    public final int x;
    public final int y;

    public Point(int x, int y) {
	this.x = x;
	this.y = y;
    }

    /**
     * Calculates the difference between this point and <code>point</code>.
     * 
     * @param point The point to subtract from this point.
     * @return A new point representing the difference between the points.
     */
    public Point Minus(Point point) {
	int x = this.x - point.x;
	int y = this.y - point.y;

	return new Point(x, y);
    }

    /**
     * Calculate the distance from this point to <code>point</code>.
     * 
     * @param point The point to calculate distance to.
     * @return The distance between this point and <code>point</code>.
     */
    public double DistanceTo(Point point) {
	double xDiff = (double) (point.x - x);
	double yDiff = (double) (point.y - y);
	double x2 = xDiff * xDiff;
	double y2 = yDiff * yDiff;

	return sqrt(x2 + y2);
    }

    /** {@InheritDoc} */
    @Override
    public boolean equals(Object obj) {
	// Reflexivity
	if (obj == this) return true;

	// Obj must not be null.
	if (obj == null) return false;

	// Ensure obj is a point.
	if (!(obj instanceof Point)) return false;

	// Ensure x and y values are the same.
	Point point = (Point) obj;
	boolean xEquals = (this.x == point.x);
	boolean yEquals = (this.y == point.y);

	return xEquals && yEquals;
    }

    @Override
    public int compareTo(Point point) {
	// Compare x values.
	if (this.x < point.x) {
	    return -1;
	} else if (this.x > point.x) {
	    return 1;
	} else {
	    // Compare y values if x values are equal.
	    if (this.y < point.y) {
		return -1;
	    } else if (this.y > point.y) {
		return 1;
	    } else {
		// Points are equal if x and y are equal.
		return 0;
	    }
	}
    }
}