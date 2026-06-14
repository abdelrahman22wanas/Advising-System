package com.advisingsystem.controller;

import com.advisingsystem.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "*", maxAge = 3600)
public class DashboardController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private AdvisorService advisorService;

    @Autowired
    private AdvicingSessionService sessionService;

    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getDashboardStats() {
        Map<String, Object> stats = new HashMap<>();
        
        stats.put("totalStudents", studentService.getAllStudents().size());
        stats.put("totalCourses", courseService.getAllCourses().size());
        stats.put("totalAdvisors", advisorService.getAllAdvisors().size());
        stats.put("totalSessions", sessionService.getAllSessions().size());
        
        // Additional stats
        stats.put("averageStudentGPA", studentService.getAllStudents().stream()
                .mapToDouble(s -> s.getGpa())
                .average()
                .orElse(0.0));
        
        stats.put("sessionsCompleted", sessionService.getAllSessions().stream()
                .filter(s -> "Completed".equals(s.getStatus()))
                .count());
        
        stats.put("sessionsScheduled", sessionService.getAllSessions().stream()
                .filter(s -> "Scheduled".equals(s.getStatus()))
                .count());
        
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "UP");
        response.put("message", "Advising System API is running");
        return ResponseEntity.ok(response);
    }
}
