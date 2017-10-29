/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Model.Datatypes;

import java.util.Arrays;
import java.util.List;

/**
 * Class that defines a priority. 
 * @author fredrikmakila
 */
public class Priority {
        private String prio = null;
    /**
        *  Creates a priority item "low". 
        */
        public static final Priority LOW = new Priority("low");
        /**
        *  Creates a priority item "medium". 
        */
        public static final Priority NORMAL = new Priority("normal");
        /**
        *  Creates a priority item "high". 
        */
        public static final Priority HIGH = new Priority("high");

    /**
         * Constructor.
         * This constructor creates a priority given the input String prio. A
         * priority can have the value low, normal or high. If another value other 
         * than these is the value of prio, then a NotAPriorityException will be thrown.
         * @param prio The priority of a task
         */
        public Priority(String prio) {
            String[] priorities = {
                    "low", "normal", "high"
            };
            List lst = Arrays.asList(priorities);
            if(lst.contains(prio.toLowerCase())) {
                this.prio = prio.toLowerCase();
            }
            else {
                try {
                    throw new NotAPriorityException(prio + " is not a valid priority");
                } 
                catch (NotAPriorityException ex) {
                    ex.printStackTrace();
                }
            }
        }
        
        /**
         * toString method for this class.
         * @return The current priority 
         */
        @Override
        public String toString() {
            return prio;
        }
        /**
         * Method for checking what priority an object has.
         * @param obj the current object
         * @return The current object priority 
         */
        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Priority) {
                Priority priority = (Priority) obj;
                return priority.toString().equals(prio);
            }
            return false;
        }



        
        /**
         * Exception that is thrown when input to the Priority constructor is invalid.
         * If the value is something else than low, normal or high, then it's considered invalid.
         */
        private class NotAPriorityException extends Exception{
            
            /**
             * Constructor that takes a description. 
             * The description can give the user more information on what went wrong
             * @param description a message to be shown
             */
            NotAPriorityException(String description) {
                super(description);
            }
        }
    }
