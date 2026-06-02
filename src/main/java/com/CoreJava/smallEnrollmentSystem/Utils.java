package com.CoreJava.smallEnrollmentSystem;

public class Utils {
    public static void checkStringForNullOrBlank (String str, String strName)
        throws NullPointerException, IllegalArgumentException{
        if(strName == null)
            strName = "";

        if(str == null)
            throw new NullPointerException(strName + " cannot be null.");

        if(str.isBlank())
            throw new IllegalArgumentException(strName + " cannot be blank or" +
                    " empty");
    }

    public static void checkStringForNullOrBlank(String str){
        checkStringForNullOrBlank(str, "");
    }
}
