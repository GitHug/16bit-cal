package View;

import Model.Datatypes.EventObject;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * The graphical representation of an event to be shown inside the DayView
 * @author Robin Horneman
 */
public class EventPanel extends JPanel{
    
    /*Private Variables*/
    private EventObject event;
    
    /*Constructors*/
    /**
     * Creates an EventPanel with an associated Event
     * @param event Event that is associated to this Panel
     */
    public EventPanel(EventObject event) {
        this.event = event;
        addContent();
    }
    
    /*Setters*/
    /**
     * Changes the associated event
     * @param event the new event
     */
    public void setEvent(EventObject event) {
        this.event = event;
    }
    
    /*Getters*/
    /**
     * Returns the associated event
     * @return this panel´s event
     */
    public EventObject getEvent() {
        return event;
    }

    /*Others*/
    /**
     * Adds the visual content to the EventPanel
     */
    private void addContent() {
        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.ipadx=2; c.ipady=2;
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(1,2,2,1);
        c.fill = GridBagConstraints.BOTH;
        //Add Colorpane
        JPanel eventColor = new JPanel();
        eventColor.setBackground(event.getCategory().getRealColor());
        c.gridx=0; c.gridy = 0;
        c.gridheight = 2;
        this.add(eventColor, c);
        c.gridheight = 1;
        //Add name
        JLabel eventName = new JLabel("Name: "+ event.getName());
        c.gridx = 1; c.gridy = 0;
        this.add(eventName, c);        
        c.gridy = 1;
        //MOAR here
    }
    
}
