package com.CoreJava.smallEnrollmentSystem;


public class Student {
    private final String studentId;
    private String name;
    private String email;


    public Student(String id, String name,
                   String email)throws NullPointerException,
            IllegalArgumentException{
        this.studentId = normalizeStudentID(id);
        this.name = normalizeName(name);
        this.email = normalizeEmail(email);
    }

    public void setName(String name){
        this.name = normalizeName(name);
    }
    public void setEmail(String email){
        this.email = normalizeEmail(email);
    }

    public String getName(){
       return this.name;
    }

    public String getStudentId(){
        return this.studentId;
    }

    public String getEmail(){
        return this.email;
    }

    private String normalizeName(String name){
        Utils.checkStringForNullOrBlank(name, "Student name");

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

    private String normalizeStudentID(String id){
        Utils.checkStringForNullOrBlank(id, "Student ID");
        StringBuilder normalizeId = new StringBuilder();
        for(char c : id.strip().toUpperCase().toCharArray()){
            if(Character.isAlphabetic(c) || Character.isDigit(c))
                normalizeId.append(c);
            else
                throw new IllegalArgumentException("Invalid student id: " + id);
        }

        return normalizeId.toString();
    }

    private String normalizeEmail(String email){
        Utils.checkStringForNullOrBlank(email, "Student email");

        String emailRegex =
                "^[A-Za-z0-9]+([._+-]?[A-Za-z0-9]+)*@[A-Za-z0-9]+([.-]?[A-Za-z0-9]+)*\\.[A-Za-z]{2,}$";
       if(!email.strip().matches(emailRegex))
           throw new IllegalArgumentException("Email: " + email + " contains " +
                   "invalid character(s)");

       return email.strip().toLowerCase();
    }

    @Override
    public boolean equals(Object otherObject){
        if(this == otherObject)
            return true;

        if(!(otherObject instanceof Student otherStudent))
            return false;

        return this.studentId.equals(otherStudent.studentId);
    }

    @Override
    public int hashCode(){
        return this.studentId.hashCode();
    }
}
