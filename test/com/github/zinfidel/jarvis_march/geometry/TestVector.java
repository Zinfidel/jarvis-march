package com.github.zinfidel.jarvis_march.geometry;

import static org.junit.Assert.*;

import org.junit.Test;


public class TestVector {
    
    private static final double DELTA = 0.01;

    @Test
    public final void testVector() {
	// Test quadrant 1
	Vector q1 = new Vector(Point.Origin, new Point(4, 3));
	assertEquals(new Point(4, 3), q1.position);
	assertEquals(5.0d, q1.magnitude, DELTA);
	assertEquals(53.13d, q1.angle, DELTA);
    }

    @Test
    public final void testAngleTo() {
	fail("Not yet implemented");
    }

}
