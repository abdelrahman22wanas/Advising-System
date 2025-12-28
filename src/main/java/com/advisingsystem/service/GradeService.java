package com.advisingsystem.service;

import com.advisingsystem.model.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service class for managing grades and academic records
 */
public class GradeService {
    private List<Grade> grades;
    private CourseService courseService;
    private StudentService studentService;

    public GradeService(CourseService courseService, StudentService studentService) {
        this.grades = new ArrayList<>();
        this.courseService = courseService;
        this.studentService = studentService;
    }

    public void recordGrade(Grade grade) {
        // Check if grade already exists for this student and course
        grades.stream()
                .filter(g -> g.getStudentId().equals(grade.getStudentId()) 
                        && g.getCourseId().equals(grade.getCourseId()))
                .findFirst()
                .ifPresent(grades::remove);
        grades.add(grade);
    }

    public Grade getGrade(String studentId, String courseId) {
        return grades.stream()
                .filter(g -> g.getStudentId().equals(studentId) && g.getCourseId().equals(courseId))
                .findFirst()
                .orElse(null);
    }

    public List<Grade> getStudentGrades(String studentId) {
        return grades.stream()
                .filter(g -> g.getStudentId().equals(studentId))
                .collect(Collectors.toList());
    }

    public List<Grade> getCourseGrades(String courseId) {
        return grades.stream()
                .filter(g -> g.getCourseId().equals(courseId))
                .collect(Collectors.toList());
    }

    public List<Grade> getGradesBySemester(String semester) {
        return grades.stream()
                .filter(g -> g.getSemester().equals(semester))
                .collect(Collectors.toList());
    }

    public double calculateStudentGPA(String studentId) {
        List<Grade> studentGrades = getStudentGrades(studentId);
        if (studentGrades.isEmpty()) {
            return 0.0;
        }
        double totalGPA = studentGrades.stream()
                .mapToDouble(Grade::getGpa)
                .sum();
        return totalGPA / studentGrades.size();
    }

    public List<Grade> getStudentGradesByLetter(String studentId, String letterGrade) {
        return grades.stream()
                .filter(g -> g.getStudentId().equals(studentId) && g.getLetterGrade().equals(letterGrade))
                .collect(Collectors.toList());
    }

    public double getAverageGradeForCourse(String courseId) {
        List<Grade> courseGrades = getCourseGrades(courseId);
        if (courseGrades.isEmpty()) {
            return 0.0;
        }
        return courseGrades.stream()
                .mapToDouble(Grade::getGpa)
                .average()
                .orElse(0.0);
    }

    public List<Grade> getAllGrades() {
        return new ArrayList<>(grades);
    }

    public int getTotalGrades() {
        return grades.size();
    }
}
