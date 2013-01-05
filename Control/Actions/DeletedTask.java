package Control.Actions;

import Control.Interface.UndoRedo;
import Model.Datatypes.TaskObject;
import Model.SixteenBitModel;

/**
 * A class that keeps a record of one deleted task
 * @author Robin Horneman
 */
public class DeletedTask implements UndoRedo{
    private TaskObject task;
    private SixteenBitModel model = SixteenBitModel.getInstance();
    
    /**
     * Constructor
     * @param task the task that was deleted (for redoing purposes)
     */
    public DeletedTask(TaskObject task){
        this.task = task;
    }

    @Override
    /**
     * The undo method that adds the deleted task again
     */
    public void undo() {
        model.addTask(task);
    }

    @Override
    /**
     * The redo method that removes the deleted task again
     */
    public void redo() {
        model.removeTask(task);
    }
    
}
