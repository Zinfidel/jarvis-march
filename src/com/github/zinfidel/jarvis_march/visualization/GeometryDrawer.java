package com.github.zinfidel.jarvis_march.visualization;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;

import com.github.zinfidel.jarvis_march.geometry.Angle;
import com.github.zinfidel.jarvis_march.geometry.Point;
import com.github.zinfidel.jarvis_march.geometry.Vector;

class GeometryDrawer {
    
    private static final int POINT_WIDTH = 6;
    private static final int ARC_DIAMETER = 40;
    private static final float DASH_MITER = 10f;
    
    /** The height of the panel to draw to. */
    public static int Height = 0;
    
    // Dashed stroke pattern for Y-Axis drawing.
    private static final BasicStroke DashedStroke;
    static {
	float dash[] = {DASH_MITER};
	DashedStroke = new BasicStroke(1f,
			               BasicStroke.CAP_BUTT,
			               BasicStroke.JOIN_MITER,
			               DASH_MITER, dash, 0f);
    }
    
    // Arrow head shape for the angle arcs.
    private static final Path2D ArrowHead;
    static {
	// Initialize the ArrowHeadHead shape to point down.
	ArrowHead = new Path2D.Double();
	ArrowHead.moveTo(5d, -5d);
	ArrowHead.lineTo(0d, 0d);
	ArrowHead.lineTo(-5d, -5d);
    }

    
    /**
     * This function flips a given y-value along the X-Axis (mirrors it). This
     * is necessary/useful because Swing JPanels have their origin set at the
     * upper-left corner rather than how Cartesian planes have their Origin at
     * the lower-left corner. Use this function anywhere a y-value is used.
     * 
     * @param y The y-value to flip.
     * @return The flipped y-value.
     */
    private static int Flip(int y) {
	return Height - y;
    }

    
    /**
     * Draws a point.
     * @param point The point to draw.
     * @param g The graphics context to draw to.
     */
    static void draw(Point point, Graphics g) {
	g.fillOval(point.x - POINT_WIDTH / 2,
		   Flip(point.y) - POINT_WIDTH / 2,
		   POINT_WIDTH, POINT_WIDTH);
    }
    
    
    /**
     * Draws a vector.
     * @param vec The vector to draw.
     * @param g The graphics context to draw to.
     */
    static void draw(Vector vec, Graphics g) {
	g.drawLine(vec.start.x, Flip(vec.start.y),
		   vec.end.x, Flip(vec.end.y));
    }
    
    
    /**
     * Draws an arc (angle) with an arrow head at the start point.
     * @param angle The angle to draw.
     * @param g The graphics context to draw to.
     */
    static void draw(Angle angle, Graphics g) {
	// Set up dimensions of arc.
	Point c = angle.center;
	int start = (int) Math.toDegrees(angle.start);
	int ang   = (int) Math.toDegrees(angle.angle);
	
	// Draw the arc and its arrowhead to the graphics context.
	g.drawArc(c.x - ARC_DIAMETER / 2,
		  Flip(c.y) - ARC_DIAMETER / 2,
		  ARC_DIAMETER, ARC_DIAMETER,
		  start, ang);
	
	drawArrowHead(c, angle.start, g);
    }
    

    /**
     * Draws the Y-Axis as a dashed line through a point (typically the left-
     * most point).
     * 
     * @param leftMost The left-most point to draw the axis through.
     * @param g The graphics context to draw to.
     */
    static void drawYAxis(Point leftMost, Graphics g) {
	Graphics2D g2d = (Graphics2D) g;
	
	// Capture the current (default) stroke, then changed to dashed.
	Stroke defaultStroke = g2d.getStroke();
	g2d.setStroke(DashedStroke);
	
	// Draw the Y-Axis through the left-most point.
	g.drawLine(leftMost.x, Height, leftMost.x, 0);
	
	// Reset the stroke.
	g2d.setStroke(defaultStroke);
    }
    
    
    /**
     * Draws the angle value text to the right of an arc.
     * 
     * @param angle The angle to draw for.
     * @param g The graphics context to draw to.
     */
    static void drawArcLabel(Angle angle, Graphics g) {
	// Get the angle in degrees, and only 5 significant figures.
	double angleDeg = Math.toDegrees(angle.angle);
	String angleStr = String.valueOf(angleDeg).substring(0, 5);
	
	g.drawString(angleStr + "\u00b0",
		     angle.center.x + ARC_DIAMETER / 2,
		     Flip(angle.center.y));
    }
    
    
    /**
     * Draws an arrowhead at the start-point of an arc (anti-clockwise arcs).
     * 
     * @param center The center of the arc.
     * @param startAngle The start angle of the arc, in radians.
     * @param g2d The graphics context to draw to.
     */
    private static void drawArrowHead(Point center, double startAngle, Graphics g) {
	AffineTransform trans = new AffineTransform();

	/*
	 * Translate the arrowhead to the center of the arc, translated to the
	 * right by the arc radius. This places the arrow as it would be for an
	 * arc going to x=0 degrees (X-axis), with the arrow pointing down. Then
	 * rotate the arc about the center of arc, which will place the arrow
	 * at the correct point and orientation on the arc.
	 * 
	 * Note that matrix transformations go in *reverse* order.
	 */
	trans.rotate(-startAngle, center.x, Flip(center.y));            // Trans 2
	trans.translate(center.x + ARC_DIAMETER / 2, Flip(center.y)); // Trans 1
	
	Shape arrow = ArrowHead.createTransformedShape(trans);
	Graphics2D g2d = (Graphics2D) g;
	g2d.draw(arrow);
    }

}