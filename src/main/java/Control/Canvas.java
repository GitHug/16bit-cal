package Control;

import Control.Interface.Drawable;
import Model.SixteenBitModel;
import View.CustomComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * Empty panel that is used to draw components on. 
 * @author Robin Horneman
 */
@SuppressWarnings("unchecked")
public class Canvas extends JPanel {
    private ArrayList<Drawable> list = new ArrayList<Drawable>();
    private ArrayList<Drawable> paintList = new ArrayList<Drawable>();
    private ArrayList<Drawable> dcList = new ArrayList<Drawable>();
    private int daycardCount;
    private Drawable drawable;
    private SixteenBitModel model;
    private int id = 0;
    long startTime;
    long endTime;
    private AnimationEngine animation;
    private boolean within = false;
    private int index;
    private boolean canClick = true;
    

    
    
    /**
     * Simple constructor.
     * @param args 
     */
    public Canvas(ArrayList ... args){
        setFocusable(true);
        /*for(int i = 0; i<args.length; i++) {
            list.addAll((ArrayList<Drawable>) args[i]);
        }*/
        
        model = SixteenBitModel.getInstance();

        list = model.getPaintList();
        
        //Allow the Canvas to read the mouseclicks
        addMouseListener(new Mouse(this));
        addMouseMotionListener(new Mouse(this));
        addComponentListener(new CanvasEventListener(this));
        
        animation = model.getAnimationEngine();
        //animation.startAnimatePrio();
        this.setLayout(null);
        
    }
    
    
    /**
     * A getter
     * @return Returns all Drawables on this Canvas as a list
     */
    public ArrayList<Drawable> getList(){
        return list;
    }
    
    /**
     * Getter
     * @return A list of all DayCards
     */
    public ArrayList<Drawable> getDayCardList() {
        for(int i = 0; i < list.size(); i++) {
            dcList.add(list.get(i));
            System.out.println();
        }
        return dcList;
    }
    
    /**
     * Setter
     * @param dim the new dimension of the canvas to be set in the model 
     */
    public void setSizeInModel(Dimension dim){
       model.setCanvasSize(dim); 
    }
    
            
        
    /**
     * Class that acts as a simple mouse listener with overridden methods
     */
    public class Mouse implements MouseListener, MouseMotionListener
    {
        private Canvas currentCanvas;
        
        
        
        /**
         * The constructor of a Mouse
         * @param can The Canvas associated to this mouselistener
         */
        public Mouse(Canvas can){
            currentCanvas = can;
        }
        
        /**
         * The function that handles mouseclicks
         * @param e The mouse event that triggered this function
         */
        @Override
        public void mousePressed(MouseEvent e)
        {
            if(canClick) {
                Point p = new Point(e.getX(), e.getY()); //Get the location of the mouse
                CustomComponent c;
                ArrayList<Drawable> list = currentCanvas.getList();
                for(int i =0; i<list.size(); i++){
                    if(list.get(i).within(p)) {
                        c = (CustomComponent) list.get(i);
                        id = c.getId();
                        model.setSelected(id);
                    }
                }
            }
            
        }
        
        /**
         * Not implemented.
         * @param e
         */
        @Override
        public void mouseClicked(MouseEvent e) {
        }

        /**
         * This method fires when the Mouse is released
         * @param e
         */
        @Override
        public void mouseReleased(MouseEvent e) {
            canClick = false;
            CustomComponent c;
            Point p = e.getPoint();
            ArrayList<Drawable> list = currentCanvas.getList();
            for(int i = 0; i < list.size(); i++) {
                if(list.get(i).within(p)) {
                    c = (CustomComponent) list.get(i);
                    if (c.getId() == id) {
                        model.setDeSelected(id);
                    }
                }
            }
            canClick = true;
        }

        /**
         * Not implemented. Don't use this one.
         * @param e
         */
        @Override
        public void mouseEntered(MouseEvent e) {
        }

        /**
         * Not implemented. Don't use this one.
         * @param e
         */
        @Override
        public void mouseExited(MouseEvent e) {}

        /**
         * Not implemented
         * @param me Not implemented
         */
        @Override
        public void mouseDragged(MouseEvent me) {
        }

        /**
         * Method that is called everytime the mouse enters
         * a component.
         * @param e A mouse moved event
         */
        private void mouseEnter(MouseEvent e, CustomComponent c) {
            animation.start(c.getId());
            
        }
        
        /**
         * Method that is called everytime the mouse exits a component
         * @param e A mouse event
         */
        private void mouseExit(MouseEvent e, CustomComponent c) {
            animation.stop();
        }
       
        /**
         * This method checks for mouse movement.
         * It also effectively acts as both mouseEntered and mouseExited.
         * @param me a mouse moved event
         */
        @Override
        public void mouseMoved(MouseEvent me) {
            Point p = new Point(me.getPoint());
            CustomComponent c;
            //Gets all components on the canvas
            ArrayList<Drawable> list = currentCanvas.getList();
            //iterates through all the components
            for(int i = 0; i < list.size(); i++) {
                if(list.get(i).within(p) && !within) {
                    index = i;
                    within = true;
                    c = (CustomComponent) list.get(i);
                    mouseEnter(me, c);
                }
                else if (!list.get(index).within(p) && within) {
                    within = false;
                    c = (CustomComponent) list.get(index);
                    mouseExit(me, c);
                }
            }
           
        }
        


    }
    
    /**
     * A custom Event Listener that listens to changes in the Canvas
     * like resize of the window for example.
     */
    public class CanvasEventListener extends ComponentAdapter {
        private Canvas canvas;
        
        /**
         * Constructor
         * @param canvas the canvas it should listen to
         */
        CanvasEventListener(Canvas canvas){
            this.canvas = canvas;
        }

        /**
         * Fires when the canvas is resized.
         * @param e A component event
         */
        @Override
        public void componentResized(ComponentEvent e) {
            canvas.setSizeInModel(e.getComponent().getSize());
        }

        /**
         * Fires when the frame/canvas is moved.
         * @param e A component event
         */
        @Override
        public void componentMoved(ComponentEvent e) {
            //throw new UnsupportedOperationException("Not supported yet.");
        }

        /**
         * Not implemented.
         * @param e Not implemented.
         */
        @Override
        public void componentShown(ComponentEvent e) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        /**
         * Not implemented
         * @param e Not implemented.
         */
        @Override
        public void componentHidden(ComponentEvent e) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
        
    }
    
    /**
     * Method that paints all the components from the arraylist list from the constructor.
     * @param g The Graphics component that we draw on (this Canvas)
     */
    
   /* @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        for(int i =0; i<list.size(); i++) {
            this.add((JPanel) list.get(i));
            list.get(i).draw(g);
            System.out.println
        }
    } */
    
    // Nya painComponent!!!!!!!!!!!!!!!!!???
    @Override
    public void paintComponent(Graphics g){
        paintList = model.getPaintList();
        super.paintComponent(g);
        //this.setLayout(null);
        for(int i =0; i<paintList.size(); i++) {
            this.add((JPanel) paintList.get(i));
            paintList.get(i).draw(g);
        }
    }
    
        
}
