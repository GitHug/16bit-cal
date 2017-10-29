/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Control.Actions.Action;
import Control.Interface.Drawable;
import Model.CalendarMethods;
import Model.Datatypes.*;
import Model.SixteenBitModel;
import Utils.CustomToolTipManager;
import View.BackgroundComponent;
import View.BitButton;
import View.Checkbox;
import View.DayCard;

import javax.swing.*;
import java.awt.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;

/**
 * Class that initiates a lot of program parameters at startup; 
 * sets the position and size of the components that will be drawn on 
 * the canvas and fills lists with objects needed to process tasks and
 * other things.
 *
 * @author fredrikmakila
 */
class ComponentLoader extends CalendarMethods {

    private final ArrayList<Drawable> list = new ArrayList<>();
    private final int wdHeight = 40;
    private final int mHeight = 50;
    private int wdTotalWidth = 0;
    private int mWidth;
    private int xPos;
    private int yPos;
    private final int month;
    private final int year;
    private final AtomicInteger id = new AtomicInteger();
    private int totalCalendarWidth;
    private final SixteenBitModel model;
    private final Preferences prefs;
    private final String[] weekDay = {
        "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"
    };
    private final DayCardIdentifiers dcId;
    private final DateFormat df = new SimpleDateFormat("yyyy-MM-dd");


    /**
     * Constructor that loads all the components.
     */
    public ComponentLoader() {
        prefs = Preferences.userNodeForPackage(getClass());
        loadResource();
        month = getMonth();
        year = getYear();
        model = SixteenBitModel.getInstance();
        model.setYear(year);
        dcId = new DayCardIdentifiers();
        set();
    }

    /**
     * Method that loads a property file. The String, url, has to be a valid
     * property file name including it's path. A general example is
     * "package.subpackage.propertyfile"
     *
     */
    private void loadResource() {
        prefs.put("resource", "Model.Database.imageResource");
    }

    /**
     * Method that creates all the daycards for a month view
     */
    private void createMonthView() {
        //Creates the weekdays
        int wWidth = 50;
        for (int i = 0; i < weekDay.length; i++) {
            String wd = weekDay[i];
            int wdWidth = 65;
            xPos = i * wdWidth + wWidth;
            yPos = mHeight;
            DayCard wdc = new DayCard(xPos, yPos, wdWidth, wdHeight, new WeekDay(wd).get(), id.incrementAndGet());
            wdc.setBackgroundColor(Color.GREEN);
            wdc.setBorderType(new BorderImage("chain"));
            wdc.setFontSize(8);
            wdc.setSelectable(false);
            model.updateObservableRegistration().addObserver(wdc);
            list.add(wdc);
            dcId.addWeekDayCard(id.intValue());
            wdTotalWidth += wdWidth;
            //adds to paintList
            model.addPaintList(wdc);
        }

        //Creates the month
        xPos = 0;
        yPos = 0;
        mWidth = wWidth + wdTotalWidth;
        DayCard mc = new DayCard(xPos, yPos, mWidth, mHeight, new Month(month).get(), id.incrementAndGet());
        mc.setBackgroundColor(Color.red);
        mc.setBorderType(new BorderImage("tiny"));
        mc.setSelectable(false);
        model.updateObservableRegistration().addObserver(mc);
        model.setMonth(month);
        //model.setMonthCardId(id.get());
        list.add(mc);
        dcId.setMonthCard(id.intValue());
        //adds to paintList
        model.addPaintList(mc);

        //Creates the week numbers
        int week = 0;
        ArrayList array = getWeekNumber(year, month);
        //model.setFirstWeekCardId(id.get() + 1);
        int wHeight = 60;
        for (int i = 0; i < array.size(); i++) {
            week = (Integer) array.get(i);
            xPos = 0;
            yPos = i * wHeight + wdHeight + mHeight;
            DayCard wc = new DayCard(xPos, yPos, wWidth, wHeight, "" + week, id.incrementAndGet());
            wc.setBackgroundColor(Color.YELLOW);
            wc.setBorderType(new BorderImage("chain"));
            wc.setSelectable(false);
            model.updateObservableRegistration().addObserver(wc);
            model.selectedObservableRegistration().addObserver(wc);
            list.add(wc);
            dcId.addWeekCard(id.intValue());
            //adds to paintList
            model.addPaintList(wc);
        }
        //Adds extra week cards if the number of weeks of a month is less than 6. 
        //The reason for this is to being able to edit and make daycards transparent
        //without needing to create new daycards
        if (array.size() < 6) {
            for (int i = array.size(); i < 6; i++) {
                week++;
                yPos = i * wHeight + wdHeight + mHeight;
                DayCard wc = new DayCard(xPos, yPos, wWidth, wHeight, ""+week, id.incrementAndGet());
                wc.setBackgroundColor(Color.YELLOW);
                wc.setBorderType(new BorderImage("chain"));
                wc.setDefaultBorder("chain");
                wc.setSelectable(false);
                model.updateObservableRegistration().addObserver(wc);
                model.selectedObservableRegistration().addObserver(wc);
                list.add(wc);
                dcId.addWeekCard(id.intValue());
                //adds to paintList
                model.addPaintList(wc);
            }
        }
        //model.setLastWeekCardId(id.get());

        //Creates the days in the previous month
        //model.setFirstDayCardId(id.get() + 1);
        int xStart = getWeekDay(year, month);
        int numDays;
        int prevMonth;
        int prevYear;
        if (month - 1 < 0) {
            prevMonth = 11;
            prevYear = year - 1;
        } else {
            prevMonth = month - 1;
            prevYear = year;
        }
        numDays = getNumberOfDays(prevYear, prevMonth);
        numDays = numDays - xStart;
        int dcWidth = 65;
        int dcHeight = 60;
        for (int i = 0; i < xStart; i++) {
            int d = numDays + i + 1;
            xPos = i * dcWidth + wWidth;
            yPos = wdHeight + mHeight;
            DayCard dc = new DayCard(xPos, yPos, dcWidth, dcHeight, "" + d, id.incrementAndGet());
            dc.setBackgroundColor(new Color(0, 200, 255, 100));
            model.selectedObservableRegistration().addObserver(dc);
            model.updateObservableRegistration().addObserver(dc);
            model.animationObservableRegistration().addObserver(dc);
            model.deSelectedObservableRegistration().addObserver(dc);
            dc.setSelectable(false);
            Action dayCardWindow = new Action("daycardw");
            dc.addActionListener(dayCardWindow);
            list.add(dc);
            dcId.addDayCard(new CardStuffList(id.intValue()));
            //adds to paintList
            model.addPaintList(dc);
        }
        //Create a date to be saved in the model
        Date date = null;
        //Creates a convenient string for the date
        String dateString = year+"-"+addChar(month+1)+"-";
        //Creates the days in the current month
        for (int i = xStart; i < getNumberOfDays(year, month) + xStart; i++) {
            int d = i - xStart + 1;
            String tempString = dateString;
            xPos = (i % 7) * dcWidth + wWidth;
            yPos = (i / 7) * dcHeight + wdHeight + mHeight;
            DayCard dc = new DayCard(xPos, yPos, dcWidth, dcHeight, "" + d, id.incrementAndGet());
            if (d == getDay()) {
                dc.setBackgroundColor(Color.BLUE);
            }
            model.selectedObservableRegistration().addObserver(dc);
            model.updateObservableRegistration().addObserver(dc);
            model.animationObservableRegistration().addObserver(dc);
            model.deSelectedObservableRegistration().addObserver(dc);
            dc.setToolTipText("Click on a day to see tasks or add a task");
            ToolTipManager toolTip = ToolTipManager.sharedInstance();
            toolTip.unregisterComponent(dc);
            CustomToolTipManager tool = CustomToolTipManager.sharedInstance();
            tool.registerComponent(dc);
            Action dayCardWindow = new Action("daycardw");
            dc.addActionListener(dayCardWindow);
            list.add(dc);
            tempString = tempString + addChar(d);

            try {
                date = df.parse(tempString);
            } catch (ParseException ex) {
                Logger.getLogger(ComponentLoader.class.getName()).log(Level.SEVERE, null, ex);
            }
            dcId.addDayCard(new CardStuffList(id.intValue(), date));
            //adds to paintList
            model.addPaintList(dc);
        }
        //Next month
        int counter = 0;

        for (int i = (xPos - wWidth) / dcWidth + 1; i < 7; i++) {
            counter++;
            xPos = i * dcWidth + wWidth;
            DayCard dc = new DayCard(xPos, yPos, dcWidth, dcHeight, "" + counter, id.incrementAndGet());
            dc.setBackgroundColor(new Color(255, 200, 0, 100));
            model.selectedObservableRegistration().addObserver(dc);
            model.updateObservableRegistration().addObserver(dc);
            model.deSelectedObservableRegistration().addObserver(dc);
            model.animationObservableRegistration().addObserver(dc);
            dc.setSelectable(false);
            Action dayCardWindow = new Action("daycardw");
            dc.addActionListener(dayCardWindow);
            list.add(dc);
            dcId.addDayCard(new CardStuffList(id.intValue()));
            //adds to paintList
            model.addPaintList(dc);
        }
        //Creates extra daycards if necessary. The reason is to not have to create more
        //daycards later.
        if (id.get() < 55) {
            yPos = yPos + dcHeight;
            for (int i = (xPos - wWidth) / dcWidth + 1 - 7; i < 7; i++) {
                counter++;
                xPos = i * dcWidth + wWidth;
                DayCard dc = new DayCard(xPos, yPos, dcWidth, dcHeight, ""+counter, id.incrementAndGet());
                dc.setBackgroundColor(new Color(255, 200, 0, 100));
                //dc.setBorderType(null);
                dc.setSelectable(false);
                model.selectedObservableRegistration().addObserver(dc);
                model.updateObservableRegistration().addObserver(dc);
                model.deSelectedObservableRegistration().addObserver(dc);
                model.animationObservableRegistration().addObserver(dc);
                dc.setSelectable(false);
                Action dayCardWindow = new Action("daycardw");
                dc.addActionListener(dayCardWindow);
                list.add(dc);
                dcId.addDayCard(new CardStuffList(id.intValue()));
                //adds to paintList
                model.addPaintList(dc);
            }
        }
        //model.setLastDayCardId(id.get());

        int dcTotalWidth = dcWidth * 7;

        totalCalendarWidth = wWidth + dcTotalWidth;

    }

    /**
     * Creates all the components for the menu panel.
     */
    private void createMenu() {
        //Menu starts here
        //Creates the background for the menu
        xPos = totalCalendarWidth;
        yPos = 0;
        int addwWidth = 240;
        int addwHeight = 600;
        BackgroundComponent rgtwindow = new BackgroundComponent(xPos, yPos, addwWidth, addwHeight);
        rgtwindow.setBorderType(new BorderImage("blob"));
        list.add(rgtwindow);
        //adds to paintList
        model.addPaintList(rgtwindow);

        //Adds another backgound
        int boxHeight = 90;
        int boxWidth = 240;
        BackgroundComponent box = new BackgroundComponent(xPos, yPos, boxWidth, boxHeight);
        box.setBackgroundColor(Color.cyan);
        box.setBorderType(new BorderImage("blob"));
        list.add(box);
        //adds to paintList
        model.addPaintList(box);
        
        /*
        //Adds a button with name "Add task"
        yPos = mHeight + wdHeight;
        BitButton add = new BitButton(xPos, yPos, btnWidth, btnHeight, "Add task", id.incrementAndGet());
        add.setBackgroundColor(Color.ORANGE);
        add.setBorderType(new BorderImage("blob"));
        list.add(add);
        //adds to paintList
        model.addPaintList(add);

        //Adds a button with name "Edit task"
        yPos = mHeight + wdHeight + btnHeight;
        BitButton edit = new BitButton(xPos, yPos, btnWidth, btnHeight, "Edit task", id.incrementAndGet());
        edit.setBackgroundColor(Color.ORANGE);
        edit.setBorderType(new BorderImage("blob"));
        list.add(edit);
        //adds to paintList
        model.addPaintList(edit);
        
        */
        /*
        //Adds a button with name "Low prio" 
        yPos = mHeight + wdHeight;
        BitButton lowP = new BitButton(xPos, yPos, btnWidth, btnHeight, "Low Prio", id.incrementAndGet());
        lowP.setBackgroundColor(Color.ORANGE);
        lowP.setBorderType(new BorderImage("blob"));
        model.selectedObservableRegistration().addObserver(lowP);
        model.deSelectedObservableRegistration().addObserver(lowP);
        lowP.addActionListener(new Action("lowP"));
        list.add(lowP);
        //adds to paintList
        model.addPaintList(lowP);
        
        //Adds a button with name "Normal Prio" 
        yPos = mHeight + wdHeight + btnHeight;
        BitButton normalP = new BitButton(xPos, yPos, btnWidth, btnHeight, "Normal Prio", id.incrementAndGet());
        normalP.setBackgroundColor(Color.ORANGE);
        normalP.setBorderType(new BorderImage("blob"));
        model.selectedObservableRegistration().addObserver(normalP);
        model.deSelectedObservableRegistration().addObserver(normalP);
        normalP.addActionListener(new Action("normalP"));
        list.add(normalP);
        //adds to paintList
        model.addPaintList(normalP);
        
        //Adds a button with name "High Prio" 
        yPos = mHeight + wdHeight + btnHeight+btnHeight;
        BitButton highP = new BitButton(xPos, yPos, btnWidth, btnHeight, "High Prio", id.incrementAndGet());
        highP.setBackgroundColor(Color.ORANGE);
        highP.setBorderType(new BorderImage("blob"));
        model.selectedObservableRegistration().addObserver(highP);
        model.deSelectedObservableRegistration().addObserver(highP);
        highP.addActionListener(new Action("highP"));
        list.add(highP);
        //adds to paintList
        model.addPaintList(highP);*/
        
        //Adds a Checkbox with name "Low Prio"
        int btnWidth = 120;
        xPos = xPos + btnWidth - 50;
        yPos = mHeight + wdHeight + 12;
        Checkbox lowPCheck = new Checkbox(xPos, yPos, "Low Prio", id.incrementAndGet());
        lowPCheck.setBackgroundColor(Color.GREEN);
        lowPCheck.setToolTipText("Shows the tasks with low priority");
        ToolTipManager toolTip = ToolTipManager.sharedInstance();
        toolTip.unregisterComponent(lowPCheck);
        CustomToolTipManager tool = CustomToolTipManager.sharedInstance();
        tool.registerComponent(lowPCheck);
        model.selectedObservableRegistration().addObserver(lowPCheck);
        model.deSelectedObservableRegistration().addObserver(lowPCheck);
        model.addToCheckboxList(lowPCheck);
        lowPCheck.addActionListener(new Action("lowP"));
        list.add(lowPCheck);
        //adds to paintList
        model.addPaintList(lowPCheck);
        
        //Adds a Checkbox with name "Med Prio"
        //xPos = xPos + btnWidth;
        int btnHeight = 60;
        yPos = yPos + btnHeight - 20;
        Checkbox medPCheck = new Checkbox(xPos, yPos, "Med Prio", id.incrementAndGet());
        medPCheck.setBackgroundColor(new Color(50, 100, 255));
        medPCheck.setToolTipText("Shows the tasks with medium priority");
        ToolTipManager.sharedInstance();
        toolTip.unregisterComponent(medPCheck);
        tool.registerComponent(medPCheck);
        model.selectedObservableRegistration().addObserver(medPCheck);
        model.deSelectedObservableRegistration().addObserver(medPCheck);
        model.addToCheckboxList(medPCheck);
        medPCheck.addActionListener(new Action("normalP"));
        list.add(medPCheck);
        //adds to paintList
        model.addPaintList(medPCheck);
        
        //Adds a Checkbox with name "High Prio"
        //xPos = xPos + btnWidth;
        yPos = yPos + btnHeight - 20;
        Checkbox highPCheck = new Checkbox(xPos, yPos, "High Prio", id.incrementAndGet());
        highPCheck.setBackgroundColor(Color.RED);
        highPCheck.setToolTipText("Shows the tasks with high priority");
        ToolTipManager.sharedInstance();
        toolTip.unregisterComponent(highPCheck);
        tool.registerComponent(highPCheck);
        model.selectedObservableRegistration().addObserver(highPCheck);
        model.deSelectedObservableRegistration().addObserver(highPCheck);
        model.addToCheckboxList(highPCheck);
        highPCheck.addActionListener(new Action("highP"));
        list.add(highPCheck);
        //adds to paintList
        model.addPaintList(highPCheck); 
        
        //Adds a button with name "Undo"
        xPos = xPos + 50;
        yPos = mHeight + wdHeight;
        BitButton undo = new BitButton(xPos, yPos, btnWidth, btnHeight, "Undo", id.incrementAndGet());
        undo.setBackgroundColor(Color.BLUE);
        undo.setBorderType(new BorderImage("blob"));
        undo.setToolTipText("Undoes the recent action");
        ToolTipManager.sharedInstance();
        toolTip.unregisterComponent(undo);
        tool.registerComponent(undo);
        model.selectedObservableRegistration().addObserver(undo);
        model.deSelectedObservableRegistration().addObserver(undo);
        undo.addActionListener(new Action("undo"));
        list.add(undo);
        //adds to paintList
        model.addPaintList(undo);
        
        //Adds a button with name "Redo"
        yPos = mHeight + wdHeight + btnHeight;
        BitButton redo = new BitButton(xPos, yPos, btnWidth, btnHeight, "Redo", id.incrementAndGet());
        redo.setToolTipText("Redoes an action");
        ToolTipManager.sharedInstance();
        toolTip.unregisterComponent(redo);
        tool.registerComponent(redo);
        redo.setBackgroundColor(Color.BLUE);
        redo.setBorderType(new BorderImage("blob"));
        model.selectedObservableRegistration().addObserver(redo);
        model.deSelectedObservableRegistration().addObserver(redo);
        redo.addActionListener(new Action("redo"));
        list.add(redo);
        //adds to paintList
        model.addPaintList(redo);
        
        //Adds a button for the help system
        yPos = addwHeight - btnHeight *2;
        xPos = xPos + btnWidth /2;
        BitButton help = new BitButton(xPos, yPos, btnWidth /2, btnHeight, "Help", id.incrementAndGet());
        help.setBackgroundColor(Color.PINK);
        help.setBorderType(new BorderImage("blob"));
        help.setToolTipText("Starts the guide that will show you how to use the application");
        ToolTipManager.sharedInstance();
        toolTip.unregisterComponent(help);
        tool.registerComponent(help);
        model.selectedObservableRegistration().addObserver(help);
        model.deSelectedObservableRegistration().addObserver(help);
        help.addActionListener(new Action("help"));
        list.add(help);
        //adds to paintList
        model.addPaintList(help);

        
        
/*
        //Adds another backgound where tasks and events can be displayed
        xPos = totalCalendarWidth;
        yPos = mHeight + wdHeight + 2 * btnHeight;
        BackgroundComponent taskinfo = new BackgroundComponent(xPos, yPos, btnWidth * 2, wdHeight);
        taskinfo.setBackgroundColor(Color.cyan);
        taskinfo.setBorderType(new BorderImage("blob"));
        list.add(taskinfo);
        //adds to paintList
        model.addPaintList(taskinfo);

        //Adds an infobox
        yPos = mHeight + 2 * wdHeight + 2 * btnHeight;
        BackgroundComponent infobox = new BackgroundComponent(xPos, yPos, boxWidth, taskinfoHeight);
        infobox.setBackgroundColor(Color.WHITE);
        infobox.setBorderType(new BorderImage("blob"));
        list.add(infobox);
        //adds to paintList
        model.addPaintList(infobox);
*/
        //Adds a button in the form of an arrow
        BitButton backArrow = new BitButton(0, 0, "back_arrow", id.incrementAndGet());
        model.selectedObservableRegistration().addObserver(backArrow);
        model.deSelectedObservableRegistration().addObserver(backArrow);
        Action prevAction = new Action("previous");
        backArrow.setToolTipText("Go to the previous month");
        ToolTipManager.sharedInstance();
        toolTip.unregisterComponent(backArrow);
        tool.registerComponent(backArrow);
        backArrow.addActionListener(prevAction);
        list.add(backArrow);
        //adds to paintList
        model.addPaintList(backArrow);

        //Adds another button in the form of an arrow
        BitButton forwardArrow = new BitButton(mWidth - backArrow.getSize().width, 0, "forward_arrow", id.incrementAndGet());
        model.selectedObservableRegistration().addObserver(forwardArrow);
        model.deSelectedObservableRegistration().addObserver(forwardArrow);
        Action nextAction = new Action("next");
        forwardArrow.setToolTipText("Go to the next month");
        ToolTipManager.sharedInstance();
        toolTip.unregisterComponent(forwardArrow);
        tool.registerComponent(forwardArrow);
        forwardArrow.addActionListener(nextAction);
        list.add(forwardArrow);
        //adds to paintList
        model.addPaintList(forwardArrow);
        
        
    }

    
    
    private void fillModelWithDayCards() {
        ArrayList<DayCard> tempDayList = new ArrayList<>();
        for (Drawable aList : list) {
            if (aList instanceof DayCard) {
                tempDayList.add((DayCard) aList);
            }
        }
        model.setDayCardList(tempDayList);
        model.setDayCardIdentifiers(dcId);
    }

    /**
     * Method to load components, called by the constructor.
     */
    private void set() {
        createMonthView();
        //createGraphics();
        createMenu();
        fillModelWithDayCards();
    }

    /**
     * Converts an integer to a string and adds an extra character 0 if
     * the integer is between 0 and 9 to make the total number to two.
     * @param integer int
     * @return string representation of int
     */
    private String addChar(int integer) {
        String string;
        if(integer < 10) {
            string = "0"+integer;
        }
        else string = ""+integer;
        return string;
    }
}
