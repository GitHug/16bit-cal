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
    private final int dayCardId;
    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private final ArrayList<EventObject> events = new ArrayList<>();
    private final ArrayList<TaskObject> tasks = new ArrayList<>();
    private Date date;

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
     * Returns all tasks associated with this CardStuffList
     * @return tasks of this dayCardId
     */
    public ArrayList<TaskObject> getTasks() {
        return tasks;
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

    /**
     * Empties all tasks and events associated with this CardStuffList 
     */
    public void setEmpty() {
        tasks.clear();
        events.clear();
    }


    
    /**
     * Adds a date to this dayCardId
     * @param date the date for this dayCardId
     */
    public void setDate(Date date) {
        this.date = date;
    }


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
