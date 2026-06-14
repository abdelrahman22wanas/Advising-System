package com.advisingsystem;

import com.advisingsystem.model.*;
import com.advisingsystem.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Student service
 */
public class StudentServiceTest {
    private StudentService studentService;
    private Student testStudent;

    @BeforeEach
    public void setUp() {
        studentService = new StudentService();
        testStudent = new Student("STU001", "John", "Doe", "john@test.com", "CS", 2025);
    }

    @Test
    public void testAddStudent() {
        studentService.addStudent(testStudent);
        assertNotNull(studentService.getStudent("STU001"));
        assertEquals("John", studentService.getStudent("STU001").getFirstName());
    }

    @Test
    public void testRemoveStudent() {
        studentService.addStudent(testStudent);
        assertTrue(studentService.removeStudent("STU001"));
        assertNull(studentService.getStudent("STU001"));
    }

    @Test
    public void testGetStudentsByMajor() {
        studentService.addStudent(testStudent);
        assertEquals(1, studentService.getStudentsByMajor("CS").size());
    }

    @Test
    public void testUpdateStudentGPA() {
        studentService.addStudent(testStudent);
        studentService.updateStudentGPA("STU001", 3.8);
        assertEquals(3.8, studentService.getStudent("STU001").getGpa());
    }

    @Test
    public void testGetStudentsByGPARange() {
        studentService.addStudent(testStudent);
        studentService.updateStudentGPA("STU001", 3.5);
        assertEquals(1, studentService.getStudentsByGPARange(3.0, 4.0).size());
    }
}
