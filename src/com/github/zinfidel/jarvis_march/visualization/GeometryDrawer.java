package com.github.zinfidel.jarvis_march.visualization;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;

import com.github.zinfidel.jarvis_march.geometry.Angle;
import com.github.zinfidel.jarvis_march.geometry.Point;
import com.github.zinfidel.jarvis_march.geometry.Vector;

class GeometryDrawer {
    
    // TODO Document
    public static int Height = 0;
    
    // TODO Document
    private static int Flip(int y) {
	return Height - y;
    }

    static void draw(Point point, Graphics g) {
	g.fillOval(point.x - 3, Flip(point.y) - 3, 6, 6);
    }
    
    static void draw(Vector vec, Graphics g) {
	g.drawLine(vec.start.x, Flip(vec.start.y),
		   vec.end.x, Flip(vec.end.y));
    }
    
    static void draw(Angle angle, Graphics g) {
	// TODO Investigate arc2d for more precise arcs.
	// http://stackoverflow.com/questions/14666018/java-graphics-drawarc-with-high-precision
	
	// TODO ERROR! ARCS ARE NOT DRAWN FLIPPED!
	Point c = angle.center;
	double conversionFactor = 180d / Math.PI;
	int start = (int) (angle.start * conversionFactor);
	int ang   = (int) (angle.angle * conversionFactor);
	
	g.drawArc(c.x - 20, Flip(c.y) - 20, 40, 40, start, ang);
	
	// TODO Put this somewhere else
	AffineTransform trans = new AffineTransform();

	// matrix order is reversed?
	trans.rotate(-angle.start, c.x, Flip(c.y));
	trans.translate(c.x + 20d, Flip(c.y));
	
	Path2D arrow = new Path2D.Double();
	arrow.moveTo(5d, -5d);
	arrow.lineTo(0d, 0d);
	arrow.lineTo(-5d, -5d);
	Shape shp = arrow.createTransformedShape(trans);
	
	Graphics2D g2d = (Graphics2D) g;
	g2d.draw(shp);
    }
    
}