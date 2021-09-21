package com.my;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationUtil {





    public static boolean matchWithPattern(String value, String regex){

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(value);
         return matcher.matches();
    }
}
