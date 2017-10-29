/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Model.Datatypes;

import Control.Interface.UndoRedo;
import Model.SixteenBitModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A class that defines the structure of a TaskObject.
 * This class also contains all the necessary getters for the class. A TaskObject
 * is differentiated from an EventObject by it having a due date, a priority and the ability to be completed.
 * @author fredrikmakila
 */
public class TaskObject implements UndoRedo{
    private final String name;
    private final String info;
    private final Date due_date;
    private final Date due_time;
    private final Complete complete;
    private final Priority prio;
    private final CategoryObject category;
    private int id;
    private final DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    private final DateFormat tf = new SimpleDateFormat("HH:mm");
    private final SixteenBitModel model;
    
    
    /**
     * A constructor for this class.
     * Creates a TaskObject by instantiating all the variables neccessary. 
     * 
     * @param name The name of the task
     * @param info The info of the task
     * @param due_date The due date of the task, as format "yyyy-MM-dd"
     * @param due_time The due time of the task, as format "HH:mm";
     * @param complete The completed status of the task, as either "yes" or "no";
     * @param prio The priority of the task
     * @param category The category of the task.
     */
    public TaskObject(String name, String info, Date due_date, Date due_time, 
            Complete complete, Priority prio, CategoryObject category){
        this.name = name;
        this.info = info;
        this.due_date = due_date;
        this.due_time = due_time;
        this.complete = complete;
        this.prio = prio;
        this.category = category;
        this.model = SixteenBitModel.getInstance();
    }
    
    /**
     * Another constructor for this class.
     * This constructor is identical to the one above, except that is has an additional
     * ID field. It is intended to be used when retrieving an already existing task from the
     * XML-file.
     * @param id The identifier of the task
     * @param name The name of the task
     * @param info The info of the task
     * @param due_date The due date of the task, as format "yyyy-MM-dd"
     * @param due_time The due time of the task, as format "HH:mm";
     * @param complete The completed status of the task, as either "yes" or "no";
     * @param prio The priority of the task
     * @param category The category of the task.
     */
    public TaskObject(int id, String name, String info, Date due_date, Date due_time, 
            Complete complete, Priority prio, CategoryObject category){
        this.id = id;
        this.name = name;
        this.info = info;
        this.due_date = due_date;
        this.due_time = due_time;
        this.complete = complete;
        this.prio = prio;
        this.category = category;
        this.model = SixteenBitModel.getInstance();
    }
    
    /**
     * Gets the type of the TaskObject.
     * A type is defined as a String with value "task"
     * @return The type of this object.
     */
    @SuppressWarnings("SameReturnValue")
    public String getType() {
        return "task";
    }
    
    /**
     * Gets the name of the TaskObject
     * @return The name of the TaskObject.
     */
    public String getName() {
        return name;
    }
    
    /**
     * Gets the info of the TaskObject
     * @return the info of the TaskObject
     */
    public String getInfo() {
        return info;
    }
    
    /**
     * Gets the due date of the TaskObject
     * @return the due date of the TaskObject
     */
    public Date getDate() {
        return due_date;
    }

    /**
     * Gets the due date of the TaskObject as a String.
     * @return the due date of the TaskObject
     */
    public String getDateAsString() {
        return df.format(due_date);
    }
    
    /**
     * Gets the due time of the TaskObject as a String
     * @return the due time of the TaskObject
     */
    public String getTimeAsString() {
        return tf.format(due_time);
    }

    /**
     * Gets the completed state of the TaskObject as a String
     * @return "yes" if the task is complete, otherwise "no"
     */
    public String getCompleteAsString() {
        return complete.toString();
    }
    
    /**
     * Gets the priority of the TaskObject
     * @return the priority of the TaskObject
     */
    public Priority getPrio() {
        return prio;
    }
    
    /**
     * Gets the priority of the TaskObject as a String
     * @return the priority of the TaskObject
     */
    public String getPrioAsString() {
        return prio.toString();
    }
    
    /**
     * Gets the category of the TaskObject
     * @return the category of the TaskObject
     */
    public CategoryObject getCategory() {
        return category;
    }
    
    /**
     * To String method.
     * This method is mostly intended for easier printing of a task in the case
     * of debugging.
     * @return The content of the TaskObject as a String
     */
    @Override
    public String toString() {
        String date = getDateAsString();
        String time = getTimeAsString();
        
        return "ID : " + id +
                ", name: " + name + 
                ", info: " + info + 
                ", due date: " + date +
                ", due time: " + time +
                ", complete: " + complete +
                ", priority: " + prio + 
                ", category: " + category;           
    }

    @Override
    public void undo() {
        model.removeTask(this);
    }

    @Override
    public void redo() {        
        model.addTaskRedo(this);
    }
}
