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
    private int currentSemester;
    private int totalCreditsEarned;

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
        this.currentSemester = 1;
        this.totalCreditsEarned = 0;
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
        totalCreditsEarned += course.getCredits();
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

    public int getCurrentSemester() {
        return currentSemester;
    }

    public void setCurrentSemester(int currentSemester) {
        this.currentSemester = currentSemester;
    }

    public int getTotalCreditsEarned() {
        return totalCreditsEarned;
    }

    public void setTotalCreditsEarned(int totalCreditsEarned) {
        this.totalCreditsEarned = totalCreditsEarned;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public String getAcademicStanding() {
        if (totalCreditsEarned < 30) return "Freshman";
        if (totalCreditsEarned < 60) return "Sophomore";
        if (totalCreditsEarned < 90) return "Junior";
        return "Senior";
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentId='" + studentId + '\'' +
                ", name='" + getFullName() + '\'' +
                ", major='" + major + '\'' +
                ", gpa=" + String.format("%.2f", gpa) +
                ", standing='" + getAcademicStanding() + '\'' +
                ", credits=" + totalCreditsEarned +
                ", graduationYear=" + graduationYear +
                '}';
    }
}
