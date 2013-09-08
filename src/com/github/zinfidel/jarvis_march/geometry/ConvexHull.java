package com.github.zinfidel.jarvis_march.geometry;

import java.util.LinkedList;

public class ConvexHull {

    private final LinkedList<Point> hullPoints = new LinkedList<Point>();
    
    private final LinkedList<Vector> hullEdges = new LinkedList<Vector>();;
    
    private final LinkedList<Double> hullAngles = new LinkedList<Double>();
    
    private Point bestPoint = null;
    
    private Vector bestVector = null;
    
    private Point nextPoint = null;
    
    private Vector nextVector = null;
    
    
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
