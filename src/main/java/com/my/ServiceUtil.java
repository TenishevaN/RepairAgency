package com.my;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * {@ code ServiceUtil} class represents the util service.
 * <br>
 *
 * @author Tenisheva N.I.
 * @version 1.0
 */
public class ServiceUtil {

    public static String getKey(String key, String locale) {
        ResourceBundle resource = ResourceBundle.getBundle("resources", new Locale(locale));
        return resource.getString(key);
    }
}
