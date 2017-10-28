package View;

import Control.Actions.Action;

import javax.swing.*;
import java.awt.*;

/**
 * A class for adding a new taskcategory in a new window
 * @author lobban
 */
public class AddCatWindow extends JFrame {
    
    
    private JColorChooser colchoose = new JColorChooser();
    private JTextField catText;
    private AddWindow aw;
    private AddEventWindow aew;
    private Action saveCatAction;
    private boolean b = true;
    
    /**
     * Constructor for the class. 
     * @param aw the Addwindow class which contains category combobox.
     */
    public AddCatWindow(AddWindow aw) {
        this.aw = aw;
        this.setResizable(false);
        addComponentsToAddPane();
    }
    
    /**
     * Constructor for the class
     * @param aew the AddEventwindow class which contains category combobox.
     */
    public AddCatWindow(AddEventWindow aew) {
        this.aew = aew;
        this.setResizable(false);
        b = false;
        addComponentsToAddPane();
    }
    
    /**
     * Adds all the components(buttons etc.) to the AddCatWindow.
     */
    private void addComponentsToAddPane() {
        Container paneCat = this.getContentPane();
        paneCat.setLayout(new GridBagLayout());
	GridBagConstraints c = new GridBagConstraints();
        
        c.weightx = 0.5;
        c.weighty = 0.5;    
        c.fill = GridBagConstraints.HORIZONTAL;
        
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        
        paneCat.add(colchoose, c); 
        
        // Label for category name
        JLabel category = new JLabel("Category Name: ");
        c.gridx = 0;
	c.gridy = 1;
        c.gridwidth = 1;
        paneCat.add(category, c);
        
        // Textfield for category name.
        catText = new JTextField(20);
        c.gridx = 0;
	c.gridy = 2;
        c.gridwidth = 1;
        paneCat.add(catText, c);
     
        
        // Button for saving a customized category, saves name and collor and 
        // adds it to a JCombobox
        if (b) {
            saveCatAction = new Action(aw, this, "saveCat");
        }      
        else {
            saveCatAction = new Action(aew, this, "saveeCat");
        }
        JButton buttonAdd = new JButton(saveCatAction);
        buttonAdd.setText("Save Category");
        c.gridx = 0;
	c.gridy = 3;
        c.anchor = GridBagConstraints.PAGE_END;
        paneCat.add(buttonAdd, c);    
    }
    
    /**
     * Returns the user defined Category name
     * @return Category name
     */
    public String getCatName() {
        return catText.getText();
    }
    
    /**
     * Returns the user defined Category color
     * @return Category color
     */
    public Color getColor() {
        return colchoose.getColor();
    }
    
}
