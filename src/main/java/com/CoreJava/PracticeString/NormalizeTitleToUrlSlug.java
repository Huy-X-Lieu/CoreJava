package com.CoreJava.PracticeString;

public class NormalizeTitleToUrlSlug {
    public static String normalizeTitleToUrlSlug(String title, int maxLength){
       if(title == null || title.isBlank() || maxLength < 1)
           return "invalid";

       StringBuilder slug = new StringBuilder();
       boolean addDash = false;

       for(char c : title.toCharArray()){
           if(Character.isAlphabetic(c)){
               slug.append(Character.toLowerCase(c));
               addDash = true;
               continue;
           }
           if(addDash){
               slug.append('-');
               addDash = false;
           }
       }
        if(slug.isEmpty())
            return "invalid";

        if(slug.length() > maxLength){
           slug.delete(maxLength, slug.length());
       }

        if(slug.charAt(slug.length() - 1) == '-')
            slug.deleteCharAt(slug.length()-1);

       return slug.toString();
    }
}
