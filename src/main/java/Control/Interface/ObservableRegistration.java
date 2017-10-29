/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Control.Interface;

/**
 * Interface that defines methods for registering observers of arbitrary type.
 * @param <CalendarEvent> The type of the event that the observers will listen to.
 * @author fredrikmakila
 */
public interface ObservableRegistration<CalendarEvent> {

    /**
     * Adds an observer to the list of observers attached to the observable.
     * @param o An observer of arbitrary type
     */
    void addObserver(Observer<CalendarEvent> o);

}
