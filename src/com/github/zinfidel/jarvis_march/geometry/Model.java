package com.github.zinfidel.jarvis_march.geometry;

import java.util.HashSet;

// TODO: Document class
public class Model {
    
    private final static Point MAX_BOUNDS =
	    new Point(Integer.MAX_VALUE, Integer.MAX_VALUE);

    private final HashSet<Point> freePoints = new HashSet<>();
    
    private Point bounds = Point.ORIGIN;
    
    // TODO: Max or null?
    private Point leftmost = MAX_BOUNDS;
    
    // TODO: Null or start with a hull?
    private ConvexHull hull = null;
    
    
    public void addPoint(Point point) {
	// Null is invalid.
	if (point == null) throw new IllegalArgumentException(
		"Null can not be added to the point cloud.");
	
	freePoints.add(point);
	updateBounds(point);
	updateLeftmost(point);
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
    }
}
