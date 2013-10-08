package com.github.zinfidel.jarvis_march.visualization;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;

import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import com.github.zinfidel.jarvis_march.algorithm.JarvisMarcher;
import com.github.zinfidel.jarvis_march.geometry.Angle;
import com.github.zinfidel.jarvis_march.geometry.ConvexHull;
import com.github.zinfidel.jarvis_march.geometry.Model;
import com.github.zinfidel.jarvis_march.geometry.Point;
import com.github.zinfidel.jarvis_march.geometry.Vector;

/**
 * @author Zach Friedland
 *
 */
public class GeometryPanel extends JPanel {

    private static final long serialVersionUID = 5897008749918594368L;
    
    // TODO: TESTING CODE!
    public Model model = null;
    public JarvisMarcher marcher = null;
    
    /**
     * TODO: Document
     */
    public GeometryPanel() {
	super();
	
	setBackground(Color.WHITE);
	setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
    }
    
    /**
     * TODO: Document
     */
    @Override
    protected void paintComponent(Graphics g) {
	super.paintComponent(g);
	
	// TODO Put this somewhere sensible.
	g.setColor(Color.LIGHT_GRAY);
	g.drawRect(19, 19, getWidth() - 40, getHeight() - 40);
	
	// TODO Apparently default color is some kind of gray? 51,51,51
	g.setColor(Color.BLACK);

	// TODO Actually use this?
	// Set up geometry flip
	GeometryDrawer.Height = getHeight();
	
	// TODO TEST
	Graphics2D g2d = (Graphics2D) g;
	g2d.addRenderingHints( new RenderingHints( RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON ));
	
	// TODO: TESTING CODE!!
	for (Point point : model.getPoints()) {
	    GeometryDrawer.draw(point, g);
	}
	
	// TODO: Probably remove this.
	// Draw the Y-Axis
	Graphics2D g2 = (Graphics2D) g;
	final float dash1[] = {10.0f};
	final Stroke defaultStroke = g2.getStroke();
	final BasicStroke dashed =
		new BasicStroke(1.0f,
			BasicStroke.CAP_BUTT,
			BasicStroke.JOIN_MITER,
			10.0f, dash1, 0.0f);
	g2.setStroke(dashed);
	int x = model.getLeftmost().x;
	g.setColor(Color.BLUE);
	g.drawLine(x, getHeight(), x, 0);
	g.setColor(Color.BLACK);
	g2.setStroke(defaultStroke);

	ConvexHull hull = null;
	if ((hull = model.getHull()) != null) {
	    for (Vector vector : hull.getEdges()) {
		GeometryDrawer.draw(vector, g);
	    }
	    
	    for (Angle angle : hull.getAngles()) {
		g.setColor(Color.RED);
		GeometryDrawer.draw(angle, g);
		g.setColor(Color.BLACK);
		
		// TODO: Put this crap in its own method.
		double angleDeg = angle.angle * ( 180d / Math.PI);
		String angleStr = String.valueOf(angleDeg).substring(0, 5);
		g.drawString("         " + angleStr + "\u00b0",
			     angle.center.x,
			     getHeight() - angle.center.y);
	    }
	}
	
	Vector next = marcher != null ? marcher.getNextVector() : null;
	if (next != null) {
	    g.setColor(Color.YELLOW);
	    GeometryDrawer.draw(next, g);
	}
	
	Vector best = marcher != null? marcher.getBestVector() : null;
	if (best != null) {
	    g.setColor(Color.RED);
	    GeometryDrawer.draw(best, g);
	}
	
	g.setColor(Color.BLACK);
    }

}