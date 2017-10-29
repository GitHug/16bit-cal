/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

/**
 * Class that checks which os the user user runs the application on
 * 
 * @author fredrikmakila
 */
public final class OsUtils {

    private static String OS = null;

     /**
     * Gets the os name
     * @return the name of the os.
     */
    private static String getOsName() {
        if (OS == null) {
            OS = System.getProperty("os.name");
        }
        return OS;
    }
    
    /**
     * Gets the os name
     * @return windows if its windows.
     */
    public static boolean isWindows() {
        return getOsName().startsWith("Windows");
    }

    
    /**
     * Gets the os name
     * @return mac if its mac.
     */
    public static boolean isNotMac() {
        return !getOsName().startsWith("Mac");
    }
}
