
package View;

import Control.Actions.Action;

import javax.swing.*;
import java.awt.*;



/**
 * Initiates the button panel in the bottom of the gui
 * @author Robert
 */
class ButtonPanel extends JPanel {
    
    /**
    * Class constructor that initiates the button panel for the GUI
    * @param f      The window that contains the ButtonPanel.
    * @author Robert
    */
    public ButtonPanel(DayCardWindow f) {
        //Setting language for this object
        //setSize(100,100);
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        
        // Add Event button
        Action OkEAction = new Action(f,"adde");
        JButton buttonAddE = new JButton(OkEAction);
        buttonAddE.setText("Add Event");
        c.gridx = 0;
	c.gridy = 0; 
        c.anchor = GridBagConstraints.PAGE_START;
        c.fill = GridBagConstraints.HORIZONTAL;
        add(buttonAddE, c);
        
        // Edit Event button
        Action EditEAction = new Action(f,"edite");
        JButton buttonEditE = new JButton(EditEAction);
        buttonEditE.setText("Edit Event");
        c.gridx = 1;
        add(buttonEditE, c);
        
        // Remove Event button
        Action RemoveEAction = new Action(f,"removee");
        JButton buttonRemoveE = new JButton(RemoveEAction);
        buttonRemoveE.setText("Remove Event");
        c.gridx = 3;
        add(buttonRemoveE, c);
        
        // Adds the FadeButton 
        FadeButton fade = new FadeButton("Fade Me");
        c.gridx = 4;
        add(fade, c);
        
        // Add Task button
        Action OkAction = new Action(f,"add");
        JButton buttonAdd = new JButton(OkAction);
        buttonAdd.setText("Add Task");
        c.gridx = 0;
	c.gridy = 1;
        add(buttonAdd, c);
        
        // Edit Task button
        Action EditAction = new Action(f,"edit");
        JButton buttonEdit = new JButton(EditAction);
        buttonEdit.setText("Edit Task");
        c.gridx = 1;
        add(buttonEdit, c);
        
        // Remove Task button
        Action RemoveAction = new Action(f,"remove");
        JButton buttonRemove = new JButton(RemoveAction);
        buttonRemove.setText("Remove Task");
        c.gridx = 3;
        add(buttonRemove, c);    
        
        // Adds the Close button
        Action CloseButton = new Action(f, "close");
        JButton buttonClose = new JButton(CloseButton);
        buttonClose.setText("Close");
        c.gridx = 4;
        add(buttonClose, c);
    }
    
}