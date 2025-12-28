package com.advisingsystem.model;

/**
 * Represents a grade for a completed course
 */
public class Grade {
    private String studentId;
    private String courseId;
    private String letterGrade;
    private double gpa;
    private String semester;

    public Grade(String studentId, String courseId, String letterGrade, double gpa, String semester) {
        this.studentId = studentId;
        this.courseId = courseId;
        this.letterGrade = letterGrade;
        this.gpa = gpa;
        this.semester = semester;
    }

    // Getters
    public String getStudentId() {
        return studentId;
    }

    public String getCourseId() {
        return courseId;
    }

    public String getLetterGrade() {
        return letterGrade;
    }

    public void setLetterGrade(String letterGrade) {
        this.letterGrade = letterGrade;
    }

    public double getGpa() {
        return gpa;
    }

    public void setGpa(double gpa) {
        this.gpa = gpa;
    }

    public String getSemester() {
        return semester;
    }

    public static double getGPAForGrade(String letterGrade) {
        return switch (letterGrade.toUpperCase()) {
            case "A" -> 4.0;
            case "A-" -> 3.7;
            case "B+" -> 3.3;
            case "B" -> 3.0;
            case "B-" -> 2.7;
            case "C+" -> 2.3;
            case "C" -> 2.0;
            case "C-" -> 1.7;
            case "D+" -> 1.3;
            case "D" -> 1.0;
            case "F" -> 0.0;
            default -> 0.0;
        };
    }

    @Override
    public String toString() {
        return "Grade{" +
                "studentId='" + studentId + '\'' +
                ", courseId='" + courseId + '\'' +
                ", grade='" + letterGrade + '\'' +
                ", gpa=" + String.format("%.2f", gpa) +
                ", semester='" + semester + '\'' +
                '}';
    }
}
