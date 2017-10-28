/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Control.Interface;

/**
 * Interface that defines a custom observable. 
 * The custom observable can take any type and notify observers with 
 * that type.
 * @param <T> An arbitrary type observable
 * @author fredrikmakila
 */
public interface Observable<T> extends ObservableRegistration<T> {  
    /**
     * Notifies all the observers attached to this observable.
     * @param event The event that will be sent to the observers.
     */
    void notifyObservers(T event);
}


