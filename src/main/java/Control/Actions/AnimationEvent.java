/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Control.Actions;

import java.awt.*;

/**
 * Simple event class used to make objects animate. 
 * @author fredrikmakila
 */
public class AnimationEvent implements CalendarEvent {
    private final int dayCardId;
    private Color color;

    /**
     * Only constructor for this class
     * @param id The ID of the component to be animated
     *
     */
    public AnimationEvent(int id) {
        this.dayCardId = id;
    }
    
    /**
     * Gets the ID of the components for this event.
     * @return The id of the component.
     */
    public int getId() {
        return dayCardId;
    }
    
    /**
     * Gets the color for which the component will pulsate with for this event.
     * @return The pulse color
     */
    public Color getColor() {
        return color;
    }

    /**
     * Sets the pulsating color for the object for this event
     * @param color The pulse color
     */
    public void setColor(Color color) {
        this.color = color;
    }
    

}
