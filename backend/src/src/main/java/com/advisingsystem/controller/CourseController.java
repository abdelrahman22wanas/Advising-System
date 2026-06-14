package com.advisingsystem.controller;

import com.advisingsystem.model.Course;
import com.advisingsystem.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping
    public ResponseEntity<List<Course>> getAllCourses() {
        return ResponseEntity.ok(courseService.getAllCourses());
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<Course> getCourse(@PathVariable String courseId) {
        Course course = courseService.getCourse(courseId);
        if (course != null) {
            return ResponseEntity.ok(course);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Course> addCourse(@RequestBody Course course) {
        courseService.addCourse(course);
        return ResponseEntity.status(201).body(course);
    }

    @DeleteMapping("/{courseId}")
    public ResponseEntity<Void> removeCourse(@PathVariable String courseId) {
        if (courseService.removeCourse(courseId)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/department/{department}")
    public ResponseEntity<List<Course>> getCoursesByDepartment(@PathVariable String department) {
        return ResponseEntity.ok(courseService.getCoursesByDepartment(department));
    }

    @GetMapping("/semester/{semester}")
    public ResponseEntity<List<Course>> getCoursesBySemester(@PathVariable String semester) {
        return ResponseEntity.ok(courseService.getCoursesBySemester(semester));
    }

    @GetMapping("/available")
    public ResponseEntity<List<Course>> getCoursesWithAvailableSeats() {
        return ResponseEntity.ok(courseService.getCoursesWithAvailableSeats());
    }
}
