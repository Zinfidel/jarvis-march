package com.github.zinfidel.jarvis_march.visualization;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.Set;

import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import com.github.zinfidel.jarvis_march.algorithm.JarvisMarcher;
import com.github.zinfidel.jarvis_march.geometry.*;
import com.github.zinfidel.jarvis_march.visualization.GeometryDrawer;

/**
 * @author Zach Friedland
 *
 */
public class GeometryPanel extends JPanel {

    private static final long serialVersionUID = 5897008749918594368L;
    
    // The model and algorithm elements to draw.
    private Model model = null;
    private JarvisMarcher marcher = null;

    
    /**
     * Construct a geometry panel with a white background and nice border.
     */
    public GeometryPanel() {
	super();
	setBackground(Color.WHITE);
	setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
    }

    
    /**
     * Draws elements from the model, convex hull, and algorithm to the
     * panel in various exciting colors and animations.
     */
    @Override
    protected void paintComponent(Graphics g) {
	super.paintComponent(g);
	Graphics2D g2d = (Graphics2D) g;
	
	// Set up geometry flip.
	GeometryDrawer.Height = getHeight();

	// Set antialiasing.
	g2d.addRenderingHints(
		new RenderingHints(RenderingHints.KEY_ANTIALIASING,
				   RenderingHints.VALUE_ANTIALIAS_ON ));
	
	Set<Point> points = model.getPoints();
	
	// Draw all of the points.
	g2d.setColor(Color.BLACK);
	for (Point point : points) {
	    GeometryDrawer.draw(point, g);
	}
	
	// Draw the Y-Axis through the left-most point.
	if (!points.isEmpty()) {
	    g2d.setColor(Color.BLUE);
	    GeometryDrawer.drawYAxis(model.getLeftmost(), g2d);
	}

	// Render the convex hull elements, if there is a hull.
	ConvexHull hull = model.getHull();
	if (hull != null) {
	    
	    // Draw vectors.
	    g.setColor(Color.BLACK);
	    for (Vector vector : hull.getEdges()) {
		GeometryDrawer.draw(vector, g);
	    }
	    
	    // Draw angles.
	    for (Angle angle : hull.getAngles()) {
		// Draw the arc.
		g.setColor(Color.RED);
		GeometryDrawer.draw(angle, g);
		
		// Draw the label.
		g.setColor(Color.BLACK);
		GeometryDrawer.drawArcLabel(angle, g2d);
	    }
	}
	
	// Draw next vector, if available.
	Vector next = marcher != null ? marcher.getNextVector() : null;
	if (next != null) {
	    g.setColor(Color.YELLOW);
	    GeometryDrawer.draw(next, g);
	}
	
	// Draw best vector, if available.
	Vector best = marcher != null? marcher.getBestVector() : null;
	if (best != null) {
	    g.setColor(Color.RED);
	    GeometryDrawer.draw(best, g);
	}	
    }
    
    
    /** Set the model to be rendered. */
    public void setModel(Model model) {
	this.model = model;
    }


    /** Set the Jarvis Marcher algorithm with elements to be rendered. */
    public void setMarcher(JarvisMarcher marcher) {
	this.marcher = marcher;
    }
}