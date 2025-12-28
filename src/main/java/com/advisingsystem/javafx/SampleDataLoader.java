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
                                     CurriculumService curriculumService,
                                     AdvicingSessionService sessionService) {
        
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
        
        Student stu006 = new Student("STU006", "Emily", "Davis", "emily.davis@university.edu", "Computer Science and Engineering", 2026);
        stu006.setCurrentSemester(4);
        studentService.addStudent(stu006);
        
        Student stu007 = new Student("STU007", "Michael", "Chen", "mchen@university.edu", "Computer Science and Engineering", 2025);
        stu007.setCurrentSemester(7);
        studentService.addStudent(stu007);
        
        Student stu008 = new Student("STU008", "Sarah", "Martinez", "smartinez@university.edu", "Computer Science and Engineering", 2027);
        stu008.setCurrentSemester(2);
        studentService.addStudent(stu008);
        
        Student stu009 = new Student("STU009", "David", "Lee", "dlee@university.edu", "Computer Science and Engineering", 2026);
        stu009.setCurrentSemester(3);
        studentService.addStudent(stu009);
        
        Student stu010 = new Student("STU010", "Jessica", "Taylor", "jtaylor@university.edu", "Computer Science and Engineering", 2025);
        stu010.setCurrentSemester(8);
        studentService.addStudent(stu010);
        
        Student stu011 = new Student("STU011", "James", "Wilson", "jwilson@university.edu", "Computer Science and Engineering", 2027);
        stu011.setCurrentSemester(1);
        studentService.addStudent(stu011);
        
        Student stu012 = new Student("STU012", "Amanda", "Moore", "amoore@university.edu", "Computer Science and Engineering", 2026);
        stu012.setCurrentSemester(5);
        studentService.addStudent(stu012);
        
        Student stu013 = new Student("STU013", "Kevin", "Garcia", "kgarcia@university.edu", "Computer Science and Engineering", 2026);
        stu013.setCurrentSemester(4);
        studentService.addStudent(stu013);
        
        Student stu014 = new Student("STU014", "Lisa", "Rodriguez", "lrodriguez@university.edu", "Computer Science and Engineering", 2027);
        stu014.setCurrentSemester(2);
        studentService.addStudent(stu014);
        
        Student stu015 = new Student("STU015", "Ryan", "White", "rwhite@university.edu", "Computer Science and Engineering", 2025);
        stu015.setCurrentSemester(6);
        studentService.addStudent(stu015);
        
        // Add sample advisors
        Advisor advisor1 = new Advisor("ADV001", "Dr. Sarah", "Anderson", "anderson@university.edu", "Room 101");
        Advisor advisor2 = new Advisor("ADV002", "Prof. Michael", "Brown", "brown@university.edu", "Room 202");
        Advisor advisor3 = new Advisor("ADV003", "Dr. Emily", "Davis", "davis@university.edu", "Room 303");
        Advisor advisor4 = new Advisor("ADV004", "Dr. Robert", "Miller", "rmiller@university.edu", "Room 104");
        Advisor advisor5 = new Advisor("ADV005", "Prof. Linda", "Martinez", "lmartinez@university.edu", "Room 205");
        Advisor advisor6 = new Advisor("ADV006", "Dr. James", "Thompson", "jthompson@university.edu", "Room 306");
        
        advisor1.addSpecialization("Computer Science and Engineering");
        advisor2.addSpecialization("Computer Science and Engineering");
        advisor3.addSpecialization("Computer Science and Engineering");
        advisor4.addSpecialization("Computer Science and Engineering");
        advisor5.addSpecialization("Computer Science and Engineering");
        advisor6.addSpecialization("Computer Science and Engineering");
        
        advisorService.addAdvisor(advisor1);
        advisorService.addAdvisor(advisor2);
        advisorService.addAdvisor(advisor3);
        advisorService.addAdvisor(advisor4);
        advisorService.addAdvisor(advisor5);
        advisorService.addAdvisor(advisor6);
        
        // Assign advisors
        advisorService.assignStudentToAdvisor("STU001", "ADV001");
        advisorService.assignStudentToAdvisor("STU002", "ADV001");
        advisorService.assignStudentToAdvisor("STU003", "ADV002");
        advisorService.assignStudentToAdvisor("STU004", "ADV001");
        advisorService.assignStudentToAdvisor("STU005", "ADV003");
        advisorService.assignStudentToAdvisor("STU006", "ADV002");
        advisorService.assignStudentToAdvisor("STU007", "ADV004");
        advisorService.assignStudentToAdvisor("STU008", "ADV003");
        advisorService.assignStudentToAdvisor("STU009", "ADV005");
        advisorService.assignStudentToAdvisor("STU010", "ADV004");
        advisorService.assignStudentToAdvisor("STU011", "ADV006");
        advisorService.assignStudentToAdvisor("STU012", "ADV005");
        advisorService.assignStudentToAdvisor("STU013", "ADV006");
        advisorService.assignStudentToAdvisor("STU014", "ADV002");
        advisorService.assignStudentToAdvisor("STU015", "ADV001");
        
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
        
        // STU006 - 4th semester student
        gradeService.recordGrade(new Grade("STU006", "CS211", "A-", 3.7, "Semester 3"));
        gradeService.recordGrade(new Grade("STU006", "CS221", "B+", 3.3, "Semester 3"));
        gradeService.recordGrade(new Grade("STU006", "MATH251", "A", 4.0, "Semester 3"));
        studentService.updateStudentGPA("STU006", 3.65);
        
        // STU007 - 7th semester senior
        gradeService.recordGrade(new Grade("STU007", "CS311", "A", 4.0, "Semester 4"));
        gradeService.recordGrade(new Grade("STU007", "CS351", "A-", 3.7, "Semester 5"));
        gradeService.recordGrade(new Grade("STU007", "CS361", "A", 4.0, "Semester 5"));
        gradeService.recordGrade(new Grade("STU007", "CS411", "A", 4.0, "Semester 6"));
        studentService.updateStudentGPA("STU007", 3.92);
        
        // STU008 - 2nd semester student
        gradeService.recordGrade(new Grade("STU008", "ENG101", "B+", 3.3, "Semester 1"));
        gradeService.recordGrade(new Grade("STU008", "MATH151", "B", 3.0, "Semester 1"));
        gradeService.recordGrade(new Grade("STU008", "CS111", "B+", 3.3, "Semester 1"));
        studentService.updateStudentGPA("STU008", 3.2);
        
        // STU009 - 3rd semester student
        gradeService.recordGrade(new Grade("STU009", "ENG101", "A", 4.0, "Semester 1"));
        gradeService.recordGrade(new Grade("STU009", "CS111", "A-", 3.7, "Semester 1"));
        gradeService.recordGrade(new Grade("STU009", "MATH151", "A", 4.0, "Semester 1"));
        gradeService.recordGrade(new Grade("STU009", "ENG102", "A-", 3.7, "Semester 2"));
        gradeService.recordGrade(new Grade("STU009", "CS112", "A", 4.0, "Semester 2"));
        studentService.updateStudentGPA("STU009", 3.88);
        
        // STU010 - Final semester senior
        gradeService.recordGrade(new Grade("STU010", "CS491", "A", 4.0, "Semester 7"));
        studentService.updateStudentGPA("STU010", 3.75);
        
        // STU012 - 5th semester student
        gradeService.recordGrade(new Grade("STU012", "CS211", "A", 4.0, "Semester 3"));
        gradeService.recordGrade(new Grade("STU012", "CS311", "A-", 3.7, "Semester 4"));
        studentService.updateStudentGPA("STU012", 3.7);
        
        // STU013 - 4th semester student
        gradeService.recordGrade(new Grade("STU013", "CS211", "B+", 3.3, "Semester 3"));
        studentService.updateStudentGPA("STU013", 3.45);
        
        // STU014 - 2nd semester student
        gradeService.recordGrade(new Grade("STU014", "CS111", "A", 4.0, "Semester 1"));
        gradeService.recordGrade(new Grade("STU014", "MATH151", "A-", 3.7, "Semester 1"));
        studentService.updateStudentGPA("STU014", 3.85);
        
        // STU015 - 6th semester student
        gradeService.recordGrade(new Grade("STU015", "CS311", "A", 4.0, "Semester 4"));
        gradeService.recordGrade(new Grade("STU015", "CS331", "A-", 3.7, "Semester 5"));
        gradeService.recordGrade(new Grade("STU015", "CS351", "A", 4.0, "Semester 5"));
        studentService.updateStudentGPA("STU015", 3.8);
        
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
        
        // STU006 enrolling in 4th semester courses
        enrollmentService.enrollStudentInCourse("STU006", "CS311");
        enrollmentService.enrollStudentInCourse("STU006", "MATH272");
        enrollmentService.enrollStudentInCourse("STU006", "HUM101");
        
        // STU007 enrolling in 7th semester courses
        enrollmentService.enrollStudentInCourse("STU007", "CS412");
        enrollmentService.enrollStudentInCourse("STU007", "CS451");
        enrollmentService.enrollStudentInCourse("STU007", "CS491");
        
        // STU008 enrolling in 2nd semester courses
        enrollmentService.enrollStudentInCourse("STU008", "ENG102");
        enrollmentService.enrollStudentInCourse("STU008", "MATH152");
        enrollmentService.enrollStudentInCourse("STU008", "CS112");
        
        // STU009 enrolling in 3rd semester courses
        enrollmentService.enrollStudentInCourse("STU009", "CS211");
        enrollmentService.enrollStudentInCourse("STU009", "MATH251");
        enrollmentService.enrollStudentInCourse("STU009", "MATH271");
        
        // STU010 enrolling in final semester
        enrollmentService.enrollStudentInCourse("STU010", "CS492");
        enrollmentService.enrollStudentInCourse("STU010", "CS431");
        
        // STU011 enrolling in 1st semester courses
        enrollmentService.enrollStudentInCourse("STU011", "ENG101");
        enrollmentService.enrollStudentInCourse("STU011", "MATH151");
        enrollmentService.enrollStudentInCourse("STU011", "CS111");
        
        // STU012 enrolling in 5th semester courses
        enrollmentService.enrollStudentInCourse("STU012", "CS351");
        enrollmentService.enrollStudentInCourse("STU012", "CS361");
        enrollmentService.enrollStudentInCourse("STU012", "MATH371");
        
        // STU013 enrolling in 4th semester courses
        enrollmentService.enrollStudentInCourse("STU013", "CS311");
        enrollmentService.enrollStudentInCourse("STU013", "CS321");
        
        // STU014 enrolling in 2nd semester courses
        enrollmentService.enrollStudentInCourse("STU014", "ENG102");
        enrollmentService.enrollStudentInCourse("STU014", "CS112");
        enrollmentService.enrollStudentInCourse("STU014", "MATH152");
        
        // STU015 enrolling in 6th semester courses
        enrollmentService.enrollStudentInCourse("STU015", "CS361");
        enrollmentService.enrollStudentInCourse("STU015", "CS401");
        enrollmentService.enrollStudentInCourse("STU015", "CS411");
        
        // Create advising sessions
        java.time.LocalDateTime now = java.time.LocalDateTime.now();
        
        AdvicingSession session1 = new AdvicingSession("SES001", "STU001", "ADV001", 
            now.minusDays(15), "Course Selection for Next Semester");
        session1.setNotes("Discussed prerequisite requirements for CS311. Student is on track.");
        session1.setStatus("Completed");
        sessionService.createSessionFromExisting(session1);
        
        AdvicingSession session2 = new AdvicingSession("SES002", "STU002", "ADV001",
            now.minusDays(10), "Academic Progress Review");
        session2.setNotes("GPA improvement plan discussed. Recommended tutoring for Math courses.");
        session2.setStatus("Completed");
        sessionService.createSessionFromExisting(session2);
        
        AdvicingSession session3 = new AdvicingSession("SES003", "STU003", "ADV002",
            now.minusDays(8), "Freshman Orientation");
        session3.setNotes("Excellent first semester performance. Encouraged to maintain study habits.");
        session3.setStatus("Completed");
        sessionService.createSessionFromExisting(session3);
        
        AdvicingSession session4 = new AdvicingSession("SES004", "STU004", "ADV001",
            now.minusDays(5), "Senior Project Planning");
        session4.setNotes("Reviewed project proposal. Approved to begin CS491 next semester.");
        session4.setStatus("Completed");
        sessionService.createSessionFromExisting(session4);
        
        AdvicingSession session5 = new AdvicingSession("SES005", "STU005", "ADV003",
            now.minusDays(3), "First Semester Check-in");
        session5.setNotes("Student adapting well to university. No concerns at this time.");
        session5.setStatus("Completed");
        sessionService.createSessionFromExisting(session5);
        
        AdvicingSession session6 = new AdvicingSession("SES006", "STU006", "ADV002",
            now.minusDays(2), "Course Registration Assistance");
        session6.setNotes("Helped with elective selection. Student interested in AI track.");
        session6.setStatus("Completed");
        sessionService.createSessionFromExisting(session6);
        
        AdvicingSession session7 = new AdvicingSession("SES007", "STU007", "ADV004",
            now.minusDays(1), "Graduation Requirements Review");
        session7.setNotes("On track to graduate. Need to complete CS492 and one more elective.");
        session7.setStatus("Completed");
        sessionService.createSessionFromExisting(session7);
        
        AdvicingSession session8 = new AdvicingSession("SES008", "STU008", "ADV003",
            now, "Academic Support");
        session8.setNotes("Discussed time management strategies for balancing coursework.");
        session8.setStatus("In Progress");
        sessionService.createSessionFromExisting(session8);
        
        AdvicingSession session9 = new AdvicingSession("SES009", "STU009", "ADV005",
            now.plusDays(2), "Career Planning Discussion");
        session9.setNotes("Scheduled future meeting to discuss internship opportunities.");
        session9.setStatus("Scheduled");
        sessionService.createSessionFromExisting(session9);
        
        AdvicingSession session10 = new AdvicingSession("SES010", "STU010", "ADV004",
            now.plusDays(3), "Final Semester Planning");
        session10.setNotes("Meeting scheduled to finalize graduation checklist.");
        session10.setStatus("Scheduled");
        sessionService.createSessionFromExisting(session10);
        
        AdvicingSession session11 = new AdvicingSession("SES011", "STU011", "ADV006",
            now.plusDays(5), "New Student Orientation");
        session11.setNotes("Initial advising meeting for new freshman student.");
        session11.setStatus("Scheduled");
        sessionService.createSessionFromExisting(session11);
        
        AdvicingSession session12 = new AdvicingSession("SES012", "STU012", "ADV005",
            now.plusDays(7), "Elective Course Planning");
        session12.setNotes("Will discuss major elective options and career paths.");
        session12.setStatus("Scheduled");
        sessionService.createSessionFromExisting(session12);
        
        AdvicingSession session13 = new AdvicingSession("SES013", "STU013", "ADV006",
            now.minusDays(6), "Mid-Semester Progress Check");
        session13.setNotes("Student performing adequately. Encouraged more class participation.");
        session13.setStatus("Completed");
        sessionService.createSessionFromExisting(session13);
        
        AdvicingSession session14 = new AdvicingSession("SES014", "STU014", "ADV002",
            now.plusDays(4), "Study Skills Workshop");
        session14.setNotes("Scheduled session to review effective study techniques.");
        session14.setStatus("Scheduled");
        sessionService.createSessionFromExisting(session14);
        
        AdvicingSession session15 = new AdvicingSession("SES015", "STU015", "ADV001",
            now.minusDays(12), "Specialization Track Selection");
        session15.setNotes("Student chose AI/Machine Learning track. Discussed course sequence.");
        session15.setStatus("Completed");
        sessionService.createSessionFromExisting(session15);
    }
}

