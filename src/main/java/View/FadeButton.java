/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;


/**
 * Creates a button that will have an animation that fades the button
 * @author Kenny
 */

public class FadeButton extends JButton implements ActionListener
{

    float alpha = 1.0f;
    Timer timer;
    int animationDuration = 2000;
    long animationStartTime;
    BufferedImage buttonImage = null;
    
    
    /**
     * Constructor to create the button.
     * 
     * @param label The string to be displayed for the button
     */
    public FadeButton(String label)
    {
        super(label);
        setOpaque(false);
        timer = new Timer(30, this);
        addActionListener(this);
        
    }
    
    
    /**
     * Constructor to create an image for the button graphics.
     * 
     * @param g The graphics.
     */
    @Override
    public void paint(Graphics g) {
 
    if (buttonImage == null || buttonImage.getWidth() != getWidth()
        || buttonImage.getHeight() != getHeight()) {
      buttonImage = getGraphicsConfiguration().createCompatibleImage(getWidth(), getHeight());
    }
    Graphics gButton = buttonImage.getGraphics();
    gButton.setClip(g.getClip());
    
    // for painting the button.
    super.paint(gButton);

    // Adding the translucence to the button graphic
    Graphics2D g2d = (Graphics2D) g;
    AlphaComposite newComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
    g2d.setComposite(newComposite);

    g2d.drawImage(buttonImage, 0, 0, null);
  }
    /**
     * This method receives a click enables the start and stop animation
     * @param ae action event.
     */
    
    @Override
    public void actionPerformed(ActionEvent ae)
    {
        if (ae.getSource().equals(this))
        {
            if (!timer.isRunning())
            {
                // code for when the button is fading
                animationStartTime = System.nanoTime() / 1000000;
                this.setText("Stop Fade ME");
                timer.start();
            }
            
            else {
                // code for when the button is not fading
                timer.stop();
                this.setText("Fade Me");
                alpha = 1.0f;
            }
        }
            else {
            long currentTime = System.nanoTime() / 1000000;
            long totalTime = currentTime - animationStartTime;
            if (totalTime > animationDuration) 
            {
                animationStartTime = currentTime;
        }
            float fraction = (float) totalTime / animationDuration;
            fraction = Math.min(1.0f, fraction);
            
            // modifying the alpha level so it looks like the button is fading
            alpha = Math.abs(1 - (2 * fraction));
            repaint();
            
        }
    } 
}
