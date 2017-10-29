/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Control.Actions.CalendarEvent;
import Control.Actions.DeSelectEvent;
import Control.Actions.SelectedEvent;
import Model.Database.ResourceHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.net.URL;

/**
 * This class creates the custom checkbox component
 *
 * @author Robert
 */
public class Checkbox extends CustomComponent {

    private Image img;
    private Image selectedImg;
    private ActionListener action;
    private boolean isSelected = false;
    private int textWidth = 0;
    private final String label;
    private final Font font;
    private boolean first = true;
    private boolean labeled = false;
    @SuppressWarnings({"unused", "FieldCanBeLocal"})
    private int selectedId;


    /**
     * Constuctor to create a checkbox.
     *
     * @param x The x position of the checkbox
     * @param y The y position of the checkbox
     * @param text The text next to the checkbox
     * @param id The id of the checkbox
     */
    public Checkbox(int x, int y, String text, int id) {
        super(x, y);
        setId(id);
        label = text;
        ResourceHandler rh = new ResourceHandler();
        labeled = true;
        
        //Load unchecked image
        URL imageURL;
        String url;
        try {
            url = rh.getString("checkbox_unchecked");
            imageURL = getClass().getClassLoader().getResource(url);
            if (imageURL == null) {
                throw new Exception("imageURL null");
            }
            img = new ImageIcon(imageURL).getImage();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        //Load checked image
        try {
            url = rh.getString("checkbox_checked");
            imageURL = getClass().getClassLoader().getResource(url);
            if (imageURL == null) {
                throw new Exception("imageURL null");
            }
            selectedImg = new ImageIcon(imageURL).getImage();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        //Gets a font from the current system and sets the font size
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        ge.getAvailableFontFamilyNames();
        font = new Font("Arial", Font.BOLD, 12);
        
        //Set Bounds
        setBounds(x, y, img.getWidth(null), img.getHeight(null));
    }
    
    /**
     * Adds an action listener to this checkbox.
     *
     * @param action The actionlistener to be attached to the checkbox.
     */
    public void addActionListener(ActionListener action) {
        this.action = action;
    }

    /**
     * Method that is called when the checkbox is clicked on.
     *
     * @param event A selected event.
     */
    private void setSelected(SelectedEvent event) {
        this.selectedId = event.getId();
    }
    
    @Override
    public boolean getSelected() {
        return isSelected;
    }
    
    
    /**
     * Makes the checkbox deselected.
     *
     * @param event A deselect event.
     */
    private void setDeSelected(DeSelectEvent event) {
        ActionEvent actionEvent;
        int id = event.getId();
        if (id == getId()) {
            isSelected = !isSelected;
            if(isSelected) {
                actionEvent = new ActionEvent(this, 0, "start");
            }
            else actionEvent = new ActionEvent(this, 0, "stop");
            if (action != null) {
                action.actionPerformed(actionEvent);
            }
        }
    }

    @Override
    public void notify(CalendarEvent arg) {
        if (arg instanceof SelectedEvent) {
            setSelected((SelectedEvent) arg);
        }
        if (arg instanceof DeSelectEvent) {
            setDeSelected((DeSelectEvent) arg);
        }
    }
    
    @Override
    public void draw(Graphics g) {
        Point p = getLocation();

        Dimension d;
        if (labeled) {
            if (first) {
                g.setFont(font);
                textWidth = g.getFontMetrics().stringWidth(label);
                int textHeight = g.getFontMetrics().getHeight();
                int height = img.getHeight(null);
                
                if (textHeight > height) height = textHeight;
                
                //Set dimentions and bound for checkbox
                setSize(new Dimension(img.getWidth(null)+textWidth+4, height));
                first = false;
            }
            
            d = getSize();
            //Ths is done if the button is not consisting of an image
            Graphics2D g2d = (Graphics2D) g;
            //Sets antialiasing for smoother shapes
            g2d.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            //Create a rectangle
            g.setColor(getBackgroundColor());
            g.fillRect(p.x - textWidth - 4, p.y, d.width, d.height);
            //Set font color
            g.setColor(Color.BLACK);

            //Gets available fonts and sets a font
            g.setFont(font);
            //Gets the width and height of the string
            Rectangle2D stringBounds = g.getFontMetrics().getStringBounds(label, g);
            g2d.drawString(label, p.x - textWidth - 2, p.y + 2 + (int) (d.height / 2 - stringBounds.getCenterY()));
            
            if (isSelected) {
                //Draws the selected version of the image
                g.drawImage(selectedImg, p.x, p.y, null);
            }
            
            else {
                //Draws the "normal" version of the image
                g.drawImage(img, p.x, p.y, null);
            }
        }
        
        else {
            d = getSize();
            g.setColor(getBackgroundColor());
            g.fillRect(p.x, p.y, d.width, d.height);
            
            if (isSelected) {
                //Draws the selected version of the image
                g.drawImage(selectedImg, p.x, p.y, null);
            }
            
            else {
                //Draws the "normal" version of the image
                g.drawImage(img, p.x, p.y, null);
            }
        }
    }


}
