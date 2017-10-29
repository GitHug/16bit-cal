/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Model.Datatypes;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Class that creates a circular arraylist of arbitrary type. 
 * @param <T> The type of the objects in the arraylist. 
 * @author fredrikmakila
 */
public class CircularArrayList<T> extends ArrayList {
    private int index = 0;
    private final ArrayList<T> list = new ArrayList<>();
    
    /**
     * Empty constructor for this class.
     */
    public CircularArrayList() {
    }
    

    /**
     * Constructor that takes an arbitrary number of objects of arbitrary type and adds them
     * to the list.
     * @param o Arbitrary type objects that are added to the list.
     */
    @SafeVarargs
    public CircularArrayList(T ... o) {
        list.addAll(Arrays.asList(o));
    }
    
    /**
     * Increments an index. 
     * if the index gets bigger than the size of the list, then the index is reset to 0.
     * @param i The index to be incremented.
     */
    private void increment(int i) {
        i++;
        if(i > list.size()-1) {
            index = 0;
        }
        else index = i;
    }
    
    /**
     * Decrements an index. 
     * if the index gets smaller than zero, then the index is set to the size of the 
     * list.
     * @param i The index to be decremented.
     */
    private void decrement(int i) {
        i--;
        if(i < 0) {
            index = list.size() - 1;
        }
        else index = i;
    }
    
    /**
     * Decrements the index by 1
     */
    public void decrement() {
        decrement(index);
    }

    
    /**
     * Returns the size of this circular arraylist.
     * @return the size of the list.
     */
    @Override
    public int size() {
        return list.size();
    }
    
    /**
     * Tostring method for this class.
     * @return a String representation of the list.
     */
    @Override
    public String toString() {
        return list.toString();
    }
    

    
    
    /**
     * Increments the index and return the object in the position in the list specified by the index. 
     * @return The object at the position specified by the index. 
     */
    public T incrementAndGet() {
        increment(index);
        return list.get(index);
    }
    
    /**
     * Decrements the index and return the object in the position in the list specified by the index. 
     * @return The object at the position specified by the index. 
     */
    public T decrementAndGet() {
        decrement(index);
        return list.get(index);
    }
    
    /**
     * Gets an object from the list specified by i.
     * @param i The position in the list from where the object should be returned.
     * @return The object at position i in the list.
     */
    @Override
    public T get(int i) {
        return list.get(i);
    }
    
    /**
     * Sets the start of the index. 
     * @param i The new index.
     */
    public void setStartIndex(int i) {
        index = i;
    }
    
    /**
     * Gets the current value of the index.
     * @return the current value of the index. 
     */
    public int getIndex() {
        return index;
    }
}
