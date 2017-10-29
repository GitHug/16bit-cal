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
    void undo();
    
    /**
     * Redoes an action
     */
    void redo();
    
}
