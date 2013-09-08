package com.github.zinfidel.jarvis_march.geometry;

import java.util.LinkedList;

public class ConvexHull {

    private final LinkedList<Point> hullPoints = new LinkedList<Point>();
    
    private final LinkedList<Vector> hullEdges = new LinkedList<Vector>();;
    
    private final LinkedList<Double> hullAngles = new LinkedList<Double>();
    
    private Point curPoint;
    
    private Point bestPoint = null;
    
    private Vector bestVector = null;
    
    private Point nextPoint = null;
    
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
	this.curPoint = startingPoint;
    }
    
    
    public Point getBestPoint() {
	return bestPoint;
    }
    
    public void setBestPoint(Point point) {
	bestPoint = point;
	//curBestVector = new Vector(
    }
    
    public Vector getBestVector() {
	return bestVector;
    }
    
    public Point getNextPoint() {
	return nextPoint;
    }
    
    public Vector getNextVector() {
	return nextVector;
    }
}
