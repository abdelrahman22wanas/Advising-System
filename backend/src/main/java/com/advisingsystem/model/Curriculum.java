package com.advisingsystem.model;

import java.util.*;

/**
 * Represents a curriculum/study plan for a major
 */
public class Curriculum {
    private String majorName;
    private int totalCreditsRequired;
    private Map<CourseCategory, List<Course>> coursesByCategory;
    private Map<String, Integer> semesterPlan; // Course ID -> Recommended Semester
    private int minimumGPA;

    public Curriculum(String majorName, int totalCreditsRequired) {
        this.majorName = majorName;
        this.totalCreditsRequired = totalCreditsRequired;
        this.coursesByCategory = new HashMap<>();
        this.semesterPlan = new HashMap<>();
        this.minimumGPA = 2;
        
        // Initialize all categories
        for (CourseCategory category : CourseCategory.values()) {
            coursesByCategory.put(category, new ArrayList<>());
        }
    }

    public void addCourse(Course course, CourseCategory category, int recommendedSemester) {
        coursesByCategory.get(category).add(course);
        semesterPlan.put(course.getCourseId(), recommendedSemester);
    }

    public List<Course> getCoursesByCategory(CourseCategory category) {
        return new ArrayList<>(coursesByCategory.getOrDefault(category, new ArrayList<>()));
    }

    public int getRecommendedSemester(String courseId) {
        return semesterPlan.getOrDefault(courseId, 0);
    }

    public String getMajorName() {
        return majorName;
    }

    public int getTotalCreditsRequired() {
        return totalCreditsRequired;
    }

    public Map<CourseCategory, List<Course>> getAllCourses() {
        return new HashMap<>(coursesByCategory);
    }

    public int getMinimumGPA() {
        return minimumGPA;
    }

    public void setMinimumGPA(int minimumGPA) {
        this.minimumGPA = minimumGPA;
    }
}
