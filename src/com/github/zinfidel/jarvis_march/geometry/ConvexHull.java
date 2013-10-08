package com.github.zinfidel.jarvis_march.geometry;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Defines a convex hull around a point cloud. Also stores the current best
 * proposed point for the next point to add, as well as a point currently
 * being assessed for being the best.
 */
public class ConvexHull {

    /** A list of points that comprise the convex hull. */
    private final LinkedList<Point> hullPoints = new LinkedList<>();

    /**
     * A list of vectors that comprise the convex hull.
     * Each vector's tail starts at the point sharing the same index and points
     * towards the next point in the hull.
     */
    private final LinkedList<Vector> hullEdges = new LinkedList<>();;

    /**
     * A list of angles between the vectors that comprise the convex hull.
     * This list "mirrors" the points list in that the first angle in this list
     * is the angle located between the two vectors sharing the first point.
     */
    private final LinkedList<Angle> hullAngles = new LinkedList<>();
    
    /** Indicates that the hull is a closed loop and thus solved. */
    private boolean closed = false;
    
    /** The current last point of the convex hull. */
    private Point curPoint;
    
    /** The current last vector of the convex hull's edges. */
    private Vector curVector;


    /**
     * Constructs a new convex hull with a starting point. Conventionally, the
     * starting point should be the left-most or right-most point in the point
     * cloud.
     * 
     * Note that the convex hull is constructed in a unique state - it has its
     * current vector set to the positive Y-Axis, but the vector is not actually
     * part of its edge list. This is so that points being considered can be
     * measured against the Y-Axis for angles.
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

	// Add temporary Y-Axis for the initial add.
	curVector = Vector.Y_AXIS;
    }

    /** @return The current last point on the convex hull. */
    public Point getCurPoint() {
	return curPoint;
    }
    
    /** @return The current last vector pointing to the current last point. */
    public Vector getCurVector() {
	return curVector;
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
    public List<Angle> getAngles() {
	return Collections.unmodifiableList(hullAngles);
    }
    
    /**
     * Adds a new point to the "end" of the convex hull, adds a vector pointing
     * to it, and calculates an angle for the <i>previous</i> point (as the
     * previous point now has two vectors to form an angle).
     * 
     * Null points or points concurrent with the last point are invalid and
     * will throw exceptions.
     * 
     * @param point The point to add to the end of the convex hull.
     * @throws IllegalArgumentException if the point is null or concurrent with
     * the last-added point.
     */
    public void addPoint(Point point) {
	if (point == null) throw new IllegalArgumentException(
		"Null can not be added to the convex hull points.");
	
	if (point.equals(curPoint)) throw new IllegalArgumentException(
		"Concurrent points can not be added to the convex hull.");
	
	if (closed) throw new RuntimeException(
		"Points can not be added to a closed (solved) convex hull.");

	// Update new point.
	Point oldPoint = curPoint;
	curPoint = point;
	
	// Close the hull if this is the initial point, otherwise add it.
	if (point.equals(hullPoints.getFirst())) {
	    closed = true;
	} else {
	    hullPoints.add(point);
	}

	// Generate the vector.
	Vector oldVector = curVector;
	curVector = new Vector(oldPoint, point);
	hullEdges.add(curVector);

	// Calculate the angle.
	double angle = curVector.angleTo(oldVector);
	hullAngles.add(new Angle(angle, curVector.angle, oldPoint));
    }
    
    /** @return True if the hull is closed and solved, false otherwise. */
    public boolean isClosed() {
	return closed;
    }

}
