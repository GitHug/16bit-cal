/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 *
 * @author fredrikmakila
 */
public class CalendarMethods {
    
    /**
     * Empty constructor for this class.
     */
    protected CalendarMethods() {
    }
    /**
     * Returns the first week day for a given month. 
     * Every sane person knows that the week starts on Monday. As such this method
     * has Monday = 0, Tuesday = 1, ..., Saturday = 5, Sunday = 6. 
     * @param month A month (1, 2, 3, ..., 12)
     * @param year A year
     * @return The first weekday for a given month.
     */
    protected int getWeekDay(int year, int month) {
        Calendar cal = new GregorianCalendar(year, month, 1);
        //Day of week has the week start on sunday, but any sane person
        //has the wee starting on monday. That's why this is added. Also for some
        //reason, sunday is 1 instead of 0. 
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK) - 2;
        if (dayOfWeek < 0) {
            dayOfWeek = 6;
        }
        return dayOfWeek;
    }
    
     /**
     * Gets the current year. 
     * @return The current year.
     */
     protected int getYear() {
        Calendar cal = new GregorianCalendar();
         return cal.get(Calendar.YEAR);
    }
    
     /**
     * Gets the current day. 
     * @return The current day number this month. 
     */
     protected int getDay() {
        Calendar cal = new GregorianCalendar();
         return cal.get(Calendar.DAY_OF_MONTH);
    }
    
    /**
     * Gets the number of days in a month.
     * @param year The year for the month to be checked
     * @param month The month to be checked
     * @return The number of days in a month
     */
    protected int getNumberOfDays(int year, int month) {
        int days;
        Calendar cal = new GregorianCalendar(year, month, 1);
        days = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        return days;
    }
    
    /**
     * Returns the current month.
     * @return the current month.
     */
    protected int getMonth() {
        Calendar cal = new GregorianCalendar();
        return cal.get(Calendar.MONTH);
    }
    
    /**
     * Creates an array containing the week numbers for a month view. 
     * The method returns 6 weeks because that's the number of day cards containing
     * weeks. 
     * @param month The month for which to get the weeknumbers
     * @param year The year for which to get the weeknumbers for a given month.
     * @return An array containg the week numbers. 
     */
    protected ArrayList<Integer> getWeekNumber(int year, int month) {
        //Creates a new calendar at the date 1
        Calendar cal = new GregorianCalendar(year, month, 1);
        //Sets the first day of the week to monday
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        //Creates another calendar that checks the week for the last date of this month
        Calendar endCal = new GregorianCalendar(year, month, getNumberOfDays(year, month));
        //Also sets monday as the first day of the week for this calendar
        endCal.setFirstDayOfWeek(Calendar.MONDAY);
        //Checks the week for december 31 of this year to check whether it's 52 or 1
        Calendar lastWeek = new GregorianCalendar(year, 11, 31);
        lastWeek.setFirstDayOfWeek(Calendar.MONDAY);
        //Gets the starting week
        int start = cal.get(Calendar.WEEK_OF_YEAR);
        //Gets the last week of this month
        int end = endCal.get(Calendar.WEEK_OF_YEAR);
        //Gets the very last week of this year
        int last = lastWeek.get(Calendar.WEEK_OF_YEAR);

        //Creates an empty integer list that later can be filled 
        ArrayList<Integer> array = new ArrayList<>();

        //If the last week of the month is same as the last week of this year
        if(end == last) {
            //Go up to 52
            for(int i = start; i <= 52; i++) {
                array.add(i);
            }
            //Add the last week
            array.add(last);
        }
        //If the first week of the year is 52
        else if(start == 52) {
            array.add(52);
            for(int i = 1; i <= end; i++) {
                array.add(i);
            }
        }
        else {
            //Everything is "normal" so nothing special has to be done
            for(int i = start; i <= end; i++) {
                array.add(i);
            }
        } 
        
        //Checks if more weeks has to be added due to the number of weeks being less than 6
        //These weeks will belong to the next month
        if(array.size() < 6) {
            //Gets the next month
            month++;
            //If the next month is january of next year, sets the year to next year
            if(month > 11) {
                month = 0;
                year++;
            }
            //Checks the first week of the next month
            Calendar calen = new GregorianCalendar(year, month, 1);
            int week = calen.get(Calendar.WEEK_OF_YEAR);
            //if the first week is the same as the last week in the previous month,
            //increase the weeknumber by 1
            if(week == array.get(array.size()-1)) {
                week++;
            }
            int counter = 0;
            for(int i = array.size(); i < 6; i++) {
                week = week + counter;
                counter++;
                array.add(week);
            }
        }

        return array;
    }
    
}
