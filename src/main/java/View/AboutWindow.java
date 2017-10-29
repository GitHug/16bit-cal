/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Control.Actions.Action;

import javax.swing.*;
import java.awt.*;

/**
 * Initiates the window where the user can view information about the application.
 * @author Kenny
 */
@SuppressWarnings("MagicConstant")
public class AboutWindow extends JFrame {


    /**
 * Class constructor for the About Window.
 */
    
    public AboutWindow() {
        
        this.setResizable(false);
        addComponentsToAddPane(this.getContentPane());
        
    }
    
/**
* Adds all the labels to the About Window.
* @param paneAdd the panel that will contain the About Window
* 
*/
    
    private void addComponentsToAddPane(Container paneAdd) {
        
       
        paneAdd.setLayout(new GridBagLayout());
	    GridBagConstraints c = new GridBagConstraints();
        c.weightx = 0.5;
        c.weighty = 0.5;    
        c.fill = GridBagConstraints.HORIZONTAL;
        
        // Label for info about the calendar and group
        JLabel lbGroupName = new JLabel("A Calendar made by Group 6");
        Font f = lbGroupName.getFont();
        lbGroupName.setFont(f.deriveFont(f.getStyle() ^ Font.BOLD));
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        paneAdd.add(lbGroupName, c);
        
        // Label for group member names
        JLabel lbFredrik = new JLabel("  Fredrik Mäkilä");
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 1;
        paneAdd.add(lbFredrik, c);
        
        // Label for group member names
        JLabel lbKenny = new JLabel("  Kenny Pussinen");
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 1;
        paneAdd.add(lbKenny, c);
        
        // Label for group member names
        JLabel lbRobert = new JLabel("  Robert Lööf");
        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 1;
        paneAdd.add(lbRobert, c);
        
        // Label for group member names
        JLabel lbRobin = new JLabel("  Robin Horneman");
        c.gridx = 0;
        c.gridy = 5;
        c.gridwidth = 1;
        paneAdd.add(lbRobin, c);
        
        // Random Label
        JLabel lbAbout = new JLabel("Awsome");
        Font g = lbAbout.getFont();
        lbAbout.setFont(g.deriveFont(f.getStyle() ^ Font.BOLD));
        c.gridx = 0;
        c.gridy = 7;
        c.gridwidth = 1;
        paneAdd.add(lbAbout, c);
        
        // Ok button for about window, closes the about window
        Action OkAction = new Action("okabout");
        JButton buttonOk = new JButton(OkAction);
        buttonOk.setText("Ok");
        c.gridx = 0;
        c.gridy = 8;
        c.fill = GridBagConstraints.NONE;
        paneAdd.add(buttonOk, c);
    }
}
