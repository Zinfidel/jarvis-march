package com.github.zinfidel.jarvis_march.geometry;

import static java.lang.Math.*;

/**
 * Represents a 2D vector (or line) as on a Cartesian plane.
 * 
 * @author Zach Friedland
 */
public class Vector {

    /** Unit vector describing the X-Axis of a 2D Cartesian plane. */
    public static final Vector XAxis = new Vector(0.0d, 1.0d);

    /** Unit vector describing the Y-Axis of a 2D Cartesian plane. */
    public static final Vector YAxis = new Vector((PI / 2.0d), 1.0D);

    
    public final Point start;
    public final Point end;
    public final Point position;
    public final double magnitude;
    public final double angle;


    /**
     * Construct a vector from a given start and end point.
     * 
     * @param start The start point of the vector.
     * @param end The end point of the vector.
     */
    public Vector(Point start, Point end) {
	this.start = start;
	this.end = end;
	this.position = end.Minus(start);
	this.magnitude = CalcMagnitude(position);
	this.angle = AngleBetween(this.position, XAxis.position);
    }

    /**
     * Construct a vector given its magnitude and angle. The vector will have
     * a start point of the origin (this will be a position vector).
     * 
     * @param magnitude The magnitude of the vector.
     * @param angle The clockwise angle of the vector from the X-Axis.
     */
    public Vector(double magnitude, double angle) {
	this.magnitude = magnitude;
	this.angle = angle;
	this.start = new Point(0, 0);

	int xComp = (int) (magnitude * cos(angle));
	int yComp = (int) (magnitude * sin(angle));

	this.end = new Point(xComp, yComp);
	this.position = this.end;
    }


    /**
     * Calculates the dot product between two 2D vectors.
     * 
     * @param components1 The component values of the first vector.
     * @param components2 The component values of the second vector.
     * @return The dot product between the two vectors.
     */
    public static double DotProduct(Point components1, Point components2) {
	double xProduct = components1.x * components2.x;
	double yProduct = components1.y * components2.y;

	return xProduct + yProduct;
    }

    /**
     * Calculates the dot product between two 2D vectors.
     * 
     * @param vec1 The first vector.
     * @param vec2 The second vector.
     * @return The dot product between the two vectors.
     */
    public static double DotProduct(Vector vec1, Vector vec2) {
	return DotProduct(vec1.position, vec2.position);
    }

    /**
     * Calculates the magnitude of a vector with given components.
     * 
     * @param components The components of the vector.
     * @return The magnitude of the vector.
     */
    public static double CalcMagnitude(Point components) {
	double x2 = components.x * components.x;
	double y2 = components.y * components.y;

	return sqrt(x2 + y2);
    }

    /**
     * Calculates the magnitude of a vector.
     * 
     * @param vector The vector.
     * @return The magnitude of the vector.
     */
    public static double CalcMagnitude(Vector vector) {
	return CalcMagnitude(vector.position);
    }

    /**
     * Calculates the clockwise angle between two vectors.
     * 
     * @param components1 The components of the first vector.
     * @param components2 The The components of the second vector.
     * @return The clockwise angle <b>from</b> the first vector having
     * <code>components1</code> to the second vector having
     * <code>components2</code> in <i>radians</i>.
     */
    public static double AngleBetween(Point components1, Point components2) {
	double dotProduct = DotProduct(components1, components2);
	double vec1Mag = CalcMagnitude(components1);
	double vec2Mag = CalcMagnitude(components2);
	double magProduct = vec1Mag * vec2Mag;
	double cosAngle = dotProduct / magProduct;

	return acos(cosAngle);
    }

    /**
     * Calculates the clockwise angle between two vectors.
     * 
     * @param vector1 The first vector.
     * @param vector2 The second vector.
     * @return The clockwise angle <b>from</b> vector1 <b>to</b> vector2.
     */
    public static double AngleBetween(Vector vector1, Vector vector2) {
	return AngleBetween(vector1.position, vector2.position);
    }

    /**
     * Calculates the clockwise angle between this vector and <code>vector</code>.
     * 
     * @param vector The vector to calculate an angle to.
     * @return The clockwise angle <b>from</b> this vector <b>to</b>
     * <code>vector</code> in <i>radians</i>.
     */
    public double AngleToVector(Vector vector) {
	return AngleBetween(this, vector);
    }
}