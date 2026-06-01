package com.CoreJava.smallEnrollmentSystem;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Course {
    private static final Pattern COURSE_CODE_PATTERN =
            Pattern.compile("^(?<department>[A-Za-z]{2,6}) (?<number>\\d+)(?:-(?<section>\\d+))?$");
    private static final String COURSE_CODE_GUIDANCE =
            "Course code must follow the format: DEP Number-Section. Eg: CSC 400, or MATH 335-003\n"
                    + "Where DEP is the department (2-6 alphabetic letters),"
                    + "Number is the course's number, and Section is the "
                    + "course's section.\n"
                    + "There is one space between DEP and Number.\n"
                    + "There is a \"-\" between Number and Section.\n";

    private String courseCode;
    private String title;
    private int credit;

    private String normalizeCourseCode(String courseCode)throws NullPointerException, IllegalArgumentException{
        if(courseCode == null)
            throw new NullPointerException("Course code cannot be null");

        Matcher matcher = COURSE_CODE_PATTERN.matcher(courseCode.strip());
        if(!matcher.matches())
            throw invalidCourseCode(courseCode);

        String department = matcher.group("department").toUpperCase(Locale.ROOT);
        String number = matcher.group("number");
        String section = matcher.group("section");

        if(section == null)
            return String.format("%s %s", department, number);

        return String.format("%s %s-%s", department, number, section);
    }

    private static IllegalArgumentException invalidCourseCode(String courseCode){
        return new IllegalArgumentException("Invalid course code: "
                + courseCode + ".\n" + COURSE_CODE_GUIDANCE);
    }
}
