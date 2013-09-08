package com.github.zinfidel.jarvis_march.geometry;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class ConvexHull {

    /** A list of points that comprise the convex hull. */
    private final LinkedList<Point> hullPoints = new LinkedList<Point>();

    /**
     * A list of vectors that comprise the convex hull.
     * This list will be length(points) - 1 large, and each entry represents
     * a vector between two points in the points list. For example, the first
     * vector in the list would represent the vector pointing from the first
     * point and the second point in the points list.
     */
    private final LinkedList<Vector> hullEdges = new LinkedList<Vector>();;

    /**
     * A list of angles between the vectors that comprise the convex hull.
     * This list "mirrors" the points list in that the first angle in this list
     * is the angle located between the two vectors sharing the first point.
     */
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

	// Add the point.
	hullPoints.add(startingPoint);
	curPoint = startingPoint;

	// Add Y-axis as starting vector.
	hullEdges.add(Vector.YAxis);
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

    /** @return An immutable view of the hull's points. */
    public List<Point> getPoints() {
	return Collections.unmodifiableList(hullPoints);
    }
    
    /** @return An immutable view of the hull's edges. */
    public List<Vector> getEdges() {
	return Collections.unmodifiableList(hullEdges);
    }
    
    /** @return An immutable view of the hull's angles. */
    public List<Double> getAngles() {
	return Collections.unmodifiableList(hullAngles);
    }
    
    /**
     * Adds a new point to the "end" of the convex hull, adds a vector pointing
     * to it, and calculates an angle for the <i>previous</i> point (as the
     * previous point now has two vectors to form an angle).
     * 
     * Null is invalid and will throw an exception.
     * 
     * @param point The point to add to the end of the convex hull.
     */
    public void addPoint(Point point) {
	if (point == null) throw new IllegalArgumentException(
		"Null can not be added to the convex hull points.");

	// Add the point.
	Point oldPoint = curPoint;
	curPoint = point;
	hullPoints.add(point);

	// Generate the vector.
	Vector oldEdge = hullEdges.peekLast();
	Vector newEdge = new Vector(oldPoint, point);
	hullEdges.add(newEdge);

	// Calculate the angle.
	hullAngles.add(oldEdge.angleTo(newEdge));
    }

}
