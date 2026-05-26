package com.CoreJava.PracticeString;

public class PracticeString {
    public static String slugifyAscii(String input)throws NullPointerException{
        if(input == null)
            throw new NullPointerException("Input string cannot be null");

        StringBuilder builder = new StringBuilder();
        int length = input.length();
        int index = 0;
        boolean needsDash = false;

        while(index < length){
            char c = input.charAt(index);

            if(isAsciiLetterOrDigit(c)){
                if(needsDash && builder.length() > 0)
                    builder.append('-');
                builder.append(Character.toLowerCase(c));
                needsDash = false;
            } else if(builder.length() > 0){
                needsDash = true;
            }

            index++;
        }

        return builder.toString();
    }

    private static boolean isAsciiLetterOrDigit(char c){
        return ('A' <= c && c <= 'Z')
                || ('a' <= c && c <= 'z')
                || ('0' <= c && c <= '9');
    }
}
