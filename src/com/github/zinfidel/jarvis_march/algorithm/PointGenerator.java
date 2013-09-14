package com.github.zinfidel.jarvis_march.algorithm;

import java.util.Random;

import com.github.zinfidel.jarvis_march.geometry.Point;

/**
 * Contains methods for randomly generating points.
 */
public class PointGenerator {
    
    /** The pseudo-random number generator for this class. */
    private static final Random RNG = new Random();
    
    /**
     * Generates a random point between the Origin (0, 0) and the
     * supplied bounds (inclusive).
     * 
     * @param bounds The upper-right bounds on the point to generate.
     * @return The generated point.
     */
    public static Point random(Point bounds) {
	int x = RNG.nextInt(bounds.x);
	int y = RNG.nextInt(bounds.y);
	return new Point(x, y);
    }
}
