/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Control.Actions.Action;

import javax.swing.*;
import java.awt.event.KeyEvent;

/**
 * Class that creates a menubar. 
 * 
 * @author Kenny
 */
public class MenuBar extends JMenuBar {
    
private final JMenuBar menubar = new JMenuBar();


/**
 * Constructor that creates the menubar.
 */
public MenuBar () {
  
        //Set menu bar
     

        // Adds the file menu and set mnemonic for it, alt+F
        JMenu filemenu = new JMenu("File");
        filemenu.setMnemonic(KeyEvent.VK_F);
        // Adds the settings menu and set mnemonic for it, alt+T
        JMenu settingsmenu = new JMenu("Settings");
        settingsmenu.setMnemonic(KeyEvent.VK_S);
        // Adds the help menu and set mnemonic for it, alt+H
        JMenu helpmenu = new JMenu("Help");
        helpmenu.setMnemonic(KeyEvent.VK_H);
        

        
        // Quit program in file menu
        Action exitAction = new Action("exit");
        JMenuItem fileItem1 = new JMenuItem(exitAction);
        fileItem1.setText("Exit");
        fileItem1.setToolTipText("Exits the calendar");
        
        // Help item in help menu
        Action aboutAction = new Action("aboutw");
        JMenuItem helpItem1 = new JMenuItem(aboutAction);
        helpItem1.setText("About");
        helpItem1.setToolTipText("About the calendar");
        
        // Guide item in help menu
        Action guideAction = new Action("help");
        JMenuItem helpItem2 = new JMenuItem(guideAction);
        helpItem2.setText("Guide");
        helpItem2.setToolTipText("Starts the help guide");

        
        // Adding file items to file menu
        filemenu.add(fileItem1);
        
        
        // Adding help items to help menu
        helpmenu.add(helpItem1);
        helpmenu.add(helpItem2);
        
        // Adding menus to menu bar
        menubar.add(filemenu);
        menubar.add(settingsmenu);
        menubar.add(helpmenu);
        

    }

/**
 * Getter for Menubar
 * @return  menu bar
 */

public JMenuBar getMenuBar()
{
    return menubar;
}
 
}

