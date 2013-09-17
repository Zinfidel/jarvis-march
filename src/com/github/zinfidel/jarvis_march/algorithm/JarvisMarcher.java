package com.github.zinfidel.jarvis_march.algorithm;

import java.util.List;

import com.github.zinfidel.jarvis_march.geometry.ConvexHull;
import com.github.zinfidel.jarvis_march.geometry.Model;
import com.github.zinfidel.jarvis_march.geometry.Point;

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
	List<Point> points = hull.getPoints();
	
	do {
	    
	} while (points.get(points.size() - 1).equals(model.getLeftmost()));
    }
}
