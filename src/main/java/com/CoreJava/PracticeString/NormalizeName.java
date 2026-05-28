package com.CoreJava.PracticeString;

import java.util.Objects;

public class NormalizeName {
    public static String normalizeName(String name){
        Objects.requireNonNull(name, "Name cannot be null");

        StringBuilder result = new StringBuilder();
        boolean shouldCapitalize = true;
        boolean needsSpace = false;

        for(int index = 0; index < name.length(); index++){
            char c = name.charAt(index);

            if(Character.isWhitespace(c)){
                if(result.length() > 0){
                    shouldCapitalize = true;
                    needsSpace = true;
                }

                continue;
            }

            if(needsSpace){
                result.append(' ');
                needsSpace = false;
            }

            if(shouldCapitalize) {
                result.append(Character.toUpperCase(c));
                shouldCapitalize = false;
            }else{
                result.append(Character.toLowerCase(c));
            }
        }

        return result.toString();
    }
}
