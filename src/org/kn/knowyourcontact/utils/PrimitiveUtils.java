package org.kn.knowyourcontact.utils;

/**
 * Contains helper methods over primitive data types
 * 
 */
public class PrimitiveUtils {

    /**
     * Returns true is string is empty, else returns false
     * 
     * @param inputString
     * @return true if the input is empty
     */
    public static boolean isEmpty(String inputString) {
        if (inputString != null) {
            if (inputString.trim().length() > 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * If input string is 'true', then returns the true boolean type. Else
     * returns false. Returns false if inputString is null
     * 
     * @param inputString
     * @return the boolean value
     */
    public static boolean convertToBoolean(String inputString) {
        if (null != inputString) {
            return inputString.trim().equalsIgnoreCase("true") ? true : false;
        } else
            return false;
    }

}
