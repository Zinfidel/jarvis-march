package com.github.zinfidel.jarvis_march.geometry;

import static java.lang.Math.atan2;
import static java.lang.Math.PI;

/**
 * Represents a 2D vector (or line) as on a Cartesian plane.
 * 
 * @author Zach Friedland
 */
public class Vector {
    
    /** Unit vector representing the X-Axis. */
    public static final Vector XAxis =
	    new Vector(Point.Origin, new Point(1, 0));
    
    /** Unit vector representing the Y-Axis. */
    public static final Vector YAxis = 
	    new Vector(Point.Origin, new Point(0, 1));

    
    /** The start point (origin) of this vector. */
    public final Point start;

    /** The end point of this vector. */
    public final Point end;

    /** The components of the position vector of this vector. */
    public final Point position;

    /** The length of this vector. */
    public final double magnitude;

    /** The anti-clockwise angle from the X-Axis to this vector. */
    public final double angle;
    
    
    /**
     * Construct a vector given a start and end point.
     * 
     * @param start The start point of this vector.
     * @param end The end point of this vector.
     */
    public Vector(Point start, Point end) {
	this.start = start;
	this.end = end;
	this.position = end.minus(start);
	this.magnitude = start.distanceTo(end);
	this.angle = refAngle(position);
    }
    
    
    /**
     * Calculates the anti-clockwise angle from the X-Axis to a vector
     * starting at the origin and pointing to <code>position</code>.
     * 
     * @param position Components of a position vector.
     * @return An angle in the range of [0 <= theta < 2PI].
     */
    private static double refAngle(Point position) {
	double theta = atan2(position.y, position.x); // Implicit int -> double
	
	return (theta >= 0.0d) ? theta : ((2.0d * PI) + theta);
    }
    
    /**
     * Calculates the anti-clockwise angle between this vector
     * and <code>vector</code>.
     * 
     * @param vector The vector to calculate the angle to.
     * @return An angle in the range of [0 <= theta < 2PI].
     */
    public double angleTo(Vector vector) {
	double theta = vector.angle - this.angle;
	
	return (theta >= 0.0d) ? theta : ((2.0d * PI) + theta);
    }
    
    /** {@InheritDoc} */
    @Override
    public boolean equals(Object obj) {
	// Reflexivity
	if (obj == this) return true;

	// Obj must not be null.
	if (obj == null) return false;

	// Ensure obj is a point.
	if (!(obj instanceof Vector)) return false;

	// Ensure x and y values are the same.
	Vector vector = (Vector) obj;
	boolean startEquals = this.start == vector.start;
	boolean endEquals = this.end == vector.end;

	return startEquals && endEquals;
    }
}