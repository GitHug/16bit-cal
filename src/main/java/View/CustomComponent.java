/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package View;

import Control.Interface.Drawable;
import Control.Interface.Observer;
import Model.Datatypes.BorderImage;
import Model.SixteenBitModel;

import javax.swing.*;

/**
 * Class that creates a custom component. 
 * This class is mostly intended as a class from where other classes can inherit from. 
 * @author fredrikmakila
 */
public abstract class CustomComponent extends JPanel implements Drawable, 
        Observer {
    private Color color;
    private int stdX;
    private int stdY;
    private int stdWidth;
    private int stdHeight;
    private int width;
    private int height;
    private int x;
    private int y;
    private boolean isSelected = false;
    private Color currentColor;
    private int myId;
    private SixteenBitModel model = SixteenBitModel.getInstance();
    private boolean isSelectable = true;



    @Override
    public abstract void draw(Graphics g);

    @Override
    public boolean within(Point p) {
        Rectangle r = getBounds();
        if(p.x >= r.x && p.x < r.x+r.width && p.y >= r.y && p.y < r.y+r.height && isSelectable) {
            return true;
        }
        else return false;
        
    }
    
    /**
     * Constructor for this class.
     * Inizializes the position of the component as well as its width and height.
     * @param x The x-position of the components upper left corner.
     * @param y The y-position of the components upper left corner.
     * @param width The width of the component
     * @param height The height of the component
     */
    public CustomComponent(int x, int y, int width, int height) {
        super();
        stdX = x;
        stdY = y;
        stdWidth = width;
        stdHeight = height;
        setLayout(null);
        //setOpaque(true);
    }
    
    /**
     * Another constructor for this class.
     * Inizializes the position of the component
     * @param x The x-position of the components upper left corner.
     * @param y The y-position of the components upper left corner.
     */
    public CustomComponent(int x, int y) {
        super();
        this.x = x;
        this.y = y;
        setLayout(null);
        //setOpaque(false);
    }
    
    /**
     * Overrides isOpaque to always return false.
     * Reason is that we never want the component to be opaque
     * @return false.
     */
    @Override
    public boolean isOpaque() {
        return false;
    }
    /**
     * Sets the id for the component.
     * @param id The new id for the component. 
     */
    public void setId(int id) {
        this.myId = id;
    }
    
    /**
     * Gets the id for this component.
     * @return The id for this component.
     */
    public int getId() {
        return myId;
    }
    
    /**
     * Sets whether the component should be selectable or not.
     * @param isSelectable true if the component is selectable, otherwise false.
     */
    public void setSelectable(boolean isSelectable) {
        this.isSelectable = isSelectable;
    }
    
    

    /**
     * Sets the background color of the component
     * @param color The new background color
     */
    public void setBackgroundColor(Color color) {
        this.color = color;
    }
    
    /**
     * Gets the background color of this component.
     * @return The background color of this component. 
     */
    public Color getBackgroundColor() {
        return color;
    }
    
    
    /**
     * Changes the border of the component.
     * The method also limits the "working area" of the component by taking into account
     * how wide the border is. 
     * @param border The name of the new border.
     * @see BorderImage
     */
    public void setBorderType(BorderImage border) {
        ImageBorder imgBorder = new ImageBorder(border);
        Insets inset = imgBorder.getBorderInsets(this);
        setBounds(stdX, stdY, stdWidth, stdHeight);
        setBorder(imgBorder);
        width = stdWidth - (inset.left+inset.right);
        height = stdHeight - (inset.top+inset.bottom);
        x = stdX+inset.left;
        y = stdY+inset.top;
    }
    
    /**
     * Returns the size of this component
     * @return The dimension of the component.
     */
    @Override
    public Dimension getSize() {
        return new Dimension(width,height);
    }
    

    
    /**
     * Returns the location of this component.
     * The location is that of the upper left corner.
     * @return the coordinates for the component.
     */
    @Override
    public Point getLocation() {
        return new Point(x, y);
    }
    
    /**
     * Setter for the position of this component
     * @param p new position
     */
    @Override
    public void setLocation(Point p) {
        x = p.x;
        y = p.y;
    }
    
    /**
     * Returns the size of this component.
     * @param dim The size of the component.
     */
    @Override
    public void setSize(Dimension dim) {
        width = dim.width;
        height = dim.height;
    }
    
    
    /**
     * Sets whether the component is selected or not
     * @param isSelected true if selected, otherwise false.
     */
    protected void isSelected(Boolean isSelected) {
        this.isSelected = isSelected;
    }
    
    /**
     * Gets whether the component is selected or not.
     * @return true if selected, otherwise false.
     */
    public boolean getSelected() {
        return isSelected;
    }
    
    /**
     * Gets difference in position between the outside of the border and the inside.
     * @return The offset position by the border
     */
    public Point getOffsetLocation() {
        return new Point(x-stdX, y-stdY);
    }


    @Override
    public abstract void notify(Object event);
    
    /**
     * Abstract method for subclasses to inherit from. 
     * @param state Not implemented in this class.
     * @param color Not implemented in this class.
     */
    protected abstract void animate(Boolean state, Color color);
}
