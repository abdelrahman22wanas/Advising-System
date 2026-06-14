package com.advisingsystem.service;

import com.advisingsystem.model.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service class for managing courses
 */
public class CourseService {
    private Map<String, Course> courses;

    public CourseService() {
        this.courses = new HashMap<>();
    }

    public void addCourse(Course course) {
        if (!courses.containsKey(course.getCourseId())) {
            courses.put(course.getCourseId(), course);
        }
    }

    public Course getCourse(String courseId) {
        return courses.get(courseId);
    }

    public List<Course> getAllCourses() {
        return new ArrayList<>(courses.values());
    }

    public boolean removeCourse(String courseId) {
        return courses.remove(courseId) != null;
    }

    public List<Course> getCoursesByDepartment(String department) {
        return courses.values().stream()
                .filter(c -> c.getDepartment().equalsIgnoreCase(department))
                .collect(Collectors.toList());
    }

    public List<Course> getCoursesBySemester(String semester) {
        return courses.values().stream()
                .filter(c -> c.getSemester().equalsIgnoreCase(semester))
                .collect(Collectors.toList());
    }

    public List<Course> getCoursesWithAvailableSeats() {
        return courses.values().stream()
                .filter(Course::hasAvailableSeats)
                .collect(Collectors.toList());
    }

    public List<Course> getCoursesWithoutAvailableSeats() {
        return courses.values().stream()
                .filter(c -> !c.hasAvailableSeats())
                .collect(Collectors.toList());
    }

    public boolean enrollStudent(String courseId) {
        Course course = getCourse(courseId);
        if (course != null && course.hasAvailableSeats()) {
            course.incrementEnrolled();
            return true;
        }
        return false;
    }

    public boolean dropStudent(String courseId) {
        Course course = getCourse(courseId);
        if (course != null) {
            course.decrementEnrolled();
            return true;
        }
        return false;
    }

    public boolean courseExists(String courseId) {
        return courses.containsKey(courseId);
    }

    public int getTotalCourses() {
        return courses.size();
    }

    public List<Course> getCoursesByCredits(int credits) {
        return courses.values().stream()
                .filter(c -> c.getCredits() == credits)
                .collect(Collectors.toList());
    }
}
