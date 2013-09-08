package com.github.zinfidel.jarvis_march.geometry;

import static org.junit.Assert.*;
import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class TestConvexHull {

    public static final Point Point11 = new Point(1,1);
    public static final Vector Vector11 = new Vector(Point.Origin, Point11);

    private static ConvexHull basicHull;  // Just after constructing.
    //private static ConvexHull normalHull; // With some points

    @Before
    public void setUp() throws Exception {
	try {
	    basicHull = new ConvexHull(Point.Origin);
	} catch (Exception e) {
	    throw e;
	}
    }


    @Test
    public final void testConvexHull() {
	// Test null exception.
	try {
	    ConvexHull hull = new ConvexHull(null);
	    Assert.fail();
	} catch (IllegalArgumentException e) {
	    // Exception thrown as expected.
	}

	// Test regular construction.
	ConvexHull hull = new ConvexHull(Point.Origin);
	assertEquals(Point.Origin, hull.getPoints().get(0));
	assertEquals(Vector.YAxis, hull.getEdges().get(0));
	assertTrue(hull.getAngles().isEmpty());
    }

    @Test
    public final void testSetBestPoint() {
	// Set regular point.
	basicHull.setBestPoint(Point11);
	assertEquals(Point11, basicHull.getBestPoint());
	assertEquals(Vector11, basicHull.getBestVector());

	// Test null point (erasure).
	basicHull.setBestPoint(null);
	assertNull(basicHull.getBestPoint());
	assertNull(basicHull.getBestVector());
    }

    @Test
    public final void testSetNextPoint() {
	// Set regular point.
	basicHull.setNextPoint(Point11);
	assertEquals(Point11, basicHull.getNextPoint());
	assertEquals(Vector11, basicHull.getNextVector());

	// Test null point (erasure).
	basicHull.setNextPoint(null);
	assertNull(basicHull.getNextPoint());
	assertNull(basicHull.getNextVector());
    }

    @Test
    public final void testImmutableListGetters() {
	// Points
	try {
	    basicHull.getPoints().add(Point11);
	    Assert.fail();
	} catch (UnsupportedOperationException e) {
	    // Exception thrown as expected.
	}
	
	// Edges
	try {
	    basicHull.getEdges().add(Vector11);
	    Assert.fail();
	} catch (UnsupportedOperationException e) {
	    // Exception thrown as expected.
	}
	
	// Angles
	try {
	    basicHull.getAngles().add(3.14);
	    Assert.fail();
	} catch (UnsupportedOperationException e) {
	    // Exception thrown as expected.
	}
    }

    @Test
    public final void testAddPoint() {
	fail("Not yet implemented");
    }

}
