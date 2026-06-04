package com.CoreJava.smallEnrollmentSystem;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

public class Section {
    private final Course course;
    private final String sectionNumber;
    private String instructor;
    private int capacity;
    private final Set<Student> enrolledList = new HashSet<>();
    private final Queue<Student> waitlist = new ArrayDeque<>();
    

    public Section(Course course, String sectionNumber, String instructor,
                   int capacity){
        validateCourse(course);
        validateCapacity(capacity);
        sectionNumber = validateSectionNumber(sectionNumber);
        instructor = validateInstructorName(instructor);

        this.course = course;
        this.sectionNumber = sectionNumber;
        this.instructor = instructor;
        this.capacity = capacity;
    }

    public void setInstructor(String instructorName){
        instructorName = validateInstructorName(instructorName);
        this.instructor = instructorName;
    }

    public void setCapacity(int capacity){
        validateCapacity(capacity);
        validateCapacityCanHoldCurrentEnrollments(capacity);
        this.capacity = capacity;
    }

    public Course getCourse(){
        return this.course;
    }

    public String getSectionNumber(){
        return this.sectionNumber;
    }

    public String getInstructor(){
        return this.instructor;
    }

    public int getCapacity(){
        return this.capacity;
    }

    public Set<Student> getEnrolledList(){
        return Set.copyOf(this.enrolledList);
    }


    private static String validateInstructorName(String name){
        return validateWellFormattedArgument(name, "Instructor name");
    }

    private static String validateSectionNumber(String sectionNumber)
            throws IllegalArgumentException{
        sectionNumber = validateWellFormattedArgument(sectionNumber,
                "Section number");

        for(char c : sectionNumber.toCharArray()){
            if(!Character.isLetterOrDigit(c))
                throw new IllegalArgumentException("Course section number " +
                        "must contain only alphabetic letters and digits.");
        }

        return sectionNumber;
    }

    private static String validateWellFormattedArgument(String value,
                                                        String argumentName){
        Utils.checkStringForNullOrBlank(value, argumentName);

        if(!value.equals(value.strip()))
            throw new IllegalArgumentException(argumentName + " must not " +
                    "contain leading or trailing whitespace.");

        return value;
    }

    private static void validateCapacity(int capacity)throws IllegalArgumentException{
        if(capacity < 1)
            throw new IllegalArgumentException("Section capacity must be at " +
                    "least 1.");
    }

    private void validateCapacityCanHoldCurrentEnrollments(int capacity)
            throws IllegalArgumentException{
        if(capacity < enrolledList.size())
            throw new IllegalArgumentException("Section capacity cannot be less " +
                    "than the current enrollment count.");
    }

    private static void validateCourse(Course course)throws NullPointerException{
        if(course == null)
            throw new NullPointerException("Course cannot be null");
    }

    public boolean isSectionFull(){
        return enrolledList.size() >= capacity;
    }

    public boolean addEnrolledStudent(Student student)throws NullPointerException{
        if(student == null)
            throw new NullPointerException("Student cannot be null");

        if(isSectionFull())
            return false;

        int oldSize = enrolledList.size();

        enrolledList.add(student);

        return enrolledList.size() > oldSize;
    }

    public boolean addStudentToWaitList(Student student) throws NullPointerException{
        if(student == null)
            throw new NullPointerException("Student cannot be null");

        if(this.waitlist.contains(student))
            return false;

        this.waitlist.add(student);
        return true;
    }

    public boolean removeStudentFromEnrolledList(Student student) throws NullPointerException{
        if(student == null)
           throw new NullPointerException("Student cannot be null");
        if(!enrolledList.contains(student))
            return false;

        enrolledList.remove(student);

        if(!waitlist.isEmpty())
            enrolledList.add(waitlist.remove());

        return true;
    }

}
