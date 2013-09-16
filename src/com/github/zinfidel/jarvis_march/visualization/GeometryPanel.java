package com.github.zinfidel.jarvis_march.visualization;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import com.github.zinfidel.jarvis_march.geometry.Model;
import com.github.zinfidel.jarvis_march.geometry.Point;

/**
 * @author Zach Friedland
 *
 */
public class GeometryPanel extends JPanel {

    private static final long serialVersionUID = 5897008749918594368L;
    
    // TODO: TESTING CODE!
    public Model model = null;
    
    /**
     * TODO: Document
     */
    public GeometryPanel() {
	super();
	
	setBackground(Color.WHITE);
	setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null)); // TODO: Does this belong here?
    }
    
    /**
     * TODO: Document
     */
    @Override
    protected void paintComponent(Graphics g) {
	super.paintComponent(g);
	// TODO: TESTING CODE!!
	for (Point point : model.getPoints()) {
	    GeometryDrawer.draw(point, g);
	}
    }

}