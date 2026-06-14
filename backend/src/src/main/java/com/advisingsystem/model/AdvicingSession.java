package com.advisingsystem.model;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Represents an advising session between a student and advisor
 */
public class AdvicingSession {
    private String sessionId;
    private String studentId;
    private String advisorId;
    private LocalDateTime sessionDate;
    private String topic;
    private String notes;
    private String status;
    private List<String> recommendedCourses;

    public AdvicingSession(String sessionId, String studentId, String advisorId, LocalDateTime sessionDate, String topic) {
        this.sessionId = sessionId;
        this.studentId = studentId;
        this.advisorId = advisorId;
        this.sessionDate = sessionDate;
        this.topic = topic;
        this.notes = "";
        this.status = "SCHEDULED";
        this.recommendedCourses = new ArrayList<>();
    }

    // Getters and Setters
    public String getSessionId() {
        return sessionId;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getAdvisorId() {
        return advisorId;
    }

    public LocalDateTime getSessionDate() {
        return sessionDate;
    }

    public void setSessionDate(LocalDateTime sessionDate) {
        this.sessionDate = sessionDate;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getRecommendedCourses() {
        return new ArrayList<>(recommendedCourses);
    }

    public void addRecommendedCourse(String courseId) {
        if (!recommendedCourses.contains(courseId)) {
            recommendedCourses.add(courseId);
        }
    }

    public boolean isPast() {
        return sessionDate.isBefore(LocalDateTime.now());
    }

    public String getFormattedDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm");
        return sessionDate.format(formatter);
    }

    @Override
    public String toString() {
        return "AdvicingSession{" +
                "sessionId='" + sessionId + '\'' +
                ", studentId='" + studentId + '\'' +
                ", advisorId='" + advisorId + '\'' +
                ", date='" + getFormattedDate() + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
