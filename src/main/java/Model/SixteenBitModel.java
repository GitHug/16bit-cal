package Model;

import Control.Actions.*;
import Control.AnimationEngine;
import Control.Canvas;
import Control.HelpSystem;
import Control.Interface.Drawable;
import Control.Interface.Observable;
import Control.Interface.ObservableRegistration;
import Model.Database.XMLInputOutput;
import Model.Datatypes.*;
import View.Checkbox;
import View.DayCard;
import View.MenuBar;
import View.MyGlassPane;

import javax.swing.*;
import java.awt.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A singleton Class that acts as the model of the Calendar. This class should
 * have the list of events and tasks as well as information about which daycards
 * that are selected. It should also include setters and getters for all this
 * information
 *
 * @author Robin Horneman
 */
public class SixteenBitModel extends CalendarMethods {
    //<---------public variables------------
    /**
     * The value for a month view
     */
    public static final int MONTHVIEW = 0;
    /**
     * The value for a week view
     */
    public static final int WEEKVIEW = 1;
    /**
     * The value for a day view
     */
    public static final int DAYVIEW = 2;
    //---------public variables------------>
    
    //<---------private variables------------
    //The xml input/output class that is used.
    private XMLInputOutput xmlhandler; 
    //The instance for this model.
    private static SixteenBitModel instance = null;
    //The current view
    private int currentView = MONTHVIEW;
    //The week view model
    private WeekViewModel weekviewmodel = new WeekViewModel();
    //The width and height of the canvas.
    private int canvasSizeWidth = 964;
    private int canvasSizeHeight = 559;
    //The id of the selected DayCard
    private int id;
    //The current month
    private int currentMonth = 0;
    //The current year
    private int currentYear;
    //The id of the first daycard created

    //A list containing all the available months
    private CircularArrayList<String> allMonths = new CircularArrayList<String>("January", "February",
            "March", "April", "May", "June", "July", "August", "September", "October",
            "November", "December");
    //Lists of things that is supposed to show up in the differen Views
    private ArrayList<Integer> days;
    private ArrayList<Integer> weeks;
    //Lists of events and tasks for a view
    private ArrayList<EventObject> events;
    private ArrayList<TaskObject> tasks;
    //Lists of ALL events and tasks
    private ArrayList<EventObject> allEvents;
    private ArrayList<TaskObject> allTasks;
    //List of userdefined categories
    private ArrayList<CategoryObject> userCategories;
    //Paintlist list of drawables that are going to be drawn
    private ArrayList<Drawable> paintList = new ArrayList<Drawable>();
    //Different observables for sending different events.
    private Observable<SelectedEvent> selectedObservable = new ObservableImplementation<SelectedEvent>();
    private Observable<UpdateEvent> updateObservable = new ObservableImplementation<UpdateEvent>();
    private Observable<DeSelectEvent> deSelectedObservable = new ObservableImplementation<DeSelectEvent>();
    private Observable<AnimationEvent> animationObservable = new ObservableImplementation<AnimationEvent>();
    //The undo/redo manager
    private UndoRedoManager undoredomanager = new UndoRedoManager();
    //The glass pane
    private MyGlassPane myGlassPane;
    //A List of DayCards with their associated tasks and events
    private ArrayList<CardStuffList> dayCardList;
    //A datatype for storing the daycards and their tasks and events.
    private DayCardIdentifiers dcId = new DayCardIdentifiers();
    //The animation engine
    private AnimationEngine animationEngine;
    //List containing all the daycards
    private ArrayList<DayCard> dcList = new ArrayList<DayCard>();
    //List containing the checkboxes
    private ArrayList<Checkbox> checkBoxList = new ArrayList<Checkbox>();
    //The canvas for this application
    private Canvas canvas;
    //The menu bar for this application
    private MenuBar menuBar;
    //The main jframe
    private JFrame frame;
    //The helpsystem
    private HelpSystem help;

    //---------private variables------------>
    
    //<--------constructor------------
    /**
     * The Standard constructor. Not able to be called from outside the class,
     * to ensure that the singleton property holds.
     */
    protected SixteenBitModel() {
    }
    //---------constructor------------>

    //<---------getters------------
    /**
     * Implements the singleton aspect of the Model. You can only get one
     * instance of this class.
     *
     * @return The singleton instance
     */
    public static SixteenBitModel getInstance() {
        if (instance == null) {
            instance = new SixteenBitModel();
        }
        return instance;
    }
    
    /**
     * Fills the model with data from the XML-file
     */
    public void fillModel(){
        xmlhandler = new XMLInputOutput();
        userCategories = xmlhandler.getCategories();
        events = xmlhandler.getEvents();
        tasks = xmlhandler.getTasks();
        allEvents = events;
        allTasks = tasks;
        
        if(tasks.isEmpty()){
            System.out.println("tasks is empty in fillModel");
        }
        
        //Updates the list of daycards
        for(int i = 0; i < dcId.getDayCardStuffList().size(); i++) {
            Date date = dcId.getDayCardStuffList().get(i).getDate();
            for(int j = 0; j < tasks.size(); j++) {
                TaskObject t = tasks.get(j);
                Date taskDate = t.getDate();
                if(taskDate.equals(date)) {
                    System.out.println(t.toString() + " ADDED AT POS " + i);
                    dcId.getDayCardStuffList().get(i).addTask(t);
                }
                else; //dcId.getDayCardStuffList().get(i).setEmpty();
                }
            } 
    
    }
    
    /**
     * Method for returning all events in the model associated with the current
     * date.
     *
     * @return list of events starting this month
     */
    public ArrayList<EventObject> getThisMonthEvents() {
        int month;
        ArrayList<EventObject> monthevents = new ArrayList<EventObject>();
        Calendar cal = Calendar.getInstance();
        if (events != null) {
            for (int i = 0; i < events.size(); i++) {
                cal.setTime(events.get(i).getStartDate());
                month = cal.get(Calendar.MONTH);
                if (month == currentMonth) {
                    monthevents.add(events.get(i));
                }
            }
        }
        return monthevents;
    }
    
    /**
     * Method for returning all tasks in the model associated with the current
     * date.
     *
     * @return list of tasks with deadline this month
     */
    public ArrayList<TaskObject> getThisMonthTasks() {
        int month;
        ArrayList<TaskObject> monthtasks = new ArrayList<TaskObject>();
        Calendar cal = Calendar.getInstance();
        if (tasks != null) {
            for (int i = 0; i < tasks.size(); i++) {
                cal.setTime(tasks.get(i).getDate());
                month = cal.get(Calendar.MONTH);
                if (month == currentMonth) {
                    monthtasks.add(tasks.get(i));
                }
            }
        }
        return monthtasks;
    }
    
    /**
     * Gets the canvas size
     *
     * @return The canvas dimensions
     */
    public Dimension getCanvasSize() {
        return new Dimension(canvasSizeWidth, canvasSizeHeight);
    }
    
    /**
     * Gets the id of the selected component
     *
     * @return The ID of the selected component
     */
    public int getSelected() {
        return id;
    }
    
    @Override
    public int getMonth() {
        return currentMonth;
    }
    
    /**
     * Returns the WeekViewModel from this SixteenBitModel
     * @return 
     */
    public WeekViewModel getWeekModel() {
        return weekviewmodel;
    }

    /**
     * Gets all the tasks in the model.
     *
     * @return All the tasks in the model
     */
    public ArrayList<TaskObject> getTasks() {
        return tasks;
    }

    /**
     * Gets all the events in the model.
     *
     * @return All the events in the model
     */
    public ArrayList<EventObject> getEvents() {
        return events;
    }
    
    /**
     * Gets the XML handler
     * @return the XML handler
     */
    public XMLInputOutput getXMLHandler() {
        return xmlhandler;
    }
    
    /**
     * A getter
     *
     * @return Returns the paintList with all Drawables that needs drawing.
     */
    public ArrayList<Drawable> getPaintList() {
        return paintList;
    }
    
    /**
     * Gets the glass pane for this application
     * @return the glass pane for this application
     */
    public MyGlassPane getGlassPane() {
        return myGlassPane;
    }
    
    /**
     * Returns the daycard identifiers
     * @return the daycard identifers object
     */
    public DayCardIdentifiers getDayCardIdentifiers() {
        return dcId;
    }
    
    /**
     * Gets the animation engine
     * @return The animation engine
     */
    public AnimationEngine getAnimationEngine() {
        return animationEngine;
    }
    /**
     * Gets the list containing all daycards
     * @return The list of daycards
     */
    public ArrayList<DayCard> getDayCardList() {
        return dcList;
    }
    
    /**
     * Returns this applications canvas
     * @return the canvas for this application
     */
    public Canvas getCanvas() {
        return canvas;
    }
    
    /**
     * Returns the menu bar for this application
     * @return The menubar
     */
    public MenuBar getMenuBar() {
        return menuBar;
    }
    
    /**
     * Gets the main frame for this application
     * @return The main frame
     */
    public JFrame getFrame() {
        return frame;
    }
    
    /**
     * Gets the helpsystem
     * @return The helpsystem
     */
    public HelpSystem getHelpSystem() {
        return help;
    }
    
    //---------getters------------>
    
    //<---------setters------------
    
    /**
     * Sets the canvas size.
     *
     * @param dim the new dimension of the canvas
     */
    public void setCanvasSize(Dimension dim) {
        canvasSizeWidth = dim.width;
        canvasSizeHeight = dim.height;
    }

    /**
     * Notifies observers that listen to selected events of an selected event.
     *
     * @param id the ID of the selected component
     */
    public void setSelected(int id) {
        this.id = id;
        this.selectedObservable.notifyObservers(new SelectedEvent(id));
    }

    /**
     * Notifies observers that listen to deselect events of an deselect event.
     *
     * @param id the ID of the new selected component
     */
    public void setDeSelected(int id) {
        this.deSelectedObservable.notifyObservers(new DeSelectEvent(id));
    }
    
    /**
     * Sets the current set of tasks 
     * @param tasks the new set of tasks 
     */
    public void setTasks(ArrayList<TaskObject> tasks){
        this.tasks = tasks;
    }
    
    /**
     * Sets the current set of events 
     * @param events the new set of events 
     */
    public void setEvents(ArrayList<EventObject> events){
        this.events = events;
    }
    
    /**
     * Sets the current set of ALL tasks 
     * @param tasks the new set of tasks 
     */
    public void setTasksAll(ArrayList<TaskObject> tasks){
        this.allTasks = tasks;
    }
    
    /**
     * Sets the current set of ALL events 
     * @param events the new set of events 
     */
    public void setEventsAll(ArrayList<EventObject> events){
        this.allEvents = events;
    }
    
    /**
     * Sets the current month and changes.
     *
     * @param i The current month as an integer from 0-11.
     */
    public void setMonth(int i) {
        currentMonth = i;
        allMonths.setStartIndex(i);
    }

    /**
     * Sets the current year.
     *
     * @param year The new year.
     */
    public void setYear(int year) {
        currentYear = year;
    }
    
    /**
     * Saves the glasspane for this application. Enables other classes that have 
     * access to the model to access the glasspane
     * @param myGlassPane The glasspane for this application
     */
    public void setGlassPane(MyGlassPane myGlassPane) {
        MyGlassPane glassPane = myGlassPane;
        glassPane.setVisible(true);
        this.myGlassPane = glassPane;
    }
    
    /**
     * Sets which CardStuffList to use 
     * @param csl the CardStuffList to use
     */
    public void setDayCardStuffList(ArrayList<CardStuffList> csl) {
        dayCardList = csl;
    }
    
    /**
     * Sets the daycard identifiers list
     * @param dcId The daycard identifiers list
     */
    public void setDayCardIdentifiers(DayCardIdentifiers dcId) {
        this.dcId = dcId;

    }
    
    /**
     * Sets the animation engine
     * @param animationEngine The animation engine
     */
    public void setAnimationEngine(AnimationEngine animationEngine) {
        this.animationEngine = animationEngine;
    } 
    
    /**
     * Sets the list of daycards
     * @param dcList A list containing all the daycards
     */
    public void setDayCardList(ArrayList<DayCard> dcList) {
        this.dcList = dcList;
    }
    
    /**
     * Adds a checkbox to the list of checkboxes.
     * @param checkbox A checkbox to be saved in the model
     */
    public void addToCheckboxList(Checkbox checkbox) {
        checkBoxList.add(checkbox);
    }
    
    /**
     * Sets the canvas for this application
     * @param canvas The canvas for this application
     */
    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }
    
    /**
     * Sets the menubar for this application
     * @param menuBar The menubar for this application
     */
    public void setMenuBar(MenuBar menuBar) {
        this.menuBar = menuBar;
    }
    
    /**
     * Saves the main frame for this application
     * @param frame The main frame
     */
    public void setFrame(JFrame frame) {
        this.frame = frame;
    }
    
    /**
     * Sets the helpsystem
     * @param help The helpsystem
     */
    public void setHelpSystem(HelpSystem help) {
        this.help = help;
    }
    

    //---------setters------------>
    
    //<---------registration methods------------
    
    /**
     * Gets an observable registrator for a selected event. Enables observers to
     * register to listen to selectedEvents.
     *
     * @return a selected event observable registrator.
     */
    public ObservableRegistration<SelectedEvent> selectedObservableRegistration() {
        return this.selectedObservable;
    }

    /**
     * Gets an observable registrator for a deselect event. Enables observers to
     * register to listen to dSelectedEvents.
     *
     * @return a deselect event observable registrator.
     */
    public ObservableRegistration<DeSelectEvent> deSelectedObservableRegistration() {
        return this.deSelectedObservable;
    }

    /**
     * Gets an observable registrator for an update event. Enables observers to
     * register to listen to updateEvents.
     *
     * @return an update event observable registrator.
     */
    public ObservableRegistration<UpdateEvent> updateObservableRegistration() {
        return this.updateObservable;
    }
    
    /**
     * Gets an observable registrator for an animation event. Enables observers to
     * register to listen to animation events.
     * @return An animation event observable registrator
     */
    public ObservableRegistration<AnimationEvent> animationObservableRegistration() {
        return this.animationObservable;
    }

    //---------registration methods------------>
    
    //---------Other methods-------------->
    /**
     * Updates the daycards to either the previous or the next month. 
     * The direction is specified by the parameter direction. The method creates
     * events and notifies all observers that has been registered to UpdateObservable.
     * @param direction Specifies the direction. If the input is "next", the next month
     * will be displayed. Any other string will get the previous month. 
     */
    public void changeMonth(String direction) {
        int monthCardId;
        int numDays;
        int prevMonth;
        int prevYear;
        int index;
        int updateId;
        int day = 0;
        ArrayList<UpdateEvent> list = new ArrayList<UpdateEvent>();
        CircularArrayList tempMonths = new CircularArrayList();
        UpdateEvent event;
        int weekNumber;
        String month;
        //If next month that will be displayed
        if(direction.equals("next")) {
            //Gets the next month's name;
            month = allMonths.incrementAndGet();
            //gets the index from the arraylist to check what position the month has
            currentMonth = allMonths.getIndex();
            //If the position is 0, it means that we just passed december into 
            //the next year year
            if (currentMonth == 0) {
                currentYear++;
            }
        }
        //if previous month that will be displayed
        else {
            //Gets the previous month's name
            month = allMonths.decrementAndGet();
            //Gets the number for that month
            currentMonth  = allMonths.getIndex();
            //if the position is 11, it means that we just passed january into the
            //previous year
            if(currentMonth == 11) {
                currentYear--;
            }
        }
        
        prevYear = currentYear;
        //Updates the month
        monthCardId = dcId.getMonthCardId();
        event = new UpdateEvent(monthCardId, month);
        event.setColor(event.MONTHCOLOR);
        list.add(event);
        
        //Gets the index of the first daycard of the current month of the daycard list
        index = getWeekDay(currentYear, currentMonth);
        //Gets the number of days in this month
        numDays = getNumberOfDays(currentYear, currentMonth);
        //Updates the daycards of the current month
        for(int i = index; i < index + numDays; i++) {
            //The day for the daycard
            day = day + 1;
            //The id for the daycard to be updated
            updateId = dcId.getDayCardIds().get(i);
            //Updates the event
            event = new UpdateEvent(updateId, ""+day); 
            //Sets the color for the daycard
            event.setColor(event.CURRENTMONTH);
            //Makes the daycard selectable
            event.setSelectable(true);
            //Adds the event to the list so it can later be despatched
            list.add(event);
            //Adds tasks/events to the daycard if it has any
            updateDayCard(currentYear, currentMonth, day, updateId);
        }

        
        //Resets the day counter
        day = 0;
        //Updates the daycards for the next month
        for(int i = index + numDays; i < dcId.getDayCardIds().size(); i++) {
            //The day for the daycard
            day++;
            //The id for the daycard to be updated
            updateId = dcId.getDayCardIds().get(i);
            //Updates the event
            event = new UpdateEvent(updateId, ""+day); 
            //Makes them unselectable
            event.setSelectable(false);
            //Sets the color for the daycard
            event.setColor(event.NEXTMONTH);
            //Adds the event to the list so it can later be despatched
            list.add(event); 
        }
        
        //Updates the daycard for the previous month
        //Saves allMonths so its index can be used to check whether the previous month
        //is in the previous year or not
        tempMonths.setStartIndex(allMonths.getIndex());
        //decrement the index
        tempMonths.decrement();
        //Gets the index
        prevMonth = tempMonths.getIndex();
        //Checks if the month is in the previous year
        if(prevMonth == 11) {
            prevYear--;
        } 
        //gets the number of days in the previous month
        numDays = getNumberOfDays(prevYear, prevMonth);
        for(int i = 0; i < index; i++) {
            //The day for the daycard
            day = numDays - (index - i) + 1;
            //The id for the daycard to be updated
            updateId = dcId.getDayCardIds().get(i);
            //Updates the event
            event = new UpdateEvent(updateId, ""+day); 
            //Makes them unselectable
            event.setSelectable(false);
            //Sets the color for the daycard
            event.setColor(event.PREVMONTH);
            //Adds the event to the list so it can later be despatched
            list.add(event); 
        }
        
        //Updates the weekcards
        //Gets the week numbers
        ArrayList<Integer> weekList = getWeekNumber(currentYear, currentMonth);
        for (int i = 0; i < dcId.getWeekCardIds().size(); i++) {
            //Sets the value for the weekcard
            weekNumber = weekList.get(i);
            //Gets the id for the daycard to be updated
            updateId = dcId.getWeekCardIds().get(i);
            //Updates the event
            event = new UpdateEvent(updateId, ""+weekNumber); 
            //Makes them unselectable
            event.setSelectable(false);
            //Sets the color for the daycard
            event.setColor(event.WEEKCOLOR);
            //Adds the event to the list so it can later be despatched
            list.add(event); 
        }
        
        //Stop the animation of daycards
        animationEngine.stopAnimatePrio();

        //Notifies the observers
        for (int i = 0; i < list.size(); i++) {
            this.updateObservable.notifyObservers(list.get(i));
        }
        
    }
    

    /**
     * Add a new task to the list of tasks and ask for it do be undoable
     *
     * @param task the task to be added
     */
    public void addTask(TaskObject task) {
        if (tasks == null) {
            tasks = new ArrayList<TaskObject>();
        }
        tasks.add(task);
        undoredomanager.addUndoRedo(task);
        //Add to daycardidentifier list
        for(int i = 0; i < dcId.getDayCardStuffList().size(); i++) {
            Date date = dcId.getDayCardStuffList().get(i).getDate();
            if(task.getDate().equals(date)) {
                dcId.getDayCardStuffList().get(i).addTask(task);
            }
        }
        
    }
    
    /**
     * Add a new event to the list of events and ask for it do be undoable
     *
     * @param event the task to be added
     */
    public void addEvent(EventObject event) {
        if (events == null) {
            events = new ArrayList<EventObject>();
        }
        events.add(event);
        undoredomanager.addUndoRedo(event);
        //Add to daycardidentifier list
        for(int i = 0; i < dcId.getDayCardStuffList().size(); i++) {
            Date date = dcId.getDayCardStuffList().get(i).getDate();
            if(event.getStartDate().equals(date)) {
                dcId.getDayCardStuffList().get(i).addEvent(event);
            }
        }
        
    }

    /**
     * Add a new task to the list of tasks
     *
     * @param task the task to be added
     */
    public void addTaskRedo(TaskObject task) {
        if (tasks == null) {
            tasks = new ArrayList<TaskObject>();
        }
        tasks.add(task);
    }

    /**
     * Add a new event to the list of events
     *
     * @param event the event to be added
     */
    public void addEventRedo(EventObject event) {
        if (events == null) {
            events = new ArrayList<EventObject>();
        }
        events.add(event);
    }

    /**
     * Removes a task from the models list of tasks without asking for undo
     *
     * @param task task to be removed
     */
    public void removeTask(TaskObject task) {
        tasks.remove(task);
    }
    
    /**
     * Removes an event from the models list of events without asking for undo
     *
     * @param event event to be removed
     */
    public void removeEvent(EventObject event) {
        events.remove(event);
    }
    
    /**
     * Adds a new userdefined category to the list of categories in the model
     * @param co the category to be added
     */
    public void addCategory(CategoryObject co) {
        userCategories.add(co);
    }

    /**
     * Removes a task from the models list of tasks and asking for undo
     *
     * @param task task to be removed
     */
    public void removeTaskUndoable(TaskObject task) {
        tasks.remove(task);
        undoredomanager.addUndoRedo(new DeletedTask(task));
    }

    /**
     * Removes an event from the models list of events and asking for undo
     *
     * @param event event to be removed
     */
    public void removeEventUndoable(EventObject event) {
        events.remove(event);
        undoredomanager.addUndoRedo(new DeletedEvent(event));
    }

    /**
     * Method that undo the latest action.
     */
    public void undo() {
        if (undoredomanager.canUndo()) {
            undoredomanager.undo();
        }
    }

    /**
     * Method for redoing the last undone action
     */
    public void redo() {
        if (undoredomanager.canRedo()) {
            undoredomanager.redo();
        }
    }


    /**
     * Adds drawable to paintList.
     * @param d The drawable to be added
     */
    public void addPaintList(Drawable d) {
        paintList.add(d);
    }

    /**
     * Clears the painList.
     */
    public void clearPaintList() {
        paintList.clear();
    }
    
    /**
     * Adds tasks or events to a day card. If there are no
     * tasks or events for this date, then nothing happens.
     * @param year The current year
     * @param month The current month
     * @param day The current date
     * @param dayCardId The ID of the daycard
     */
    public void updateDayCard(int year, int month, int day, int dayCardId) {
        String yearString = ""+year;
        String monthString = ""+(month + 1);
        String dayString = ""+day;
        Date date = null;
        //Creates a new date to be able to compare it with that of the tasks and events
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        //Checks if the month is consisting of one or two integers. If less than 10,
        //Then an extra zero has to be added in front
        if(monthString.length() < 2) {
            monthString = "0"+monthString;
        }
        //Do the same thing with the day
        if(dayString.length() < 2) {
            dayString = "0"+dayString;
        }
        
        try {
            //Creates the date
            date = df.parse(yearString+"-"+monthString+"-"+dayString);
            
        } catch (ParseException ex) {
            Logger.getLogger(SixteenBitModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        //Adds the date to the daycard
        for(int i = 0; i < dcId.getDayCardStuffList().size(); i++) {
            //Compares the id so the correct daycard gets updated
            if(dcId.getDayCardStuffList().get(i).getDayCardId() == dayCardId) {
                dcId.getDayCardStuffList().get(i).setDate(date);
                //Check the date with that of the tasks
                for(int j = 0; j < tasks.size(); j++) {
                    TaskObject t = tasks.get(j);
                    Date taskDate = t.getDate();
                    if(date.equals(taskDate)) {
                        dcId.getDayCardStuffList().get(i).addTask(t);
                    }
                    else dcId.getDayCardStuffList().get(i).setEmpty();
                }
                break;
            }
        }   
    }
    
    /**
     * Dispatches all animation events to the observers that is listening to
     * animation observable.
     * @param events A list of events to be dispatched
     */
    public void animateDayCard(ArrayList<AnimationEvent> events) {
        //Notifies the observers
        for (int i = 0; i < events.size(); i++) {
            this.animationObservable.notifyObservers(events.get(i));
        }
    }
    
    /**
     * Dispatches an animation event to the observers that is listening to animation 
     * observable.
     * @param dayCardId The ID of the daycard that will be notified
     * @param state The state of the animation. True for start, false for stop.
     * @param alpha An alpha value.
     */
    public void animateDayCard(int dayCardId, boolean state, int alpha) {
        AnimationEvent event = new AnimationEvent(dayCardId, state, null);
        event.setColor(new Color(255, 255, 255, alpha));
        this.animationObservable.notifyObservers(event);
    }
    
    /************************SAVER********************************************/
    /**
     * Saves the content of the Model in the XML file
     * (events, tasks and categories)
     */
    public void saveModelXML(){
        //Add new tasks and events to the list of ALL tasks and events
        //removing duplicates
        for(int i=0;i<tasks.size();i++){
            boolean shouldadd = true;
            for(int j=0;j<allTasks.size();j++){
                if(tasks.get(i).toString().equals(allTasks.get(j).toString())){
                    shouldadd = false;
                }
            }
            if(shouldadd){
               //Add to the XML-file
               allTasks.add(tasks.get(i));
               xmlhandler.put(tasks.get(i));
            }
        }
        for(int i=0;i<events.size();i++){
            boolean shouldadd = true;
            for(int j=0;j<allEvents.size();j++){
                if(events.get(i).toString().equals(allEvents.get(j).toString())){
                    shouldadd = false;
                }
            }
            if(shouldadd){
               //Add to the XML-file
               allEvents.add(events.get(i));
               xmlhandler.put(events.get(i));
            }
        }
        
        
    }
    
    /**
     * Deselects all selected checkboxes.
     */
    public void deSelectCheckBoxes() {
        //Deselects the checkboxes
        for(int i = 0; i < checkBoxList.size(); i++) {
            Checkbox check = checkBoxList.get(i);
            if(check.getSelected()) {
                this.deSelectedObservable.notifyObservers(new DeSelectEvent(check.getId()));
            }
        }
    }
}