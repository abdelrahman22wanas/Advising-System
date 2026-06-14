package com.advisingsystem.controller;

import com.advisingsystem.model.AdvicingSession;
import com.advisingsystem.service.AdvicingSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sessions")
@CrossOrigin(origins = "*", maxAge = 3600)
public class SessionController {

    @Autowired
    private AdvicingSessionService sessionService;

    @GetMapping
    public ResponseEntity<List<AdvicingSession>> getAllSessions() {
        return ResponseEntity.ok(sessionService.getAllSessions());
    }

    @GetMapping("/{sessionId}")
    public ResponseEntity<AdvicingSession> getSession(@PathVariable String sessionId) {
        AdvicingSession session = sessionService.getSession(sessionId);
        if (session != null) {
            return ResponseEntity.ok(session);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<AdvicingSession> addSession(@RequestBody AdvicingSession session) {
        sessionService.addSession(session);
        return ResponseEntity.status(201).body(session);
    }

    @DeleteMapping("/{sessionId}")
    public ResponseEntity<Void> removeSession(@PathVariable String sessionId) {
        if (sessionService.removeSession(sessionId)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<AdvicingSession>> getStudentSessions(@PathVariable String studentId) {
        return ResponseEntity.ok(sessionService.getStudentSessions(studentId));
    }

    @GetMapping("/advisor/{advisorId}")
    public ResponseEntity<List<AdvicingSession>> getAdvisorSessions(@PathVariable String advisorId) {
        return ResponseEntity.ok(sessionService.getAdvisorSessions(advisorId));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<AdvicingSession>> getSessionsByStatus(@PathVariable String status) {
        return ResponseEntity.ok(sessionService.getSessionsByStatus(status));
    }

    @PutMapping("/{sessionId}/status/{newStatus}")
    public ResponseEntity<AdvicingSession> updateSessionStatus(
            @PathVariable String sessionId,
            @PathVariable String newStatus) {
        sessionService.updateSessionStatus(sessionId, newStatus);
        AdvicingSession session = sessionService.getSession(sessionId);
        if (session != null) {
            return ResponseEntity.ok(session);
        }
        return ResponseEntity.notFound().build();
    }
}
