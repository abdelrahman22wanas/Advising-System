package com.advisingsystem.model;

import java.util.*;

/**
 * Tracks a student's progress toward degree completion
 */
public class DegreeProgress {
    private Student student;
    private Curriculum curriculum;
    private Map<CourseCategory, Integer> completedCreditsByCategory;
    private Map<CourseCategory, Integer> requiredCreditsByCategory;
    private int totalCompletedCredits;

    public DegreeProgress(Student student, Curriculum curriculum) {
        this.student = student;
        this.curriculum = curriculum;
        this.completedCreditsByCategory = new HashMap<>();
        this.requiredCreditsByCategory = new HashMap<>();
        this.totalCompletedCredits = 0;
        
        calculateProgress();
    }

    private void calculateProgress() {
        // Reset counters
        totalCompletedCredits = 0;
        for (CourseCategory category : CourseCategory.values()) {
            completedCreditsByCategory.put(category, 0);
        }

        // Calculate completed credits
        for (Course course : student.getCompletedCourses()) {
            totalCompletedCredits += course.getCredits();
            // Determine category and add credits
            CourseCategory category = determineCourseCategory(course);
            if (category != null) {
                int current = completedCreditsByCategory.getOrDefault(category, 0);
                completedCreditsByCategory.put(category, current + course.getCredits());
            }
        }

        // Calculate required credits per category from curriculum
        for (Map.Entry<CourseCategory, List<Course>> entry : curriculum.getAllCourses().entrySet()) {
            int totalRequired = entry.getValue().stream()
                    .mapToInt(Course::getCredits)
                    .sum();
            requiredCreditsByCategory.put(entry.getKey(), totalRequired);
        }
    }

    private CourseCategory determineCourseCategory(Course course) {
        // Check in which category this course belongs in the curriculum
        for (Map.Entry<CourseCategory, List<Course>> entry : curriculum.getAllCourses().entrySet()) {
            if (entry.getValue().stream().anyMatch(c -> c.getCourseId().equals(course.getCourseId()))) {
                return entry.getKey();
            }
        }
        return null;
    }

    public double getCompletionPercentage() {
        if (curriculum.getTotalCreditsRequired() == 0) return 0.0;
        return (totalCompletedCredits * 100.0) / curriculum.getTotalCreditsRequired();
    }

    public int getTotalCompletedCredits() {
        return totalCompletedCredits;
    }

    public int getTotalRequiredCredits() {
        return curriculum.getTotalCreditsRequired();
    }

    public Map<CourseCategory, Integer> getCompletedCreditsByCategory() {
        return new HashMap<>(completedCreditsByCategory);
    }

    public Map<CourseCategory, Integer> getRequiredCreditsByCategory() {
        return new HashMap<>(requiredCreditsByCategory);
    }

    public List<Course> getRemainingCourses() {
        List<Course> remaining = new ArrayList<>();
        Set<String> completedIds = new HashSet<>();
        
        for (Course c : student.getCompletedCourses()) {
            completedIds.add(c.getCourseId());
        }

        for (List<Course> courses : curriculum.getAllCourses().values()) {
            for (Course course : courses) {
                if (!completedIds.contains(course.getCourseId())) {
                    remaining.add(course);
                }
            }
        }
        
        return remaining;
    }

    public boolean isEligibleForGraduation() {
        return totalCompletedCredits >= curriculum.getTotalCreditsRequired() 
                && student.getGpa() >= curriculum.getMinimumGPA();
    }
}
