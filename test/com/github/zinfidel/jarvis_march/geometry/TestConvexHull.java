package com.github.zinfidel.jarvis_march.geometry;

import static org.junit.Assert.*;
import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import static java.lang.Math.*;

public class TestConvexHull {

    private static final double DELTA = 0.01;
    public static final Point Point11 = new Point(1,1);
    public static final Vector Vector11 = new Vector(Point.ORIGIN, Point11);

    private static ConvexHull basicHull;  // Just after constructing.

    @Before
    public void setUp() throws Exception {
	try {
	    basicHull = new ConvexHull(Point.ORIGIN);
	} catch (Exception e) {
	    throw e;
	}
    }

    @Test @SuppressWarnings("unused")
    public final void testConvexHull() {
	// Test null exception.
	try {
	    ConvexHull hull = new ConvexHull(null);
	    Assert.fail();
	} catch (IllegalArgumentException e) {
	    // Exception thrown as expected.
	}

	// Test regular construction.
	ConvexHull hull = new ConvexHull(Point.ORIGIN);
	assertEquals(Point.ORIGIN, hull.getPoints().get(0));
	assertEquals(Vector.Y_AXIS, hull.getCurVector());
	assertTrue(hull.getAngles().isEmpty());
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
	    basicHull.getAngles().add(new Angle(1d,1d,Point.ORIGIN));
	    Assert.fail();
	} catch (UnsupportedOperationException e) {
	    // Exception thrown as expected.
	}
    }

    @Test
    public final void testAddPoint() {
	// Test null exception.
	try {
	    basicHull.addPoint(null);
	    Assert.fail();
	} catch (IllegalArgumentException e) {
	    // Exception thrown as expected.
	}
	
	// Test add to just-initialized hull.
	basicHull.addPoint(Point11);
	assertEquals(Point11, basicHull.getCurPoint());
	assertEquals(Point11, basicHull.getPoints().get(1));
	assertEquals(Vector11, basicHull.getCurVector());
	assertEquals(Vector11, basicHull.getEdges().get(0));
	Angle actAngle = basicHull.getAngles().get(0);
	Angle expAngle = new Angle(PI + PI/4d, PI/4d, Point.ORIGIN);
	assertEquals(expAngle.angle, actAngle.angle, DELTA);
	assertEquals(expAngle.start, actAngle.start, DELTA);
	assertEquals(expAngle.center, actAngle.center);
	
	// Add another point.
	Point point21 = new Point(2,1);
	basicHull.addPoint(point21);
	assertEquals(point21, basicHull.getPoints().get(2));
	assertEquals(new Vector(Point11, point21), basicHull.getEdges().get(1));
	assertEquals(PI * (5.0d / 4.0d), basicHull.getAngles().get(1).angle, DELTA);
	
	// Add concurrent point (fail case)
	try {
	    basicHull.addPoint(point21);
	    Assert.fail();
	} catch (IllegalArgumentException e) {
	    // Exception thrown as expected.
	}
    }

    @Test
    public final void testClosed() {
	basicHull.addPoint(new Point(1,1));
	basicHull.addPoint(new Point(2,1));
	basicHull.addPoint(Point.ORIGIN);
	
	// Ensure closed is set.
	assertTrue(basicHull.isClosed());
	
	// Ensure exception is thrown if we try to add more points.
	try {
	    basicHull.addPoint(new Point(5,5));
	    Assert.fail();
	} catch (Exception e) {
	    // Exception thrown as expected.
	}
    }
    
}