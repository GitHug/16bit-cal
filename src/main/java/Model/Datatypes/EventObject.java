package Model.Datatypes;

import Control.Interface.UndoRedo;
import Model.SixteenBitModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A class that defines the structure of an EventObject.
 * This class also contains all the necessary getters for the class. An EventObject
 * is differentiated from aa TaskObject by it having a start date and a end date.
 * @author fredrikmakila
 */

public class EventObject implements UndoRedo{
    String name;
    String info;
    Date start_date;
    Date start_time;
    Date end_date;
    Date end_time;
    CategoryObject category;
    int id;
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    DateFormat tf = new SimpleDateFormat("HH:mm");
    private SixteenBitModel model;
    
    /**
     * A constructor for this class.
     * Creates an EventObject by instantiating all the variables neccessary. 
     * 
     * @param name The name of the event
     * @param info The info of the event
     * @param start_date The start date of the event, as format "yyyy-MM-dd"
     * @param start_time The start time of the event, as format "HH:mm";
     * @param end_date The end date of the event, as format "yyyy-MM-dd"
     * @param end_time The end time of the event, as format "HH:mm";
     * @param category The category of the event.
     */
    public EventObject(String name, String info, Date start_date, Date start_time,
            Date end_date, Date end_time, CategoryObject category) {
        this.name = name;
        this.info = info;
        this.start_date = start_date;
        this.start_time = start_time;
        this.end_date = end_date;
        this.end_time = end_time;
        this.category = category;
        this.model = SixteenBitModel.getInstance();
    }
    
    /**
     * Another constructor for this class.
     * This constructor is identical except that it has an additional id variable. 
     * It is intended to be used when retrieving an already existing event from the
     * XML-file.
     * 
     * @param id The identifier of the event.
     * @param name The name of the event
     * @param info The info of the event
     * @param start_date The start date of the event, as format "yyyy-MM-dd"
     * @param start_time The start time of the event, as format "HH:mm";
     * @param end_date The end date of the event, as format "yyyy-MM-dd"
     * @param end_time The end time of the event, as format "HH:mm";
     * @param category The category of the event.
     */    
    public EventObject(int id, String name, String info, Date start_date, Date start_time,
            Date end_date, Date end_time, CategoryObject category) {
        this.id = id;
        this.name = name;
        this.info = info;
        this.start_date = start_date;
        this.start_time = start_time;
        this.end_date = end_date;
        this.end_time = end_time;
        this.category = category;
        this.model = SixteenBitModel.getInstance();
    }
    
    /**
    * Gets the type of the EventObject.
    * A type is defined as a String with value "event"
    * @return The type of this object.
    */
    public String getType() {
        return "event";
    }
    
    /**
    * Gets the name of the EventObject
    * @return The name of the EventObject.
    */
    public String getName() {
        return name;
    }
    
    /**
    * Gets the info of the EventObject
    * @return the info of the EventObject
    */
    public String getInfo() {
        return info;
    }
    
    /**
    * Gets the identifier of the EventObject
    * @return The id of the EventObject
    */
    public int getID() {
        return id;
    }
    
    /**
     * Gets the start date of the EventObject
     * @return The start date of the EventObject
     */
    public Date getStartDate() {
        return start_date;
    }
    
    /**
    * Gets the start date of the EventObject as a String
    * @return The start date of the EventObject
    */
    public String getStartDateAsString() {
        return df.format(start_date);
    }
    
    /**
    * Gets the start time of the EventObject
    * @return The start time of the EventObject
    */
    public Date getStartTime() {
        return start_time;
    }
    
    /**
    * Gets the start time of the EventObject as a String
    * @return The start time of the EventObject
    */
    public String getStartTimeAsString() {
        return tf.format(start_time);
    }
    
    /**
    * Gets the end date of the EventObject
    * @return The end date of the EventObject
    */
    public Date getEndDate(){
        return end_date;
    }
    
    /**
    * Gets the end date of the EventObject as a String
    * @return The end date of the EventObject
    */
    public String getEndDateAsString() {
        return df.format(end_date);
    }
    
    /**
    * Gets the end time of the EventObject
    * @return The end time of the EventObject
    */
    public Date getEndTime() {
        return end_time;
    }
    
    /**
    * Gets the end time of the EventObject as a String
    * @return The end time of the EventObject
    */    
    public String getEndTimeAsString() {
        return tf.format(end_time);
    }
    
    /**
     * Gets the category of the EventObject as a String
     * @return 
     */
    
    public CategoryObject getCategory() {
        return category;
    }
    
    
    
    /**
    * To String method.
    * This method is mostly intended for easier printing of an event in the case
    * of debugging.
    * @return The content of the EventObject as a String
    */
    @Override
    public String toString() {
        String startDate = getStartDateAsString();
        String startTime = getStartTimeAsString();
        String endDate = getEndDateAsString();
        String endTime = getEndTimeAsString();
        
        return "ID : " + id +
                ", name: " + name + 
                ", info: " + info + 
                ", start date: " + startDate +
                ", start time: " + startTime +
                ", end date: " + endDate +
                ", end time: " + endTime + 
                ", category: " + category;
                
    }

    @Override
    public void undo() {
        model.removeEvent(this);
    }

    @Override
    public void redo() {        
        model.addEventRedo(this);
    }
}
