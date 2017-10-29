
package Control.Actions;

import Control.AnimationEngine;
import Control.HelpSystem;
import Model.Datatypes.*;
import Model.SixteenBitModel;
import View.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Class that handles different action depending on in parameters
 * @author Robert
 */
public class Action extends AbstractAction {
    private final String actionType;
    private DayCardWindow dcw;
    private AddWindow aw;
    private AddEventWindow aew;
    private EditWindow ew;
    private AddCatWindow acw;
    private final DateFormat tdf = new SimpleDateFormat("yyyy-MM-dd");
    private final DateFormat ttf = new SimpleDateFormat("HH:mm");
    private Date tDate;
    private CategoryObject tempCat;
    private final SixteenBitModel model = SixteenBitModel.getInstance();
    private ArrayList<CardStuffList> dcId;
    private final AnimationEngine animation = model.getAnimationEngine();
    private ArrayList<Integer> dayCardIdListNormal;
    private ArrayList<Integer> dayCardIdListHigh;
    private boolean b = true;
    
    /**
    * Class constructor that takes a reference to the chosen action.
     * @param f the DayCardWindow that the action comes from.
     * @param at string that represents the chosen action.
     */
    public Action(DayCardWindow f, String at) {
        actionType = at;
        dcw = f;
    }
    
    /**
    * Class constructor that takes a reference to the chosen action.
     * @param dc the current DayCardWindow.
     * @param f the AddWindow that the action comes from.
     * @param at string that represents the chosen action.
     */
    public Action(DayCardWindow dc, AddWindow f, String at) {
        actionType = at;
        dcw = dc;
        aw = f;
    }
    
    /**
    * Class constructor that takes a reference to the chosen action.
     * @param dc the current DayCardWindow.
     * @param f the AddEventWindow that the action comes from.
     * @param at string that represents the chosen action.
     */
    public Action(DayCardWindow dc, AddEventWindow f, String at) {
        actionType = at;
        dcw = dc;
        aew = f;
    }
    
    /**
    * Class constructor that takes a reference to the chosen action.
     * @param f the AddEventWindow that the action comes from.
     * @param at string that represents the chosen action.
     */
    public Action(AddEventWindow f, String at) {
        actionType = at;
        aew = f;
    }
    
    /**
    * Class constructor that takes a reference to the chosen action.
     * @param f the AddWindow that the action comes from.
     * @param at string that represents the chosen action.
     */
    public Action(AddWindow f, String at) {
        actionType = at;
        aw = f;
    }
    

    
    /**
     * Class constructor that takes a reference to the chosen action.
     * @param g the AddWindow that created the AddCatWindow.
     * @param f the AddCatWindow that the action comes from.
     * @param at string that represents the chosen action.
     */
    public Action(AddWindow g, AddCatWindow f, String at) {
        aw = g;
        actionType = at;
        acw = f;
    }
    
    /**
     * Class constructor that takes a reference to the chosen action.
     * @param g the AddEventWindow that created the AddCatWindow.
     * @param f the AddCatWindow that the action comes from.
     * @param at string that represents the chosen action.
     */
    public Action(AddEventWindow g, AddCatWindow f, String at) {
        aew = g;
        actionType = at;
        acw = f;
        b = false;
    }
    
    /**
    * Class constructor that takes a reference to the chosen action.
     * @param dc the current DayCardWindow.
     * @param f the EditWindow that the action comes from.
     * @param at string that represents the chosen action.
     */
    public Action(DayCardWindow dc, EditWindow f, String at) {
        actionType = at;
        dcw = dc;
        ew = f;
    }
    
    /**
    * Class constructor that takes a reference to the chosen action.
     * @param f the EditWindow that the action comes from.
     * @param at string that represents the chosen action.
     */
    public Action(EditWindow f, String at) {
        actionType = at;
        ew = f;
    }
    

    /**
    * Class constructor that takes a reference to the chosen action.
     * @param at string that represents the chosen action.
     */
    public Action(String at) {
        actionType = at;
    }
    
    /**
    * Performs the chosen action.
    * @param e the action event
    */
    @Override
    public void actionPerformed(ActionEvent e) {
        HelpSystem help;
        int dayCardId;
        TaskObject to;
        String tCat;
        Complete tComp;
        Priority tPrio;
        Date tTime;
        String sTimes;
        String sDates;
        String tInfo;
        String tName;
        switch (actionType) {
            case "add": {
                AddWindow frame = new AddWindow(dcw);
                frame.setAlwaysOnTop(true);
                frame.setTitle("Add task");
                frame.pack();
                frame.setVisible(true);
                break;
            }
            case "edit": {
                EditWindow frame = new EditWindow(dcw);
                frame.setAlwaysOnTop(true);
                frame.setTitle("Edit task");
                frame.pack();
                frame.setVisible(true);
                break;
            }
            case "aboutw": {
                AboutWindow frame = new AboutWindow();
                frame.setTitle("About");
                frame.pack();
                frame.setSize(300, 300);
                frame.setVisible(true);
                break;
            }
            case "okabout":
                break;
            case "exit":
                System.exit(0);
            case "remove":

                dcw.updateGUI();
                break;
            case "adde": {
                AddEventWindow frame = new AddEventWindow(dcw);
                frame.setTitle("Add Event");
                frame.pack();
                frame.setVisible(true);
                break;
            }
            case "edite": {
                EditWindow frame = new EditWindow(dcw);
                frame.setAlwaysOnTop(true);
                frame.setTitle("Edit Event");
                frame.pack();
                frame.setVisible(true);
                break;
            }
            case "removee":

                dcw.updateGUI();
                break;
            case "close":
                model.getGlassPane().enableMouseDispatch(true);
                dcw.dispose();
                break;
            case "okaw":
                tName = aw.getTaskName();
                tInfo = aw.getTaskInfo();
                tCat = aw.getTaskCat();
                //Get category from XML
                tempCat = model.getXMLHandler().getCategory(tCat);
                //Gets the selected daycard id
                dayCardId = model.getSelected();
                //Gets the date for that daycard
                dcId = model.getDayCardIdentifiers().getDayCardStuffList();
                for (CardStuffList aDcId1 : dcId) {
                    if (aDcId1.getDayCardId() == dayCardId) {
                        tDate = aDcId1.getDate();
                        break;
                    }
                }
                sTimes = "12:12";
                tComp = new Complete("no");
                tPrio = new Priority(aw.getTaskPrio());
                //tDate = null;
                tTime = null;


            /*try {
                tDate = tdf.parse(sDates);
            }
            catch(Exception es) {
            }*/
                try {
                    tTime = ttf.parse(sTimes);
                } catch (Exception es) {
                    es.printStackTrace();
                }

                to = new TaskObject(tName, tInfo, tDate, tTime,
                        tComp, tPrio, tempCat);
                model.addTask(to);
                aw.dispose();
                dcw.updateGUI();
                System.out.println(".....>" + model.getThisMonthTasks());
                break;
            case "createCat":
                //open the add category window here!
                acw = new AddCatWindow(aw);
                acw.setTitle("Add category");
                acw.setAlwaysOnTop(true);
                acw.pack();
                acw.setVisible(true);
                break;
            case "createECat":
                //open the add category window here!
                acw = new AddCatWindow(aew);
                acw.setAlwaysOnTop(true);
                acw.setTitle("Add category");
                acw.pack();
                acw.setVisible(true);
                break;
            case "saveCat":
                //Add the category name to the list of categories in the AddWindow
                //and to the list of categories in the model
                //

                //Make the CategoryObject that will be saved
                //
                CategoryObject tempCatObj = new CategoryObject(acw.getCatName(),
                        new ImageIcon(""), acw.getColor());

                //Add it to the AddWindow (so you can use it directly) and to the model
                //
                if (b) {
                    aw.addCategory(acw.getCatName());
                } else {
                    aew.addCategory(acw.getCatName());
                }
                model.addCategory(tempCatObj);

                //Put the CategoryObject inside the XML-file (Name and Color mainly)
                //
                model.getXMLHandler().put(tempCatObj);

                //Close the AddCatWindow
                //
                acw.dispose();
                break;
            case "okew":
                tName = ew.getTaskName();
                tInfo = ew.getTaskInfo();
                sDates = "2012-12-12";
                sTimes = "12:12";
                tComp = new Complete("no");
                tPrio = new Priority(ew.getTaskPrio());
                tDate = null;
                tTime = null;
                try {
                    tDate = tdf.parse(sDates);
                } catch (Exception es) {
                    es.printStackTrace();
                }
                try {
                    tTime = ttf.parse(sTimes);
                } catch (Exception es) {
                    es.printStackTrace();
                }
                to = new TaskObject(tName, tInfo, tDate, tTime,
                        tComp, tPrio, tempCat);
                model.addTask(to);
                ew.dispose();
                break;
            case "okaew":
                String eName = aew.getEventName();
                String eInfo = aew.getEventInfo();
                tCat = aew.getEventCat();
                //Get category from XML
                tempCat = model.getXMLHandler().getCategory(tCat);
                //Gets the selected daycard id
                dayCardId = model.getSelected();
                //Gets the date for that daycard
                dcId = model.getDayCardIdentifiers().getDayCardStuffList();
                for (CardStuffList aDcId : dcId) {
                    if (aDcId.getDayCardId() == dayCardId) {
                        tDate = aDcId.getDate();
                        break;
                    }
                }

                try {
                    Date esTime = aew.getEventStartTime();
                    Date eeTime = aew.getEventEndTime();

                    EventObject eo = new EventObject(eName, eInfo, tDate, esTime, tDate, eeTime, tempCat);
                    model.addEvent(eo);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }


                aew.dispose();
                dcw.updateGUI();
                System.out.println(".....>" + model.getThisMonthEvents());
                break;
            case "cancel":
                dcw.dispose();
                break;
            case "cancelaw":
                aw.dispose();
                break;
            case "cancelew":
                ew.dispose();
                break;
            case "cancelaew":
                aew.dispose();
                break;
            case "next":
                model.changeMonth("next");
                break;
            case "previous":
                model.changeMonth("previous");
                break;
            case "undo":
                model.undo();
                break;
            case "redo":
                model.redo();
                break;
            case "daycardw":
                DayCardWindow f = new DayCardWindow(700, 400);
                f.setAlwaysOnTop(true);
                f.setVisible(true);
                //Stops the animation of daycards
                animation.stopAnimatePrio();
                model.deSelectCheckBoxes();
                model.getGlassPane().enableMouseDispatch(false);
                break;
            case "lowP":
                Priority low = new Priority("low");


                //This line should probably be removed later
                if (e != null) {
                    if (e.getActionCommand().equals("start")) {
                        System.out.println("Low prio");
                        ArrayList<Integer> dayCardIdListLow = getDayCardWithTask(low);
                        animation.startAnimatePrio(dayCardIdListLow, low);
                    } else if (e.getActionCommand().equals("stop")) {
                        animation.stopAnimatePrio(low);

                    }
                }
                break;
            case "normalP":
                Priority normal = new Priority("normal");
                if (e != null) {
                    if (e.getActionCommand().equals("start")) {
                        System.out.println("Normal prio");
                        dayCardIdListNormal = getDayCardWithTask(normal);
                        animation.startAnimatePrio(dayCardIdListNormal, normal);
                    } else if (e.getActionCommand().equals("stop")) {
                        animation.stopAnimatePrio(normal);
                        dayCardIdListNormal.clear();
                    }
                }
                break;
            case "highP":
                Priority high = new Priority("high");
                if (e != null) {
                    if (e.getActionCommand().equals("start")) {
                        System.out.println("High prio");
                        dayCardIdListHigh = getDayCardWithTask(high);
                        animation.startAnimatePrio(dayCardIdListHigh, high);
                    } else if (e.getActionCommand().equals("stop")) {
                        animation.stopAnimatePrio(high);
                        dayCardIdListHigh.clear();
                    }
                }

                break;
            case "help":
                help = new HelpSystem();
                model.setHelpSystem(help);
                animation.start(help);
                break;
            case "runInstruction":
                if (animation.getRunningPriority()) {
                    new Action("lowP").actionPerformed(new ActionEvent(this, 0, "stop"));
                }
                animation.runInstruction(true);
                help = model.getHelpSystem();
                help.drawScreenImage(true);
                break;
            case "NEXT":
                help = model.getHelpSystem();
                new Action("next").actionPerformed(e);
                help.drawScreenImage(false);
                break;
            case "PREVIOUS":
                help = model.getHelpSystem();
                new Action("previous").actionPerformed(e);
                help.drawScreenImage(false);
                break;
            case "LOWP":
                help = model.getHelpSystem();
                new Action("lowP").actionPerformed(new ActionEvent(this, 0, "start"));
                help.drawScreenImage(false);
                break;
            case "UNDO":
                help = model.getHelpSystem();
                new Action("undo").actionPerformed(e);
                help.drawScreenImage(false);
                break;
            case "HELP":
                help = model.getHelpSystem();
                new Action("help").actionPerformed(e);
                help.drawScreenImage(false);
                break;
            case "DAYCARDW":
                help = model.getHelpSystem();
                new Action("daycardw").actionPerformed(e);
                help.drawScreenImage(false);
                break;
        }
        
    }
    /**
     * Gets all daycard identifiers with a task of a certain priority for this month.
     * Only one of each id is in the returned list.
     * @param prio The priority to get
     * @return All daycard ids with priority prio for this month
     */    
    private ArrayList<Integer> getDayCardWithTask(Priority prio) {
        ArrayList<Integer> list = new ArrayList<>();
        dcId = model.getDayCardIdentifiers().getDayCardStuffList();
        for (CardStuffList aDcId : dcId) {
            ArrayList<TaskObject> tasks = aDcId.getTasks();
            for (TaskObject task : tasks) {
                System.out.println(task.toString());
                if (task.getPrio().toString().equalsIgnoreCase(prio.toString())) {
                    list.add(aDcId.getDayCardId());
                }
            }
        }
        //Removes all duplicates
        System.out.println("...Size of list....> " + list.size());
        return list;
    }
}

