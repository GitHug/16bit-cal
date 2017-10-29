/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Model.Datatypes;

import java.util.Arrays;
import java.util.List;

/**
 * Class that defines a border piece for a border. 
 * @author fredrikmakila
 */
public class BorderPiece {
        private String piece = null;

    /**
         * Constructor. 
         * A border piece is a String with one of the following values:
         * <ul>
         *  <li> top_left
         *  <li> top_center
         *  <li> top_right
         *  <li> left_center
         *  <li> bottom_left
         *  <li> bottom_center
         *  <li> bottom_right
         * <ul>
         * The choice of the String depends on what location of the border the
         * object should define. 
         * @param piece A piece of the border
         */
        public BorderPiece(String piece) {
            String[] pieces = {
                    "top_left", "top_center", "top_right",
                    "left_center", "right_center",
                    "bottom_left", "bottom_center", "bottom_right"
            };
            List lst = Arrays.asList(pieces);
            if(lst.contains(piece)) {
                this.piece = piece;
            }
            else {
                try {
                    throw new NotABorderPieceException(piece + " is not a border piece");
                } 
                catch (NotABorderPieceException ex) {
                    ex.printStackTrace();
                }
            }
        }
        
        /**
         * toString method for this class.
         * @return The border piece as a string
         */
        @Override
        public String toString() {
            return piece;
        }
        
        /**
         * Custom made exception class.
         * This exception is thrown when something else besides a border piece
         * is the input to the BorderPiece constructor
         */
        private class NotABorderPieceException extends Exception{
            
            /**
             * Constructor that takes a description. 
             * The description can give the user more information on what went wrong
             * @param description a message to be shown
             */
            NotABorderPieceException(String description) {
                super(description);
            }
        }
    }