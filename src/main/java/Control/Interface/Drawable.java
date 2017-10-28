/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Control.Interface;

import java.awt.*;

/**
 * An interface for drawing stuff. 
 * @author fredrikmakila
 */
public abstract interface Drawable  {
    
    /**
     * Draws the graphic components.
     * @param g The graphic component to be drawn
     */
    public abstract void draw(Graphics g);
    
    /**
     * Checks whether a mouseclick has occured within an area.
     * @param p The location of the mouseklick
     * @return true if the location p is within the area, otherwise false.
     */
    public boolean within(Point p);
}
