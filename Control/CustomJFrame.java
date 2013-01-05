package Control;

import java.awt.event.HierarchyBoundsListener;
import java.awt.event.HierarchyEvent;
import javax.swing.JFrame;

/**
 * Our Custom JFrame that controls what happens when
 * we resize the window.
 * @author Robin Horneman
 */
public class CustomJFrame extends JFrame{
    
    /**
     * Constructor for this class.
     * @param name The name of the frame.
     */
    public CustomJFrame(String name){
        super(name);
        this.getContentPane().addHierarchyBoundsListener(new HierarchyBoundsListener(){
 
            @Override
            public void ancestorMoved(HierarchyEvent e) {
                System.out.println(e);             
            }
            @Override
            public void ancestorResized(HierarchyEvent e) {
                System.out.println(e);
                 
            }          
        });
    }
    
}
