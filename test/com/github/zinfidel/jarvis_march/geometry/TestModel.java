package com.github.zinfidel.jarvis_march.geometry;

import static org.junit.Assert.*;

import java.util.Set;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class TestModel {
    
    private static Model model;
    
    @Before
    public void setUp() throws Exception {
	try {
	    model = new Model();
	} catch (Exception e) {
	    throw e;
	}
    }

    @Test
    public final void testAddPoint() {
	Point p1 = new Point(1, 1);
	Point p2 = new Point(2, 1);

	// Add two points normally.
	model.addPoint(p1);
	model.addPoint(p2);
	
	// Add concurrent point.
	model.addPoint(new Point(2, 1));
	
	// List should only consist of first two points.
	Set<Point> points = model.getPoints();
	assertEquals(2, points.size());
	assertTrue(points.contains(p1));
	assertTrue(points.contains(p2));
    }

    @Test
    public final void testClearPoints() {
	model.addPoint(new Point(1,1));
	model.addPoint(new Point(2,2));
	model.addPoint(new Point(3,3));
	
	model.clearPoints();
	
	assertEquals(0, model.getPoints().size());
    }

    @Test
    public final void testGetBounds() {
	model.addPoint(new Point(3, 2094));
	model.addPoint(new Point(1, 30922309));
	model.addPoint(new Point(238245, 219405));
	
	Point p1 = new Point(238245, 30922309);
	assertEquals(p1, model.getBounds());
    }

    @Test
    public final void testGetLeftmost() {
	Point p1 = new Point(0, 3);

	model.addPoint(new Point(3, 2094));
	model.addPoint(new Point(1, 30922309));
	model.addPoint(p1);
	model.addPoint(new Point(238245, 219405));

	assertEquals(p1, model.getLeftmost());
    }

    @Test
    public final void testGetPoints() {
	// Test immutability of returned list by trying to add point.
	try {
	    model.getPoints().add(Point.ORIGIN);
	    Assert.fail();
	} catch (UnsupportedOperationException e) {
	    // Exception thrown as expected.
	}
    }

}
