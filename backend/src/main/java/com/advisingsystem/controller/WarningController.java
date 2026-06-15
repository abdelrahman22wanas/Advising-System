package com.advisingsystem.controller;

import com.advisingsystem.model.Warning;
import com.advisingsystem.service.WarningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/warnings")
@CrossOrigin(origins = "*", maxAge = 3600)
public class WarningController {

    @Autowired
    private WarningService warningService;

    @GetMapping
    public ResponseEntity<List<Warning>> getAllWarnings() {
        return ResponseEntity.ok(warningService.getAllWarnings());
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Warning>> getStudentWarnings(@PathVariable String studentId) {
        return ResponseEntity.ok(warningService.getStudentWarnings(studentId));
    }

    @PostMapping("/generate")
    public ResponseEntity<List<Warning>> generateWarnings() {
        return ResponseEntity.ok(warningService.generateAllWarnings());
    }
}
