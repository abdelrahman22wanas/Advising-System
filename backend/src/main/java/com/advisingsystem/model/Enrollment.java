package com.advisingsystem.model;

public class Enrollment {
    private String studentId;
    private String studentName;
    private String courseId;
    private String courseName;
    private int credits;

    public Enrollment(String studentId, String studentName, String courseId, String courseName, int credits) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.courseId = courseId;
        this.courseName = courseName;
        this.credits = credits;
    }

    public String getStudentId() { return studentId; }
    public String getStudentName() { return studentName; }
    public String getCourseId() { return courseId; }
    public String getCourseName() { return courseName; }
    public int getCredits() { return credits; }
}
