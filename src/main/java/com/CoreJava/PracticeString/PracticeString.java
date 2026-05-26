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

    public static String toCsvRow(String[] fields){
        if(fields.length == 0)
            return "";

        StringBuilder row = new StringBuilder();
        char c;

        for (String field : fields) {
            if (field == null || field.isEmpty()) {
                row.append(',');
            }  else if (doesFieldNeedDoubleQuotes(field)) {
                row.append("\"");
                if (doesFieldContainDoubleQuotes(field)) {
                    for (int i = 0; i < field.length(); i++) {
                        c = field.charAt(i);
                        if (c == '\"') {
                            row.append("\"");
                        }
                        row.append(c);
                    }
                } else {
                    row.append(field);
                }
                row.append("\",");
            }else if (field.length() == 1) {
                row.append(field);
                row.append(',');
            } else {
                row.append(field);
                row.append(',');
            }
        }

        row.deleteCharAt(row.length()-1);

        return row.toString();
    }

    private static boolean doesFieldNeedDoubleQuotes(String field){
        return field.contains(",") ||
                field.contains("\"") ||
                field.contains("\n") ||
                field.contains("\r") ||
                field.contains("\t") ||
                (field.length() > field.trim().length());
    }

    private static boolean doesFieldContainDoubleQuotes(String field){
        return field.contains("\"");
    }
}
