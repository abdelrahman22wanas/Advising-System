package com.advisingsystem.model;

import java.util.*;

/**
 * Represents a student in the advising system
 */
public class Student {
    private String studentId;
    private String firstName;
    private String lastName;
    private String email;
    private String major;
    private int graduationYear;
    private List<Course> completedCourses;
    private List<Course> enrolledCourses;
    private double gpa;

    public Student(String studentId, String firstName, String lastName, String email, String major, int graduationYear) {
        this.studentId = studentId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.major = major;
        this.graduationYear = graduationYear;
        this.completedCourses = new ArrayList<>();
        this.enrolledCourses = new ArrayList<>();
        this.gpa = 0.0;
    }

    // Getters and Setters
    public String getStudentId() {
        return studentId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public int getGraduationYear() {
        return graduationYear;
    }

    public void setGraduationYear(int graduationYear) {
        this.graduationYear = graduationYear;
    }

    public List<Course> getCompletedCourses() {
        return new ArrayList<>(completedCourses);
    }

    public void addCompletedCourse(Course course) {
        completedCourses.add(course);
    }

    public List<Course> getEnrolledCourses() {
        return new ArrayList<>(enrolledCourses);
    }

    public void enrollCourse(Course course) {
        enrolledCourses.add(course);
    }

    public void dropCourse(Course course) {
        enrolledCourses.remove(course);
    }

    public double getGpa() {
        return gpa;
    }

    public void setGpa(double gpa) {
        this.gpa = Math.min(4.0, Math.max(0.0, gpa));
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentId='" + studentId + '\'' +
                ", name='" + getFullName() + '\'' +
                ", major='" + major + '\'' +
                ", gpa=" + String.format("%.2f", gpa) +
                ", graduationYear=" + graduationYear +
                '}';
    }
}
