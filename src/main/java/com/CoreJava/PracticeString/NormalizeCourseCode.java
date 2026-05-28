package com.CoreJava.PracticeString;

import java.util.Locale;
import java.util.Objects;

public class NormalizeCourseCode {
    private static final String INVALID = "INVALID";

    public static String normalizeCourseCode(String raw)throws NullPointerException{
        Objects.requireNonNull(raw, "Course Code cannot be null");

        String courseCode = raw.strip().toUpperCase(Locale.ROOT);

        int index = 0;

        while(index < courseCode.length() && Character.isLetter(courseCode.charAt(index))){
            index++;
        }

        if(index == 0 || index == courseCode.length())
            return INVALID;

        String department = courseCode.substring(0, index);
        int delimiterCount = 0;

        while(index < courseCode.length() && !Character.isDigit(courseCode.charAt(index))){
            char c = courseCode.charAt(index);

            if(Character.isWhitespace(c)){
                index++;
                continue;
            }

            if(isSeparator(c)){
                delimiterCount++;

                if(delimiterCount > 1)
                    return INVALID;

                index++;
                continue;
            }

            return INVALID;
        }

        if(index == courseCode.length())
            return INVALID;

        int numberStart = index;

        while(index < courseCode.length()){
            if(!Character.isDigit(courseCode.charAt(index)))
                return INVALID;

            index++;
        }

        return department + "-" + courseCode.substring(numberStart);
    }

    private static boolean isSeparator(char c){
        return c == '-' || c == ':' || c == '/';
    }
}
