package com.anushka.androidtutz.bindingdemo1;

import android.support.annotation.NonNull;

public class Student {

    private String studentName;
    private String studentEmail;

    public Student(String studentName, String studentEmail) {
        this.studentName = studentName;
        this.studentEmail = studentEmail;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }

    @NonNull
    @Override
    public String toString() {
        return "Nome: " + studentName + ". Email: " + studentEmail;
    }
}
