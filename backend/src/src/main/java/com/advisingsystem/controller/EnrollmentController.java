package com.advisingsystem.controller;

import com.advisingsystem.model.Enrollment;
import com.advisingsystem.service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
@CrossOrigin(origins = "*", maxAge = 3600)
public class EnrollmentController {

    @Autowired
    private EnrollmentService enrollmentService;

    @GetMapping
    public ResponseEntity<List<Enrollment>> getAllEnrollments() {
        return ResponseEntity.ok(enrollmentService.getAllEnrollments());
    }

    @PostMapping("/student/{studentId}/course/{courseId}")
    public ResponseEntity<?> enrollStudentInCourse(
            @PathVariable String studentId,
            @PathVariable String courseId) {
        boolean success = enrollmentService.enrollStudentInCourse(studentId, courseId);
        if (success) {
            return ResponseEntity.status(201).body("Enrollment successful");
        }
        return ResponseEntity.status(400).body("Enrollment failed");
    }

    @DeleteMapping("/student/{studentId}/course/{courseId}")
    public ResponseEntity<?> dropCourse(
            @PathVariable String studentId,
            @PathVariable String courseId) {
        boolean success = enrollmentService.dropCourse(studentId, courseId);
        if (success) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(400).body("Drop failed");
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Enrollment>> getStudentEnrollments(@PathVariable String studentId) {
        return ResponseEntity.ok(enrollmentService.getStudentEnrollments(studentId));
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<Enrollment>> getCourseEnrollments(@PathVariable String courseId) {
        return ResponseEntity.ok(enrollmentService.getCourseEnrollments(courseId));
    }

    @GetMapping("/student/{studentId}/available-courses")
    public ResponseEntity<List<String>> getAvailableCoursesForStudent(@PathVariable String studentId) {
        return ResponseEntity.ok(enrollmentService.getAvailableCoursesForStudent(studentId));
    }

    @GetMapping("/student/{studentId}/credits")
    public ResponseEntity<Integer> getStudentTotalCreditsCompleted(@PathVariable String studentId) {
        int credits = enrollmentService.getStudentTotalCreditsCompleted(studentId);
        return ResponseEntity.ok(credits);
    }
}
