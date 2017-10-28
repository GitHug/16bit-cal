/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Model.Datatypes;

import java.util.Arrays;
import java.util.List;

/**
 * Class that defines a month for a daycard
 * @author fredrikmakila
 */
public class Month {
    String month;
    private String[] months = {
        "January", "February", "March", "April", "May", "June", "July", "August",
        "September", "October", "November", "December"
    };
    private List lst = Arrays.asList(months);
    
    /**
     * Constructor.
     * @param month The month
     */
    public Month(String month){
        if(lst.contains(month)) {
            this.month = month;
        }
        else {
            try {
                throw new NotAMonthException(month + " is not a valid month");
            } 
            catch (NotAMonthException ex) {
                System.err.println(ex);
            }
        }
    }
    
    /**
     * Constructor.
     * @param monthNumber The month as an integer
     */
    public Month(int monthNumber){
        String monthString;
        switch (monthNumber) {
            case 0: month = "January";
                    break;
            case 1: month = "February";
                    break;
            case 2: month = "March";
                    break;
            case 3: month = "April";
                    break;
            case 4: month = "May";
                    break;
            case 5: month = "June";
                    break;
            case 6: month = "July";
                    break;
            case 7: month = "August";
                    break;
            case 8: month = "September";
                    break;
            case 9: month = "October";
                     break;
            case 10: month = "November";
                     break;
            case 11: month = "December";
                     break;
            default: try {
                        throw new NotAMonthException(month + " is not a valid month");
                     } 
                     catch (NotAMonthException ex) {
                        System.err.println(ex);
                     }
                     break;
        }
    }
    
    /**
     * Convenience method to display the month.
     * The output will be the month as a string.
     * @return the month
     */
    public String get() {
        return month;
    }
    
    /**
     * Exception that is thrown when something other than a month is input to the
     * constructor.
     */
    private class NotAMonthException extends Exception{
            
            /**
             * Empty constructor to be able to cast the exception without a message.
             */
            public NotAMonthException() {   
            }
            
            /**
             * Constructor that takes a description. 
             * The description can give the user more information on what went wrong
             * @param description a message to be shown
             */
            public NotAMonthException(String description) {
                super(description);
            }
        }

}
