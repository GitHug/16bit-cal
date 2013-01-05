/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Control.Actions;

import Model.Datatypes.Priority;
import java.awt.Color;

/**
 * Simple event class used to make objects animate. 
 * @author fredrikmakila
 */
public class AnimationEvent {
    private int dayCardId;
    private Color color;
    private boolean state;
    private Priority prio;
    
    /**
     * Only constructor for this class
     * @param id The ID of the component to be animated
     * @param state The state for the animation. True for start, false for stop.
     * @param prio The priority which will be animated
     */
    public AnimationEvent(int id, boolean state, Priority prio) {
        this.dayCardId = id;
        this.state = state;
        this.prio = prio;
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
     * Gets whenther the animation should start or stop. True for start, false for stop
     * @return The animation state
     */
    public boolean getState() {
        return state;
    }
    
    /**
     * Gets the priority of the animation event. 
     * @return The priority for this event
     */
    public Priority getPrio() {
        return prio;
    }
    
    /**
     * Sets the pulsating color for the object for this event
     * @param color The pulse color
     */
    public void setColor(Color color) {
        this.color = color;
    }
    

}
