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
    private ArrayList<Drawable> list = new ArrayList<>();
    private final SixteenBitModel model;
    private int id = 0;
    private final AnimationEngine animation;
    private boolean within = false;
    private int index;
    private boolean canClick = true;
    

    
    
    /**
     * Simple constructor.
     */
    public Canvas(){
        setFocusable(true);
        /*for(int i = 0; i<args.length; i++) {
            list.addAll((ArrayList<Drawable>) args[i]);
        }*/
        
        model = SixteenBitModel.getInstance();

        list = model.getPaintList();
        
        //Allow the Canvas to read the mouseclicks
        addMouseListener(new Mouse(this));
        addMouseMotionListener(new Mouse(this));
        addComponentListener(new CanvasEventListener());
        
        animation = model.getAnimationEngine();
        //animation.startAnimatePrio();
        this.setLayout(null);
        
    }
    
    
    /**
     * A getter
     * @return Returns all Drawables on this Canvas as a list
     */
    private ArrayList<Drawable> getList(){
        return list;
    }
        
    /**
     * Class that acts as a simple mouse listener with overridden methods
     */
    class Mouse implements MouseListener, MouseMotionListener
    {
        private final Canvas currentCanvas;
        
        
        
        /**
         * The constructor of a Mouse
         * @param can The Canvas associated to this mouselistener
         */
        Mouse(Canvas can){
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
                for (Drawable aList : list) {
                    if (aList.within(p)) {
                        c = (CustomComponent) aList;
                        id = c.getId();
                        model.setSelected(id);
                    }
                }
            }
            
        }
        
        /**
         * Not implemented.
         * @param e mouse event
         */
        @Override
        public void mouseClicked(MouseEvent e) {
        }

        /**
         * This method fires when the Mouse is released
         * @param e mouse event
         */
        @Override
        public void mouseReleased(MouseEvent e) {
            canClick = false;
            CustomComponent c;
            Point p = e.getPoint();
            ArrayList<Drawable> list = currentCanvas.getList();
            for (Drawable aList : list) {
                if (aList.within(p)) {
                    c = (CustomComponent) aList;
                    if (c.getId() == id) {
                        model.setDeSelected(id);
                    }
                }
            }
            canClick = true;
        }

        /**
         * Not implemented. Don't use this one.
         * @param e mouse event
         */
        @Override
        public void mouseEntered(MouseEvent e) {
        }

        /**
         * Not implemented. Don't use this one.
         * @param e mouse event
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
         */
        private void mouseEnter(CustomComponent c) {
            animation.start(c.getId());
            
        }
        
        /**
         * Method that is called everytime the mouse exits a component
         */
        private void mouseExit() {
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
                    mouseEnter(c);
                }
                else if (!list.get(index).within(p) && within) {
                    within = false;
                    mouseExit();
                }
            }
           
        }
        


    }
    
    /**
     * A custom Event Listener that listens to changes in the Canvas
     * like resize of the window for example.
     */
    class CanvasEventListener extends ComponentAdapter {
        
        /**
         * Constructor
         */
        CanvasEventListener(){ }

        /**
         * Fires when the canvas is resized.
         * @param e A component event
         */
        @Override
        public void componentResized(ComponentEvent e) { }

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
        ArrayList<Drawable> paintList = model.getPaintList();
        super.paintComponent(g);
        //this.setLayout(null);
        for (Drawable aPaintList : paintList) {
            this.add((JPanel) aPaintList);
            aPaintList.draw(g);
        }
    }
    
        
}
