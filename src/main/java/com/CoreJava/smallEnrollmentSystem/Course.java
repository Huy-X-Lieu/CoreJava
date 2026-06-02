package com.CoreJava.smallEnrollmentSystem;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Course {
    private String courseCode;
    private String title;
    private int credit;


    private static final Pattern COURSE_CODE_PATTERN =
            Pattern.compile("^(?<department>[A-Za-z]{2,6}) (?<number>\\d+)$");
    private static final String COURSE_CODE_GUIDANCE =
            "Course code must follow the format: DEPARTMENT Number. Eg: CSC " +
                    "400, or MATH 335.\n"
                    + "DEPARTMENT is uppercased and contains " +
                    "(2-6 alphabetic letters),"
                    + "Number is the course's number.\n"
                    + "There is one space between DEP and Number.\n";

    public Course(String courseCode, String courseTitle, int credit)throws IllegalArgumentException{
        if(credit < 1)
            throw new IllegalArgumentException("Course credit cannot be less " +
                    "than 1");

        this.courseCode = normalizeCourseCode(courseCode);
        this.title = courseTitle;
        this.credit = credit;
    }

    public void setCourseCode(String courseCode){
        this.courseCode = normalizeCourseCode(courseCode);
    }

    public void setTitle(String title){
        this.title = normalizeTitle(title);
    }

    public void setCredit (int credit) throws IllegalArgumentException{
        if(credit < 1)
            throw new IllegalArgumentException("Course credit cannot be less " +
                    "than 1");

        this.credit = credit;
    }

    public String getCourseCode(){
        return this.courseCode;
    }

    public String getTitle(){
        return this.title;
    }

    public int getCredit(){
        return this.credit;
    }

    private String normalizeCourseCode(String courseCode)throws IllegalArgumentException{
        Utils.checkStringForNullOrBlank(courseCode, "Course code");

        Matcher matcher = COURSE_CODE_PATTERN.matcher(courseCode.strip());
        if(!matcher.matches())
            throw invalidCourseCode(courseCode);

        String department = matcher.group("department").toUpperCase(Locale.ROOT);
        String number = matcher.group("number");

        return String.format("%s %s", department, number);
    }
    private String normalizeTitle(String title)throws NullPointerException,
            IllegalArgumentException{
        if(title == null)
            throw new NullPointerException("Course title cannot be null");
        if(title.isBlank())
            throw new IllegalArgumentException("Course title cannot be blank " +
                    "or empty");

        StringBuilder formattedTitle = new StringBuilder();
        boolean needsSpace = false;

        for(char c : title.strip().toCharArray()){
            if(Character.isWhitespace(c)){
                needsSpace = true;
                continue;
            }

            if(needsSpace){
                formattedTitle.append(' ');
                needsSpace = false;
            }

            formattedTitle.append(c);
        }

        return formattedTitle.toString();
    }

    private static IllegalArgumentException invalidCourseCode(String courseCode){
        return new IllegalArgumentException("Invalid course code: "
                + courseCode + ".\n" + COURSE_CODE_GUIDANCE);
    }
}
