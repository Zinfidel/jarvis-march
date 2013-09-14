package com.github.zinfidel.jarvis_march.geometry;

import static org.junit.Assert.*;
import static java.lang.Math.*;
import junit.framework.Assert;

import org.junit.Test;


public class TestVector {
    
    private static final double DELTA = 0.01;

    @Test
    public final void testVector() {
	// Edge Cases
	Vector zeroLength = new Vector(Point.Origin, Point.Origin);
	assertEquals(0.0d, zeroLength.magnitude, DELTA);
	assertEquals(zeroLength.end, zeroLength.position);
	assertEquals(0.0d, zeroLength.angle, DELTA);
	
	// Test quadrant 1
	Vector q1 = new Vector(Point.Origin, new Point(4, 3));
	assertEquals(new Point(4, 3), q1.position);
	assertEquals(5.0d, q1.magnitude, DELTA);
	assertEquals(atan(3.0d / 4.0d), q1.angle, DELTA);
	
	// Test quadrant 2 and translation
	Vector q2 = new Vector(new Point(-1, 1), new Point(-5, 4));
	assertEquals(new Point(-4, 3), q2.position);
	assertEquals(5.0d, q2.magnitude, DELTA);
	assertEquals(PI - atan(3.0d / 4.0d), q2.angle, DELTA);
	
	// Test quadrant 3 with vector in quadrant 4
	Vector q3 = new Vector(new Point(3, 0), new Point(0, -3));
	assertEquals(new Point(-3, -3), q3.position);
	assertEquals(4.24d, q3.magnitude, DELTA);
	assertEquals((PI + (PI / 4)), q3.angle, DELTA);
	
	// Test quadrant 4 with reversed position in quadrant 2
	Vector q4 = new Vector(new Point(-1, 1), Point.Origin);
	assertEquals(new Point(1, -1), q4.position);
	assertEquals(sqrt(2.0d), q4.magnitude, DELTA);
	assertEquals((7.0d / 4.0d ) * PI, q4.angle, DELTA);
    }

    @Test
    public final void testAngleTo() {
	Vector vec1 = null;
	Vector vec2 = null;

	// Test right-angle (quadrant 1).
	vec1 = new Vector(Point.Origin, new Point(1, 0));
	vec2 = new Vector(vec1.end, new Point(1, 1));
	assertEquals(PI / 2.0d, vec2.angleTo(vec1), DELTA);
	assertEquals(PI * (3.0d/2.0d), vec1.angleTo(vec2), DELTA);
	
	// Test acute-angle (quadrant 3).
	vec1 = new Vector(Point.Origin, new Point(-1, -1));
	vec2 = new Vector(vec1.end, new Point(-1, 0));
	assertEquals(PI / 4.0d, vec1.angleTo(vec2), DELTA);
	assertEquals(2.0d * PI - (PI / 4.0d), vec2.angleTo(vec1), DELTA);
	
	// Test obtuse-angle (quadrant 1 - 4).
	vec1 = new Vector(new Point(2, -2), new Point(1, -1));
	vec2 = new Vector(vec1.end, new Point(2, 0));
	assertEquals(PI / 2.0d, vec1.angleTo(vec2), DELTA);
	assertEquals(PI * 3.0d/2.0d, vec2.angleTo(vec1), DELTA);
	
	// Test concurrent lines.
	vec1 = new Vector(Point.Origin, new Point(1, 0));
	vec2 = new Vector(vec1.end, Point.Origin);
	assertEquals(0.0d, vec1.angleTo(vec2), DELTA);
	assertEquals(0.0d, vec2.angleTo(vec1), DELTA);
	
	// Test angle to zero-vector (undefined).
	vec1 = Vector.XAxis;
	vec2 = new Vector(Point.Origin, Point.Origin);
	try {
	    vec1.angleTo(vec2);
	    Assert.fail();
	} catch (IllegalArgumentException e) {
	    // Exception thrown as expected.
	}
	try {
	    vec2.angleTo(vec1);
	    Assert.fail();
	} catch (IllegalArgumentException e) {
	    // Exception thrown as expected.
	}
    }

}
