/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Control.Actions;

import java.awt.*;

/**
 * Event class that notifies components that they should be updated.
 * An update can be to change the color of the component, change the text and/or remove
 * the border. 
 * @author fredrikmakila
 */
public class UpdateEvent {
    /**
     * Defines the color for the daycards of the current month for an update event
     */
    public final Color CURRENTMONTH = Color.lightGray;
    /**
     * Defines the color for the daycards of the next month for an update event
     */
    public final Color NEXTMONTH = new Color(255, 200, 0, 100);
    /**
     * Defines the color for the daycards of the previous month for an update event
     */
    public final Color PREVMONTH = new Color(0, 200, 255, 100);
    /**
     * Defines the color for the daycard for today's date
     */
    public final Color TODAY = Color.RED;
    /**
     * Defines the color for the month for an update event
     */
    public final Color MONTHCOLOR = Color.RED;
    /**
     * Defines the color for the weeks for an update event
     */
    public final Color WEEKCOLOR = Color.YELLOW;
    
    private final int id;
    private final String s;
    private Color color;
    private Color textColor = Color.DARK_GRAY;
    private boolean showBorder = true;
    private boolean selectable;
    
    /**
     * Only constructor for this class.
     * @param id The id of component that will be updated
     * @param s The text that the updated component will have
     */
    public UpdateEvent(int id, String s) {
        this.id = id;
        this.s = s;
    }
    
    
    
    /**
     * Tells the component what background color it should have.
     * The method also enables the border and sets the text color to dark gray.
     * @param color The color for the background for the component
     */
    public void setColor(Color color) {
        this.color = color;
        showBorder = true;
        textColor = Color.DARK_GRAY;
    }
    
    
    /**
     * Sets whether the object should be selectable after an update. 
     * True for selectable, false for unselectable.
     * @param state The selectable state
     */
    public void setSelectable(boolean state) {
        selectable = state;
    }
    /**
     * Gets whether this event will make the object selectable or not
     * @return The selectable state
     */
    public boolean getSelectable() {
        return selectable;
    }
    
    /**
     * Gets the color for the text of the component.
     * @return the color for the text of the component.
     */
    public Color getTextColor() {
        return textColor;
    }
    
    /**
     * Returns whether the border should be visible or not.
     * @return true if the border should be visible, otherwise false.
     */
    public boolean getShowBorder() {
        return showBorder;
    }
    
    /**
     * Makes the daycard completly transparent. 
     * It also makes the border not visible as well as the text color.
     */
    public void setTransparent() {
        color = new Color(0, 0, 0, 0);
        showBorder = false;
        textColor = color;
        selectable = false;
    }
    
    /**
     * Returns the color that the component should have.
     * @return The color for the component. 
     */
    public Color getColor() {
        return color;
    }
    
    /**
     * Returns the id of the event for the component it should update.
     * @return the id of the event.
     */
    public int getId() {
        return id;
    }
    
    /**
     * Returns the text that the component should have for this event.
     * @return The text of the event.
     */
    public String getString() {
        return s;
    }
}
