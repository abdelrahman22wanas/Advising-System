package com.advisingsystem.service;

import com.advisingsystem.model.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service class for managing enrollments and prerequisites
 */
public class EnrollmentService {
    private StudentService studentService;
    private CourseService courseService;
    private GradeService gradeService;

    public EnrollmentService(StudentService studentService, CourseService courseService, GradeService gradeService) {
        this.studentService = studentService;
        this.courseService = courseService;
        this.gradeService = gradeService;
    }

    public boolean enrollStudentInCourse(String studentId, String courseId) {
        Student student = studentService.getStudent(studentId);
        Course course = courseService.getCourse(courseId);

        if (student == null || course == null) {
            return false;
        }

        // Check if student is already enrolled
        if (student.getEnrolledCourses().stream().anyMatch(c -> c.getCourseId().equals(courseId))) {
            return false;
        }

        // Check prerequisites
        if (!hasPrerequisites(studentId, course)) {
            return false;
        }

        // Check seat availability
        if (!course.hasAvailableSeats()) {
            return false;
        }

        student.enrollCourse(course);
        course.incrementEnrolled();
        return true;
    }

    public boolean dropCourse(String studentId, String courseId) {
        Student student = studentService.getStudent(studentId);
        Course course = courseService.getCourse(courseId);

        if (student == null || course == null) {
            return false;
        }

        boolean removed = student.getEnrolledCourses().removeIf(c -> c.getCourseId().equals(courseId));
        if (removed) {
            course.decrementEnrolled();
            return true;
        }
        return false;
    }

    public boolean completeCourse(String studentId, String courseId) {
        Student student = studentService.getStudent(studentId);
        Course course = courseService.getCourse(courseId);

        if (student == null || course == null) {
            return false;
        }

        // Remove from enrolled
        if (dropCourse(studentId, courseId)) {
            // Add to completed
            student.addCompletedCourse(course);
            return true;
        }
        return false;
    }

    public List<Course> getAvailableCoursesForStudent(String studentId) {
        Student student = studentService.getStudent(studentId);
        if (student == null) {
            return new ArrayList<>();
        }

        return courseService.getCoursesWithAvailableSeats().stream()
                .filter(course -> hasPrerequisites(studentId, course))
                .filter(course -> student.getEnrolledCourses().stream()
                        .noneMatch(c -> c.getCourseId().equals(course.getCourseId())))
                .collect(Collectors.toList());
    }

    private boolean hasPrerequisites(String studentId, Course course) {
        if (course.getPrerequisites().isEmpty()) {
            return true;
        }

        Student student = studentService.getStudent(studentId);
        if (student == null) {
            return false;
        }

        Set<String> completedCourseIds = student.getCompletedCourses().stream()
                .map(Course::getCourseId)
                .collect(Collectors.toSet());

        return course.getPrerequisites().stream()
                .allMatch(completedCourseIds::contains);
    }

    public List<Course> getStudentEnrolledCourses(String studentId) {
        Student student = studentService.getStudent(studentId);
        return student != null ? student.getEnrolledCourses() : new ArrayList<>();
    }

    public List<Course> getStudentCompletedCourses(String studentId) {
        Student student = studentService.getStudent(studentId);
        return student != null ? student.getCompletedCourses() : new ArrayList<>();
    }

    public int getStudentTotalCreditsCompleted(String studentId) {
        return getStudentCompletedCourses(studentId).stream()
                .mapToInt(Course::getCredits)
                .sum();
    }
}
