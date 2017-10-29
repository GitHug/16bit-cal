package Control;


import Model.Datatypes.Instruction;
import Model.SixteenBitModel;
import Utils.OsUtils;
import Utils.ScreenImage;
import View.MyGlassPane;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * A class for creating a help system.
 * The primary objective of this class is to create instructions which are then
 * passed to the animation engine where they are processed.
 * @author fredrikmakila
 * @see AnimationEngine
 */
public class HelpSystem {
    private BufferedImage menuScreenImage;
    private final MyGlassPane glassPane;
    private final Canvas canvas;
    private final ArrayList<Instruction> instructionList = new ArrayList<>();

    /*Constructors*/
    
    /**
     * Creates the HelpSystem by setting getting information from the model and
     * taking a printscreen of the application to use on the glasspane
     */
    public HelpSystem() {
        SixteenBitModel model = SixteenBitModel.getInstance();
        glassPane = model.getGlassPane();
        canvas = model.getCanvas();


        try {
            // Take the screenshot
            new Robot();
            if(OsUtils.isNotMac()) {
                menuScreenImage = ScreenImage.createImage(model.getMenuBar().getMenuBar());
            }
        } catch (AWTException ex) {
            System.err.println("You have failed me for the last time!");
            ex.printStackTrace();
        }

        
    }
    
    /*Getters*/
    
    /**
     * Returns the animation instruction list
     * @return the animation instruction list
     */
    public ArrayList<Instruction> getInstructionList() {
        return instructionList;
    }
    
    /*Others*/
    
    /**
     * Draws the canvas on the glasspane depending of the state
     * @param state paints on top on the glasspane if state is true
     */
    public void drawScreenImage(boolean state) {
        if(state) {
            BufferedImage screenImage = ScreenImage.createImage(canvas);
            glassPane.drawImage(screenImage, "screen");
            glassPane.drawImage(menuScreenImage, "menu");
            glassPane.setOpaque(true);
        }
        else {
            glassPane.drawImage(null, "screen");
            glassPane.setOpaque(false);
        }
    }
    
    
    /**
     * Starts the help animation
     */
    public void start() {
        drawScreenImage(true);
        glassPane.enableMouseDispatch(false);
        instructionEngine();
    }
    
    /**
     * Describes the route of the zeppelin and makes it act accordingly
     */
    private void instructionEngine() {
        instructionList.clear();
        Point p0 = glassPane.getZeppelinLocation();
        Point p1 = new Point(p0.x, p0.y - 100);
        Point p2;
        
        instructionList.add(new Instruction(Instruction.START_INSTRUCTION, null, null));
        rotationInstruction(0.0, -2.0, 50.0, false);
        moveInstruction(p0, p1, p1, 200.0);
        p0 = p1;
        p1 = new Point(400, 400);
        p2 = new Point(490, 10);
        moveInstruction(p0, p1, p2, 150.0);
        popupInstruction(p2, Instruction.NEXT_MONTH, Instruction.RIGHT, "NEXT");
        
        p0 = p2;
        p1 = new Point(250, 250);
        p2 = new Point(10, 10);
        moveInstruction(p0, p1, p2, 150.0);
        popupInstruction(p2, Instruction.PREVIOUS_MONTH, Instruction.RIGHT, "PREVIOUS");
        p0 = p2;
        p1 = new Point(250, 200);
        p2 = new Point(600, 100);
        moveInstruction(p0, p1, p2, 200.0);
        popupInstruction(p2, Instruction.CHECKBOX, Instruction.RIGHT, "LOWP");
        p0 = p2;
        p1 = new Point(670, 120);
        p2 = new Point(690, 100);
        moveInstruction(p0, p1, p2, 100.0);
        popupInstruction(p2, Instruction.UNDOREDO, Instruction.RIGHT, "UNDO");
        p0 = p2;
        p1 = new Point(720, 250);
        p2 = new Point(650, 520);
        moveInstruction(p0, p1, p2, 150.0);
        popupInstruction(p2, Instruction.HELPSYSTEM, Instruction.LEFT, "HELP");
        p0 = p2;
        p1 = new Point(720, 400);
        p2 = new Point(300, 300);
        moveInstruction(p0, p1, p2, 150.0);
        popupInstruction(p2, Instruction.DAYCARD, Instruction.RIGHT, "DAYCARDW");
        p0 = p2;
        p1 = new Point(-100, 50);
        p2 = p1;
        moveInstruction(p0, p1, p2, 100.0);
        moveInstruction(p2, p2, p2, 300.0);
        p0 = new Point(-100, 300);
        p1 = new Point(glassPane.getZeppelinLocation().x, 300);
        p2 = p1;
        moveInstruction(p0, p1, p2, 200.0);
        p0 = p2;
        p1 = glassPane.getZeppelinLocation();
        p2 = p1;
        moveInstruction(p0, p1, p2, 200.0);
        rotationInstruction(-2.0, 0.0, 100.0, true);
        instructionList.add(new Instruction(Instruction.STOP_INSTRUCTION, null, null));
    }
    
    
    /**
     * An instruction describing a rotational movement
     * @param p0 degree at the beginning
     * @param p1 degree at the end
     * @param numStep how many animation steps that this will take (speed)
     * @param close if it will return to its start point or not
     */
    private void rotationInstruction(double p0, double p1, Double numStep, boolean close) {
        Double rotationValue;
        Double steps = 1/numStep;
        
        for(double t = 0.0; t <= 1.0; t = t + steps) {
            rotationValue = (1.0 - t)*p0 + t*p1;
            instructionList.add(new Instruction(Instruction.ROTATE, rotationValue, Instruction.LID));
        }
        
        if(close) {
            //close the loop
            instructionList.add(new Instruction(Instruction.ROTATE, p1, Instruction.LID));
        }
        
    }
    
    /**
     * An instruction describing a positional movement
     * @param p0 start position
     * @param p1 middle (weight) position
     * @param p2 end position
     * @param numStep how many animation steps that this will take (speed)
     */
    private void moveInstruction(Point p0, Point p1, Point p2, Double numStep) {
        Double xValue;
        Double yValue;
        Double steps = 1.0/numStep;
        Point point;
        
        for(double t = 0; t <= 1.0; t = t + steps) {
            xValue = (1.0 - t)*((1.0 - t)*p0.x + t*p1.x) + t*((1.0 - t)*p1.x + t*p2.x); 
            yValue = (1.0 - t)*((1.0 - t)*p0.y + t*p1.y) + t*((1.0 - t)*p1.y + t*p2.y);
            
            point = new Point(xValue.intValue(), yValue.intValue());
            
            instructionList.add(new Instruction(Instruction.MOVE, point, Instruction.ZEPPELIN));
        }   
    }
    
    /**
     * Instruction for writing a popup message
     * @param p position for this popup
     * @param message the text message to be inside the popup
     * @param alignment In which direction from the point the popup should be
     * @param action which action it will show
     */
    private void popupInstruction(Point p, String message, boolean alignment, String action) {
        instructionList.add(new Instruction(Instruction.POPUP, message, p, Instruction.START, alignment));
        userInputInstructon(action);
        instructionList.add(new Instruction(Instruction.POPUP, message, p, Instruction.STOP, alignment));
    }
    
    
    
    /**
     * Creates a paus instruction that waits for the user to press a button
     * before continuing
     * @param action the continue action
     */
    private void userInputInstructon(String action) {
       instructionList.add(new Instruction(Instruction.USERINPUT, null, action));
    }
    
}
