/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Control.Actions.Action;
import Control.Canvas;
import Model.Database.ResourceHandler;
import Model.Datatypes.Instruction;
import Model.SixteenBitModel;
import Utils.OsUtils;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Class for creating a panel used as a glass pane. 
 * @author fredrikmakila
 */
public class MyGlassPane extends JPanel {

    private String imgName = "zeppelin";
    private String lidImgName = "lid";
    private String warehouseImgName = "warehouse";
    private String walkImgName = "walk";
    private String houseImgName = "house";
    private String tallHouseImgName = "tall_house";
    private String moonImgName = "moon";
    private String secretImgName = "secret";
    private String url;
    private URL imageURL;
    private final Image zeppelinImage;
    private final Image lidImage;
    private final Image warehouseImage;
    private final Image walkImage;
    private final Image houseImage;
    private final Image tallHouseImage;
    private final Image moonImage;
    private final Image secretImage;
    private BufferedImage image;
    private SixteenBitModel model;
    private Canvas canvas;
    private boolean state = true;
    private MenuBar menu;
    private int imgX = 0;
    private int imgY = 0;
    private JMenuBar menubar;
    private Instruction rotationInstruction;
    private Instruction moveInstruction;
    private Instruction popupInstruction;
    private JFrame frame;
    private ArrayList<Point> positionList = new ArrayList<Point>();
    private ArrayList<Image> imageList = new ArrayList<Image>();
    private int warehouseX;
    private int warehouseY;
    private BufferedImage menuImage;
    private int zeppelinX;
    private int zeppelinY;
    private BasicStroke stroke = new BasicStroke(2.0f);
    private Font font;
    private int fontSize = 12;
    private JButton button;
    private JButton buttonOk;
    private Action action;
    private JButton buttonCancel;
    private boolean runInstruction = false;
    MouseAdapter mouse;
    private int secretCounter = 0;

//********************CONSTRUCTOR*****************/
    /**
     * The only constructor in this class. This is the JPanel that we use as our
     * glasspane. The constructor Initializes the glasspane and loads all
     * neccessary images that will be displayed. It also adds a mouselistener to
     * itself so it can intercept mouse events.
     */
    public MyGlassPane() {
        setLayout(null);
        setOpaque(false);

        //Loads a zeppelin image
        ResourceHandler rh = new ResourceHandler();
        url = rh.getString(imgName);
        imageURL = getClass().getClassLoader().getResource(url);
        zeppelinImage = new ImageIcon(imageURL).getImage();

        //Loads an image of a lid/hatch
        url = rh.getString(lidImgName);
        imageURL = getClass().getClassLoader().getResource(url);
        lidImage = new ImageIcon(imageURL).getImage();

        //Loads an image of a warehouse
        url = rh.getString(warehouseImgName);
        imageURL = getClass().getClassLoader().getResource(url);
        warehouseImage = new ImageIcon(imageURL).getImage();

        //Loads an image of a board walk
        url = rh.getString(walkImgName);
        imageURL = getClass().getClassLoader().getResource(url);
        walkImage = new ImageIcon(imageURL).getImage();

        //Loads an image of a house
        url = rh.getString(houseImgName);
        imageURL = getClass().getClassLoader().getResource(url);
        houseImage = new ImageIcon(imageURL).getImage();

        //Loads an image of a taller house
        url = rh.getString(tallHouseImgName);
        imageURL = getClass().getClassLoader().getResource(url);
        tallHouseImage = new ImageIcon(imageURL).getImage();

        //Loads an image of a moon
        url = rh.getString(moonImgName);
        imageURL = getClass().getClassLoader().getResource(url);
        moonImage = new ImageIcon(imageURL).getImage();
        
        //Loads a secret image... shush!
        url = rh.getString(secretImgName);
        imageURL = getClass().getClassLoader().getResource(url);
        secretImage = new ImageIcon(imageURL).getImage();


        model = SixteenBitModel.getInstance();
        canvas = model.getCanvas();
        menu = model.getMenuBar();
        menubar = menu.getMenuBar();
        frame = model.getFrame();


        //Create all the graphics that are seen as static on the glasspane.
        createGraphics();

        //Sets the font
        font = new Font("Arial", Font.BOLD, fontSize);

        //Create a button so the user can perform an action during the help system tour
        button = new JButton();
        button.setText("Perform action");
        button.setVisible(false);
        this.add(button);

        //Creates an OK button that the user can use to go forward in the tour
        Action actionRun = new Action("runInstruction");
        buttonOk = new JButton(actionRun);
        buttonOk.setText("OK");
        buttonOk.setVisible(false);
        this.add(buttonOk);

        //Creates a cancel button so the user can end the tour
        buttonCancel = new JButton(new GlassAction());
        buttonCancel.setText("Cancel tour");
        buttonCancel.setSize(100, 50);
        buttonCancel.setLocation(new Point(800, 20));
        buttonCancel.setVisible(false);
        this.add(buttonCancel);


    }

    //****************GETTERS***************************
    /**
     * Gets the start position of the zeppelin on the screen
     *
     * @return The zeppelin's start position
     */
    public Point getZeppelinLocation() {
        return new Point(zeppelinX, zeppelinY);
    }

    //***************HELPSYSTEM METHODS****************
    /**
     * Provides the glasspane with a screen capture of a component on the
     * screen. The type specifies the type of the screen. If the type is
     * "screen", it means that the image provided is a screencapture of the
     * contentpane. Otherwise the image should be a screencapture of the
     * menubar.
     *
     * @param image A screen capture that the glasspane will show
     * @param type The kind of screen capture. "screen" denotes contentpane
     * screen capture
     */
    public void drawImage(BufferedImage image, String type) {
        if (type.equals("screen")) {
            this.image = image;
        } else {
            this.menuImage = image;
        }
    }

    /**
     * Stops execution of the instructions. This will cause the helpsystem to
     * reset. This method is either called when there are no more instructions
     * or if the user presses the cancel button.
     */
    private void stopInstruction() {
        runInstruction = false;
        enableMouseDispatch(true);
        setOpaque(false);
        buttonCancel.setVisible(false);
        button.setVisible(false);
        buttonOk.setVisible(false);
        model.getAnimationEngine().stop(null);
        rotationInstruction = null;
        moveInstruction = null;
        popupInstruction = null;
        menubar.setEnabled(true);
        secretCounter = 0;
    }

    /**
     * Passes instructions by checking what kind of command the instruction has.
     * The command will affect what is drawn in the paintcomponent method and
     * how it is drawn.
     *
     * @param instruction The instruction passed to this glasspane
     */
    public void passInstruction(Instruction instruction) {
        //The instruction is a rotate instruction
        if (instruction.getCommand().equals(Instruction.ROTATE)) {
            this.rotationInstruction = instruction;
        } //The instruction is a move instruction
        else if (instruction.getCommand().equals(Instruction.MOVE)) {
            this.moveInstruction = instruction;
        } //The instruction brings up a popup
        else if (instruction.getCommand().equals(Instruction.POPUP)) {
            this.popupInstruction = instruction;
        } //The instruction will make the animation wait for user input
        else if (instruction.getCommand().equals(Instruction.USERINPUT)) {
            model.getAnimationEngine().runInstruction(false);
            //removes the last action listener
            button.removeActionListener(action);
            if (instruction.getObject().equals("HELP")) {
                //It doesn't make sense to start another helpsystem
                //when it is already running.
                button.setEnabled(false);
            } else {
                action = new Action(instruction.getObject());
                //Adds the new action listener
                button.addActionListener(action);
                //Makes sure the button is usable
                button.setEnabled(true);
            }
        } //This is the start instruction, that is the very first instruction
        else if (instruction.getCommand().equals(Instruction.START_INSTRUCTION)) {
            menubar.setEnabled(false);
            runInstruction = true;
            buttonCancel.setVisible(true);
        } //This is the stop instruction, tat is the very last instruction
        else if (instruction.getCommand().equals(Instruction.STOP_INSTRUCTION)) {
            stopInstruction();
        }
    }

    //**************PAINT METHODS******************  
    /**
     * Draws all the components on the glasspane.
     *
     * @param g A graphics
     */
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        //Checks if the current running operating system is something other than mac.
        //Then the menubar and contentpane will both be "replaced" by a screen capture
        if (!OsUtils.isMac() && image != null && menuImage != null && runInstruction) {
            g2.drawImage(menuImage, imgX, imgY, this);
            g2.drawImage(image, imgX, imgY + menuImage.getHeight(), this);
        } //Else just the content pane will be replaced by a screen capture
        else if (image != null && runInstruction) {
            g2.drawImage(image, imgX, imgY, this);
        }

        //Check if move instruction
        if (moveInstruction != null) {
            //Gets the object to move
            if (moveInstruction.getObject().equals(Instruction.ZEPPELIN) && runInstruction) {
                Point p = moveInstruction.getLocation();
                //Translate position
                g2.translate(p.x, p.y);
            } else {
                g2.translate(zeppelinX, zeppelinY);
            }
        } else {
            g2.translate(zeppelinX, zeppelinY);
        }
        //Draws the zeppelin after translating
        g2.drawImage(zeppelinImage, 0, 0, null);

        if (moveInstruction != null) {
            if (moveInstruction.getObject().equals(Instruction.ZEPPELIN) && runInstruction) {
                Point p = moveInstruction.getLocation();
                //Translate back
                g2.translate(-p.x, -p.y);
            } else {
                g2.translate(-zeppelinX, -zeppelinY);
            }
        } else {
            g2.translate(-zeppelinX, -zeppelinY);
        }

        //Do rotation if rotationInstruction
        if (rotationInstruction != null) {
            if (rotationInstruction.getObject().equals(Instruction.LID) && runInstruction) {
                //g2.translate(x, lidImage.getHeight(null));
                g2.rotate(rotationInstruction.getRotation(), warehouseX, warehouseY);
            }
        }

        //Draw the left hatch on the warehouse
        g2.drawImage(lidImage, warehouseX, warehouseY - lidImage.getHeight(this), null);

        //Rotate back
        if (rotationInstruction != null) {
            if (rotationInstruction.getObject().equals(Instruction.LID) && runInstruction) {
                //g2.translate(x, lidImage.getHeight(null));
                g2.rotate(-rotationInstruction.getRotation(), warehouseX, warehouseY);
            }
        }

        //Rotate the other hatch
        if (rotationInstruction != null) {
            if (rotationInstruction.getObject().equals(Instruction.LID) && runInstruction) {
                //g2.translate(x, lidImage.getHeight(null));
                g2.rotate(-rotationInstruction.getRotation(), warehouseX + lidImage.getWidth(this) * 2, warehouseY);
            }
        }

        //Draw the other hatch
        g2.drawImage(lidImage, warehouseX + lidImage.getWidth(this), warehouseY - lidImage.getHeight(this), null);

        //Rotate back
        if (rotationInstruction != null) {
            if (rotationInstruction.getObject().equals(Instruction.LID) && runInstruction) {
                //g2.translate(x, lidImage.getHeight(null));
                g2.rotate(rotationInstruction.getRotation(), warehouseX + lidImage.getWidth(this) * 2, warehouseY);
            }
        }

        //Draws all the graphics defined in the createGraphics method
        for (int i = 0; i < positionList.size(); i++) {
            Point point = positionList.get(i);
            g2.drawImage(imageList.get(i), point.x, point.y, this);
        }

        //Brings up a popup and two buttons
        if (popupInstruction != null) {
            if (popupInstruction.getDisplay() && runInstruction) {
                drawPopup(g);
            } else {
                //Hide the buttons
                button.setVisible(false);
                buttonOk.setVisible(false);
            }
        }
        
        if(secretCounter >= 10 && runInstruction) {
            g.drawImage(secretImage, 200, 0, this);
        }
        
    }
    
    /**
     * Draws a popup box containing some message.
     * The position of the popup box is to the right of the zeppelin relative to the 
     * zeppelins position. The two buttons will appear underneath the popup. If the instruction says the alignment should be left instead,
     * then the popup appears to the left of the zeppelin and the buttons is on top. Line break
     * of text is done automatically.
     * @param g Graphics
     */
    private void drawPopup(Graphics g) {
        Point p = popupInstruction.getLocation();
        String message = popupInstruction.getMessage();
        ArrayList<String> list = new ArrayList<String>();
        String string = "";
        String tempString;
        String token;
        //The spacing in front and after a line of text
        int spacing = 5;
        //The spacing between two lines of text
        int lineSpacing = 2;
        //The maximum width of a line of text.
        int maxWidth = 200 - 2 * spacing;
        int rectMaxWidth;
        int xPos;
        int yPos;
        int buttonY;
        Dimension dimension;

        Graphics2D g2 = (Graphics2D) g;
        
        g2.setFont(font);
        //Gets the fontmetrics for the size of the font
        FontMetrics fm = g.getFontMetrics();
        
        //If the length of the entire message is bigger than the maximum width, 
        //then the message has to be broken down into smaller pieces. 
        if (fm.stringWidth(message) > maxWidth) {
            rectMaxWidth = 200;
            //Creates tokens with space as delimiter
            StringTokenizer tokenizer = new StringTokenizer(message, "[ ]", true);
            while (tokenizer.hasMoreTokens()) {
                tempString = string;
                token = tokenizer.nextToken();

                //Whenever the line can contain no more words, add that line to the 
                //array and continue.
                if (fm.stringWidth(tempString + token) > maxWidth) {
                    list.add(string);
                    string = "";
                }
                string = string + token;


            }
            //Adds the last line of text to the array
            list.add(string);
        } else {
            //If the message is smaller than the maximum width of the rectangle,
            //then just add it to the list
            rectMaxWidth = fm.stringWidth(message) + 2 * spacing;
            list.add(message);
        }
        
        //Calculates the dimension for the box given how much text it is
        dimension = new Dimension(rectMaxWidth + spacing, list.size() * fm.getHeight() + spacing * 2);
        //y-position of the popup box's upper left corner
        yPos = p.y;
        //Check the alignment
        //if alignment is right
        if (popupInstruction.getAlignment()) {
            xPos = p.x + zeppelinImage.getWidth(this);
            buttonY = yPos + dimension.height;

        } else { //else alignment is left
            xPos = p.x - rectMaxWidth - spacing;
            buttonY = yPos - 60;
        }

        //place the buttons
        button.setLocation(new Point(xPos, buttonY));
        button.setSize(dimension.width, 30);

        buttonOk.setLocation(new Point(xPos, buttonY + button.getHeight()));
        buttonOk.setSize(dimension.width, 30);

        //make them visible
        button.setVisible(true);
        buttonOk.setVisible(true);

        //Sets the color of the background of the popup
        g2.setColor(new Color(255, 236, 179));
        //Draw the rounded rectangle
        g2.fillRoundRect(xPos, yPos, dimension.width, dimension.height, 15, 15);

        //Sets a stroke with black color
        g2.setStroke(stroke);
        g2.setColor(Color.BLACK);
        //Draw the stroke
        g2.drawRoundRect(xPos, yPos, dimension.width, dimension.height, 15, 15);

        //Translate the position of the text
        g2.translate(xPos + spacing, yPos + fm.getHeight());
        for (int i = 0; i < list.size(); i++) {
            //Draw all lines of text on different rows
            g2.drawString(list.get(i), 0 + spacing, 0 + i * fm.getHeight() + lineSpacing);
        }
        //Translate back
        g2.translate(-(xPos + spacing), -(yPos + fm.getHeight()));


        
    }

    /**
     * Method that creates the fancy graphics that can be seen on the glasspane.
     * This includes some houses, a moon, a warehosue and plenty of boardwalks. It uses
     * the images loaded by the constructor
     */
    private void createGraphics() {
        int xPos;
        int yPos;
        if (!OsUtils.isMac()) {
            yPos = frame.getHeight() - 38;
        } else {
            yPos = frame.getHeight() - 22;
        }
        int py;
        int px;
        //Draws some silly graphics
        //Creates a board walk
        xPos = 0;
        py = yPos - walkImage.getHeight(this);
        imageList.add(walkImage);
        positionList.add(new Point(xPos, py));

        //And another one
        xPos = xPos + walkImage.getWidth(this);
        //adds to paintList
        imageList.add(walkImage);
        positionList.add(new Point(xPos, py));

        //And another one
        xPos = xPos + walkImage.getWidth(this);
        //adds to paintList
        imageList.add(walkImage);
        positionList.add(new Point(xPos, py));

        //And another one
        xPos = xPos + walkImage.getWidth(this);
        //adds to paintList
        imageList.add(walkImage);
        positionList.add(new Point(xPos, py));

        //And another one
        xPos = xPos + walkImage.getWidth(this);
        //adds to paintList
        imageList.add(walkImage);
        positionList.add(new Point(xPos, py));

        //And another one
        xPos = xPos + walkImage.getWidth(this);
        //adds to paintList
        imageList.add(walkImage);
        positionList.add(new Point(xPos, py));

        //And a house
        xPos = xPos + walkImage.getWidth(this);
        py = yPos - houseImage.getHeight(this);
        //adds to paintList
        imageList.add(houseImage);
        positionList.add(new Point(xPos, py));

        //And a taller house
        xPos = xPos + houseImage.getWidth(this);
        py = yPos - tallHouseImage.getHeight(this);
        //adds to paintList
        imageList.add(tallHouseImage);
        positionList.add(new Point(xPos, py));

        //And more board walks because they are cool
        px = xPos + tallHouseImage.getWidth(this);
        py = yPos - walkImage.getHeight(this);
        imageList.add(walkImage);
        positionList.add(new Point(px, py));

        //And more board walks because they are cool
        px = px + walkImage.getWidth(this);
        py = yPos - walkImage.getHeight(this);
        imageList.add(walkImage);
        positionList.add(new Point(px, py));
        //And more board walks because they are cool
        px = px + walkImage.getWidth(this);
        py = yPos - walkImage.getHeight(this);
        imageList.add(walkImage);
        positionList.add(new Point(px, py));

        //And a warehouse
        px = px + walkImage.getWidth(this);
        py = yPos - warehouseImage.getHeight(this);
        warehouseX = px + 8;
        warehouseY = py;
        zeppelinX = px + 16;
        zeppelinY = py;
        imageList.add(warehouseImage);
        positionList.add(new Point(px, py));

        //And a moon
        xPos = xPos + tallHouseImage.getWidth(this) + 25;
        py = yPos - moonImage.getHeight(this) - 100;
        //adds to paintList
        imageList.add(moonImage);
        positionList.add(new Point(xPos, py));
    }

    //*******************MOUSELISTENER METHODS*************
    /**
     * Enables or disables the dispatching of mouseevents from the glasspane
     * to the canvas. 
     * @param state true if mouseevents should be dispatched, false if they shouldn't
     */
    public void enableMouseDispatch(boolean state) {
        MouseListener m;
        MouseMotionListener mm;
        this.state = state;
        //Mouse events are not dispatched
        if (!state) {
            //Adds mouselistener so the events will be blocked
            mouse = new MouseAdapter(this);
            addMouseListener(mouse);
            addMouseMotionListener(mouse);
        } else {
            //remove the mouselistener so the events will go through
            for (int i = 0; i < getMouseListeners().length; i++) {
                m = getMouseListeners()[i];
                removeMouseListener(m);
            }
            for (int i = 0; i < getMouseMotionListeners().length; i++) {
                mm = getMouseMotionListeners()[i];
                removeMouseMotionListener(mm);
            }
        }
    }
    
    
    /**
     * Class to intercept mouseevents on the glasspane. 
     * It will block all mouseevents received.
     */
    private class MouseAdapter extends MouseInputAdapter {

        private MyGlassPane glassPane;
        private final JMenuBar menubar;

        public MouseAdapter(MyGlassPane glassPane) {
            this.glassPane = glassPane;
            menubar = menu.getMenuBar();
        }

        @Override
        public void mouseClicked(MouseEvent me) {
            if (state) {
                redispatchMouseEvent(me);
            }
        }

        @Override
        public void mousePressed(MouseEvent me) {
            if (state) {
                redispatchMouseEvent(me);
            }
        }

        @Override
        public void mouseReleased(MouseEvent me) {
            secretCounter++;
            if (state) {
                redispatchMouseEvent(me);
            }
        }

        @Override
        public void mouseEntered(MouseEvent me) {
            if (state) {
                redispatchMouseEvent(me);
            }
        }

        @Override
        public void mouseExited(MouseEvent me) {
            if (state) {
                redispatchMouseEvent(me);
            }
        }

        @Override
        public void mouseDragged(MouseEvent me) {
            if (state) {
                redispatchMouseEvent(me);
            }
        }

        @Override
        public void mouseMoved(MouseEvent me) {
            if (state) {
                redispatchMouseEvent(me);
            }
        }
        
        /**
         * Redispatch mouse events to the menubar and the content pane. 
         * This method is not really used at the moment, but support exists if
         * you wish to allow some or all mouseevents through the the underlying
         * components. 
         * @param me The mouseevent to be dispatched
         */
        private void redispatchMouseEvent(MouseEvent me) {
            Point glassPanePoint = me.getPoint();
            Point containerPoint = SwingUtilities.convertPoint(
                    glassPane,
                    glassPanePoint,
                    canvas);
            //we're not in the content pane
            if (containerPoint.y < 0) {
                //We're on the menubar
                if (containerPoint.y + menubar.getHeight() >= 0) {

                    Point menuPoint = SwingUtilities.convertPoint(
                            glassPane,
                            glassPanePoint,
                            menubar);

                    Component component = SwingUtilities.getDeepestComponentAt(
                            menubar,
                            menuPoint.x,
                            menuPoint.y);

                    Point componentPoint = SwingUtilities.convertPoint(glassPane,
                            glassPanePoint, component);

                    if (component != null) {
                        component.dispatchEvent(
                                new MouseEvent(
                                component,
                                me.getID(),
                                me.getWhen(),
                                me.getModifiers(),
                                componentPoint.x,
                                componentPoint.y,
                                me.getClickCount(),
                                me.isPopupTrigger()));
                    }

                }
            } else { //We're on the canvas
                /*
                 * Point componentPoint = SwingUtilities.convertPoint(
                 * glassPane, glassPanePoint, canvas);
                 */
                Point canvasPoint = SwingUtilities.convertPoint(glassPane,
                        glassPanePoint, canvas);

                canvas.dispatchEvent(
                        new MouseEvent(
                        canvas,
                        me.getID(),
                        me.getWhen(),
                        me.getModifiers(),
                        canvasPoint.x,
                        canvasPoint.y,
                        me.getClickCount(),
                        me.isPopupTrigger()));


            }
        }
    }

    
     //**********************ACTION************************
     /**
      * A simple action class for an action that is added to the cancel tour button.
      * Doesn't do much except calls the stopInstruction method.
      */
    private class GlassAction extends AbstractAction {

        public GlassAction() {
        }

        @Override
        public void actionPerformed(ActionEvent ae) {
            stopInstruction();
        }
    }
}
