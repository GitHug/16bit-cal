/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Model.Datatypes;

import java.util.ArrayList;

/**
 *
 * @author fredrikmakila
 */
public class DayCardIdentifiers {
    /****************PRIVATE VARIABLES *****************/

    private int monthCardId;
    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private final ArrayList<Integer> weekDayCardIds = new ArrayList<>();
    private final ArrayList<Integer> weekCardIds = new ArrayList<>();
    private final ArrayList<CardStuffList> dayCardIds = new ArrayList<>();
    
    /****************CONSTRUCTOR*****************/
    public DayCardIdentifiers() {
        
    }


    /** 
     * Sets the monthcard ID 
     * @param dcId the monthcard ID. 
     */
    public void setMonthCard(int dcId) {
        monthCardId = dcId;
    }
    
    /** 
     * Adds the weekday cards 
     * @param dcId the weekday card ID. 
     */
    public void addWeekDayCard(int dcId) {
        weekDayCardIds.add(dcId);
    }
    
    /** 
     * adds the week card
     * @param dcId the week card ID. 
     */
    public void addWeekCard(int dcId) {
        weekCardIds.add(dcId);
    }
    
    /** 
     * Adds the daycards
     * @param csl list of daycards. 
     */
    public void addDayCard(CardStuffList csl) {
        dayCardIds.add(csl);
    }

    /**
     * gets the monthcard ID 
     * @return month card ID.
     */
    public int getMonthCardId() {
        return monthCardId;
    }
    
     /** 
     * gets the week cards ID 
     * @return week cards ID.
     */
    public ArrayList<Integer> getWeekCardIds() {
        return weekCardIds;
    }
    
     /** 
     * gets the daycards ID 
     * @return a list of daycards ID.
     */
    public ArrayList<Integer> getDayCardIds() {
        ArrayList<Integer> list = new ArrayList<>();
        for (CardStuffList dayCardId : dayCardIds) {
            list.add(dayCardId.getDayCardId());
        }
        return list;
    }
    
    /** 
     * gets the daycards ID 
     * @return a list of daycards ID.
     */
    public ArrayList<CardStuffList> getDayCardStuffList() {
        return dayCardIds;
    }
}
