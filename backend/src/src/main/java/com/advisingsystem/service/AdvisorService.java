package com.advisingsystem.service;

import com.advisingsystem.model.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service class for managing advisors
 */
public class AdvisorService {
    private Map<String, Advisor> advisors;
    private Map<String, String> studentAdvisorMapping; // studentId -> advisorId

    public AdvisorService() {
        this.advisors = new HashMap<>();
        this.studentAdvisorMapping = new HashMap<>();
    }

    public void addAdvisor(Advisor advisor) {
        if (!advisors.containsKey(advisor.getAdvisorId())) {
            advisors.put(advisor.getAdvisorId(), advisor);
        }
    }

    public Advisor getAdvisor(String advisorId) {
        return advisors.get(advisorId);
    }

    public List<Advisor> getAllAdvisors() {
        return new ArrayList<>(advisors.values());
    }

    public boolean removeAdvisor(String advisorId) {
        if (advisors.containsKey(advisorId)) {
            Advisor advisor = advisors.get(advisorId);
            // Remove all student assignments
            advisor.getAssignedStudentIds().forEach(studentId -> 
                studentAdvisorMapping.remove(studentId)
            );
            return advisors.remove(advisorId) != null;
        }
        return false;
    }

    public boolean assignStudentToAdvisor(String studentId, String advisorId) {
        Advisor advisor = getAdvisor(advisorId);
        if (advisor != null && advisor.canAcceptMoreStudents()) {
            advisor.assignStudent(studentId);
            studentAdvisorMapping.put(studentId, advisorId);
            return true;
        }
        return false;
    }

    public boolean removeStudentFromAdvisor(String studentId, String advisorId) {
        Advisor advisor = getAdvisor(advisorId);
        if (advisor != null) {
            advisor.removeStudent(studentId);
            studentAdvisorMapping.remove(studentId);
            return true;
        }
        return false;
    }

    public Advisor getStudentAdvisor(String studentId) {
        String advisorId = studentAdvisorMapping.get(studentId);
        return advisorId != null ? getAdvisor(advisorId) : null;
    }

    public List<Advisor> getAdvisorsBySpecialization(String specialization) {
        return advisors.values().stream()
                .filter(a -> a.getSpecializations().contains(specialization))
                .collect(Collectors.toList());
    }

    public List<Advisor> getAdvisorsWithAvailableCapacity() {
        return advisors.values().stream()
                .filter(Advisor::canAcceptMoreStudents)
                .collect(Collectors.toList());
    }

    public int getTotalAdvisors() {
        return advisors.size();
    }

    public boolean advisorExists(String advisorId) {
        return advisors.containsKey(advisorId);
    }
}
