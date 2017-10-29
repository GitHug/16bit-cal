
package Model.Datatypes;

import java.util.Arrays;
import java.util.List;

/**
 * Class that defines the completed state of a task
 * @author fredrikmakila
 */
public class Complete {
        private String complete = null;

    /**
         * Constructor.
         * A task's complete state can have two diferent values: yes or no. 
         * If something else than these two values are the input, then
         * a NotCompleteException is thrown.
         * @param complete The completed state of a task
         * @see NotCompleteException
         */
        public Complete(String complete) {
            String[] completes = {
                    "yes", "no"
            };
            List lst = Arrays.asList(completes);
            if(lst.contains(complete.toLowerCase())) {
                this.complete = complete;
            }
            else {
                try {
                    throw new NotCompleteException(complete + " is not either yes or no");
                } 
                catch (NotCompleteException ex) {
                    ex.printStackTrace();
                }
            }
        }
        
        /**
         * toString method for this class.
         * @return The completed state 
         */
    @Override
        public String toString() {
            return complete;
        }
        
        /**
         * Exception that is thrown when input to the Complete constructor is invalid.
         * If the value is something else than yes or no, then it's considered invalid.
         */
        private class NotCompleteException extends Exception{

            
            /**
             * Constructor that takes a description. 
             * The description can give the user more information on what went wrong
             * @param description A message to be shown
             */
            NotCompleteException(String description) {
                super(description);
            }
        }
    }

