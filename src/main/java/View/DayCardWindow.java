package View;

import Model.Datatypes.EventObject;
import Model.Datatypes.TaskObject;
import Model.SixteenBitModel;
import Utils.OsUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.List;

/**
 * Initiates the window where the user can view tasks.
 *
 * @author Robert
 */
public class DayCardWindow extends JFrame {

    private final DefaultTableModel tableModel = new DefaultTableModel();
    private final JTable table = new JTable();
    private final Point point = new Point();


    /**
     * Constructor for the class.
     *
     * @param width The width of the window
     * @param height The height of the window
     */
    public DayCardWindow(int width, int height) {
        //this.setResizable(false);
        setUndecorated(true);
        //setAlwaysOnTop(true);

        setBackground(new Color(0, 0, 0, 0));
        setSize(new Dimension(600, 400));

        setSize(width, height);

        JPanel panel = new JPanel() {

            /**
             * Constructor to add a gradient background.
             *
             * @param g The graphics.
             */
            @Override
            protected void paintComponent(Graphics g) {
                if (g instanceof Graphics2D) {
                    final int R = 240;
                    final int G = 240;
                    final int B = 240;

                    Paint p = new GradientPaint(0.8f, 0.8f, new Color(R, G, B, 40),
                            0.8f, getHeight(), new Color(R, G, B, 255), true);
                    Graphics2D g2d = (Graphics2D) g;
                    g2d.setPaint(p);
                    g2d.fillRect(0, 0, getWidth(), getHeight());

                }
            }
        };
        setContentPane(panel);
        addComponentsToPane();

        addMouseListener(new MouseAdapter() {

            /**
             * Method that is called everytime the mouse is pressed on the
             * DayCardWindow.
             *
             * @param e A mouse pressed event
             */
            @Override
            public void mousePressed(MouseEvent e) {
                if (!e.isMetaDown()) {
                    // gets the points x, y, where the mouse is pressed
                    point.x = e.getX();
                    point.y = e.getY();
                }
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {

            /**
             * Method that is called everytime the mouse is pressed and dragged
             * on the DayCardWindow.
             *
             * @param e A mouse dragged event
             */
            @Override
            public void mouseDragged(MouseEvent e) {
                if (!e.isMetaDown()) {
                    Point p = getLocation();
                    // sets the new location for the window when the 
                    // mouse is dragged. 
                    if (OsUtils.isWindows()) {
                        setLocation(p.x + e.getX() - point.x,
                                p.y + e.getY() - point.y);
                    }

                }
            }
        });
    }

    /**
     * Adds all the components(buttons etc.) to the DayCardWindow.
     */
    private void addComponentsToPane() {
        Container pane = this.getContentPane();
        pane.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        SixteenBitModel model = SixteenBitModel.getInstance();
        List<TaskObject> tasklist = new ArrayList<>();
        List<EventObject> eventlist = new ArrayList<>();

        //Getting list of tasks
        if (model.getTasks() != null) {
            tasklist = model.getTasks();
        }
        //Getting list of events
        if (model.getEvents() != null) {
            eventlist = model.getEvents();
        }
        
        tableModel.setRowCount(0);
        tableModel.setColumnCount(0);
        tableModel.addColumn("TASKS");
        tableModel.addColumn("EVENTS");
        
        //Fill table
        int minimumRows;
        if(tasklist.size()<= eventlist.size()){
            minimumRows = tasklist.size();
        }
        else{
            minimumRows = eventlist.size();
        }
        
        for (int i = 0; i < minimumRows; i++) {
            tableModel.addRow(new Object[]{tasklist.get(i).getName(), eventlist.get(i).getName()});
            System.out.println("Adding " + tasklist.get(i).getName() + " to tablemodel");
        }
        if(tasklist.size()> eventlist.size()){
            for(int i = minimumRows; i< tasklist.size(); i++){
                tableModel.addRow(new Object[]{tasklist.get(i).getName(), ""});
            }
        }
        else if(eventlist.size()> tasklist.size()){
            for(int i = minimumRows; i< eventlist.size(); i++){
                tableModel.addRow(new Object[]{"", eventlist.get(i).getName()});
            }
        }

        System.out.println("Setting tableModel");
        table.setModel(tableModel);
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            System.out.println("Row " + i + " in tableModel: " + tableModel.getValueAt(i, 0));
            table.setRowHeight(i, 32);
        }
        Dimension d = new Dimension(650, 300);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(d);

        table.setFillsViewportHeight(true);
        table.setDefaultRenderer(Object.class, new CustomTableCellRenderer());

        c.fill = GridBagConstraints.BOTH;

        pane.add(scrollPane, c);
        System.out.println("ScrollPane dims: " + scrollPane.getSize());
        System.out.println("Table dims: " + table.getSize());

        c.anchor = GridBagConstraints.PAGE_START;

        ButtonPanel buttonpanel = new ButtonPanel(this);
        c.fill = GridBagConstraints.NONE;
        c.gridy = 2;
        c.weighty = 1.0;
        c.anchor = GridBagConstraints.PAGE_END;
        pane.add(buttonpanel, c);

    }

    /**
     * Removes the contents of content pane, adds new content to pane and
     * repaints the DayCardWindow.
     */
    public void updateGUI() {
        this.getContentPane().removeAll();
        addComponentsToPane();
        this.validate();
        this.repaint();
    }

    /**
     * Class to create a custom cell renderer.
     */
    class CustomTableCellRenderer extends DefaultTableCellRenderer {

        private final SixteenBitModel model = SixteenBitModel.getInstance();
        private List<TaskObject> taskobjects;
        private List<EventObject> eventobjects;

        /**
         * Method that defines what will be shown in the cells. Automatically
         * called from the JTable table.
         *
         * @param table The table that is asking the renderer to draw
         * @param obj the value of the cell to be rendered. It is up to the
         * specific renderer to interpret and draw the value. For example, if
         * value is the string "true", it could be rendered as a string or it
         * could be rendered as a check box that is checked. null is a valid
         * value
         * @param isSelected true if the cell is to be rendered with the
         * selection highlighted; otherwise false
         * @param hasFocus if true, render cell appropriately. For example, put
         * a special border on the cell, if the cell can be edited, render in
         * the color used to indicate editing
         * @param row the row index of the cell being drawn. When drawing the
         * header, the value of row is -1
         * @param column the column index of the cell being drawn
         * @return component
         */
        @Override
        public Component getTableCellRendererComponent(JTable table,
                Object obj, boolean isSelected, boolean hasFocus, int row, int column) {
            Component cell = super.getTableCellRendererComponent(
                    table, obj, isSelected, hasFocus, row, column);
            taskobjects = model.getTasks();
            eventobjects = model.getEvents();
            System.out.println("Column number is: "+column);
            if(column==0){
                for (TaskObject taskobject : taskobjects) {
                    if (obj != null && obj.toString().equals(taskobject.getName())) {
                        //System.out.println("Found task with color: " + taskobjects.get(i).getCategory().getColor());
                        return new TaskPanel(taskobject);
                    }
                }
            }
            if(column==1){
                for (EventObject eventobject : eventobjects) {
                    if (obj != null && obj.toString().equals(eventobject.getName())) {
                        //System.out.println("Found event with color: " + eventobjects.get(i).getCategory().getColor());
                        return new EventPanel(eventobject);
                    }
                }
            }
            //if no match is found (unlikely) set TaskPanel with index value
            return cell;

        }
    }
    /*
     * public class CustomListCellRenderer extends DefaultListCellRenderer {
     *
     * private SixteenBitModel model = SixteenBitModel.getInstance(); private
     * ArrayList<CategoryObject> catobjects; private ArrayList<TaskObject>
     * taskobjects;
     *
     * public CustomListCellRenderer() {
     *
     * }
     *
     * @Override public Component getListCellRendererComponent(JList list,
     * Object value, int index, boolean isSelected, boolean cellHasFocus) {
     * taskobjects = model.getTasks(); int r = 0; int s; //Parse the name from
     * value while(value.toString().charAt(r)!= ','){ r++; } r=r+8; s=r;
     * while(value.toString().charAt(s)!=','){ s++; } //set value to the name of
     * the task in value value = value.toString().substring(r, s); value =
     * value.toString().trim(); //Check all tasks for(int i=0;
     * i<taskobjects.size();i++){
     * if(value.toString().equals(taskobjects.get(i).getName())){
     * System.out.println("Found task with color:
     * "+taskobjects.get(i).getCategory().getColor()); return new
     * TaskPanel(taskobjects.get(i)); } } //if no match is found (unlikely) set
     * TaskPanel with index value return new TaskPanel(taskobjects.get(index));
     * } }
     */
}
