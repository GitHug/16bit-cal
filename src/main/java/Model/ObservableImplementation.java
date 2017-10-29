/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Model;

import Control.Interface.Observable;
import Control.Interface.Observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that implements the custom observable methods.
 * @param <T> An arbitrary type.
 * @author fredrikmakila
 */
public class ObservableImplementation<T> implements Observable<T>{

    //list of all the observers.
    private final List<Observer<T>> observers = new ArrayList<>();

    @Override
    public void addObserver(Observer<T> o) {
        this.observers.add(o);
    }

    @Override
    public void notifyObservers(T event) {  
        for(Observer<T> observer : observers) {
            observer.notify(event);
        }
    }

}
