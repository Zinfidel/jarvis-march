package com.github.zinfidel.jarvis_march.algorithm;

import java.util.Set;

import com.github.zinfidel.jarvis_march.geometry.Angle;
import com.github.zinfidel.jarvis_march.geometry.ConvexHull;
import com.github.zinfidel.jarvis_march.geometry.Model;
import com.github.zinfidel.jarvis_march.geometry.Point;
import com.github.zinfidel.jarvis_march.geometry.Vector;

// TODO Document me
public class JarvisMarcher {
      
    /** The convex hull that the marcher is current constructing. */
    private ConvexHull hull = null;
    
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
    
    /** The angle between the next vector and current last vector. */
    private Angle nextAngle = null;

   
    /**
     * Solves the convex hull problem for the model associated with this
     * JarvisMarch object. This can solve for a brand new convex hull or
     * for a partially completed hull.
     */
    public void solve(Model model) {

	// Get the point cloud, ensure it is not degenerate for a CH.
	Set<Point> points = model.getPoints();
	if (points.size() < 3) throw new DegenerateGeometryException(
		"Degenerate geometry detected: Less than 3 vertices.");
	
	// Start solving - go until the hull is closed.
	ConvexHull hull = model.newHull();
	while (!hull.isClosed()) {

	    for (Point point : points) {
		
		// Don't consider the current point, can cause problems.
		if (point != hull.getCurPoint()) {
		    
		    setNextPoint(point);
		    
		    // If the point's angle is valid (>= 180deg), and its better
		    // than the current best point's angle, set it as best.
		    if (nextAngle.angle >= Math.PI ) {
			
			if (bestPoint == null || nextAngle.angle < bestAngle.angle) {
			    setBestPoint(point);
			}
		    }
		}
	    }
	    
	    // If we don't have a best point, something went wrong.
	    if (bestPoint == null) throw new DegenerateGeometryException(
	        "No valid point was found to add - probably degenerate geometry.");
	    
	    // Add the best point to the hull, then clear the algorithm state
	    // for the next iteration.
	    hull.addPoint(bestPoint);
	    setNextPoint(null);
	    setBestPoint(null);
	}
    }
    
    // TODO: Document me.
    public boolean iterate() {
	
	
	
	return false;
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
	Point curPoint = hull.getCurPoint();
	Vector curVector = hull.getCurVector();

	// Calculate the new best vector, or set to null if point is null.
	bestVector = point == null ? null :
	    new Vector(curPoint, bestPoint);
	
	// Calculate the new angle, or set to null if point is null.
	bestAngle = point == null ? null :
	    new Angle(bestVector.angleTo(curVector),
		      bestVector.angle,
		      curPoint);
    }
    
    /** @return The current best point. */
    public Point getBestPoint() {
	return bestPoint;
    }
       
    /** @return The current best vector. */
    public Vector getBestVector() {
	return bestVector;
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
	Point curPoint = hull.getCurPoint();
	Vector curVector = hull.getCurVector();

	// Calculate the new vector, or set to null if point is null.
	nextVector = point == null ? null :
	    new Vector(curPoint, nextPoint);
	
	// Calculate the new angle, or set to null if point is null.
	nextAngle = point == null ? null :
	    new Angle(nextVector.angleTo(curVector),
		      nextVector.angle,
		      curPoint);
    }
    
    /** @return The current point being considered for best point. */
    public Point getNextPoint() {
	return nextPoint;
    }
    
    /** @return The current vector being considered for best vector. */
    public Vector getNextVector() {
	return nextVector;
    }
}
