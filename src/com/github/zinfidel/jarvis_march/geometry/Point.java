package com.github.zinfidel.jarvis_march.geometry;

import static java.lang.Math.sqrt;

/**
 * Represents a 2D point as on a Cartesian plane.
 */
public class Point {

    public static final Point ORIGIN = new Point(0, 0);

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
    public Point minus(Point point) {
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
    public double distanceTo(Point point) {
	double xDiff = (double) (point.x - x);
	double yDiff = (double) (point.y - y);
	double x2 = xDiff * xDiff;
	double y2 = yDiff * yDiff;

	return sqrt(x2 + y2);
    }
    
    /**
     * This is the Cantor Pairing algorithm for 2 natural numbers. This
     * algorithm is likely to generate values much higher than
     * Integer.MAX_VALUE for large xs and ys, and so will wrap into negatives
     * or positives. This weakness is not accounted for! The hashing algorithm
     * should behave for smaller values of x and y.
     * 
     * @see http://en.wikipedia.org/wiki/Pairing_function
     */
    @Override
    public int hashCode() {
	return (int) (0.5d * (x + y) * (x + y + 1) + y);
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
	
	// Hashcodes must be equal.
	if (this.hashCode() != obj.hashCode()) return false;

	// Ensure x and y values are the same.
	Point point = (Point) obj;
	boolean xEquals = (this.x == point.x);
	boolean yEquals = (this.y == point.y);

	return xEquals && yEquals;
    }
}