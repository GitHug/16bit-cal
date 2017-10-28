/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Control.Interface;

/**
 * Interface that defines methods for registering observers of arbitrary type.
 * @param <T> The type of the event that the observers will listen to.
 * @author fredrikmakila
 */
public interface ObservableRegistration<T> {

    /**
     * Adds an observer to the list of observers attached to the observable.
     * @param o An observer of arbitrary type
     */
    void addObserver(Observer<T> o);
    /**
     * Removes an observer from the list of observers.
     * @param o An observer of arbitrary type
     */
    void removeObserver(Observer<T> o);
    /**
     * Completely removes all observers from the list of observers.
     */
    void removeAllObservers();

}
