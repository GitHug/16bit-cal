/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Model.Datatypes;

/**
 * Class that defines a month for a daycard
 * @author fredrikmakila
 */
public class Month {
    private String month;

    /**
     * Constructor.
     * @param monthNumber The month as an integer
     */
    public Month(int monthNumber){

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
                         ex.printStackTrace();
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
             * Constructor that takes a description. 
             * The description can give the user more information on what went wrong
             * @param description a message to be shown
             */
            NotAMonthException(String description) {
                super(description);
            }
        }

}
