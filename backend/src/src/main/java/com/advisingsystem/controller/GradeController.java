package com.advisingsystem.controller;

import com.advisingsystem.model.Grade;
import com.advisingsystem.service.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/grades")
@CrossOrigin(origins = "*", maxAge = 3600)
public class GradeController {

    @Autowired
    private GradeService gradeService;

    @GetMapping
    public ResponseEntity<List<Grade>> getAllGrades() {
        return ResponseEntity.ok(gradeService.getAllGrades());
    }

    @PostMapping
    public ResponseEntity<Grade> recordGrade(@RequestBody Grade grade) {
        gradeService.recordGrade(grade);
        return ResponseEntity.status(201).body(grade);
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Grade>> getStudentGrades(@PathVariable String studentId) {
        return ResponseEntity.ok(gradeService.getStudentGrades(studentId));
    }

    @GetMapping("/student/{studentId}/gpa")
    public ResponseEntity<Double> calculateStudentGPA(@PathVariable String studentId) {
        double gpa = gradeService.calculateStudentGPA(studentId);
        return ResponseEntity.ok(gpa);
    }

    @GetMapping("/course/{courseId}/average")
    public ResponseEntity<Double> getAverageGradeForCourse(@PathVariable String courseId) {
        double average = gradeService.getAverageGradeForCourse(courseId);
        return ResponseEntity.ok(average);
    }

    @GetMapping("/student/{studentId}/semester/{semester}")
    public ResponseEntity<List<Grade>> getStudentSemesterGrades(
            @PathVariable String studentId,
            @PathVariable String semester) {
        return ResponseEntity.ok(gradeService.getStudentSemesterGrades(studentId, semester));
    }
}
