package com.advisingsystem.ui;

import com.advisingsystem.model.*;
import com.advisingsystem.service.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Console-based UI for the advising system
 */
public class AdvisingSystemUI {
    private StudentService studentService;
    private CourseService courseService;
    private AdvisorService advisorService;
    private AdvicingSessionService sessionService;
    private GradeService gradeService;
    private EnrollmentService enrollmentService;
    private Scanner scanner;

    public AdvisingSystemUI() {
        this.studentService = new StudentService();
        this.courseService = new CourseService();
        this.advisorService = new AdvisorService();
        this.sessionService = new AdvicingSessionService();
        this.gradeService = new GradeService(courseService, studentService);
        this.enrollmentService = new EnrollmentService(studentService, courseService, gradeService);
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        loadSampleData();
        boolean running = true;

        while (running) {
            displayMainMenu();
            int choice = getIntInput();

            switch (choice) {
                case 1 -> studentMenu();
                case 2 -> courseMenu();
                case 3 -> advisorMenu();
                case 4 -> enrollmentMenu();
                case 5 -> sessionMenu();
                case 6 -> gradeMenu();
                case 0 -> {
                    running = false;
                    System.out.println("\nThank you for using the Advising System. Goodbye!");
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
        scanner.close();
    }

    private void displayMainMenu() {
        System.out.println("\n========== ADVISING SYSTEM MAIN MENU ==========");
        System.out.println("1. Manage Students");
        System.out.println("2. Manage Courses");
        System.out.println("3. Manage Advisors");
        System.out.println("4. Manage Enrollments");
        System.out.println("5. Manage Advising Sessions");
        System.out.println("6. Manage Grades");
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");
    }

    private void studentMenu() {
        boolean inMenu = true;
        while (inMenu) {
            System.out.println("\n========== STUDENT MENU ==========");
            System.out.println("1. Add Student");
            System.out.println("2. View All Students");
            System.out.println("3. View Student Details");
            System.out.println("4. Update Student GPA");
            System.out.println("5. Search Students by Major");
            System.out.println("6. Search Students by GPA Range");
            System.out.println("0. Back to Main Menu");
            System.out.print("Enter your choice: ");
            int choice = getIntInput();

            switch (choice) {
                case 1 -> addStudent();
                case 2 -> viewAllStudents();
                case 3 -> viewStudentDetails();
                case 4 -> updateStudentGPA();
                case 5 -> searchByMajor();
                case 6 -> searchByGPARange();
                case 0 -> inMenu = false;
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private void addStudent() {
        System.out.print("Enter Student ID: ");
        String studentId = scanner.nextLine().trim();
        if (studentService.studentExists(studentId)) {
            System.out.println("Student already exists!");
            return;
        }

        System.out.print("Enter First Name: ");
        String firstName = scanner.nextLine().trim();
        System.out.print("Enter Last Name: ");
        String lastName = scanner.nextLine().trim();
        System.out.print("Enter Email: ");
        String email = scanner.nextLine().trim();
        System.out.print("Enter Major: ");
        String major = scanner.nextLine().trim();
        System.out.print("Enter Graduation Year: ");
        int year = getIntInput();

        Student student = new Student(studentId, firstName, lastName, email, major, year);
        studentService.addStudent(student);
        System.out.println("Student added successfully!");
    }

    private void viewAllStudents() {
        List<Student> students = studentService.getAllStudents();
        if (students.isEmpty()) {
            System.out.println("No students found.");
        } else {
            System.out.println("\n========== ALL STUDENTS ==========");
            students.forEach(s -> System.out.println(s));
        }
    }

    private void viewStudentDetails() {
        System.out.print("Enter Student ID: ");
        String studentId = scanner.nextLine().trim();
        Student student = studentService.getStudent(studentId);

        if (student == null) {
            System.out.println("Student not found.");
        } else {
            System.out.println("\n========== STUDENT DETAILS ==========");
            System.out.println("ID: " + student.getStudentId());
            System.out.println("Name: " + student.getFullName());
            System.out.println("Email: " + student.getEmail());
            System.out.println("Major: " + student.getMajor());
            System.out.println("GPA: " + String.format("%.2f", student.getGpa()));
            System.out.println("Graduation Year: " + student.getGraduationYear());
            System.out.println("Enrolled Courses: " + student.getEnrolledCourses().size());
            System.out.println("Completed Courses: " + student.getCompletedCourses().size());
        }
    }

    private void updateStudentGPA() {
        System.out.print("Enter Student ID: ");
        String studentId = scanner.nextLine().trim();
        if (!studentService.studentExists(studentId)) {
            System.out.println("Student not found.");
            return;
        }

        System.out.print("Enter New GPA (0.0-4.0): ");
        double gpa = getDoubleInput();
        studentService.updateStudentGPA(studentId, gpa);
        System.out.println("GPA updated successfully!");
    }

    private void searchByMajor() {
        System.out.print("Enter Major: ");
        String major = scanner.nextLine().trim();
        List<Student> students = studentService.getStudentsByMajor(major);

        if (students.isEmpty()) {
            System.out.println("No students found with major: " + major);
        } else {
            System.out.println("\n========== STUDENTS BY MAJOR: " + major + " ==========");
            students.forEach(s -> System.out.println(s));
        }
    }

    private void searchByGPARange() {
        System.out.print("Enter Minimum GPA: ");
        double minGpa = getDoubleInput();
        System.out.print("Enter Maximum GPA: ");
        double maxGpa = getDoubleInput();

        List<Student> students = studentService.getStudentsByGPARange(minGpa, maxGpa);
        if (students.isEmpty()) {
            System.out.println("No students found in GPA range: " + minGpa + " - " + maxGpa);
        } else {
            System.out.println("\n========== STUDENTS BY GPA RANGE: " + minGpa + " - " + maxGpa + " ==========");
            students.forEach(s -> System.out.println(s));
        }
    }

    private void courseMenu() {
        boolean inMenu = true;
        while (inMenu) {
            System.out.println("\n========== COURSE MENU ==========");
            System.out.println("1. Add Course");
            System.out.println("2. View All Courses");
            System.out.println("3. View Course Details");
            System.out.println("4. Search Courses by Department");
            System.out.println("5. Search Courses by Semester");
            System.out.println("6. View Courses with Available Seats");
            System.out.println("0. Back to Main Menu");
            System.out.print("Enter your choice: ");
            int choice = getIntInput();

            switch (choice) {
                case 1 -> addCourse();
                case 2 -> viewAllCourses();
                case 3 -> viewCourseDetails();
                case 4 -> searchByDepartment();
                case 5 -> searchBySemester();
                case 6 -> viewAvailableCourses();
                case 0 -> inMenu = false;
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private void addCourse() {
        System.out.print("Enter Course ID: ");
        String courseId = scanner.nextLine().trim();
        if (courseService.courseExists(courseId)) {
            System.out.println("Course already exists!");
            return;
        }

        System.out.print("Enter Course Name: ");
        String courseName = scanner.nextLine().trim();
        System.out.print("Enter Department: ");
        String department = scanner.nextLine().trim();
        System.out.print("Enter Credits: ");
        int credits = getIntInput();
        System.out.print("Enter Capacity: ");
        int capacity = getIntInput();
        System.out.print("Enter Semester: ");
        String semester = scanner.nextLine().trim();

        Course course = new Course(courseId, courseName, department, credits, capacity, semester);
        courseService.addCourse(course);
        System.out.println("Course added successfully!");
    }

    private void viewAllCourses() {
        List<Course> courses = courseService.getAllCourses();
        if (courses.isEmpty()) {
            System.out.println("No courses found.");
        } else {
            System.out.println("\n========== ALL COURSES ==========");
            courses.forEach(c -> System.out.println(c));
        }
    }

    private void viewCourseDetails() {
        System.out.print("Enter Course ID: ");
        String courseId = scanner.nextLine().trim();
        Course course = courseService.getCourse(courseId);

        if (course == null) {
            System.out.println("Course not found.");
        } else {
            System.out.println("\n========== COURSE DETAILS ==========");
            System.out.println("ID: " + course.getCourseId());
            System.out.println("Name: " + course.getCourseName());
            System.out.println("Department: " + course.getDepartment());
            System.out.println("Credits: " + course.getCredits());
            System.out.println("Semester: " + course.getSemester());
            System.out.println("Enrolled: " + course.getEnrolledCount() + "/" + course.getCapacity());
            System.out.println("Available Seats: " + course.getAvailableSeats());
        }
    }

    private void searchByDepartment() {
        System.out.print("Enter Department: ");
        String department = scanner.nextLine().trim();
        List<Course> courses = courseService.getCoursesByDepartment(department);

        if (courses.isEmpty()) {
            System.out.println("No courses found in department: " + department);
        } else {
            System.out.println("\n========== COURSES IN " + department + " ==========");
            courses.forEach(c -> System.out.println(c));
        }
    }

    private void searchBySemester() {
        System.out.print("Enter Semester (e.g., Fall 2024): ");
        String semester = scanner.nextLine().trim();
        List<Course> courses = courseService.getCoursesBySemester(semester);

        if (courses.isEmpty()) {
            System.out.println("No courses found in semester: " + semester);
        } else {
            System.out.println("\n========== COURSES IN " + semester + " ==========");
            courses.forEach(c -> System.out.println(c));
        }
    }

    private void viewAvailableCourses() {
        List<Course> courses = courseService.getCoursesWithAvailableSeats();
        if (courses.isEmpty()) {
            System.out.println("No courses with available seats.");
        } else {
            System.out.println("\n========== COURSES WITH AVAILABLE SEATS ==========");
            courses.forEach(c -> System.out.println(c));
        }
    }

    private void advisorMenu() {
        boolean inMenu = true;
        while (inMenu) {
            System.out.println("\n========== ADVISOR MENU ==========");
            System.out.println("1. Add Advisor");
            System.out.println("2. View All Advisors");
            System.out.println("3. View Advisor Details");
            System.out.println("4. Assign Student to Advisor");
            System.out.println("5. Remove Student from Advisor");
            System.out.println("0. Back to Main Menu");
            System.out.print("Enter your choice: ");
            int choice = getIntInput();

            switch (choice) {
                case 1 -> addAdvisor();
                case 2 -> viewAllAdvisors();
                case 3 -> viewAdvisorDetails();
                case 4 -> assignStudentToAdvisor();
                case 5 -> removeStudentFromAdvisor();
                case 0 -> inMenu = false;
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private void addAdvisor() {
        System.out.print("Enter Advisor ID: ");
        String advisorId = scanner.nextLine().trim();
        if (advisorService.advisorExists(advisorId)) {
            System.out.println("Advisor already exists!");
            return;
        }

        System.out.print("Enter First Name: ");
        String firstName = scanner.nextLine().trim();
        System.out.print("Enter Last Name: ");
        String lastName = scanner.nextLine().trim();
        System.out.print("Enter Email: ");
        String email = scanner.nextLine().trim();
        System.out.print("Enter Office: ");
        String office = scanner.nextLine().trim();

        Advisor advisor = new Advisor(advisorId, firstName, lastName, email, office);
        advisorService.addAdvisor(advisor);
        System.out.println("Advisor added successfully!");
    }

    private void viewAllAdvisors() {
        List<Advisor> advisors = advisorService.getAllAdvisors();
        if (advisors.isEmpty()) {
            System.out.println("No advisors found.");
        } else {
            System.out.println("\n========== ALL ADVISORS ==========");
            advisors.forEach(a -> System.out.println(a));
        }
    }

    private void viewAdvisorDetails() {
        System.out.print("Enter Advisor ID: ");
        String advisorId = scanner.nextLine().trim();
        Advisor advisor = advisorService.getAdvisor(advisorId);

        if (advisor == null) {
            System.out.println("Advisor not found.");
        } else {
            System.out.println("\n========== ADVISOR DETAILS ==========");
            System.out.println("ID: " + advisor.getAdvisorId());
            System.out.println("Name: " + advisor.getFullName());
            System.out.println("Email: " + advisor.getEmail());
            System.out.println("Office: " + advisor.getOffice());
            System.out.println("Students Assigned: " + advisor.getAssignedStudentIds().size() + "/" + advisor.getMaxStudents());
        }
    }

    private void assignStudentToAdvisor() {
        System.out.print("Enter Student ID: ");
        String studentId = scanner.nextLine().trim();
        System.out.print("Enter Advisor ID: ");
        String advisorId = scanner.nextLine().trim();

        if (advisorService.assignStudentToAdvisor(studentId, advisorId)) {
            System.out.println("Student assigned to advisor successfully!");
        } else {
            System.out.println("Failed to assign student. Check if advisor has available capacity.");
        }
    }

    private void removeStudentFromAdvisor() {
        System.out.print("Enter Student ID: ");
        String studentId = scanner.nextLine().trim();
        System.out.print("Enter Advisor ID: ");
        String advisorId = scanner.nextLine().trim();

        if (advisorService.removeStudentFromAdvisor(studentId, advisorId)) {
            System.out.println("Student removed from advisor successfully!");
        } else {
            System.out.println("Failed to remove student.");
        }
    }

    private void enrollmentMenu() {
        boolean inMenu = true;
        while (inMenu) {
            System.out.println("\n========== ENROLLMENT MENU ==========");
            System.out.println("1. Enroll Student in Course");
            System.out.println("2. Drop Course");
            System.out.println("3. View Student's Enrolled Courses");
            System.out.println("4. View Student's Completed Courses");
            System.out.println("5. View Available Courses for Student");
            System.out.println("0. Back to Main Menu");
            System.out.print("Enter your choice: ");
            int choice = getIntInput();

            switch (choice) {
                case 1 -> enrollStudentInCourse();
                case 2 -> dropCourse();
                case 3 -> viewEnrolledCourses();
                case 4 -> viewCompletedCourses();
                case 5 -> viewAvailableCoursesForStudent();
                case 0 -> inMenu = false;
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private void enrollStudentInCourse() {
        System.out.print("Enter Student ID: ");
        String studentId = scanner.nextLine().trim();
        System.out.print("Enter Course ID: ");
        String courseId = scanner.nextLine().trim();

        if (enrollmentService.enrollStudentInCourse(studentId, courseId)) {
            System.out.println("Student enrolled successfully!");
        } else {
            System.out.println("Enrollment failed. Check prerequisites and course capacity.");
        }
    }

    private void dropCourse() {
        System.out.print("Enter Student ID: ");
        String studentId = scanner.nextLine().trim();
        System.out.print("Enter Course ID: ");
        String courseId = scanner.nextLine().trim();

        if (enrollmentService.dropCourse(studentId, courseId)) {
            System.out.println("Course dropped successfully!");
        } else {
            System.out.println("Failed to drop course.");
        }
    }

    private void viewEnrolledCourses() {
        System.out.print("Enter Student ID: ");
        String studentId = scanner.nextLine().trim();
        List<Course> courses = enrollmentService.getStudentEnrolledCourses(studentId);

        if (courses.isEmpty()) {
            System.out.println("Student is not enrolled in any courses.");
        } else {
            System.out.println("\n========== ENROLLED COURSES ==========");
            courses.forEach(c -> System.out.println(c));
        }
    }

    private void viewCompletedCourses() {
        System.out.print("Enter Student ID: ");
        String studentId = scanner.nextLine().trim();
        List<Course> courses = enrollmentService.getStudentCompletedCourses(studentId);

        if (courses.isEmpty()) {
            System.out.println("Student has not completed any courses.");
        } else {
            System.out.println("\n========== COMPLETED COURSES ==========");
            courses.forEach(c -> System.out.println(c));
        }
    }

    private void viewAvailableCoursesForStudent() {
        System.out.print("Enter Student ID: ");
        String studentId = scanner.nextLine().trim();
        List<Course> courses = enrollmentService.getAvailableCoursesForStudent(studentId);

        if (courses.isEmpty()) {
            System.out.println("No available courses for this student.");
        } else {
            System.out.println("\n========== AVAILABLE COURSES ==========");
            courses.forEach(c -> System.out.println(c));
        }
    }

    private void sessionMenu() {
        boolean inMenu = true;
        while (inMenu) {
            System.out.println("\n========== ADVISING SESSION MENU ==========");
            System.out.println("1. Create Session");
            System.out.println("2. View All Sessions");
            System.out.println("3. View Student's Sessions");
            System.out.println("4. View Advisor's Sessions");
            System.out.println("5. View Upcoming Sessions");
            System.out.println("6. Complete Session");
            System.out.println("0. Back to Main Menu");
            System.out.print("Enter your choice: ");
            int choice = getIntInput();

            switch (choice) {
                case 1 -> createSession();
                case 2 -> viewAllSessions();
                case 3 -> viewStudentSessions();
                case 4 -> viewAdvisorSessions();
                case 5 -> viewUpcomingSessions();
                case 6 -> completeSession();
                case 0 -> inMenu = false;
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private void createSession() {
        System.out.print("Enter Student ID: ");
        String studentId = scanner.nextLine().trim();
        System.out.print("Enter Advisor ID: ");
        String advisorId = scanner.nextLine().trim();
        System.out.print("Enter Topic: ");
        String topic = scanner.nextLine().trim();

        LocalDateTime now = LocalDateTime.now();
        AdvicingSession session = sessionService.createSession(studentId, advisorId, now, topic);
        System.out.println("Session created successfully! Session ID: " + session.getSessionId());
    }

    private void viewAllSessions() {
        List<AdvicingSession> sessions = sessionService.getAllSessions();
        if (sessions.isEmpty()) {
            System.out.println("No sessions found.");
        } else {
            System.out.println("\n========== ALL SESSIONS ==========");
            sessions.forEach(s -> System.out.println(s));
        }
    }

    private void viewStudentSessions() {
        System.out.print("Enter Student ID: ");
        String studentId = scanner.nextLine().trim();
        List<AdvicingSession> sessions = sessionService.getSessionsByStudent(studentId);

        if (sessions.isEmpty()) {
            System.out.println("No sessions found for this student.");
        } else {
            System.out.println("\n========== STUDENT SESSIONS ==========");
            sessions.forEach(s -> System.out.println(s));
        }
    }

    private void viewAdvisorSessions() {
        System.out.print("Enter Advisor ID: ");
        String advisorId = scanner.nextLine().trim();
        List<AdvicingSession> sessions = sessionService.getSessionsByAdvisor(advisorId);

        if (sessions.isEmpty()) {
            System.out.println("No sessions found for this advisor.");
        } else {
            System.out.println("\n========== ADVISOR SESSIONS ==========");
            sessions.forEach(s -> System.out.println(s));
        }
    }

    private void viewUpcomingSessions() {
        List<AdvicingSession> sessions = sessionService.getUpcomingSessions();
        if (sessions.isEmpty()) {
            System.out.println("No upcoming sessions.");
        } else {
            System.out.println("\n========== UPCOMING SESSIONS ==========");
            sessions.forEach(s -> System.out.println(s));
        }
    }

    private void completeSession() {
        System.out.print("Enter Session ID: ");
        String sessionId = scanner.nextLine().trim();

        if (sessionService.completeSession(sessionId)) {
            System.out.println("Session marked as completed!");
        } else {
            System.out.println("Failed to complete session.");
        }
    }

    private void gradeMenu() {
        boolean inMenu = true;
        while (inMenu) {
            System.out.println("\n========== GRADE MENU ==========");
            System.out.println("1. Record Grade");
            System.out.println("2. View Student Grades");
            System.out.println("3. View Course Grades");
            System.out.println("4. View Student GPA");
            System.out.println("5. View Average Grade for Course");
            System.out.println("0. Back to Main Menu");
            System.out.print("Enter your choice: ");
            int choice = getIntInput();

            switch (choice) {
                case 1 -> recordGrade();
                case 2 -> viewStudentGrades();
                case 3 -> viewCourseGrades();
                case 4 -> viewStudentGPA();
                case 5 -> viewAverageGradeForCourse();
                case 0 -> inMenu = false;
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private void recordGrade() {
        System.out.print("Enter Student ID: ");
        String studentId = scanner.nextLine().trim();
        System.out.print("Enter Course ID: ");
        String courseId = scanner.nextLine().trim();
        System.out.print("Enter Letter Grade (A, A-, B+, B, B-, C+, C, C-, D+, D, F): ");
        String letterGrade = scanner.nextLine().trim().toUpperCase();
        System.out.print("Enter Semester: ");
        String semester = scanner.nextLine().trim();

        double gpa = Grade.getGPAForGrade(letterGrade);
        Grade grade = new Grade(studentId, courseId, letterGrade, gpa, semester);
        gradeService.recordGrade(grade);
        System.out.println("Grade recorded successfully!");
    }

    private void viewStudentGrades() {
        System.out.print("Enter Student ID: ");
        String studentId = scanner.nextLine().trim();
        List<Grade> grades = gradeService.getStudentGrades(studentId);

        if (grades.isEmpty()) {
            System.out.println("No grades found for this student.");
        } else {
            System.out.println("\n========== STUDENT GRADES ==========");
            grades.forEach(g -> System.out.println(g));
        }
    }

    private void viewCourseGrades() {
        System.out.print("Enter Course ID: ");
        String courseId = scanner.nextLine().trim();
        List<Grade> grades = gradeService.getCourseGrades(courseId);

        if (grades.isEmpty()) {
            System.out.println("No grades found for this course.");
        } else {
            System.out.println("\n========== COURSE GRADES ==========");
            grades.forEach(g -> System.out.println(g));
        }
    }

    private void viewStudentGPA() {
        System.out.print("Enter Student ID: ");
        String studentId = scanner.nextLine().trim();
        double gpa = gradeService.calculateStudentGPA(studentId);
        System.out.println("Student GPA: " + String.format("%.2f", gpa));
    }

    private void viewAverageGradeForCourse() {
        System.out.print("Enter Course ID: ");
        String courseId = scanner.nextLine().trim();
        double avgGpa = gradeService.getAverageGradeForCourse(courseId);
        System.out.println("Average GPA for Course: " + String.format("%.2f", avgGpa));
    }

    private void loadSampleData() {
        // Add sample students
        studentService.addStudent(new Student("STU001", "John", "Doe", "john@university.edu", "Computer Science", 2025));
        studentService.addStudent(new Student("STU002", "Jane", "Smith", "jane@university.edu", "Computer Science", 2026));
        studentService.addStudent(new Student("STU003", "Bob", "Johnson", "bob@university.edu", "Mathematics", 2024));

        // Add sample courses
        courseService.addCourse(new Course("CS101", "Introduction to Java", "Computer Science", 3, 30, "Fall 2024"));
        courseService.addCourse(new Course("CS201", "Data Structures", "Computer Science", 3, 25, "Fall 2024"));
        courseService.addCourse(new Course("MATH101", "Calculus I", "Mathematics", 4, 35, "Fall 2024"));

        // Add sample advisors
        advisorService.addAdvisor(new Advisor("ADV001", "Dr.", "Anderson", "anderson@university.edu", "Room 101"));
        advisorService.addAdvisor(new Advisor("ADV002", "Prof.", "Brown", "brown@university.edu", "Room 202"));

        // Update student GPAs
        studentService.updateStudentGPA("STU001", 3.8);
        studentService.updateStudentGPA("STU002", 3.5);
        studentService.updateStudentGPA("STU003", 3.2);

        System.out.println("Sample data loaded successfully!");
    }

    private int getIntInput() {
        try {
            String input = scanner.nextLine().trim();
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid number.");
            return -1;
        }
    }

    private double getDoubleInput() {
        try {
            String input = scanner.nextLine().trim();
            return Double.parseDouble(input);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid number.");
            return -1.0;
        }
    }
}
