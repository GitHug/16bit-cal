package Control.Actions;

import Control.Interface.UndoRedo;

/**
 * Manages a queue of actions to perform undo and/or redo operations on.
 * @author Kenny
 */
public class UndoRedoManager {
    
    // the current index node
    private Node currentIndex = null;
    // the present node far left node
    private final Node parentNode = new Node();
    
    /**
     * Creates a new UndoRedoManager object which is from the start empty
     */    
    public UndoRedoManager()
    {
        currentIndex = parentNode;
    }

    /**
     * Adds an action to the manager
     * @param undoredo the undoredo object to be added
     */    
    public void addUndoRedo(UndoRedo undoredo)
    {
        Node node = new Node(undoredo);
        currentIndex.right = node;
        node.left = currentIndex;
        currentIndex = node;
    }
    
    /**
     * Determines if an undo can be performed. 
     * @return true if an undo can be performed else false
     */    
    public boolean canUndo()
    {
        return currentIndex != parentNode;
    }
    
    /**
     * Determines if a redo can be performed. 
     * @return true if a redo can be performed else false
     */    
    public boolean canRedo()
    {
        return currentIndex.right != null;
    }
    
    /**
     * Undoes an action at the current index.
     * @throws IllegalStateException if canUndo returns false
     */    
    public void undo()
    {
        if ( !canUndo())
        {
            throw new IllegalStateException("Cannot undo. Index is out of range");
        }

        if (currentIndex != null && currentIndex.undoredo != null) {
            currentIndex.undoredo.undo();
        }
        moveLeft();
    }
    
    /**
     * Moves the internal pointer of the backed linked list to the left.
     * @throws IllegalStateException if the left index is null
     */
    private void moveLeft()
    {
        if ( currentIndex.left == null ) 
        {
            throw new IllegalStateException("Internal index set to null ");
        }
        currentIndex = currentIndex.left;
    }
    
    /**
     * Moves the internal pointer of the backed linked list to the right.
     */
    private void moveRight()
    {
        if ( currentIndex.right == null ) 
        {
            throw new IllegalStateException("Internal index set to null ");
        }
        currentIndex = currentIndex.right;
    }
     
     /**
      * Redoes an action at the current index.
      * @throws IllegalStateException if canRedo returns false.
      */     
     public void redo()
     {
        if ( !canRedo() )
        {
            throw new IllegalStateException("Cannot redo. Index is out of range");
        }
        
        moveRight();
         if (currentIndex != null && currentIndex.undoredo != null) {
             currentIndex.undoredo.redo();
         }
        
     }
     
     /**
      * Inner class to implement a doubly linked list for our queue of UndoRedo
      * @author Kenny
      */     
    private class Node 
    {
        private Node left = null;
        private Node right = null;
        
        private final UndoRedo undoredo;
        
        /**
         * Constructor
         * @param c the current undoredo object in the node
         */
        Node(UndoRedo c)
        {
            undoredo = c;
        }
        
        /**
         * Constructor where the node is empty
         */
        Node()
        {
            undoredo = null;
        }
    }
     
}
