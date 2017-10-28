package View;

import Model.Datatypes.CardStuffList;
import Model.SixteenBitModel;
import Model.WeekViewModel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * The view that shows a week with days and their events
 * @author Robin Horneman
 */
public class WeekView extends JPanel{
    
    /*Private variables*/
    private ArrayList<CardStuffList> daysThisWeek;  
    private WeekViewModel weekmodel;
    
    /*Constructors*/
    
    /**
     * Creates an empty WeekView
     */
    public WeekView() {
        SixteenBitModel.getInstance().getWeekModel();
    }
    
    /*Setters*/
    /**
     * Sets the days for the weekview
     * @param days
     */
    public void setDays(ArrayList<CardStuffList> days) {
        daysThisWeek = days;
    }
    
    /*Getters*/
    /**
     * Gets the day at position dayofweek
     * @param dayofweek The position of the day to get
     * @return The day at position dayofweek
     */
    public CardStuffList getDay(int dayofweek) {
        return daysThisWeek.get(dayofweek);
    } 
    
    /**
     * Draws the viewkview. 
     * Not yet implemented.
     * @param g Graphics
     */
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        /* Draw the WeekView here */
    }
    
    
}
