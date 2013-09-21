package com.github.zinfidel.jarvis_march.algorithm;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.github.zinfidel.jarvis_march.geometry.ConvexHull;
import com.github.zinfidel.jarvis_march.geometry.Model;
import com.github.zinfidel.jarvis_march.geometry.Point;

public class TestJarvisMarcher {
    
    // TODO Test degenerate cases and such.
    
    private static Model model;
    
    @Before
    public void setUp() throws Exception {
	try {
	    model = new Model();
	} catch (Exception e) {
	    throw e;
	}
    }

    // TODO Reenable this when not debugging!
    //@Test(timeout=3000)
    @Test
    public final void testSolve() {
	// Testing points.
	Point point24 = new Point(2,4);
	Point point22 = new Point(2,2);
	Point point40 = new Point(4,0);
	
	// Add triangle with point in center.
	model.addPoint(Point.ORIGIN);
	model.addPoint(point24);
	model.addPoint(point22); // Centerpoint
	model.addPoint(point40);
	
	// Run the algorithm.
	JarvisMarcher.solve(model);
	ConvexHull hull = model.getHull();
	
	// Test for closedness.
	assertTrue(hull.isClosed());
	
	// Test Points
	List<Point> points = model.getHull().getPoints();
	assertEquals(points.size(), 3);
	assertEquals(points.get(0), Point.ORIGIN);
	assertEquals(points.get(1), point24);
	assertEquals(points.get(2), point40);
	
    }

}
