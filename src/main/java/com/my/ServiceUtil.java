package com.my;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ServiceUtil {

    public static String getKey(String key, String locale){
        ResourceBundle resource = ResourceBundle.getBundle("resources", new Locale(locale));
        return resource.getString(key);
    }

    public static boolean validateField(String value, String regex){

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }
}
