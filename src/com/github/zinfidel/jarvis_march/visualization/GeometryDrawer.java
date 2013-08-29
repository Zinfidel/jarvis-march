package com.github.zinfidel.jarvis_march.visualization;

import java.awt.Graphics;

import com.github.zinfidel.jarvis_march.geometry.Point;
import com.github.zinfidel.jarvis_march.geometry.Vector;

class GeometryDrawer {

    static void draw(Point point, Graphics g) {
	g.drawOval(point.x, point.y, 10, 10);
    }
    
    static void draw(Vector vec, Graphics g) {
	
    }
    
}