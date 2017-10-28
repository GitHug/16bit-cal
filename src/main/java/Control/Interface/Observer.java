/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Control.Interface;

/**
 * Defines an arbitrary type observer. 
 * @param <T> An arbitrary type
 * @author fredrikmakila
 */
public interface Observer<T> {
    /**
     * Method that receives an arbitrary type event.
     * @param event The event that is received.
     */
    void notify(T event);
}
