package com.github.zinfidel.jarvis_march.geometry;

/**
 * Defines an angle with starting and ending angles as well.
 */
public class Angle {

    /** Total angle, in radians. */
    public final double angle;
    
    /** Starting angle, in radians, anti-clockwise from the X-Axis. */
    public final double start;
    
    /** Ending angle, in radians, anti-clockwise from the X-Axis. */
    public final double end;
    
    
    /**
     * Constructs an angle based on the total angle and the starting angle.
     * @param angle The total angle.
     * @param start The starting angle.
     */
    public Angle(double angle, double start) {
	this.angle = angle;
	this.start = start;
	this.end = (start + angle) % (2 * Math.PI);
    }
}
