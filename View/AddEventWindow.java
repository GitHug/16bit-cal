/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Control.Actions.Action;
import Model.Datatypes.CategoryObject;
import Model.SixteenBitModel;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.swing.*;

/**
 * Initiates the window where the user adds a event.
 * The window makes it possible for the user to fill in information about a event.
 * @author Robert
 */
public class AddEventWindow extends JFrame {
    
    private JTextField tfEventName;
    private JTextField tfEventInfo;
    private JLabel lbEventName;
    private JLabel lbEventInfo;
    private JLabel lbEventCat;
    private JLabel lbSTime;
    private JLabel lbETime;
    private DayCardWindow d;
    private String[] stringarray = {"Standard"};
    private DefaultComboBoxModel comboboxmodel = new DefaultComboBoxModel(stringarray);
    private JComboBox combobox;
    private JComboBox comboboxSH;
    private JComboBox comboboxSM;
    private JComboBox comboboxEH;
    private JComboBox comboboxEM;
    private SixteenBitModel model = SixteenBitModel.getInstance();
    private Date eTime;
    private String[] hours = {
        "00","01","02","03","04","05","06","07","08","09","10","11","12",
        "13","14","15","16","17","18","19","20","21","22","23"};
    
    private String[] minutes = {
        "00","01","02","03","04","05","06","07","08","09","10","11","12",
        "13","14","15","16","17","18","19","20","21","22","23","24","25",
        "26","27","28","29","30","31","32","33","34","35","36","37","38",
        "39","40","41","42","43","44","45","46","47","48","49","50","51",
        "52","53","54","55","56","57","58","59"};
    private DateFormat tf = new SimpleDateFormat("HH:mm");
    
    
    
    
    /**
     * Constructor for the class. 
     * @param dcw the DayCardWindow which contains the event list.
     */
    public AddEventWindow(DayCardWindow dcw) {
        this.setResizable(false);
        //setAlwaysOnTop(true);
        d = dcw;
        addComponentsToAddPane(); 
    }
    
    
    
    /**
     * Adds all the components(buttons etc.) to the AddEventWindow.
     */
    private void addComponentsToAddPane() {
        Container paneAdd = this.getContentPane();
        paneAdd.setLayout(new GridBagLayout());
	GridBagConstraints c = new GridBagConstraints();
        c.weightx = 0.5;
        c.weighty = 0.5;    
        c.fill = GridBagConstraints.HORIZONTAL;
        
        // Event Name option
        lbEventName = new JLabel("Event name");
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        paneAdd.add(lbEventName, c);
    
        // Label for event name
        tfEventName = new JTextField(20);
        c.gridx = 1;
        c.gridy = 0;
        c.gridwidth = 2;
        paneAdd.add(tfEventName, c);
        
        // Event Info option
        lbEventInfo = new JLabel("Event Info");
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        paneAdd.add(lbEventInfo, c);
    
        // Label for event info
        tfEventInfo = new JTextField(20);
        c.gridx = 1;
        c.gridy = 1;
        c.gridwidth = 2;
        paneAdd.add(tfEventInfo, c);
        
        // event Category
        lbEventCat = new JLabel("Event Category ");
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 1;
        paneAdd.add(lbEventCat, c);
        
        
        combobox = new JComboBox(comboboxmodel);
        // Get all the saved Categories and add it to the combobox
        ArrayList<CategoryObject> tempList = model.getXMLHandler().getCategories();
        for(int i = 0; i<tempList.size();i++) {
            if(!tempList.get(i).getName().equals("Standard")){
                combobox.addItem(tempList.get(i).getName());
            }
        }
        c.gridx = 1;
        c.gridy = 2;
        c.gridwidth = 2;
        paneAdd.add(combobox, c);
        
        // Start time label
        lbSTime = new JLabel("Event Start Time");
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 1;
        paneAdd.add(lbSTime, c);
        
        //Start time comboboxes
        comboboxSH = new JComboBox(hours);
        c.gridx = 1;
        c.gridy = 3;
        paneAdd.add(comboboxSH,c);
        
        comboboxSM = new JComboBox(minutes);
        c.gridx = 2;
        c.gridy = 3;
        paneAdd.add(comboboxSM,c);
        
        // End time label
        lbETime = new JLabel("Event End Time");
        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 1;
        paneAdd.add(lbETime, c);
        
        //End time comboboxes
        comboboxEH = new JComboBox(hours);
        //comboboxSH.setSelectedIndex(cal.get(Calendar.HOUR_OF_DAY));
        c.gridx = 1;
        c.gridy = 4;
        paneAdd.add(comboboxEH,c);
        
        comboboxEM = new JComboBox(minutes);
        //comboboxSM.setSelectedIndex(cal.get(Calendar.MINUTE));
        c.gridx = 2;
        c.gridy = 4;
        paneAdd.add(comboboxEM,c);
        
        //Add new Category button
        Action createCatAction = new Action(d, this, "createECat");
        JButton buttonCreateCat = new JButton(createCatAction);
        buttonCreateCat.setText("Create new Category");
        c.gridx = 2;
        c.gridy = 5;
        paneAdd.add(buttonCreateCat, c);
        
        // OK button in AddEventWindow. Saves the information about the new event.
        Action OkAction = new Action(d, this, "okaew");
        JButton buttonOk = new JButton(OkAction);
        buttonOk.setText("Ok");
        c.gridx = 0;
        c.gridy = 5;
        paneAdd.add(buttonOk, c);
        
        // Cancel button in AddEventWindow, cancel the add event operation.
        // Closes the AddEventWindow. 
        Action CancelAction = new Action(this, "cancelaew");
        JButton buttonCancel = new JButton(CancelAction);
        buttonCancel.setText("Cancel");
        c.gridx = 1;
        c.gridy = 5;
        paneAdd.add(buttonCancel, c);
    }
    
    /**
     * Adds a new category to the combobox.
     * 
     * @param cat The category to be added
     */
    public void addCategory(String cat) {
        combobox.addItem(cat);
    }
    
    /**
     * Returns the event name.
     * @return          Event name string from text field.
     */
    public String getEventName() {
        return tfEventName.getText();
    }
    
    /**
     * Returns the event info.
     * @return          Event info string from text field.
     */
    public String getEventInfo() {
        return tfEventInfo.getText();
    }
    
    /**
     * Returns the event category.
     * @return          Event Category string from text field.
     */
    public String getEventCat() {
        return (String)combobox.getSelectedItem();
    }
    
    
    /**
     * Returns the start time of the task.
     * @return          Time from combo boxes.
     */
    public Date getEventStartTime() {
        String H = hours[comboboxSH.getSelectedIndex()];
        String M = minutes[comboboxSM.getSelectedIndex()];
        String sTime = (H+":"+M);
        try { 
            eTime = tf.parse(sTime);
        } 
        catch(Exception e) {
        }
        return eTime;
    }
    
    /**
     * Returns the end time of the task.
     * @return          Time from combo boxes.
     */
    public Date getEventEndTime() {
        String H = hours[comboboxEH.getSelectedIndex()];
        String M = minutes[comboboxEM.getSelectedIndex()];
        String sTime = (H+":"+M);
        try { 
            eTime = tf.parse(sTime);
        } 
        catch(Exception e) {
        }
        return eTime;
    }
    
}
