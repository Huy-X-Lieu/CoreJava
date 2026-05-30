package com.CoreJava.smallEnrollmentSystem;

public class Student {
    private final String studentId;
    private String name;
    private String email;


    public Student(String id, String name,
                   String email)throws NullPointerException,
            IllegalArgumentException{
        if(id == null)
            throw new NullPointerException("Student ID cannot be null");
        if(name == null)
            throw new NullPointerException("Student name cannot be null");
        if(email == null)
            throw new NullPointerException("Student email cannot be null");

        if(id.isBlank())
            throw new IllegalArgumentException("Student ID cannot be " +
                    "blank or empty");
        if(name.isBlank())
            throw new IllegalArgumentException("Student name cannot be " +
                    "blank or empty");
        if(email.isBlank())
            throw new IllegalArgumentException("Student email cannot be " +
                    "blank or empty");

        this.studentId = normalizeStudentID(id);
        this.name = normalizeName(name);
        this.email = email;
    }

    public void setName(String name){
        this.name = normalizeName(name);
    }

    private String normalizeName(String name)throws IllegalArgumentException,
            NullPointerException{

        if(name == null)
            throw new NullPointerException("Student name cannot be null");
        if(name.isBlank())
            throw new IllegalArgumentException("Student name cannot be blank " +
                    "or empty");

        StringBuilder formatedName = new StringBuilder();
        boolean isUpperCase = true;
        for(char c: name.toLowerCase().toCharArray()){
            if(Character.isLetter(c)){
                if(isUpperCase){
                    isUpperCase = false;
                    c = Character.toUpperCase(c);
                }
                formatedName.append(c);
            }else if(Character.isSpaceChar(c)){
                if(!isUpperCase){
                    formatedName.append(' ');
                    isUpperCase = true;
                }
            }else{
                throw new IllegalArgumentException("Student name {"+name +
                        "}can contain only alphabetic letters and space");
            }
        }

        if(Character.isSpaceChar(formatedName.charAt(formatedName.length()-1)))
            formatedName.deleteCharAt(formatedName.length() - 1);

        return formatedName.toString();
    }

    private String normalizeStudentID(String id)throws NullPointerException,
            IllegalArgumentException{
        if(id == null)
            throw new NullPointerException("Student name cannot be null");
        if(id.isBlank())
            throw new IllegalArgumentException("Student name cannot be blank " +
                    "or empty");

        StringBuilder normalizeId = new StringBuilder();
        for(char c : id.strip().toUpperCase().toCharArray()){
            if(Character.isAlphabetic(c) || Character.isDigit(c))
                normalizeId.append(c);
            else
                throw new IllegalArgumentException("Invalid student id: " + id);
        }

        return normalizeId.toString();
    }
}
