/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Model.Datatypes;

import java.util.Arrays;
import java.util.List;

/**
 * Class that defines a border name. 
 * @author fredrikmakila
 */
public class BorderImage {
        private String border = null;
        private String[] borders = {
            "flashy_border", "beam", "chain", "diamond", "tiny", "blob"
        };
        List lst = Arrays.asList(borders);
        
        /**
         * Constructor.
         * This constructor creates a new border. The following borders are available
         * <lu>
         *  <li> flashy_border
         *  <li> beam
         *  <li> chain
         *  <li> diamond
         *  <li> tiny
         *  <li> blob
         * <lu>
         * @param border The name of the border
         */
        public BorderImage(String border) {
            if(lst.contains(border.toLowerCase())) {
                this.border = border;
            }
            else {
                try {
                    throw new NotABorderException(border + " is not a valid border");
                } 
                catch (NotABorderException ex) {
                    System.err.println(ex);
                }
            }
        }
        
        /**
         * toString method for this class.
         * @return The border name
         */
        @Override
        public String toString() {
            return border;
        }
        
        /**
         * Returns all available borders to choose from
         * @return The name of all borders as an array
         */
        public String[] getAvailable() {
            return borders;
        }
        
        /**
         * Exception that is thrown when input to the BorderImage constructor is invalid.
         * If the value is something else than than what is specified as a valid border
         * then this exception is thrown.
         */
        private class NotABorderException extends Exception{
            
            /**
             * Empty constructor to be able to cast the exception without a message.
             */
            public NotABorderException() {   
            }
            
            /**
             * Constructor that takes a description. 
             * The description can give the user more information on what went wrong
             * @param description a message to be shown
             */
            public NotABorderException(String description) {
                super(description);
            }
        }
    }