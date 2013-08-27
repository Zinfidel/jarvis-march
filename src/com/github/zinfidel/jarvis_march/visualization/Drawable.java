package com.github.zinfidel.jarvis_march.visualization;

import java.awt.Graphics;

/**
 * An interface that defines objects that can be drawn to a Swing graphics
 * context. Objects that implement this interface should not modify the
 * graphics context in any way outside of drawing themselves to it.
 * 
 * @author Zach Friedland
 */
interface Drawable {
    
    public void Draw(Graphics g);

}