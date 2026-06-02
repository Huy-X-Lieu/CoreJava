package com.CoreJava.smallEnrollmentSystem;

import java.util.Set;

public class Section {
    private Course course;
    private String sectionNumber;
    private String instructor;
    int capacity;
    private Set<Student> enrolledStudents;

    private String normalizeInstructorName(String name){
        Utils.checkStringForNullOrBlank(name, "Instructor name");
        return name.strip();
    }
}
