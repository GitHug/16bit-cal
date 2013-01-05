
package Control.Actions;

import Control.AnimationEngine;
import Control.HelpSystem;
import Model.Datatypes.*;
import Model.SixteenBitModel;
import Utils.Debug;
import View.*;
import java.awt.event.ActionEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

/**
 * Class that handles different action depending on in parameters
 * @author Robert
 */
public class Action extends AbstractAction {
    private String actionType;
    private DayCardWindow dcw;
    private AddWindow aw;
    private AddEventWindow aew;
    private EditWindow ew;
    private AddCatWindow acw;
    private AboutWindow about;
    private DateFormat tdf = new SimpleDateFormat("yyyy-MM-dd");
    private DateFormat ttf = new SimpleDateFormat("HH:mm");
    private String tName;
    private String tInfo;
    private String sDates;
    private String sTimes;        
    private Date tDate;
    private Date tTime;
    private Priority tPrio;
    private Complete tComp;
    private String tCat;
    private TaskObject to;
    private CategoryObject tempCat;
    private String eName; 
    private String eInfo;
    private Date esTime;
    private Date eeTime;
    private EventObject eo;
    private SixteenBitModel model = SixteenBitModel.getInstance();
    private int dayCardId;
    private ArrayList<CardStuffList> dcId;
    private AnimationEngine animation = model.getAnimationEngine();
    private ArrayList<Integer> dayCardIdListLow;
    private ArrayList<Integer> dayCardIdListNormal;
    private ArrayList<Integer> dayCardIdListHigh;
    private HelpSystem help;
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
     * @param dc the current DayCardWindow.
     * @param a The addwindow
     * @param f the AddWindow that the action comes from.
     * @param at string that represents the chosen action.
     */
    public Action(DayCardWindow dc, AddWindow a, AddCatWindow f, String at) {
        actionType = at;
        dcw = dc;
        aw = a;
        acw = f;
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
     * @param bit The bitbutton that the action comes from. 
     * @param at string that represents the chosen action.
     */
    public Action(BitButton bit, String at) {
        actionType = at;
    }
    
    /**
    * Class constructor that takes a reference to the chosen action.
     * @param at string that represents the chosen action.
     */
    public Action(String at) {
        actionType = at;
    }
    
    /**
    * Class constructor that takes a reference to the chosen action.
     * @param dc The daycard that the action comes from. 
     * @param at string that represents the chosen action.
     */
    public Action(DayCard dc, String at) {
        actionType = at;
    }
    
        /**
    * Class constructor that takes a reference to the chosen action.
     * @param abw The About Window that the action comes from. 
     * @param at string that represents the chosen action.
     */
    public Action(AboutWindow abw, String at) {
        actionType = at;
    }
    
 
    
    /**
    * Performs the chosen action.
    * @param e the action event
    */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (actionType.equals("add")) {
            AddWindow frame = new AddWindow(dcw);
            frame.setAlwaysOnTop(true);
            frame.setTitle("Add task");
            frame.pack();
            frame.setVisible(true);
        }
        
        else if (actionType.equals("edit")) {
            EditWindow frame = new EditWindow(dcw);
            frame.setAlwaysOnTop(true);
            frame.setTitle("Edit task");
            frame.pack();
            frame.setVisible(true);
        }
        
         else if (actionType.equals("aboutw")) {
            AboutWindow frame = new AboutWindow();
            frame.setTitle("About");
            frame.pack();
            frame.setSize(300,300);
            frame.setVisible(true);
        }
         
         else if (actionType.equals("okabout")){
            about.dispose();
        }        
         
        else if (actionType.equals("exit")) {
            System.exit(0);
        }
        
        
        else if (actionType.equals("remove")) {
            
            dcw.updateGUI();
        }
        
        else if (actionType.equals("adde")) {
            AddEventWindow frame = new AddEventWindow(dcw);
            frame.setTitle("Add Event");
            frame.pack();
            frame.setVisible(true);
        }
        
        else if (actionType.equals("edite")) {
            EditWindow frame = new EditWindow(dcw);
            frame.setAlwaysOnTop(true);
            frame.setTitle("Edit Event");
            frame.pack();
            frame.setVisible(true);
        }
        
        else if (actionType.equals("removee")) {
            
            dcw.updateGUI();
        }
        
        else if (actionType.equals("close")) {
            model.getGlassPane().enableMouseDispatch(true);
            dcw.dispose();
        }
        
        
        else if (actionType.equals("okaw")) {
            tName = aw.getTaskName();   
            tInfo = aw.getTaskInfo();
            tCat = aw.getTaskCat();
            //Get category from XML
            tempCat = model.getXMLHandler().getCategory(tCat);
            //Gets the selected daycard id
            dayCardId = model.getSelected();
            //Gets the date for that daycard
            dcId = model.getDayCardIdentifiers().getDayCardStuffList();
            for(int i = 0; i < dcId.size(); i++) {
                if(dcId.get(i).getDayCardId() == dayCardId) {
                    tDate = dcId.get(i).getDate();
                    break;
                }
            }
            sDates = "2012-12-12";
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
            } 
            catch(Exception es) {
            }
            
            to = new TaskObject(tName, tInfo, tDate, tTime, 
            tComp, tPrio, tempCat);
            model.addTask(to);
            aw.dispose();
            dcw.updateGUI();
            System.out.println(".....>" + model.getThisMonthTasks());
        }
        
        else if (actionType.equals("createCat")) {
            //open the add category window here!
            acw = new AddCatWindow(aw);
            acw.setTitle("Add category");
            acw.setAlwaysOnTop(true);
            acw.pack();
            acw.setVisible(true);
        }
        
        else if (actionType.equals("createECat")) {
            //open the add category window here!
            acw = new AddCatWindow(aew);
            acw.setAlwaysOnTop(true);
            acw.setTitle("Add category");
            acw.pack();
            acw.setVisible(true);
        } 
        
        else if (actionType.equals("saveCat")) {
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
            }
            else {
                aew.addCategory(acw.getCatName());                
            }
            model.addCategory(tempCatObj);
            CategoryObject tempObj = tempCatObj;
            
            //Put the CategoryObject inside the XML-file (Name and Color mainly)
            //
            model.getXMLHandler().put(tempObj);
            
            //Close the AddCatWindow
            //
            acw.dispose();
        }
            
        
        else if (actionType.equals("okew")) {
            tName = ew.getTaskName();   
            tInfo = ew.getTaskInfo();
            sDates = "2012-12-12";
            sTimes = "12:12";
            tComp = new Complete("no");
            tPrio = new Priority(ew.getTaskPrio());
            tCat = "Standard";
            tDate = null;
            tTime = null;
            try { 
                tDate = tdf.parse(sDates);
            } 
            catch(Exception es) {
            }
            try { 
                tTime = ttf.parse(sTimes);
            } 
            catch(Exception es) {
            }
            to = new TaskObject(tName, tInfo, tDate, tTime, 
            tComp, tPrio, tempCat);
            model.addTask(to);
            ew.dispose();
        }
        
        else if (actionType.equals("okaew")) {
            eName = aew.getEventName();   
            eInfo = aew.getEventInfo();
            tCat = aew.getEventCat();
            //Get category from XML
            tempCat = model.getXMLHandler().getCategory(tCat);
            //Gets the selected daycard id
            dayCardId = model.getSelected();
            //Gets the date for that daycard
            dcId = model.getDayCardIdentifiers().getDayCardStuffList();
            for(int i = 0; i < dcId.size(); i++) {
                if(dcId.get(i).getDayCardId() == dayCardId) {
                    tDate = dcId.get(i).getDate();
                    break;
                }
            }
            
            esTime = aew.getEventStartTime();
            eeTime = aew.getEventEndTime();
            
            eo = new EventObject(eName, eInfo, tDate, esTime, tDate, eeTime, tempCat);
            model.addEvent(eo);
            aew.dispose();
            dcw.updateGUI();
            System.out.println(".....>" + model.getThisMonthEvents());
        }
        
        else if (actionType.equals("editaew")) {
            
        }
        
        else if (actionType.equals("cancel")) {
            dcw.dispose();
        }
        
        else if (actionType.equals("cancelaw")) {
            aw.dispose();
        }
        
        else if (actionType.equals("cancelew")) {
            ew.dispose();
        }
        
        else if (actionType.equals("cancelaew")) {
            aew.dispose();
        }
        
        else if(actionType.equals("next")) {
            model.changeMonth("next");
        }
        
        else if(actionType.equals("previous")) {
            model.changeMonth("previous");
        }
        
        else if (actionType.equals("undo")) {
            model.undo();
        }
        
        else if (actionType.equals("redo")) {
            model.redo();
        }
        
        else if(actionType.equals("daycardw")) {
            DayCardWindow f = new DayCardWindow(700, 400);
            f.setAlwaysOnTop(true);
            f.setVisible(true);
            //Stops the animation of daycards
            animation.stopAnimatePrio();
            model.deSelectCheckBoxes();
            model.getGlassPane().enableMouseDispatch(false);
        }
        else if(actionType.equals("lowP")) {
            Priority low = new Priority("low");
            
            
            //This line should probably be removed later
            if(e != null) {
                if(e.getActionCommand().equals("start")) {
                    System.out.println("Low prio");
                    dayCardIdListLow = getDayCardWithTask(low);
                    animation.startAnimatePrio(dayCardIdListLow, low);
                }
                else if(e.getActionCommand().equals("stop")) {
                    animation.stopAnimatePrio(low);
                                        
                }
            }
        }
        else if(actionType.equals("normalP")) {
            Priority normal = new Priority("normal");
            if(e != null) {
                if(e.getActionCommand().equals("start")) {
                    System.out.println("Normal prio");
                    dayCardIdListNormal = getDayCardWithTask(normal);
                    animation.startAnimatePrio(dayCardIdListNormal, normal);
                }
                else if (e.getActionCommand().equals("stop")) {
                    animation.stopAnimatePrio(normal);
                    dayCardIdListNormal.clear();
                }
            }
        }
        else if(actionType.equals("highP")) {
            Priority high = new Priority("high");
            if(e != null) {
                if(e.getActionCommand().equals("start")) {
                    System.out.println("High prio");
                    dayCardIdListHigh = getDayCardWithTask(high);
                    animation.startAnimatePrio(dayCardIdListHigh, high);
                }
                else if (e.getActionCommand().equals("stop")) {
                    animation.stopAnimatePrio(high);
                    dayCardIdListHigh.clear();
                }
            }
            
        }
        else if(actionType.equals("help")) {
            help = new HelpSystem();
            model.setHelpSystem(help);
            animation.start(help);
        }
        else if(actionType.equals("runInstruction")) {
            if(animation.getRunningPriority()) {
                new Action("lowP").actionPerformed(new ActionEvent(this, 0, "stop"));
            }   
            animation.runInstruction(true);
            help = model.getHelpSystem();
            help.drawScreenImage(true);
        }
        else if(actionType.equals("NEXT")) {
            help = model.getHelpSystem();
            new Action("next").actionPerformed(e);
            help.drawScreenImage(false);   
        }
        else if(actionType.equals("PREVIOUS")) {
            help = model.getHelpSystem();
            new Action("previous").actionPerformed(e);
            help.drawScreenImage(false);  
        }
        else if(actionType.equals("LOWP")) {
            help = model.getHelpSystem();
            new Action("lowP").actionPerformed(new ActionEvent(this, 0, "start"));
            help.drawScreenImage(false); 
        }
        else if(actionType.equals("UNDO")) {
            help = model.getHelpSystem();
            new Action("undo").actionPerformed(e);
            help.drawScreenImage(false);  
        }
        else if(actionType.equals("HELP")) {
            help = model.getHelpSystem();
            new Action("help").actionPerformed(e);
            help.drawScreenImage(false);  
        }
        else if(actionType.equals("DAYCARDW")) {
            help = model.getHelpSystem();
            new Action("daycardw").actionPerformed(e);
            help.drawScreenImage(false);  
        }
        
    }
    /**
     * Gets all daycard identifiers with a task of a certain priority for this month.
     * Only one of each id is in the returned list.
     * @param prio The priority to get
     * @return All daycard ids with priority prio for this month
     */    
    private ArrayList<Integer> getDayCardWithTask(Priority prio) {
        ArrayList<Integer> list = new ArrayList<Integer>();
        dcId = model.getDayCardIdentifiers().getDayCardStuffList();
        for(int i = 0; i < dcId.size(); i++) {
            ArrayList<TaskObject> tasks = dcId.get(i).getTasks();
            for(int j = 0; j < tasks.size(); j++) {
                System.out.println(tasks.get(j).toString());
                if(tasks.get(j).getPrio().toString().equalsIgnoreCase(prio.toString())) {
                    list.add(dcId.get(i).getDayCardId());
                }
            }
        }
        //Removes all duplicates
        System.out.println("...Size of list....> " + list.size());
        return list;
    }
}

