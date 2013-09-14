package com.github.zinfidel.jarvis_march.geometry;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

// TODO: Document class
public class Model {
    
    private final static Point MAX_BOUNDS =
	    new Point(Integer.MAX_VALUE, Integer.MAX_VALUE);

    private final HashSet<Point> freePoints = new HashSet<>();
    
    private Point bounds = Point.ORIGIN;
    
    private Point leftmost = MAX_BOUNDS;
    
    private ConvexHull hull = null;
    
    
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
    
    private void updateBounds(Point point) {
	boolean xGreater = point.x > bounds.x ? true : false;
	boolean yGreater = point.y > bounds.y ? true : false;
	
	if (xGreater || yGreater) {
	    int x = xGreater ? point.x : bounds.x;
	    int y = yGreater ? point.y : bounds.y;
	    bounds = new Point(x, y);
	}
    }
    
    private void updateLeftmost(Point point) {
	if (point.x < leftmost.x) leftmost = point;
    }
    
    public void clearPoints() {
	freePoints.clear();
	bounds = Point.ORIGIN;
	leftmost = MAX_BOUNDS;
	hull = null;
    }
    
    public Point getBounds() {
	return bounds;
    }
    
    public Point getLeftmost() {
	return leftmost;
    }
    
    public Set<Point> getPoints() {
	return Collections.unmodifiableSet(freePoints);
    }
    
    public ConvexHull getHull() {
	return hull;
    }

    public ConvexHull newHull() {
	hull = new ConvexHull(leftmost);
	return hull;
    }
}
