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
        String[] priorities = {
            "low", "normal", "high"
        };
        /**
        *  Creates a priority item "low". 
        */
        public static Priority LOW = new Priority("low");
        /**
        *  Creates a priority item "medium". 
        */
        public static Priority NORMAL = new Priority("normal");
        /**
        *  Creates a priority item "high". 
        */
        public static Priority HIGH = new Priority("high");
        List lst = Arrays.asList(priorities);
        
        /**
         * Constructor.
         * This constructor creates a priority given the input String prio. A
         * priority can have the value low, normal or high. If another value other 
         * than these is the value of prio, then a NotAPriorityException will be thrown.
         * @param prio The priority of a task
         */
        public Priority(String prio) {
            if(lst.contains(prio.toLowerCase())) {
                this.prio = prio.toLowerCase();
            }
            else {
                try {
                    throw new NotAPriorityException(prio + " is not a valid priority");
                } 
                catch (NotAPriorityException ex) {
                    System.err.println(ex);
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
            Priority priority = (Priority) obj;
            return priority.toString().equals(prio);
        }



        
        /**
         * Exception that is thrown when input to the Priority constructor is invalid.
         * If the value is something else than low, normal or high, then it's considered invalid.
         */
        private class NotAPriorityException extends Exception{
            
            /**
             * Empty constructor to be able to cast the exception without a message.
             */
            public NotAPriorityException() {   
            }
            
            /**
             * Constructor that takes a description. 
             * The description can give the user more information on what went wrong
             * @param description a message to be shown
             */
            public NotAPriorityException(String description) {
                super(description);
            }
        }
    }
