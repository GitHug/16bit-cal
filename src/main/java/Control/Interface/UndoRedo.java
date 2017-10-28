/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Control.Interface;

/**
 * An Interface for redo and undo stuff
 * @author Kenny
 */
public interface UndoRedo {
    
    /**
     * Undoes an action
     */
    public void undo();
    
    /**
     * Redoes an action
     */
    public void redo();
    
}
