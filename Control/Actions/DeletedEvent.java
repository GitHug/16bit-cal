package Control.Actions;

import Control.Interface.UndoRedo;
import Model.Datatypes.EventObject;
import Model.SixteenBitModel;

/**
 * A class that keeps a record of one deleted event
 * @author Robin Horneman
 */
public class DeletedEvent implements UndoRedo{
    private EventObject event;
    private SixteenBitModel model = SixteenBitModel.getInstance();
    
    /**
     * Constructor
     * @param event the event that was deleted (for redoing purposes)
     */
    public DeletedEvent(EventObject event){
        this.event = event;
    }

    @Override
    /**
     * The undo method that adds the deleted task again
     */
    public void undo() {
        model.addEvent(event);
    }

    @Override
    /**
     * The redo method that removes the deleted task again
     */
    public void redo() {
        model.removeEvent(event);
    }
    
}
