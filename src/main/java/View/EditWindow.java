package View;

import Control.Actions.Action;
import Model.Datatypes.CategoryObject;
import Model.SixteenBitModel;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Initiates the window where the user edit a task.
 * The window makes it possible for the user to fill in information about a task.
 *
 * @author Robert
 */
public class EditWindow extends JFrame {

    private JTextField tfTaskName;
    private JTextField tfTaskInfo;
    private final DayCardWindow d;
    private final String[] priority = {"High", "Medium", "Low"};
    private final String[] stringarray = {"Standard"};
    private final DefaultComboBoxModel<String> comboboxmodel = new DefaultComboBoxModel<>(stringarray);
    private JComboBox<String> combobox;
    private final SixteenBitModel model = SixteenBitModel.getInstance();


    /**
     * Constructor for the class.
     *
     * @param dcw the DayCardWindow which contains the task list.
     */
    public EditWindow(DayCardWindow dcw) {
        this.setResizable(false);
        d = dcw;
        addComponentsToAddPane();
    }


    /**
     * Adds all the components(buttons etc.) to the EditWindow.
     */
    private void addComponentsToAddPane() {
        Container paneAdd = this.getContentPane();
        paneAdd.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 0.5;
        c.weighty = 0.5;
        c.fill = GridBagConstraints.HORIZONTAL;

        // Task Name option
        JLabel lbTaskName = new JLabel("Task name");
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        paneAdd.add(lbTaskName, c);

        // Label for task name
        tfTaskName = new JTextField(20);
        c.gridx = 1;
        c.gridy = 0;
        c.gridwidth = 2;
        paneAdd.add(tfTaskName, c);

        // Task Info option
        JLabel lbTaskInfo = new JLabel("Task Info");
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        paneAdd.add(lbTaskInfo, c);

        // Label for task info
        tfTaskInfo = new JTextField(20);
        c.gridx = 1;
        c.gridy = 1;
        c.gridwidth = 2;
        paneAdd.add(tfTaskInfo, c);

        // Task Category
        JLabel lbTaskCat = new JLabel("Task Category ");
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 1;
        paneAdd.add(lbTaskCat, c);


        combobox = new JComboBox<>(comboboxmodel);

        // Get all the saved Categories and add it to the combobox
        List<CategoryObject> tempList = model.getXMLHandler().getCategories();
        for (CategoryObject aTempList : tempList) {
            if (!aTempList.getName().equals("Standard")) {
                combobox.addItem(aTempList.getName());
            }
        }
        c.gridx = 1;
        c.gridy = 2;
        c.gridwidth = 2;
        paneAdd.add(combobox, c);

        // Label for task priority
        JLabel lbTaskPrio = new JLabel("Set Priority");
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 1;
        paneAdd.add(lbTaskPrio, c);

        // Combobox for choosing task priority
        JComboBox<String> comboboxprio = new JComboBox<>(priority);
        c.gridx = 1;
        c.gridy = 3;
        c.gridwidth = 2;
        paneAdd.add(comboboxprio, c);

        //Add new Category button
        Action createCatAction = new Action(d, this, "createCat");
        JButton buttonCreateCat = new JButton(createCatAction);
        buttonCreateCat.setText("Create new Category");
        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 3;
        paneAdd.add(buttonCreateCat, c);

        // OK button in EditWindow. Saves the information about the new task.
        Action OkAction = new Action(d, this, "okaw");
        JButton buttonOk = new JButton(OkAction);
        buttonOk.setText("Ok");
        c.gridx = 0;
        c.gridy = 6;
        c.anchor = GridBagConstraints.PAGE_END;
        paneAdd.add(buttonOk, c);

        // Cancel button in EditWindow, cancel the add task operation.
        // Closes the EditWindow. 
        Action CancelAction = new Action(this, "cancelaw");
        JButton buttonCancel = new JButton(CancelAction);
        buttonCancel.setText("Cancel");
        c.gridx = 1;
        c.gridy = 6;
        paneAdd.add(buttonCancel, c);
    }

    /**
     * Returns the task name.
     *
     * @return Task name string from text field.
     */
    public String getTaskName() {
        return tfTaskName.getText();
    }


    /**
     * Returns the task info.
     *
     * @return Task info string from text field.
     */
    public String getTaskInfo() {
        return tfTaskInfo.getText();
    }

    /**
     * Returns the task priority.
     *
     * @return Task Priority string from text field.
     */
    public String getTaskPrio() {
        return (String) combobox.getSelectedItem();
    }

}
