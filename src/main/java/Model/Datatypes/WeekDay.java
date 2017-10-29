/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Model.Datatypes;

import java.util.Arrays;
import java.util.List;

/**
 * Class that defines a week day for a daycard.
 * @author fredrikmakila
 */
public class WeekDay {
    private String weekDay = null;

    /**
     * Constructor to create a week day.
     * If something else other than a week day, that is Monday, Tuesday etc. is input,
     * then a NotAWeekDayException will be thrown. 
     * @param weekDay name of weekday
     */
    public WeekDay(String weekDay) {
        String[] weekDays = {
                "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"
        };
        List lst = Arrays.asList(weekDays);
        if(lst.contains(weekDay)) {
            this.weekDay = weekDay;
            }
            else {
                try {
                    throw new NotAWeekDayException(weekDay + " is not a valid week day");
                } 
                catch (NotAWeekDayException ex) {
                    ex.printStackTrace();
                }
            }
    }
    
    /**
     * Returns the week day;
     * @return the week day
     */
    public String get() {
        return weekDay;
    }
    
    /**
     * Exception that is thrown when something other than a week day is input to the
     * constructor.
     */
    private class NotAWeekDayException extends Exception{
            
            /**
             * Constructor that takes a description. 
             * The description can give the user more information on what went wrong
             * @param description a message to be shown
             */
            NotAWeekDayException(String description) {
                super(description);
            }
        }
}
