package View;

import Model.Datatypes.TaskObject;

import javax.swing.*;
import java.awt.*;

/**
 * The graphical representation of a task to be shown inside the DayView
 * @author Robin Horneman
 */
class TaskPanel extends JPanel{
    
   /*Private variables*/
    private final TaskObject task;
    
    /*Constructors*/
    /**
     * Creates a TaskPanel with an associated task
     * @param task task that is associated to this panel
     */
    public TaskPanel(TaskObject task) {
        this.task = task;
        addContent();
        //this.setBackground(this.task.getCategory().getRealColor());
    }
    
    /*Setters*/
    
    /*Getters*/
    
    /*Others*/
    /**
     * Adds the visual content to the TaskPanel
     */
    private void addContent() {
        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.ipadx=2; c.ipady=2;
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(1,2,2,1);
        c.fill = GridBagConstraints.BOTH;
        //Add Colorpane
        JPanel taskColor = new JPanel();
        taskColor.setBackground(task.getCategory().getRealColor());
        c.gridx=0; c.gridy = 0;
        c.gridheight = 2;
        this.add(taskColor, c);
        c.gridheight = 1;
        //Add name
        JLabel taskName = new JLabel("Name: "+ task.getName());
        c.gridx = 1; c.gridy = 0;
        this.add(taskName, c);        
        //Add priority
        JLabel taskPrio = new JLabel("Priority: "+task.getPrioAsString());
        c.gridx = 2; c.gridy = 1;
        this.add(taskPrio, c);
        //Add deadline
        JLabel taskDeadline = new JLabel("Deadline: " + task.getDateAsString());
        c.gridx = 3; c.gridy = 1;
        this.add(taskDeadline, c);
    }

}
