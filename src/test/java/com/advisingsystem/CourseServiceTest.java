package com.advisingsystem;

import com.advisingsystem.model.*;
import com.advisingsystem.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Course service
 */
public class CourseServiceTest {
    private CourseService courseService;
    private Course testCourse;

    @BeforeEach
    public void setUp() {
        courseService = new CourseService();
        testCourse = new Course("CS101", "Intro to Java", "Computer Science", 3, 30, "Fall 2024");
    }

    @Test
    public void testAddCourse() {
        courseService.addCourse(testCourse);
        assertNotNull(courseService.getCourse("CS101"));
        assertEquals("Intro to Java", courseService.getCourse("CS101").getCourseName());
    }

    @Test
    public void testRemoveCourse() {
        courseService.addCourse(testCourse);
        assertTrue(courseService.removeCourse("CS101"));
        assertNull(courseService.getCourse("CS101"));
    }

    @Test
    public void testEnrollStudent() {
        courseService.addCourse(testCourse);
        assertTrue(courseService.enrollStudent("CS101"));
        assertEquals(1, testCourse.getEnrolledCount());
    }

    @Test
    public void testDropStudent() {
        courseService.addCourse(testCourse);
        courseService.enrollStudent("CS101");
        assertTrue(courseService.dropStudent("CS101"));
        assertEquals(0, testCourse.getEnrolledCount());
    }

    @Test
    public void testGetCoursesWithAvailableSeats() {
        courseService.addCourse(testCourse);
        assertEquals(1, courseService.getCoursesWithAvailableSeats().size());
    }
}
