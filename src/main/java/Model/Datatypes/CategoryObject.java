/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Model.Datatypes;

import javax.swing.*;
import java.awt.*;

/**
 * A class that defines the structure of a CategoryObject.
 * This class also contains all the necessary getters for the class. 
 * @author fredrikmakila
 */

public class CategoryObject {
    String name;
    ImageIcon icon;
    Color color;
    int id;
    
    
    /**
     * A constructor for category object
     * Creates a standard TaskObject category with fixed variables. 
     */
    public CategoryObject() {
        name = "Standard";
        color = new Color(255,255,255); //white color
        icon = new ImageIcon("");
    }
    
    /**
     * Another constructor for category object.
     * Creates a TaskObject by instantiating all the variables
     * @param name The name of the category
     * @param icon The icon of the category 
     * @param color The color of the category
     */
    public CategoryObject(String name, ImageIcon icon, Color color) {
        this.name = name;
        this.icon = icon;
        this.color = color;
    }
    
    /**
     * Another constructor.
     * This constructor is identical to the first constructor, except that it contains
     * an ID variable
     * @param id The identifier of the category 
     * @param name The name of the category
     * @param icon The icon of the category
     * @param color The color of the category
     */
    public CategoryObject(int id, String name, ImageIcon icon, Color color) {
        this.id = id;
        this.name = name;
        this.icon = icon;
        this.color = color;
    }
    
    /**
     * Gets the identifier of the category
     * @return the id of the category
     */
    public int getID() {
        return id;
    }
    
    /**
     * Gets the type of the category.
     * The type of a category is defined as a String of value "category"
     * @return the type of the category
     */
    public String getType() {
        return "category";
    }
    
    
    /**
     * Gets the name of the category.
     * @return the name of the category
     */
    public String getName() {
        return name;
    }
    
    /**
     * Gets the name of the category.
     * @return the name of the category
     */    
    public String getIcon() {
        return icon.toString();
    }
    
    /**
     * Gets the color of the category
     * @return the color of the category
     */
    public String getColor() {
        Integer rgb = color.getRGB();
        return rgb.toString();
    }
    
    /**
     * Gets the color of the category
     * @return the color of the category
     */
    public Color getRealColor() {
        return color;
    }
    
    /**
     * To String method.
     * This method is mostly intended for easier printing of a category in the case
     * of debugging.
     * @return The content of the category as a String
     */
    @Override
    public String toString() {
        String imgIcon = getIcon();
        String rgb = getColor();
        
        return "ID: " + id +
                ", name: " + name + 
                ", icon: " + imgIcon + 
                ", color: " + rgb;
    }
}
