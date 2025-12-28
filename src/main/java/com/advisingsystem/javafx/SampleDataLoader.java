package com.advisingsystem.javafx;

import com.advisingsystem.model.*;
import com.advisingsystem.service.*;
import com.advisingsystem.data.CSECurriculumLoader;

/**
 * Loads sample data for the application using the CSE curriculum
 */
public class SampleDataLoader {
    
    public static void loadSampleData(StudentService studentService, CourseService courseService,
                                     AdvisorService advisorService, GradeService gradeService,
                                     EnrollmentService enrollmentService, 
                                     CurriculumService curriculumService) {
        
        // Load CSE Curriculum with all courses
        Curriculum cseCurriculum = CSECurriculumLoader.loadCSECurriculum(courseService);
        curriculumService.addCurriculum(cseCurriculum);
        
        // Add sample students with CS major
        Student stu001 = new Student("STU001", "John", "Doe", "john@university.edu", "Computer Science and Engineering", 2026);
        stu001.setCurrentSemester(3);
        studentService.addStudent(stu001);
        
        Student stu002 = new Student("STU002", "Jane", "Smith", "jane@university.edu", "Computer Science and Engineering", 2026);
        stu002.setCurrentSemester(3);
        studentService.addStudent(stu002);
        
        Student stu003 = new Student("STU003", "Bob", "Johnson", "bob@university.edu", "Computer Science and Engineering", 2027);
        stu003.setCurrentSemester(2);
        studentService.addStudent(stu003);
        
        Student stu004 = new Student("STU004", "Alice", "Williams", "alice@university.edu", "Computer Science and Engineering", 2025);
        stu004.setCurrentSemester(6);
        studentService.addStudent(stu004);
        
        Student stu005 = new Student("STU005", "Charlie", "Brown", "charlie@university.edu", "Computer Science and Engineering", 2027);
        stu005.setCurrentSemester(1);
        studentService.addStudent(stu005);
        
        // Add sample advisors
        Advisor advisor1 = new Advisor("ADV001", "Dr. Sarah", "Anderson", "anderson@university.edu", "Room 101");
        Advisor advisor2 = new Advisor("ADV002", "Prof. Michael", "Brown", "brown@university.edu", "Room 202");
        Advisor advisor3 = new Advisor("ADV003", "Dr. Emily", "Davis", "davis@university.edu", "Room 303");
        
        advisor1.addSpecialization("Computer Science and Engineering");
        advisor2.addSpecialization("Computer Science and Engineering");
        advisor3.addSpecialization("Computer Science and Engineering");
        
        advisorService.addAdvisor(advisor1);
        advisorService.addAdvisor(advisor2);
        advisorService.addAdvisor(advisor3);
        
        // Assign advisors
        advisorService.assignStudentToAdvisor("STU001", "ADV001");
        advisorService.assignStudentToAdvisor("STU002", "ADV001");
        advisorService.assignStudentToAdvisor("STU003", "ADV002");
        advisorService.assignStudentToAdvisor("STU004", "ADV001");
        advisorService.assignStudentToAdvisor("STU005", "ADV003");
        
        // Simulate completed courses for STU001 (3rd semester student)
        gradeService.recordGrade(new Grade("STU001", "ENG101", "A", 4.0, "Semester 1"));
        gradeService.recordGrade(new Grade("STU001", "MATH151", "A-", 3.7, "Semester 1"));
        gradeService.recordGrade(new Grade("STU001", "CS111", "A", 4.0, "Semester 1"));
        gradeService.recordGrade(new Grade("STU001", "CHEM101", "B+", 3.3, "Semester 1"));
        gradeService.recordGrade(new Grade("STU001", "PSY101", "A", 4.0, "Semester 1"));
        
        gradeService.recordGrade(new Grade("STU001", "ENG102", "A", 4.0, "Semester 2"));
        gradeService.recordGrade(new Grade("STU001", "MATH152", "A-", 3.7, "Semester 2"));
        gradeService.recordGrade(new Grade("STU001", "CS112", "A", 4.0, "Semester 2"));
        gradeService.recordGrade(new Grade("STU001", "PHYS201", "B+", 3.3, "Semester 2"));
        gradeService.recordGrade(new Grade("STU001", "MATH271", "A-", 3.7, "Semester 2"));
        
        // Update GPA for STU001
        studentService.updateStudentGPA("STU001", 3.8);
        
        // Simulate some completed courses for STU002
        gradeService.recordGrade(new Grade("STU002", "ENG101", "B+", 3.3, "Semester 1"));
        gradeService.recordGrade(new Grade("STU002", "MATH151", "B", 3.0, "Semester 1"));
        gradeService.recordGrade(new Grade("STU002", "CS111", "A-", 3.7, "Semester 1"));
        gradeService.recordGrade(new Grade("STU002", "CHEM101", "B", 3.0, "Semester 1"));
        
        gradeService.recordGrade(new Grade("STU002", "ENG102", "A-", 3.7, "Semester 2"));
        gradeService.recordGrade(new Grade("STU002", "MATH152", "B+", 3.3, "Semester 2"));
        gradeService.recordGrade(new Grade("STU002", "CS112", "A", 4.0, "Semester 2"));
        
        studentService.updateStudentGPA("STU002", 3.4);
        
        // STU003 - 2nd semester student
        gradeService.recordGrade(new Grade("STU003", "ENG101", "A", 4.0, "Semester 1"));
        gradeService.recordGrade(new Grade("STU003", "MATH151", "A", 4.0, "Semester 1"));
        gradeService.recordGrade(new Grade("STU003", "CS111", "A", 4.0, "Semester 1"));
        gradeService.recordGrade(new Grade("STU003", "CHEM101", "A-", 3.7, "Semester 1"));
        
        studentService.updateStudentGPA("STU003", 3.9);
        
        // STU004 - Senior student with many completed courses
        gradeService.recordGrade(new Grade("STU004", "CS211", "A", 4.0, "Semester 3"));
        gradeService.recordGrade(new Grade("STU004", "CS221", "A-", 3.7, "Semester 3"));
        gradeService.recordGrade(new Grade("STU004", "MATH251", "B+", 3.3, "Semester 3"));
        gradeService.recordGrade(new Grade("STU004", "CS311", "A", 4.0, "Semester 4"));
        gradeService.recordGrade(new Grade("STU004", "CS321", "A", 4.0, "Semester 4"));
        gradeService.recordGrade(new Grade("STU004", "CS331", "A-", 3.7, "Semester 5"));
        gradeService.recordGrade(new Grade("STU004", "CS351", "A", 4.0, "Semester 5"));
        
        studentService.updateStudentGPA("STU004", 3.85);
        
        // STU005 - Freshman, just starting
        studentService.updateStudentGPA("STU005", 0.0);
        
        // Enroll students in current semester courses
        // STU001 enrolling in 3rd semester recommended courses
        enrollmentService.enrollStudentInCourse("STU001", "MATH251");
        enrollmentService.enrollStudentInCourse("STU001", "CS211");
        enrollmentService.enrollStudentInCourse("STU001", "CS221");
        enrollmentService.enrollStudentInCourse("STU001", "MATH272");
        enrollmentService.enrollStudentInCourse("STU001", "COMM101");
        
        // STU002 enrolling in 3rd semester courses
        enrollmentService.enrollStudentInCourse("STU002", "CS211");
        enrollmentService.enrollStudentInCourse("STU002", "MATH271");
        enrollmentService.enrollStudentInCourse("STU002", "SOC101");
        
        // STU003 enrolling in 2nd semester courses
        enrollmentService.enrollStudentInCourse("STU003", "ENG102");
        enrollmentService.enrollStudentInCourse("STU003", "MATH152");
        enrollmentService.enrollStudentInCourse("STU003", "CS112");
        enrollmentService.enrollStudentInCourse("STU003", "PHYS201");
        
        // STU005 enrolling in 1st semester courses
        enrollmentService.enrollStudentInCourse("STU005", "ENG101");
        enrollmentService.enrollStudentInCourse("STU005", "MATH151");
        enrollmentService.enrollStudentInCourse("STU005", "CS111");
        enrollmentService.enrollStudentInCourse("STU005", "CHEM101");
    }
}

