/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package View;

import Model.Datatypes.BorderImage;
import java.awt.*;

/**
 * Creates a background component
 * @author fredrikmakila
 */
public class BackgroundComponent extends CustomComponent {
    private Point p;
    private Dimension d;

    /**
     * Only constructor for this class.
     * @param x The x position for the component.
     * @param y The y position for the component.
     * @param width The width for the component.
     * @param height The height of the component.
     */
    public BackgroundComponent(int x, int y, int width, int height) {
        super(x, y, width, height);
    }
    
    /**
     * Convenience method for public access to setBorderByName.
     * @param border The name of the new border.
     * @see BorderImage
     */
    private void setBorderByName(BorderImage border) {
        setBorderType(border);
    }
    
    @Override
    public void draw(Graphics g) {
        p = getLocation();
        d = getSize();
        Graphics2D g2d = (Graphics2D) g;
        //Sets antialiasing for smoother shapes
        g2d.setRenderingHint(
           RenderingHints.KEY_ANTIALIASING,                
           RenderingHints.VALUE_ANTIALIAS_ON);
        //Create a rectangle
        g.setColor(getBackgroundColor());
        g.fillRect(p.x, p.y, d.width, d.height);
        //Set font color
        g.setColor(Color.darkGray);  
    }
    



    @Override
    public void notify(Object event) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Not implemented
     * @param state Not implemented
     * @param color Not implemented
     */
    @Override
    protected void animate(Boolean state, Color color) {
    }
}
