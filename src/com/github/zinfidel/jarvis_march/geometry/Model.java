package com.github.zinfidel.jarvis_march.geometry;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Models the entirety of the convex hull problem. Contains a point cloud from
 * which to generate a hull, and stores a hull to build.
 */
public class Model {
    
    /** A point representing the maximum upper-right bounds possible. */
    private final static Point MAX_BOUNDS =
	    new Point(Integer.MAX_VALUE, Integer.MAX_VALUE);

    /** The point cloud. Using a set protects against concurrent points. */
    private final HashSet<Point> freePoints = new HashSet<>();
    
    /** The current most upper-right point in the point cloud. */
    private Point bounds = Point.ORIGIN;
    
    /** The current left-most point in the point cloud. */
    private Point leftmost = MAX_BOUNDS;
    
    /** The current convex hull. */
    private ConvexHull hull = null;
    
    
    /**
     * Adds a point to the model's point cloud, and updates the bounds and
     * leftmost point.
     * 
     * The point can not be null, and must reside in quadrant I (positive x
     * and y) of the Cartesian plane.
     * 
     * @param point The point to add.
     * @throws IllegalArgumentException if the point is null or does not reside
     * in quadrant I of the plane.
     */
    public void addPoint(Point point) {
	// Null is invalid.
	if (point == null) throw new IllegalArgumentException(
		"Null can not be added to the point cloud.");
	
	// Points must be in quadrant 1.
	if (point.x < 0 || point.y < 0) throw new IllegalArgumentException(
		"Only points in quadrant I (positive x and y coordinates) " +
		"can be added to the point cloud.");
	
	// If point is added (wasn't in set) update other fields.
	if (freePoints.add(point)) {
	    updateBounds(point);
	    updateLeftmost(point);
	}
    }
    
    /**
     * Updates the upper-right boundary of the model. Note that the bounds are
     * not necessarily modified, only if the point supplied has a greater
     * x or y value.
     * 
     * @param point The point to check against the current boundary.
     */
    private void updateBounds(Point point) {
	boolean xGreater = point.x > bounds.x ? true : false;
	boolean yGreater = point.y > bounds.y ? true : false;
	
	if (xGreater || yGreater) {
	    int x = xGreater ? point.x : bounds.x;
	    int y = yGreater ? point.y : bounds.y;
	    bounds = new Point(x, y);
	}
    }
    
    /**
     * Updates the leftmost point of the model. This will only actually change
     * the leftmost point if the supplied point has an x-value less than the
     * current leftmost point's x-value.
     * @param point
     */
    private void updateLeftmost(Point point) {
	if (point.x < leftmost.x) leftmost = point;
    }
    
    /**
     * Clears (deletes) the point cloud. Bounds and related fields are also
     * reset, and the current convex hull is deleted as well.
     */
    public void clearPoints() {
	freePoints.clear();
	bounds = Point.ORIGIN;
	leftmost = MAX_BOUNDS;
	hull = null;
    }
    
    /** @return The model's current bounds. */
    public Point getBounds() {
	return bounds;
    }
    
    /** @return The model's current left-most point. */
    public Point getLeftmost() {
	return leftmost;
    }
    
    /** @return An immutable view of the point cloud. */
    public Set<Point> getPoints() {
	return Collections.unmodifiableSet(freePoints);
    }
    
    /** @return The current convex hull. This can be null! */
    public ConvexHull getHull() {
	return hull;
    }

    /**
     * Disposes of the current hull, generates a new one, and installs
     * the new hull to the model.
     * 
     * @return The newly generated convex hull.
     */
    public ConvexHull newHull() {
	hull = new ConvexHull(leftmost);
	return hull;
    }
}
