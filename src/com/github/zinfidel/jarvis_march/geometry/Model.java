package com.github.zinfidel.jarvis_march.geometry;

import java.util.PriorityQueue;

// TODO: Document class
public class Model {

    private final PriorityQueue<Point> freePoints = new PriorityQueue<>();
    
    private Point bounds = Point.Origin;
    
    // TODO: Null or start with a hull?
    private ConvexHull hull = null;
    
    
    public void addPoint(Point point) {
	// Null is invalid.
	if (point == null) throw new IllegalArgumentException(
		"Null can not be added to the point cloud.");
	
	freePoints.add(point);
	updateBounds(point);
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
    
    public void clearPoints() {
	freePoints.clear();
	bounds = Point.Origin;
    }
}
