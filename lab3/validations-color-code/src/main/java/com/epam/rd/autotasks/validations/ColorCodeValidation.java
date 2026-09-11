package com.epam.rd.autotasks.validations;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ColorCodeValidation {
    public static boolean validateColorCode(String color) {
        if (color == null ||
                !color.startsWith("#") ||
                color.contains("+") ||
                color.contains("-") ||
                (color.length() != 4 && color.length() != 7))
            return false;
        String num = color.substring(1);
        try{
            int n = Integer.parseInt(num, 16);
            return n >= 0 && n <= 0xFFFFFF;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}





