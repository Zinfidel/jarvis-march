package com.github.zinfidel.jarvis_march.algorithm;

import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.github.zinfidel.jarvis_march.geometry.ConvexHull;
import com.github.zinfidel.jarvis_march.geometry.Model;
import com.github.zinfidel.jarvis_march.geometry.Point;
import com.github.zinfidel.jarvis_march.geometry.Vector;

public class TestJarvisMarcher {
    
    // Stock points/vectors.
    public static final Point Point11 = new Point(1,1);
    public static final Vector Vector11 = new Vector(Point.ORIGIN, Point11);
    
    // Instances to set up and test.
    private static Model model;
    
    @Before
    public void setUp() throws Exception {
	try {
	    model = new Model();
	} catch (Exception e) {
	    throw e;
	}
    }

    @Test(timeout=3000)
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
	JarvisMarcher marcher = new JarvisMarcher(model);
	marcher.solve();
	ConvexHull hull = model.getHull();
	
	// Test for closedness.
	assertTrue(hull.isClosed());
	
	// Test Points
	List<Point> points = model.getHull().getPoints();
	assertEquals(3, points.size());
	assertEquals(Point.ORIGIN, points.get(0));
	assertEquals(point24, points.get(1));
	assertEquals(point40, points.get(2));
    }
    
    //@Test
    // TODO: Refactor this to test via iterate().
    public final void testSetBestPoint() {
	
	// Set up model with origin point and marcher.
	model.addPoint(Point.ORIGIN);
	JarvisMarcher marcher = new JarvisMarcher(model);

	// Set regular point.
	//marcher.setBestPoint(Point11);
	assertEquals(Point11, marcher.getBestPoint());
	assertEquals(Vector11, marcher.getBestVector());

	// Test null point (erasure).
	//marcher.setBestPoint(null)
	assertNull(marcher.getBestPoint());
	assertNull(marcher.getBestVector());
    }

    //@Test
    // TODO: Refactor this to test via iterate().
    public final void testSetNextPoint() {
	// Set up model with origin point and marcher.
	model.addPoint(Point.ORIGIN);
	JarvisMarcher marcher = new JarvisMarcher(model);
	
	// Set regular point.
	//marcher.setNextPoint(Point11);
	assertEquals(Point11, marcher.getNextPoint());
	assertEquals(Vector11, marcher.getNextVector());

	// Test null point (erasure).
	//marcher.setNextPoint(null);
	assertNull(marcher.getNextPoint());
	assertNull(marcher.getNextVector());
    }
    
    @Test(timeout=3000)
    public final void testColinearPoints() {
	// Testing points.
	Point point01 = new Point(0,1);
	Point point11 = new Point(1,1);
	Point point21 = new Point(2,1);
	
	// And colinear points.
	model.addPoint(point01);
	model.addPoint(point11);
	model.addPoint(point21);
	
	// Run the algorithm and check for degenerate exception.
	try {
	    JarvisMarcher marcher = new JarvisMarcher(model);
	    marcher.solve();
	    Assert.fail();
	} catch (DegenerateGeometryException e) {
	    // Caught as expected.
	}
    }
    
    @Test(timeout=3000)
    public final void testNotEnoughPoints() {
	// Testing points.
	Point point01 = new Point(0,1);
	Point point11 = new Point(1,1);
	
	// Add points.
	model.addPoint(point01);
	model.addPoint(point11);
	
	// Try to solve - catch exception because of too few points.
	try {
	    JarvisMarcher marcher = new JarvisMarcher(model);
	    marcher.solve();
	    Assert.fail();
	} catch (DegenerateGeometryException e) {
	    // Caught as expected.
	}
    }
}
