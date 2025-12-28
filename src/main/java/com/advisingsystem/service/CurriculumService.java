package com.advisingsystem.service;

import com.advisingsystem.model.*;
import java.util.*;

/**
 * Service for managing curriculum and degree progress tracking
 */
public class CurriculumService {
    private Map<String, Curriculum> curriculaByMajor;

    public CurriculumService() {
        this.curriculaByMajor = new HashMap<>();
    }

    public void addCurriculum(Curriculum curriculum) {
        curriculaByMajor.put(curriculum.getMajorName(), curriculum);
    }

    public Curriculum getCurriculum(String majorName) {
        return curriculaByMajor.get(majorName);
    }

    public List<String> getAllMajors() {
        return new ArrayList<>(curriculaByMajor.keySet());
    }

    public DegreeProgress calculateProgress(Student student) {
        Curriculum curriculum = curriculaByMajor.get(student.getMajor());
        if (curriculum == null) {
            return null;
        }
        return new DegreeProgress(student, curriculum);
    }

    public List<Course> getRecommendedCoursesForSemester(Student student, int semester) {
        Curriculum curriculum = curriculaByMajor.get(student.getMajor());
        if (curriculum == null) {
            return new ArrayList<>();
        }

        List<Course> recommended = new ArrayList<>();
        Set<String> completedIds = new HashSet<>();
        Set<String> enrolledIds = new HashSet<>();

        for (Course c : student.getCompletedCourses()) {
            completedIds.add(c.getCourseId());
        }
        for (Course c : student.getEnrolledCourses()) {
            enrolledIds.add(c.getCourseId());
        }

        // Find courses recommended for this semester that haven't been taken
        for (Map.Entry<CourseCategory, List<Course>> entry : curriculum.getAllCourses().entrySet()) {
            for (Course course : entry.getValue()) {
                if (course.getRecommendedSemester() == semester &&
                    !completedIds.contains(course.getCourseId()) &&
                    !enrolledIds.contains(course.getCourseId()) &&
                    arePrerequisitesMet(course, completedIds)) {
                    recommended.add(course);
                }
            }
        }

        return recommended;
    }

    public boolean arePrerequisitesMet(Course course, Set<String> completedCourseIds) {
        for (String prereq : course.getPrerequisites()) {
            if (!completedCourseIds.contains(prereq)) {
                return false;
            }
        }
        return true;
    }

    public List<Course> getAvailableCourses(Student student) {
        Curriculum curriculum = curriculaByMajor.get(student.getMajor());
        if (curriculum == null) {
            return new ArrayList<>();
        }

        List<Course> available = new ArrayList<>();
        Set<String> completedIds = new HashSet<>();
        Set<String> enrolledIds = new HashSet<>();

        for (Course c : student.getCompletedCourses()) {
            completedIds.add(c.getCourseId());
        }
        for (Course c : student.getEnrolledCourses()) {
            enrolledIds.add(c.getCourseId());
        }

        for (Map.Entry<CourseCategory, List<Course>> entry : curriculum.getAllCourses().entrySet()) {
            for (Course course : entry.getValue()) {
                if (!completedIds.contains(course.getCourseId()) &&
                    !enrolledIds.contains(course.getCourseId()) &&
                    arePrerequisitesMet(course, completedIds)) {
                    available.add(course);
                }
            }
        }

        return available;
    }

    public Map<CourseCategory, Integer> getRemainingCreditsByCategory(Student student) {
        DegreeProgress progress = calculateProgress(student);
        if (progress == null) {
            return new HashMap<>();
        }

        Map<CourseCategory, Integer> remaining = new HashMap<>();
        Map<CourseCategory, Integer> completed = progress.getCompletedCreditsByCategory();
        Map<CourseCategory, Integer> required = progress.getRequiredCreditsByCategory();

        for (CourseCategory category : CourseCategory.values()) {
            int completedCredits = completed.getOrDefault(category, 0);
            int requiredCredits = required.getOrDefault(category, 0);
            remaining.put(category, Math.max(0, requiredCredits - completedCredits));
        }

        return remaining;
    }
}
