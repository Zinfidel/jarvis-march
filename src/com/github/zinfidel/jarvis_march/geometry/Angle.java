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
    
    /** The common point of the two vectors that form this angle. */
    public final Point center;
    
    
    /**
     * Constructs an angle based on the total angle and the starting angle.
     * @param angle The total angle.
     * @param start The starting angle.
     */
    public Angle(double angle, double start, Point center) {
	this.angle = angle;
	this.start = start;
	this.end = (start + angle) % (2 * Math.PI);
	this.center = center;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	return "Angle [angle=" + angle
		+ ", start=" + start
		+ ", end=" + end
		+ ", center=[" + center.x + "," + center.y + "]]";
    }

}
