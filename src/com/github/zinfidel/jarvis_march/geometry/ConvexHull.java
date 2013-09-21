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

    /** The best proposed next point. Null means there is no best point. */
    private Point bestPoint = null;

    /** The vector pointing to the best point. Null means there is no vector.*/
    private Vector bestVector = null;
    
    /** The angle between the best vector and current last vector. */
    private Angle bestAngle = null;

    /** The next point being considered as the best point. */
    private Point nextPoint = null;

    /** The vector pointing to the next point being considered as best point. */
    private Vector nextVector = null;
    
    /** the angle between the next vector and current last vector. */
    private Angle nextAngle = null;
    
    /** Indicates that the hull is a closed loop and thus solved. */
    private boolean closed = false;
    
    /** The current last point of the convex hull. */
    private Point curPoint;
    
    /** The current last vector of the convex hull's edges. */
    private Vector curVector;


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
	// TODO Reversed direction y-axis to calc angle from common origin?
	//hullEdges.add(Vector.Y_AXIS);
	// Add temporary -y-axis vector as a starter, but not actually to list.
	curVector = new Vector(Point.ORIGIN, new Point(0, -1));
	//hullEdges.add(new Vector(Point.ORIGIN, new Point(0, -1)));
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
	bestVector = point == null ? null :
	    new Vector(curPoint, bestPoint);
	
	// Calculate the new angle, or set to null if point is null.
	bestAngle = point == null ? null :
		new Angle(bestVector.angleTo(curVector), bestVector.angle);
    }

    /** @return The vector pointing to the best proposed point. */
    public Vector getBestVector() {
	return bestVector;
    }
    
    /** @return The angle between the best vector and the last vector. */
    public Angle getBestAngle() {
	return bestAngle;
    }

    /** @return The next point being considered for adding to the convex hull. */
    public Point getNextPoint() {
	return nextPoint;
    }
    
    /** @return The vector pointing to the next considered point. */
    public Vector getNextVector() {
	return nextVector;
    }
    
    /** @return The angle from the next vector to the last current vector. */
    public Angle getNextAngle() {
	return nextAngle;
    }
    
    /** @return The current last point on the convex hull. */
    public Point getCurPoint() {
	return curPoint;
    }
    
    /** @return The current last vector pointing to the current last point. */
    public Vector getCurVector() {
	return curVector;
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
	nextVector = point == null ? null :
	    new Vector(curPoint, nextPoint);
	
	// Calculate the new angle, or set to null if point is null.
	nextAngle = point == null ? null :
	    new Angle(nextVector.angleTo(curVector), nextVector.angle);
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
	
	// TODO Custom exception or boolean return type?
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
	Vector oldEdge = getCurVector();
	Vector newEdge = new Vector(oldPoint, point);
	hullEdges.add(newEdge);

	// Calculate the angle.
	double angle = newEdge.angleTo(oldEdge);
	hullAngles.add(new Angle(angle, oldEdge.angle));
    }
    
    /** @return True if the hull is closed and solved, false otherwise. */
    public boolean isClosed() {
	return closed;
    }

}
