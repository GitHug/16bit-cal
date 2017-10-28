
package Model.Resources;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

/**
 * Connection to language property files.
 * This class deals with establishing a connection 
 * to the correct language property files to enable
 * internationalization of the main application. It's
 * also inteded to ease the way other classes uses the 
 * use of bundles since this class can be used instead of
 * calling the resource bundle class directly 
 * 
 * @see ResourceBundle
 * @see Locale
 * 
 * @author Fredrik Makila
 */
public class LanguageResource {
    private Locale locale;
    private ResourceBundle rb;
    private Preferences prefs;
    private Locale localeDefault = Locale.getDefault();


    
    /**
     * Sole constructor.
     * For invocation by other classes. The constructor retrieves the Locale
     * from a preferences property file.
     * 
     * @see Preferences
     * @see ResourceBundle
     *  
     */
    public LanguageResource() {
        //Gets the locale from the property file
        locale = new Locale(prefs.get("locale", localeDefault.toString()));
        rb = ResourceBundle.getBundle("Model.Resources.LanguageResource", locale);
    }
    
    /**
     * Retrieves a word from a property file.
     * This method checks a LanguageResource property file for the String
     * specified in s. Depending on the Locale set in the constructor for this
     * class, the corresponding word in that Locale (language) will be returned.
     * 
     * @param s The name that will be retrieved from the property file
     * @return The name that is specified in the property file, given s
     * @throws NullPointerException if s is null
     */
    public String getString(String s) {
        return rb.getString(s);
    }
}