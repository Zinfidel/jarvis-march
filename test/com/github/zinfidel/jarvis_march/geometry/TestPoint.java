package com.github.zinfidel.jarvis_march.geometry;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestPoint {

    private static final double DELTA = 0.01;

    @Test
    public final void testEqualsObject() {
	Point p1 = new Point(3, 134);
	Point p2 = new Point(3, 134);
	assertTrue(p1.equals(p2));
    }

    @Test
    public final void testHashCode() {
	Point p1 = new Point(3, 134);
	Point p2 = new Point(3, 134);
	assertEquals(p1.hashCode(), p2.hashCode());
    }

    @Test
    public final void testMinus() {
	Point p1 = new Point(1, 4);
	Point p2 = new Point(3, 5);
	assertEquals(p2.minus(p1), new Point(2, 1));
    }

    @Test
    public final void testDistanceTo() {
	Point p1 = Point.ORIGIN;
	Point p2 = new Point(3, 4);
	assertEquals(5d, p1.distanceTo(p2), DELTA);
    }

}
