package com.advisingsystem.controller;

import com.advisingsystem.model.Advisor;
import com.advisingsystem.service.AdvisorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/advisors")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AdvisorController {

    @Autowired
    private AdvisorService advisorService;

    @GetMapping
    public ResponseEntity<List<Advisor>> getAllAdvisors() {
        return ResponseEntity.ok(advisorService.getAllAdvisors());
    }

    @GetMapping("/{advisorId}")
    public ResponseEntity<Advisor> getAdvisor(@PathVariable String advisorId) {
        Advisor advisor = advisorService.getAdvisor(advisorId);
        if (advisor != null) {
            return ResponseEntity.ok(advisor);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Advisor> addAdvisor(@RequestBody Advisor advisor) {
        advisorService.addAdvisor(advisor);
        return ResponseEntity.status(201).body(advisor);
    }

    @DeleteMapping("/{advisorId}")
    public ResponseEntity<Void> removeAdvisor(@PathVariable String advisorId) {
        if (advisorService.removeAdvisor(advisorId)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/{advisorId}/students/{studentId}")
    public ResponseEntity<Void> assignStudentToAdvisor(
            @PathVariable String advisorId,
            @PathVariable String studentId) {
        advisorService.assignStudentToAdvisor(studentId, advisorId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{advisorId}/students")
    public ResponseEntity<List<String>> getAdvisedStudents(@PathVariable String advisorId) {
        Advisor advisor = advisorService.getAdvisor(advisorId);
        if (advisor != null) {
            return ResponseEntity.ok(advisor.getStudentIds());
        }
        return ResponseEntity.notFound().build();
    }
}
