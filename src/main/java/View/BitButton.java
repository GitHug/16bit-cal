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
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.net.URL;

/**
 * Class for construction of customized bit buttons
 *
 * @author fredrikmakila
 */
public class BitButton extends CustomComponent {

    private String text;
    private Color currentColor;
    private Image img;
    private Image selectedImg;
    private boolean drawImgButton = false;
    private ActionListener action;
    private boolean isSelected = false;
    private Font font;

    /**
     * Constructor to create a rectangular bit button.
     *
     * @param x The x position of the button
     * @param y The y position of the button
     * @param width The width of the button
     * @param height The height of the button
     * @param text The text of the button
     * @param id The id of the button
     */
    public BitButton(int x, int y, int width, int height, String text, int id) {
        super(x, y, width, height);
        this.text = text;
        setId(id);
        //Gets a font from the current system and sets the font size
        GraphicsEnvironment.getLocalGraphicsEnvironment();
        font = new Font("Arial", Font.BOLD, 12);
       
    }

    /**
     * Constuctor to create a button in the shape of an image.
     *
     * @param x The x position of the button
     * @param y The y position of the button
     * @param imgName The name of an image specified in the resource property
     * files.
     * @param id The id of the button
     */
    public BitButton(int x, int y, String imgName, int id) {
        super(x, y);
        setId(id);
        ResourceHandler rh = new ResourceHandler();
        String url = rh.getString(imgName);

        //Tries to load a selected version of the button
        try {
            URL imageURL = getClass().getClassLoader().getResource(url);

            if (imageURL == null) {
                throw new Exception("Image not found");
            }
            img = new ImageIcon(imageURL).getImage();
            String selected = imgName + "_selected";
            url = rh.getString(selected);
            imageURL = getClass().getClassLoader().getResource(url);
            if (imageURL == null) {
                throw new Exception("Image not found");
            }
            selectedImg = new ImageIcon(imageURL).getImage();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // drawing the button with the chosen size and image
        drawImgButton = true;
        setSize(new Dimension(img.getWidth(null), img.getHeight(null)));
        setBounds(x, y, img.getWidth(null), img.getHeight(null));

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

    /**
     * Method that is called when the button is clicked on.
     *
     * @param event A selected event.
     */
    private void setSelected(SelectedEvent event) {
        int selectedId = event.getId();
        if (selectedId == getId()) {
            isSelected = true;
            if (!drawImgButton) {
                currentColor = getBackgroundColor();
                setBackgroundColor(currentColor.darker());
            }
        }
    }

    @Override
    public void draw(Graphics g) {
        Point p = getLocation();
        Dimension d = getSize();
        //This is done if the button is an image
        if (drawImgButton) {
            if (isSelected) {
                //Draws the selected version of the image
                g.drawImage(selectedImg, p.x, p.y, null);
            } else {
                //Draws the "normal" version of the image
                g.drawImage(img, p.x, p.y, null);
            }
        } else {
            //Ths is done if the button is not consisting of an image
            Graphics2D g2d = (Graphics2D) g;
            //Sets antialiasing for smoother shapes
            g2d.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            //Create a rectangle
            g.setColor(getBackgroundColor());
            g.fillRect(p.x, p.y, d.width, d.height);
            //Set font color
            g.setColor(Color.BLACK);

            //Gets available fonts and sets a font
            g.setFont(font);
            //Gets the width and height of the string
            Rectangle2D stringBounds = g.getFontMetrics().getStringBounds(text, g);
            g2d.drawString(text, p.x + (int) (d.width / 2 - stringBounds.getCenterX()), p.y + (int) (d.height / 2 - stringBounds.getCenterY()));
        }
    }

    /**
     * Makes the button deselected.
     *
     * @param event A deselect event.
     */
    private void setDeSelected(DeSelectEvent event) {
        int id = event.getId();

        if (getId() == id && isSelected) {
            setBackgroundColor(currentColor);
            isSelected = false;
            if (action != null) {
                action.actionPerformed(null);
            }

        }
    }

    /**
     * Adds an action listener to this button.
     *
     * @param action The actionlistener to be attached to the button.
     */
    public void addActionListener(ActionListener action) {
        this.action = action;
    }


}
