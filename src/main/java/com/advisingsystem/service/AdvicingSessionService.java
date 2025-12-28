package com.advisingsystem.service;

import com.advisingsystem.model.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service class for managing advising sessions
 */
public class AdvicingSessionService {
    private Map<String, AdvicingSession> sessions;
    private int sessionCounter;

    public AdvicingSessionService() {
        this.sessions = new HashMap<>();
        this.sessionCounter = 1000;
    }

    public AdvicingSession createSession(String studentId, String advisorId, java.time.LocalDateTime sessionDate, String topic) {
        String sessionId = "SESSION" + (++sessionCounter);
        AdvicingSession session = new AdvicingSession(sessionId, studentId, advisorId, sessionDate, topic);
        sessions.put(sessionId, session);
        return session;
    }

    public AdvicingSession getSession(String sessionId) {
        return sessions.get(sessionId);
    }

    public List<AdvicingSession> getAllSessions() {
        return new ArrayList<>(sessions.values());
    }

    public List<AdvicingSession> getSessionsByStudent(String studentId) {
        return sessions.values().stream()
                .filter(s -> s.getStudentId().equals(studentId))
                .collect(Collectors.toList());
    }

    public List<AdvicingSession> getSessionsByAdvisor(String advisorId) {
        return sessions.values().stream()
                .filter(s -> s.getAdvisorId().equals(advisorId))
                .collect(Collectors.toList());
    }

    public List<AdvicingSession> getScheduledSessions() {
        return sessions.values().stream()
                .filter(s -> s.getStatus().equals("SCHEDULED"))
                .collect(Collectors.toList());
    }

    public List<AdvicingSession> getCompletedSessions() {
        return sessions.values().stream()
                .filter(s -> s.getStatus().equals("COMPLETED"))
                .collect(Collectors.toList());
    }

    public List<AdvicingSession> getUpcomingSessions() {
        return sessions.values().stream()
                .filter(s -> !s.isPast() && s.getStatus().equals("SCHEDULED"))
                .collect(Collectors.toList());
    }

    public boolean completeSession(String sessionId) {
        AdvicingSession session = getSession(sessionId);
        if (session != null) {
            session.setStatus("COMPLETED");
            return true;
        }
        return false;
    }

    public boolean cancelSession(String sessionId) {
        AdvicingSession session = getSession(sessionId);
        if (session != null && session.getStatus().equals("SCHEDULED")) {
            session.setStatus("CANCELLED");
            return true;
        }
        return false;
    }

    public void createSessionFromExisting(AdvicingSession session) {
        sessions.put(session.getSessionId(), session);
    }

    public int getTotalSessions() {
        return sessions.size();
    }
}
