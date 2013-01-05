/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Control.Actions.AnimationEvent;
import Model.Datatypes.Instruction;
import Model.Datatypes.Priority;
import Model.SixteenBitModel;
import View.CustomComponent;
import View.DayCard;
import View.MyGlassPane;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * This class handles all the animations that occur in this application. It uses
 * a timer and a scheduled timer task to achieve this.
 *
 * @author fredrikmakila
 */
public class AnimationEngine {

    //The model
    private final SixteenBitModel model;
    //The glass pane
    private MyGlassPane myGlassPane;
    //The timer used for the animations
    private Timer timer;
    //The animation timer task class
    private Animation graphicAnimation;
    //The alpha value used for flashing a daycard when hovering on it
    private int alpha = 0;
    //The color for a daycard containing a low priority task
    private Color lowP = new Color(0, 255, 0); //Green
    //The color for a daycard containing a low priority task
    private Color normalP = new Color(50, 100, 255); //Blue
    //The color for a daycard containing a low priority task
    private Color highP = new Color(255, 0, 100); //Red
    //A variable that tells the animation task to make a daycard flash by changin its alpha value
    private boolean pulsating = false;
    //A boolean that stops or starts the animation of flashing tasks
    private boolean pulseComponents = false;
    //Lists that contains the id for daycards with low, normal or high priority tasks respectively
    private ArrayList<Integer> componentPrioLow = new ArrayList<Integer>();
    private ArrayList<Integer> componentPrioNormal = new ArrayList<Integer>();
    private ArrayList<Integer> componentPrioHigh = new ArrayList<Integer>();
    //Lists that contains the events for low, normal or high priority tasks
    private ArrayList<AnimationEvent> eventListLow = new ArrayList<AnimationEvent>();
    private ArrayList<AnimationEvent> eventListNormal = new ArrayList<AnimationEvent>();
    private ArrayList<AnimationEvent> eventListHigh = new ArrayList<AnimationEvent>();
    //The id of the daycard that the mouse is hovered on
    private int id;
    //A list containing all the daycards
    private ArrayList<DayCard> dayCardList;
    //Just a general color variable that might be nice to have
    private Color color;
    //The offset for the alpha value in each itereation of the animation timer task class for a daycard that the mouse is hovered on
    private int offset = 5;
    //This tells if the offset should be positive or negative.
    private int increase = 1;
    //This does the same for the low priorities
    private int increaseLow = 1;
    //The alpha value for low priorities
    private int alphaLow = 0;
    //increase value for normal priorities
    private int increaseNormal = 1;
    //alpha value for normal priorities
    private int alphaNormal = 0;
    //increase value for high priorities
    private int increaseHigh = 1;
    //alpha value for high priorities
    private int alphaHigh = 0;
    //Tells whether the animation for daycards containing low, normal or high priority respectively is running or not
    private boolean currentlyRunningPriorityLow = false;
    private boolean currentlyRunningPriorityNormal = false;
    private boolean currentlyRunningPriorityHigh = false;
    //This is the component that the mouse is hovered upon and thus will flash
    private CustomComponent animateComponent;
    //List with instructions for the helpsystem
    private ArrayList<Instruction> instructionList;
    //Should the instruction run or not
    private boolean runInstructions = false;
    //Counter for instructions
    private int counter = 0;
    //The canvas
    private Canvas canvas;

    /**
     * Only constructor for this class. Starts the timer and schedule the
     * animation timer task class.
     *
     * @see Animation
     */
    public AnimationEngine() {
        model = SixteenBitModel.getInstance();
        myGlassPane = model.getGlassPane();
        graphicAnimation = new Animation();
        //Init timer
        timer = new Timer();
        canvas = model.getCanvas();
        //Schedule animation task
        RepainterAction repainterAction = new RepainterAction();
        timer.schedule(graphicAnimation, 0, //initial delay
                10); //subsequent rate
        javax.swing.Timer swingTimer = new javax.swing.Timer(15, repainterAction);
        swingTimer.start();
    }

    /**
     * Starts the animation of a daycard to make it pulsate. This animation
     * occurs whenever the mouse is on top a daycard that has been told to
     * animate
     *
     * @param id The id of the daycard that will pulsate
     */
    public void start(int id) {
        this.id = id;
        //If the daycard is not already pulsating
        if (!pulsating) {
            for (int i = 0; i < dayCardList.size(); i++) {
                //gets the correct daycard
                if (dayCardList.get(i).getId() == id) {
                    animateComponent = dayCardList.get(i);
                    break;
                }
            }
            //Tells the animation task to animate the daycard
            pulsating = true;
        }

    }

    /**
     * Starts the helpsystem. It receives a list of instructions from the help
     * system and then process each instruction at scheduled intervals.
     *
     * @param help The help system of this application
     */
    public void start(HelpSystem help) {
        help.start();
        instructionList = help.getInstructionList();
        runInstructions = true;
        counter = 0;
    }

    /**
     * Stops the help system. Makes sure no more instructions are processed in
     * the scheduled timer task and resets the counter
     *
     * @param help The help system for this application.
     */
    public void stop(HelpSystem help) {
        runInstructions = false;
        counter = 0;
    }

    /**
     * Sets whether to start processing instructions or not.
     *
     * @param runInstructions True to start processing instructions, otherwise
     * false.
     */
    public void runInstruction(boolean runInstructions) {
        this.runInstructions = runInstructions;
    }

    /**
     * Stop the animation of daycard. A pulsating daycard will stop. This
     * typically occurs when moving from a daycard with the mouse.
     */
    public void stop() {
        pulsating = false;
        alpha = 0;
        model.animateDayCard(id, false, alpha);
    }

    /**
     * All daycards that contains a Priority prio will start to flash. The color
     * depends on the priority of the tasks. This method allows the user to make
     * more than one type of priority task flash at the same time. If there are
     * more than one task on the same day but with different priority, then the
     * color that will flash will be of the highest priority.
     *
     * @param ids A list of daycard identifiers containing tasks with priority
     * prio
     * @param prio The priority of the tasks that will make the daycards flash
     */
    public void startAnimatePrio(ArrayList<Integer> ids, Priority prio) {
        //Checks if the list is empy or not. If it's empty, nothing will happen as there are no
        //daycard identifiers to work with
        if (ids.size() > 0) {
            AnimationEvent event;
            //checks the priority
            if (prio.equals(Priority.LOW)) {
                //Sets the color to the low priority color
                color = lowP;
                //Adds all identifiers to the low priority component list, which will be empty anyway from the start
                componentPrioLow.addAll(ids);
                //iterates through the identifier list
                for (int i = 0; i < componentPrioLow.size(); i++) {
                    //creates the animation event
                    event = new AnimationEvent(componentPrioLow.get(i), true, prio);
                    //Adds a color to the event
                    event.setColor(color);
                    //Adds the event to the event list so it later can be animatated
                    eventListLow.add(event);
                    //Adds the daycards to the repaint list for low priorities so it can be repainted
                   
                }
                //Indicates that the low priority is currently running
                currentlyRunningPriorityLow = true;

                //For inline comments, please check above. It's basically the same thing
            } else if (prio.equals(Priority.NORMAL)) {
                color = normalP;
                componentPrioNormal.addAll(ids);
                for (int i = 0; i < componentPrioNormal.size(); i++) {
                    event = new AnimationEvent(componentPrioNormal.get(i), true, prio);
                    event.setColor(color);
                    eventListNormal.add(event);
                    
                }
                currentlyRunningPriorityNormal = true;
            } else {
                color = highP;
                componentPrioHigh.addAll(ids);
                for (int i = 0; i < componentPrioHigh.size(); i++) {
                    event = new AnimationEvent(componentPrioHigh.get(i), true, prio);
                    event.setColor(color);
                    eventListHigh.add(event);
                    
                }
                currentlyRunningPriorityHigh = true;

            }

            //Indicates that some animation will occur in the animation class
            pulseComponents = true;
        }
    }

    /**
     * Stops all animation of daycards that is flashing due to them having a
     * certain priority. Resets all values that is related to this animation.
     */
    public void stopAnimatePrio() {
        int alphaValue = 0;
        pulseComponents = false;
        currentlyRunningPriorityLow = false;
        currentlyRunningPriorityNormal = false;
        currentlyRunningPriorityHigh = false;
        //Tells daycards to reset so the they return to their default color again
        for (int i = 0; i < componentPrioLow.size(); i++) {
            model.animateDayCard(componentPrioLow.get(i), false, alphaValue);
        }
        for (int i = 0; i < componentPrioNormal.size(); i++) {
            model.animateDayCard(componentPrioNormal.get(i), false, alphaValue);
        }
        for (int i = 0; i < componentPrioHigh.size(); i++) {
            model.animateDayCard(componentPrioHigh.get(i), false, alphaValue);
        }

        componentPrioLow.clear();
        componentPrioNormal.clear();
        componentPrioHigh.clear();
        eventListLow.clear();
        eventListNormal.clear();
        eventListHigh.clear();
        alphaLow = 0;
        increaseLow = 1;
        alphaNormal = 0;
        increaseNormal = 1;
        alphaHigh = 0;
        increaseHigh = 1;

    }

    /**
     * Stops the animation of daycards for a certain priority. This will cause
     * daycards of priority prio to stop flashing if they are currently
     * flashing. Stopping one kind of priority will not cause the other kind of
     * prioritie daycards to stop flashing.
     *
     * @param prio The priority for which the daycards should stop flashing
     */
    public void stopAnimatePrio(Priority prio) {
        //A reset alpha value
        int alphaValue = 0;
        //Checks the priority and if it's currently running
        if (prio.equals(Priority.LOW) && currentlyRunningPriorityLow) {
            //Indicates that the low priority animation is no longer running
            currentlyRunningPriorityLow = false;
            //Tells the animated daycards to return to their default color
            for (int i = 0; i < componentPrioLow.size(); i++) {
                model.animateDayCard(componentPrioLow.get(i), false, alphaValue);
            }
            //Resets the id list containing the low priorities
            componentPrioLow.clear();
            //Resets the event list
            eventListLow.clear();
            //Resets the alpha value for low priorities
            alphaLow = 0;
            //Resets the increase value for low priorities
            increaseLow = 1;
        }
        //For inline comments check above. It's basically the same thing all over again.
        if (prio.equals(Priority.NORMAL) && currentlyRunningPriorityNormal) {
            currentlyRunningPriorityNormal = false;
            for (int i = 0; i < componentPrioNormal.size(); i++) {
                model.animateDayCard(componentPrioNormal.get(i), false, alphaValue);
            }
            componentPrioNormal.clear();
            eventListNormal.clear();
            alphaNormal = 0;
            increaseNormal = 1;
        }
        if (prio.equals(Priority.HIGH) && currentlyRunningPriorityHigh) {
            currentlyRunningPriorityHigh = false;
            for (int i = 0; i < componentPrioHigh.size(); i++) {
                model.animateDayCard(componentPrioHigh.get(i), false, alphaValue);
            }
            componentPrioHigh.clear();
            eventListHigh.clear();
            alphaHigh = 0;
            increaseHigh = 1;
        }
        //If no daycards are no longer flashing, tell the animation to stop completely. Might not
        //be completely necessary as it should already have stopped.
        if (!currentlyRunningPriorityLow && !currentlyRunningPriorityNormal && !currentlyRunningPriorityHigh) {
            pulseComponents = false;
        }

    }

    /**
     * Sets the glasspane for the animation engine. The name might be
     * missleading but this is a way for the animation engine to get access to
     * the glasspane
     *
     * @param myGlassPane
     */
    public void setGlassPane(MyGlassPane myGlassPane) {
        this.myGlassPane = myGlassPane;
    }

    /**
     * Animation timer task class that does the actual animation of daycards.
     */
    private class Animation extends TimerTask {

        //The dimension of the components
        private Dimension d;
        //The location of the components.
        private Point p;
        //The color for the daycard
        private Color dcColor;

        /**
         * This is the method that runs whenever the timer has fired.
         */
        @Override
        public void run() {


            //Checks if the component should pulsate due to the mouse being over it or not
            if (pulsating && animateComponent != null) {
                //Changes the alpha value. The alpha value will go increase with a certain offset
                //each time. When the alpha value reaches 255, it will start to decrease with the same offset
                //until the alpha value is 0. This is then repeated as long as the animation is running.
                if ((alpha + offset > 255 && increase > 0) || (alpha - offset < 0 && increase < 0)) {
                    increase = increase * -1;
                }
                //Change the actual alpha value
                alpha = alpha + offset * increase;
                //Change the color of the daycard by changing its alpha value
                model.animateDayCard(id, true, alpha);
                //Gets the drawing area of the daycard
                p = animateComponent.getOffsetLocation();
                //Gets the size
                d = animateComponent.getSize();
                //Repaint the daycard
            }

            if (pulseComponents) {
                //Animate daycards with low priority tasks
                if (currentlyRunningPriorityLow) {
                    //Change the alpha value in a fashion similar to that of the above
                    if ((alphaLow + offset > 255 && increaseLow > 0) || (alphaLow - offset < 0 && increaseLow < 0)) {
                        increaseLow = increaseLow * -1;
                    }
                    alphaLow = alphaLow + offset * increaseLow;
                    //Go through the list of low priority events and change their color by adding the new
                    //alpha value
                    for (int i = 0; i < eventListLow.size(); i++) {
                        dcColor = setAlpha(eventListLow.get(i).getColor(), alphaLow);
                        eventListLow.get(i).setColor(dcColor);
                    }
                    //"Send" the list of low priority events to the model so it can despatch them
                    //to the correct daycards
                    model.animateDayCard(eventListLow);
                    //Go through the repaint list and repaint all the daycards with a low priority task

                }
                //For inline comments, please check above. It's basically the same thing that is happening
                if (currentlyRunningPriorityNormal) {
                    if ((alphaNormal + offset > 255 && increaseNormal > 0) || (alphaNormal - offset < 0 && increaseNormal < 0)) {
                        increaseNormal = increaseNormal * -1;
                    }
                    alphaNormal = alphaNormal + offset * increaseNormal;
                    for (int i = 0; i < eventListNormal.size(); i++) {
                        dcColor = setAlpha(eventListNormal.get(i).getColor(), alphaNormal);
                        eventListNormal.get(i).setColor(dcColor);
                    }
                    model.animateDayCard(eventListNormal);
                }
                //For inline comments, please check above. It's basically the same thing that is happening
                if (currentlyRunningPriorityHigh) {
                    if ((alphaHigh + offset > 255 && increaseHigh > 0) || (alphaHigh - offset < 0 && increaseHigh < 0)) {
                        increaseHigh = increaseHigh * -1;
                    }
                    alphaHigh = alphaHigh + offset * increaseHigh;
                    for (int i = 0; i < eventListHigh.size(); i++) {
                        dcColor = setAlpha(eventListHigh.get(i).getColor(), alphaHigh);
                        eventListHigh.get(i).setColor(dcColor);
                    }
                    model.animateDayCard(eventListHigh);

                }
            }
            //Passes instructions to glasspane
            if (instructionList != null && runInstructions) {
                myGlassPane.passInstruction(instructionList.get(counter));
                //If there are no more instructions
                if (counter == instructionList.size() - 1) {
                    //Stop running
                    runInstructions = false;
                    //Reset counter
                    counter = 0;
                }
                //Next instruction
                counter++;
            }
        }
    }

    /**
     * Adds a list of daycards to the animation engine. These daycards is
     * necessary to be able to repaint them.
     */
    public void setDayCardList() {
        dayCardList = model.getDayCardList();
    }

    /**
     * Changes the alpha value of a color.
     *
     * @param color The color that should be modified
     * @param alpha The new alpha value for that color.
     * @return The color with the added alpha value.
     */
    private Color setAlpha(Color color, int alpha) {
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha);
    }

    /**
     * Returns true if either the daycards containing low priorty tasks, high
     * priority tasks or low priority tasks is running. Otherwise false.
     *
     * @return true if the animation of priority daycards is runnning.
     */
    public boolean getRunningPriority() {
        return (currentlyRunningPriorityLow || currentlyRunningPriorityNormal || currentlyRunningPriorityHigh);
    }
    
    private class RepainterAction implements ActionListener {


        @Override
        public void actionPerformed(ActionEvent ae) {
            /*if (canvas != null) {
                canvas.repaint();
            }
            else {
                canvas = model.getCanvas();
            }*/
            if(myGlassPane != null) {
                myGlassPane.repaint();
            }
        }
        
    }
}
