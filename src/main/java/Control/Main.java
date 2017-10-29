package Control;

import Model.SixteenBitModel;
import View.MenuBar;
import View.MyGlassPane;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Creates the maincontroller which starts up everything
 * @author Group 6
 */
class Main {


    /**
     * Starting point of the entire program
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here 
        try {
            System.setProperty("apple.laf.useScreenMenuBar", "true");
            System.setProperty("com.apple.mrj.application.apple.menu.about.name", "16 Bit Calendar");
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | UnsupportedLookAndFeelException | IllegalAccessException | InstantiationException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        SixteenBitModel model = SixteenBitModel.getInstance();
        AnimationEngine animation = new AnimationEngine();
        model.setAnimationEngine(animation);
        
        new ComponentLoader();
        animation.setDayCardList();

        model.fillModel();

        
        // Read in ALL tasks and events from the XML-file to the model
        model.setTasksAll(model.getXMLHandler().getTasks());
        model.setEventsAll(model.getXMLHandler().getEvents());
        model.setTasks(model.getXMLHandler().getTasks());

        // MyGlassPane myGlassPane = new MyGlassPane(new JButton("herpa"));
        JFrame f = new JFrame("16 Bit Calendar");
        f.setSize(980, 620);
        model.setFrame(f);
        MenuBar menu = new MenuBar();
        model.setMenuBar(menu);
        f.setJMenuBar(menu.getMenuBar());
        
          f.addWindowListener (new WindowAdapter() {
              
            final SixteenBitModel model = SixteenBitModel.getInstance();
            /**
            * Listener that stores frame size and position and shut down application on window close.
            */
            @Override
            public void windowClosing(WindowEvent arg0) {
                model.saveModelXML();
                System.exit(0);
            }
        });

        Canvas canvas = new Canvas();
        model.setCanvas(canvas);
        MyGlassPane myGlassPane = new MyGlassPane();
        model.setGlassPane(myGlassPane);
        animation.setGlassPane(myGlassPane);

        //panel.setPreferredSize(new Dimension(300, 300));
        f.getContentPane().add(canvas);
        f.setGlassPane(myGlassPane);
        f.getGlassPane().setVisible(true);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
    }
}
