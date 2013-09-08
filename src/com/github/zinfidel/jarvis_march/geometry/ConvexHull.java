package com.github.zinfidel.jarvis_march.geometry;

import java.util.LinkedList;

public class ConvexHull {

    /** A list of points that comprise the convex hull. */
    private final LinkedList<Point> hullPoints = new LinkedList<Point>();

    /** A list of vectors that comprise the convex hull. */
    private final LinkedList<Vector> hullEdges = new LinkedList<Vector>();;

    /** A list of angles between the vectors that comprise the convex hull. */
    private final LinkedList<Double> hullAngles = new LinkedList<Double>();

    /** The current last point of the convex hull being constructed. */
    private Point curPoint;

    /** The best proposed next point. Null means there is no best point. */
    private Point bestPoint = null;

    /** The vector pointing to the best point. Null means there is no vector.*/
    private Vector bestVector = null;

    /** The next point being considered as the best point. */
    private Point nextPoint = null;

    /** The vector pointing to the next point being considered as best point. */
    private Vector nextVector = null;


    /**
     * Constructs a new convex hull with a starting point. Conventionally, the
     * starting point should be the left-most or right-most point in the point
     * cloud, but it is not necessary.
     * 
     * @param startingPoint The starting point to use. This must not be NULL or
     * an <code>IllegalArgumentException</code> will be thrown.
     */
    public ConvexHull(Point startingPoint) {
	if (startingPoint == null) throw new IllegalArgumentException(
		"Hull starting point must not be null.");

	hullPoints.add(startingPoint);
	curPoint = startingPoint;
    }


    /** @return The best proposed point. */
    public Point getBestPoint() {
	return bestPoint;
    }

    /**
     * Sets the best proposed point for the next point in the hull.
     * Automatically updates the best vector to point to it. Note that this
     * accessor does not scrutinize the input, so stuff like concurrent points
     * will be accepted.
     * 
     * Passing null to this method will "erase" the best point and vector.
     * 
     * @param point The new best proposed point, or null.
     */
    public void setBestPoint(Point point) {

	bestPoint = point;

	// Calculate the new best vector, or set to null if point is null.
	bestVector = point != null ? new Vector(curPoint, bestPoint) : null;
    }

    /** @return The vector pointing to the best proposed point. */
    public Vector getBestVector() {
	return bestVector;
    }

    /** @return The next point being considered for adding to the convex hull. */
    public Point getNextPoint() {
	return nextPoint;
    }

    /**
     * Sets the point being considered for the next point in the hull.
     * Automatically updates the next vector to point to it. Note that this
     * accessor does not scrutinize the input, so stuff like concurrent points
     * will be accepted.
     * 
     * Passing null to this method will "erase" the next point and vector.
     * 
     * @param point The next proposed point, or null.
     */
    public void setNextPoint(Point point) {
	nextPoint = point;

	// Calculate the new vector, or set to null if point is null.
	nextVector = point != null ? new Vector(curPoint, nextPoint) : null;
    }

    /** @return The vector pointing to the next considered point. */
    public Vector getNextVector() {
	return nextVector;
    }
}
