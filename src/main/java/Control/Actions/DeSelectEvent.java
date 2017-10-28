/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Control.Actions;

/**
 * Simple event class used to deselect components.
 * @author fredrikmakila
 */
public class DeSelectEvent {
    private final int id;

    /**
     * Only constructor for this class.
     * @param id The id of the component that will be deselected
     */
    public DeSelectEvent(int id) {       
        this.id = id;
    }

    /**
     * Returns the id of this event.
     * @return The events id.
     */
    public int getId() {
        return id;
    }

}
