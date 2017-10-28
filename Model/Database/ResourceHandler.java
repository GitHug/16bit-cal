/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Model.Database;

import Model.Datatypes.BorderPiece;

import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

/**
 * Convenient class that allows the user to get URLs for images that is stored.
 * The class has several methods for getting URLs from a propertyfile. The location
 * of the property file is set in the method, however it exists at 
 * src/Model/Database/imageResource.properties.
 * @author fredrikmakila
 */
@SuppressWarnings("unchecked")
public class ResourceHandler {
    private ResourceBundle rb;
 

    /**
     * Constructor that creates a ResourceBundle with the property file specified above. 
     */
    public ResourceHandler() {
        String location;
        Preferences prefs = Preferences.userNodeForPackage(getClass());
        location = prefs.node("/Control").get("resource", "Model.Database.imageResource");
        rb = ResourceBundle.getBundle(location);
    }
    
    
    /**
     * Method to retrieve a string (as an URL) from the property file.
     * @param resource The key for the given URL
     * @return An URL as a String
     */
    public String getString(String resource) {
        return rb.getString(resource);
    }
    
    /**
     * Returns the URL for a piece of the border.
     * The method checks in the provided arraylist bList for a key that contains
     * the border piece bp and returns the associated URL for the border image
     * @param bp The URL for the border piece to be retrieved
     * @param bList The list from where the URL is retrieved
     * @see BorderPiece
     * @return The URL for the border piece
     */
    
    public String getBorder(BorderPiece bp, ArrayList<BorderType> bList) {
        BorderType bt;
        String piece = bp.toString();
        String key;
        for(int i = 0; i < bList.size(); i++) {
            bt = bList.get(i);
            key = bt.getKey();
            if(key.contains(piece)) {
                return bt.getBorder();
            }
        }
        return "Not found";
    }
    
    /**
     * Simple class that stores a key and a border URL.
     */
    public class BorderType {
        String key;
        String border;
        
        /**
         * Constructor.
         * @param key The key that holds the border URL.
         * @param border The border URL
         */
        public BorderType(String key, String border) {
            this.key = key;
            this.border = border;
        }
        
        /**
         * Returns the key for this object
         * @return The key
         */
        public String getKey() {
            return key;
        }
        
        /**
         * Returns the border URL for this object
         * @return The border URL as a string
         */
        public String getBorder() {
            return border;
        }
        
    }
    
    /**
     * Returns an array with all the different border pieces with the same name.
     * The name provided is simply that of the border. For example for the 
     * flashy border, the name would be flashy_border. Please consult the 
     * imageResource.properties file for further details on the available borders.
     * @param borderName The name of the border
     * @return All the different border pieces with the same name as an arraylist
     */
    public ArrayList getBorders(String borderName) {
        BorderType bt;
        ArrayList<BorderType> borders = new ArrayList<BorderType>();

        
        String border = borderName;
        String top_left = border + "_top_left";
        String top_center = border + "_top_center";
        String top_right = border + "_top_right";
        String left_center = border + "_left_center";
        String right_center = border + "_right_center";
        String bottom_left = border + "_bottom_left";
        String bottom_center = border + "_bottom_center";
        String bottom_right = border + "_bottom_right";
        String url;
        try{
            url = rb.getString(top_left);
            bt = new BorderType(top_left, url);
            borders.add(bt);
            
            url = rb.getString(top_center);
            bt = new BorderType(top_center, url);
            borders.add(bt);
                    
            url = rb.getString(top_right);
            bt = new BorderType(top_right, url);
            borders.add(bt);
            
            url = rb.getString(left_center);
            bt = new BorderType(left_center, url);
            borders.add(bt);
            
            url = rb.getString(right_center);
            bt = new BorderType(right_center, url);
            borders.add(bt);
            
            url = rb.getString(bottom_left);
            bt = new BorderType(bottom_left, url);
            borders.add(bt);
            
            url = rb.getString(bottom_center);
            bt = new BorderType(bottom_center, url);
            borders.add(bt);
            
            url = rb.getString(bottom_right);
            bt = new BorderType(bottom_right, url);
            borders.add(bt);
            
        }
        catch(Exception ex) {
            System.err.println("Unable to retrieve border");
        }
        return borders;
    }
}
