package com.advisingsystem.service;

import com.advisingsystem.model.*;
import java.util.*;
import java.util.stream.Collectors;

public class WarningService {
    private StudentService studentService;
    private GradeService gradeService;
    private CurriculumService curriculumService;
    private List<Warning> warnings;
    private int warningCounter;

    public WarningService(StudentService studentService, GradeService gradeService, CurriculumService curriculumService) {
        this.studentService = studentService;
        this.gradeService = gradeService;
        this.curriculumService = curriculumService;
        this.warnings = new ArrayList<>();
        this.warningCounter = 0;
    }

    public List<Warning> generateAllWarnings() {
        warnings.clear();
        warningCounter = 0;

        for (Student student : studentService.getAllStudents()) {
            checkGPACriteria(student);
            checkLowCompletionRate(student);
            checkGraduationProgress(student);
            checkExcessiveEnrollment(student);
        }

        return new ArrayList<>(warnings);
    }

    public List<Warning> getStudentWarnings(String studentId) {
        return warnings.stream()
                .filter(w -> w.getStudentId().equals(studentId))
                .collect(Collectors.toList());
    }

    private void addWarning(String studentId, String type, String message, String severity) {
        String warningId = "WARN" + (++warningCounter);
        warnings.add(new Warning(warningId, studentId, type, message, severity));
    }

    private void checkGPACriteria(Student student) {
        double gpa = student.getGpa();
        if (gpa > 0 && gpa < 2.0) {
            addWarning(student.getStudentId(), "LOW_GPA",
                "GPA is " + String.format("%.2f", gpa) + " - below 2.0 minimum. Academic probation risk.",
                "HIGH");
        } else if (gpa >= 2.0 && gpa < 2.5) {
            addWarning(student.getStudentId(), "LOW_GPA",
                "GPA is " + String.format("%.2f", gpa) + " - below 2.5. Consider academic support.",
                "MEDIUM");
        }
    }

    private void checkLowCompletionRate(Student student) {
        List<Course> completed = student.getCompletedCourses();
        List<Course> enrolled = student.getEnrolledCourses();
        int totalAttempted = completed.size() + enrolled.size();

        if (totalAttempted > 0) {
            double completionRate = (double) completed.size() / totalAttempted * 100;
            if (completionRate < 50 && totalAttempted >= 3) {
                addWarning(student.getStudentId(), "LOW_COMPLETION",
                    "Course completion rate is " + String.format("%.0f", completionRate) +
                    "% (" + completed.size() + "/" + totalAttempted + " courses completed).",
                    "HIGH");
            }
        }
    }

    private void checkGraduationProgress(Student student) {
        DegreeProgress progress = curriculumService.calculateProgress(student);
        if (progress != null) {
            double completionPct = progress.getCompletionPercentage();
            int currentSemester = student.getCurrentSemester();
            int totalSemesters = 8;

            double expectedProgress = (double) currentSemester / totalSemesters * 100;
            if (completionPct < expectedProgress - 15 && currentSemester >= 4) {
                addWarning(student.getStudentId(), "GRADUATION_OFF_TRACK",
                    "Only " + String.format("%.0f", completionPct) + "% of degree completed by semester " +
                    currentSemester + ". On track for " + String.format("%.0f", expectedProgress) + "%.",
                    "MEDIUM");
            }
        }
    }

    private void checkExcessiveEnrollment(Student student) {
        int enrolledCount = student.getEnrolledCourses().size();
        if (enrolledCount > 6) {
            addWarning(student.getStudentId(), "EXCESSIVE_ENROLLMENT",
                "Currently enrolled in " + enrolledCount + " courses. Consider dropping some.",
                "LOW");
        }
    }

    public List<Warning> getAllWarnings() {
        return new ArrayList<>(warnings);
    }
}
