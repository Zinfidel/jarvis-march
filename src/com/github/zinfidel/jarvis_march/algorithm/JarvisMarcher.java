package com.github.zinfidel.jarvis_march.algorithm;

import com.github.zinfidel.jarvis_march.geometry.ConvexHull;
import com.github.zinfidel.jarvis_march.geometry.Model;
import com.github.zinfidel.jarvis_march.geometry.Point;
import com.github.zinfidel.jarvis_march.geometry.Vector;

// TODO Document me
public class JarvisMarcher {

    // TODO Provide implementations for solving all at once and for iterating
    //      through a solution.
    
    //private Model model;
    
    //public JarvisMarcher(Model model) {
    //	this.model = model;
    //}
    
    // TODO Static or not?
    public static void solve(Model model) {
	// TODO Initial implementation of algorithm.
	// TODO Should be calling an iterate method when available.
	// TODO Assuming completely unsolved - should be more robust?
	
	// TODO Throw custom exception? Normal runtime exception?
	// Degenerate case.
	if (model.getPoints().size() < 3) return;
	
	// Set up convex hull.
	model.newHull();
	ConvexHull hull = model.getHull();
	
	while (!hull.isClosed()) {
	    // TODO Set up hash list or something to keep track of points
	    // already in hull? Linear scan through list could get very
	    // expensive very fast, no scan could result in errors for
	    // points in a perfect line.
	    for (Point point : model.getPoints()) {
		if (!point.equals(hull.getCurPoint())) {
		    hull.setNextPoint(point);
		    Vector next = hull.getNextVector();
		    Vector best = hull.getBestVector();

		    // TODO LOGIC ERROR need to add current point/vector to the hull
		    // so that angle can be referenced against it in calcs.
		    //boolean isBetter = (best == null) ? true : next.angle < best.angle;
		    boolean isBetter = (best == null) ? true :
			hull.getNextAngle().angle < hull.getBestAngle().angle;
		    if (isBetter) hull.setBestPoint(point);
		   //System.out.println(hull.getNextAngle().angle);
		    //System.out.println(hull.getBestAngle().angle);
		    //System.out.println("Best Point: " + hull.getBestPoint());
		}
	    }
	    //System.out.println(hull.getBestPoint());
	    hull.addPoint(hull.getBestPoint());
	    hull.setNextPoint(null);
	    hull.setBestPoint(null);
	}
    }
}
