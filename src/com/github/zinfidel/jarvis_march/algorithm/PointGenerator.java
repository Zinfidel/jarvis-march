package com.github.zinfidel.jarvis_march.algorithm;

import java.util.Random;

import com.github.zinfidel.jarvis_march.geometry.Point;

/**
 * Contains methods for randomly generating points.
 */
public class PointGenerator {
    
    /** Sigma value to use when generating gaussian values. */
    private static final double SIGMA = 3d;
    
    /** The pseudo-random number generator for this class. */
    private static final Random RNG = new Random();
    
    /**
     * Generates a uniformly distributed random point between the
     * Origin (0, 0) and the supplied bounds (inclusive).
     * 
     * @param bounds The upper-right bounds on the point to generate.
     * @return The generated point.
     */
    public static Point random(Point bounds) {
	int x = RNG.nextInt(bounds.x);
	int y = RNG.nextInt(bounds.y);
	return new Point(x, y);
    }
    
    
    /**
     * Generates a normally distributed point between the Origin (0,0)
     * and the supplied bounds (inclusive).
     * 
     * @param bounds The upper-right bounds on the point to generate.
     * @return The generated point.
     */
    public static Point normalRandom(Point bounds) {
	double halfBoundsX = (double) bounds.x / 2d;
	double halfBoundsY = (double) bounds.y / 2d;
	double x = -5d;
	double y = -5d;
	
	// Gaussian random variable in range [-SIGMA, SIGMA].
	while (x <= -SIGMA || x >= SIGMA)
	    x = RNG.nextGaussian();
	
	while (y <= -SIGMA || y >= SIGMA)
	    y = RNG.nextGaussian();
	
	// Normalize distribution to [-1, 1].
	x /= SIGMA;
	y /= SIGMA;
	
	// Scale distribution to size of a quadrant in the bounds.
	x *= halfBoundsX;
	y *= halfBoundsY;
	
	// Translate the distribution to the center of quadrant I.
	x += halfBoundsX;
	y += halfBoundsY;
	
	return new Point((int) x, (int) y);
    }
   
}
