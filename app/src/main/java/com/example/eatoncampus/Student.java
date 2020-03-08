package com.example.eatoncampus;
import java.io.Serializable;

public class Student implements Serializable{
    public static String studentID;
    public static String studentFname;
    public static String studentLname;
    public static String studentPassword;

    public Student(String studentID, String studentFname, String studentLname, String studentPassword) {
        this.studentID = studentID;
        this.studentFname = studentFname;
        this.studentLname = studentLname;
        this.studentPassword = studentPassword;
    }

    public String getStudentID() {
        return studentID;
    }


}
