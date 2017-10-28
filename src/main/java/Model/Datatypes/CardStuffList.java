package Model.Datatypes;

import java.util.ArrayList;
import java.util.Date;

/**
 * Class that holds a Daycard ID and the lists of events and tasks
 * associated to it.
 * @author lobban
 */
public class CardStuffList {
    /******** PRIVATE VARIABLES ******************************************/
    private int dayCardId;
    ArrayList<EventObject> events = new ArrayList<EventObject>();
    ArrayList<TaskObject> tasks = new ArrayList<TaskObject>();
    private Date date;
    
    /******** CONSTRUCTORS ***********************************************/
    
    /**
     * Constructor that creates an empty CardStuffList with given dayCardId attached
     * @param dayCardId the associated dayCardId
     */
    public CardStuffList(int dayCardId) {
        this.dayCardId = dayCardId;
    }
    
    /**
     * Constructor that creates an empty cardstufflist with given dayCardId attached
     * @param dayCardId The id of the daycard
     * @param date The date of the daycard
     */
    public CardStuffList(int dayCardId, Date date) {
        this.dayCardId = dayCardId;
        this.date = date;
    }
    
    /**
     * Constructor that creates a CardStuffList with given id and task list
     * @param daycard the id of the associated dayCardId
     * @param tasks list of tasks
     */
    public CardStuffList(int daycard, ArrayList<TaskObject> tasks) {
        this.dayCardId = daycard;
        this.tasks = tasks;
    } 
    
    /**
     * Constructor that creates a CardStuffList with given id and task list and
     * event list
     * @param daycard the id of the associated dayCardId
     * @param tasks list of tasks in dayCardId
     * @param events list of events in the dayCardId
     */
    public CardStuffList(int daycard, ArrayList<TaskObject> tasks,ArrayList<EventObject> events) {
        this.dayCardId = daycard;
        this.tasks = tasks;
        this.events = events;
    }
    
    /******** GETTERS ****************************************************/
    
    /**
     * Returns all tasks associated with this CardStuffList
     * @return tasks of this dayCardId
     */
    public ArrayList<TaskObject> getTasks() {
        return tasks;
    }
    
    /**
     * Returns all events associated with this CardStuffList
     * @return events of this dayCardId
     */
    public ArrayList<EventObject> getEvents() {
        return events;
    }
    
    /**
     * Returns the ID number of this CardStuffList
     * @return id directly associated with a certain dayCardId
     */
    public int getDayCardId() {
        return dayCardId;
    }
    
    /**
     * Gets this dayCardId's date
     * @return This dayCardId's date if it has one.
     */
    public Date getDate() {
        return date;
    }
    
    /******** SETTERS ****************************************************/
    
    /**
     * Manually sets a new ID to this CardStuffList
     * @param daycard the new ID for a daycard
     */
    public void setDayCard(int daycard) {
        this.dayCardId = daycard;
    }
    
    /**
     * Empties all tasks and events associated with this CardStuffList 
     */
    public void setEmpty() {
        tasks.clear();
        events.clear();
    }
    
    /**
     * Sets the list of tasks
     * @param tasks the new tasklist
     */
    public void setTasks(ArrayList<TaskObject> tasks) {
        this.tasks = tasks;
    }
    
    /**
     * Sets the list of events
     * @param events the new eventlist
     */
    public void setEvents(ArrayList<EventObject> events) {
        this.events = events;
    }
    
    /**
     * Adds a date to this dayCardId
     * @param date the date for this dayCardId
     */
    public void setDate(Date date) {
        this.date = date;
    }
    
    
    /******** OTHERS *****************************************************/
    
    /**
     * Adds a task to the list of tasks
     * @param task the task to be added
     */
    public void addTask(TaskObject task) {
        tasks.add(task);
    }
    
    /**
     * Adds an event to the list of events
     * @param event the event to be added
     */
    public void addEvent(EventObject event) {
        events.add(event);
    }
    

    
}
