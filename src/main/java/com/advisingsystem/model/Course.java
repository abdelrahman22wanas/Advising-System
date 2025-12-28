package com.advisingsystem.model;

import java.util.*;

/**
 * Represents a course in the academic system
 */
public class Course {
    private String courseId;
    private String courseName;
    private String department;
    private int credits;
    private List<String> prerequisites;
    private int capacity;
    private int enrolledCount;
    private String semester;
    private String instructor;

    public Course(String courseId, String courseName, String department, int credits, int capacity, String semester) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.department = department;
        this.credits = credits;
        this.capacity = capacity;
        this.enrolledCount = 0;
        this.prerequisites = new ArrayList<>();
        this.semester = semester;
    }

    // Getters and Setters
    public String getCourseId() {
        return courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public List<String> getPrerequisites() {
        return new ArrayList<>(prerequisites);
    }

    public void addPrerequisite(String prerequisiteCourseId) {
        if (!prerequisites.contains(prerequisiteCourseId)) {
            prerequisites.add(prerequisiteCourseId);
        }
    }

    public int getCapacity() {
        return capacity;
    }

    public int getEnrolledCount() {
        return enrolledCount;
    }

    public boolean hasAvailableSeats() {
        return enrolledCount < capacity;
    }

    public void incrementEnrolled() {
        if (hasAvailableSeats()) {
            enrolledCount++;
        }
    }

    public void decrementEnrolled() {
        if (enrolledCount > 0) {
            enrolledCount--;
        }
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public int getAvailableSeats() {
        return capacity - enrolledCount;
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseId='" + courseId + '\'' +
                ", courseName='" + courseName + '\'' +
                ", credits=" + credits +
                ", semester='" + semester + '\'' +
                ", available=" + getAvailableSeats() + "/" + capacity +
                '}';
    }
}
