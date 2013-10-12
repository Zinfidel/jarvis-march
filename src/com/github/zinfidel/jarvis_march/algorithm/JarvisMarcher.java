package com.github.zinfidel.jarvis_march.algorithm;

import java.util.Iterator;

import com.github.zinfidel.jarvis_march.geometry.*;

/**
 * This class is the brains behind the operation, and provides a couple methods
 * of solving a convex hull problem. Standard use involves creating a new
 * marcher object every time a new model is being solved (different point
 * cloud, basically). The iterate method solves one atomic unit of the
 * algorithm, while solve method will solve it all at once, and update at the
 * end.
 * 
 * This class has the objects nextPoint/Vector and bestPoint/Vector, which are
 * used internally, but also are used in rendering. This object, when used
 * in conjunction with the JarvisMarch main panel, should always install itself
 * to the geometry panel so it can be used for drawing objects.
 */
public class JarvisMarcher {
    
    /** The model that the marcher is currently operating on. */
    private Model model = null;
      
    /** The convex hull that the marcher is currently constructing. */
    private ConvexHull hull = null;
    
    /**
     * Iterates over the set of points in the model in between invocations
     * of the iteration method. This retains the "position" in the algorithm
     * between point selections.
     */
    private Iterator<Point> pointIterator = null;
    
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
    
    
    /** Constructs a marcher for the given model. */
    public JarvisMarcher(Model model) {
	this.model = model;
    }

   
    /**
     * Solves the convex hull problem for the model associated with this
     * JarvisMarch object. This can solve for a brand new convex hull or
     * for a partially completed hull.
     * 
     * @throws DegenerateGeometryException if no valid best point can be found.
     * @see com.github.zinfidel.jarvis_march.algorithm.iterate
     */
    public void solve() {

	// Iterate until we get false back, CH is solved then.
	while (iterate());
    }


    /**
     * Solves one atomic "unit" of the Jarvis' March algorithm on the current
     * model. This can simply update the next-best point/vector/angle, update
     * the current best point/vector/angle, or complete the convex hull and
     * close it.
     * 
     * @return false if there is more iteration to be done, true if finished.
     * @throws DegenerateGeometryException if no valid best point can be found.
     */
    public boolean iterate() {
	
	/*
	 * Deal with setting up the hull. If there isn't one, set up a new one
	 * in the model. If it exists, check if it's closed, and if so, return
	 * false to indicate that no more iteration is necessary. Finally, if
	 * none of the above, just use whatever hull is present.
	 */
	if (hull == null) {
	    hull = model.newHull();
	} else if (hull.isClosed()) {
	    return false;
	}

	// Set up a point iterator if need be.
	if (pointIterator == null) pointIterator = model.getPoints().iterator();
	
	/*
	 * Deal with getting the next point. If there are points left, set up
	 * as the next point and determine if it's the best. If there are no more
	 * points left, add the point to the hull and perform clean-up/reset.
	 */
	if (pointIterator.hasNext()) {
	    Point point = pointIterator.next();

	    // Don't consider the current hull point - can cause problems.
	    // See Vector.angleTo() for more details on this.
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
	    
	} else {

	    // If we don't have a best point, something went wrong.
	    if (bestPoint == null) throw new DegenerateGeometryException(
		    "No valid point was found to add - probably degenerate geometry.");

	    // Add the best point to the hull, then clear the algorithm state
	    // for the next iteration.
	    hull.addPoint(bestPoint);
	    setNextPoint(null);
	    setBestPoint(null);
	    pointIterator = null;
	}

	// Return false if iteration is done, true if there is more left to do.
	return hull.isClosed() ? false : true;
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
    private void setBestPoint(Point point) {
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
    private void setNextPoint(Point point) {
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
