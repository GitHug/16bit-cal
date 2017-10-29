/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Control.Actions;

/**
 * Simple event class that make components selected.
 * @author fredrikmakila
 */
public class SelectedEvent implements CalendarEvent {
    private final int id;

    /**
     * Only constructor for this class.
     * @param id The id of the component that will be selected.
     */
    public SelectedEvent(int id) {       
        this.id = id;
    }

    /**
     * Gets this events id.
     * @return The id of this event.
     */
    public int getId() {
        return id;
    }
}