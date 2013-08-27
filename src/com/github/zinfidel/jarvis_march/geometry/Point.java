package com.github.zinfidel.jarvis_march.geometry;

/**
 * Represents a 2D point as on a Cartesian plane.
 * 
 * @author Zach Friedland
 */
public class Point {
    
    public final int x;
    public final int y;
    
    public Point(int x, int y) {
	this.x = x;
	this.y = y;
    }
    
    /**
     * Calculates the difference between this point and <code>point</code>.
     * 
     * @param point The point to subtract from this point.
     * @return A new point representing the difference between the points.
     */
    public Point Minus(Point point) {
	int x = this.x - point.x;
	int y = this.y - point.y;
	
	return new Point(x, y);
    }

}
