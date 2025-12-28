package com.advisingsystem.javafx;

import com.advisingsystem.model.*;
import com.advisingsystem.service.*;

/**
 * Loads sample data for the application
 */
public class SampleDataLoader {
    
    public static void loadSampleData(StudentService studentService, CourseService courseService,
                                     AdvisorService advisorService, GradeService gradeService,
                                     EnrollmentService enrollmentService) {
        // Add sample students
        studentService.addStudent(new Student("STU001", "John", "Doe", "john@university.edu", "Computer Science", 2025));
        studentService.addStudent(new Student("STU002", "Jane", "Smith", "jane@university.edu", "Computer Science", 2026));
        studentService.addStudent(new Student("STU003", "Bob", "Johnson", "bob@university.edu", "Mathematics", 2024));
        studentService.addStudent(new Student("STU004", "Alice", "Williams", "alice@university.edu", "Computer Science", 2025));
        studentService.addStudent(new Student("STU005", "Charlie", "Brown", "charlie@university.edu", "Engineering", 2026));
        
        // Add sample courses
        Course cs101 = new Course("CS101", "Introduction to Java", "Computer Science", 3, 30, "Fall 2024");
        Course cs201 = new Course("CS201", "Data Structures", "Computer Science", 3, 25, "Fall 2024");
        Course math101 = new Course("MATH101", "Calculus I", "Mathematics", 4, 35, "Fall 2024");
        Course cs301 = new Course("CS301", "Algorithms", "Computer Science", 3, 20, "Spring 2025");
        Course eng101 = new Course("ENG101", "Engineering Fundamentals", "Engineering", 3, 30, "Fall 2024");
        
        cs201.addPrerequisite("CS101");
        cs301.addPrerequisite("CS201");
        
        courseService.addCourse(cs101);
        courseService.addCourse(cs201);
        courseService.addCourse(math101);
        courseService.addCourse(cs301);
        courseService.addCourse(eng101);
        
        // Add sample advisors
        Advisor advisor1 = new Advisor("ADV001", "Dr. Sarah", "Anderson", "anderson@university.edu", "Room 101");
        Advisor advisor2 = new Advisor("ADV002", "Prof. Michael", "Brown", "brown@university.edu", "Room 202");
        Advisor advisor3 = new Advisor("ADV003", "Dr. Emily", "Davis", "davis@university.edu", "Room 303");
        
        advisor1.addSpecialization("Computer Science");
        advisor2.addSpecialization("Mathematics");
        advisor3.addSpecialization("Engineering");
        
        advisorService.addAdvisor(advisor1);
        advisorService.addAdvisor(advisor2);
        advisorService.addAdvisor(advisor3);
        
        // Update student GPAs
        studentService.updateStudentGPA("STU001", 3.8);
        studentService.updateStudentGPA("STU002", 3.5);
        studentService.updateStudentGPA("STU003", 3.2);
        studentService.updateStudentGPA("STU004", 3.9);
        studentService.updateStudentGPA("STU005", 3.6);
        
        // Assign advisors
        advisorService.assignStudentToAdvisor("STU001", "ADV001");
        advisorService.assignStudentToAdvisor("STU002", "ADV001");
        advisorService.assignStudentToAdvisor("STU003", "ADV002");
        advisorService.assignStudentToAdvisor("STU004", "ADV001");
        advisorService.assignStudentToAdvisor("STU005", "ADV003");
        
        // Enroll students
        enrollmentService.enrollStudentInCourse("STU001", "CS201");
        enrollmentService.enrollStudentInCourse("STU002", "CS101");
        enrollmentService.enrollStudentInCourse("STU003", "MATH101");
        
        // Add some grades
        gradeService.recordGrade(new Grade("STU001", "CS101", "A", 4.0, "Fall 2023"));
        gradeService.recordGrade(new Grade("STU002", "MATH101", "B+", 3.3, "Fall 2023"));
        gradeService.recordGrade(new Grade("STU003", "CS101", "B", 3.0, "Fall 2023"));
    }
}
