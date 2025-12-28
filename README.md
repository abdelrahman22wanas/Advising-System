# Academic Advising System

A comprehensive Java-based system for managing academic advising, student records, course enrollments, and advising sessions.

## Project Overview

The Advising System is designed to streamline academic advising processes within educational institutions. It provides functionality for managing:

- **Students**: Track student information, majors, GPAs, and course enrollments
- **Courses**: Manage course offerings, prerequisites, and enrollment capacity
- **Advisors**: Assign advisors to students and track advisement relationships
- **Advising Sessions**: Schedule and track advising sessions between students and advisors
- **Grades**: Record and track student grades and GPA calculations

## Features

### Student Management
- Add and remove students
- View student details and academic records
- Search students by major or GPA range
- Track completed and enrolled courses

### Course Management
- Create and manage courses with prerequisites
- Track course capacity and enrollment
- View available courses by semester or department
- Manage course prerequisites

### Advisor Management
- Add and manage advisors
- Assign students to advisors
- Track advisor workload and specializations
- Monitor student-advisor relationships

### Enrollment Management
- Enroll students in courses with prerequisite validation
- Drop courses
- Track completed courses
- Calculate total completed credits

### Advising Sessions
- Schedule advising sessions
- Track session status (scheduled, completed, cancelled)
- Record session notes and recommendations
- View upcoming sessions

### Grade Management
- Record grades for completed courses
- Calculate student GPAs
- View course averages
- Track grades by semester

## Project Structure

```
advising-system/
├── src/
│   ├── main/
│   │   └── java/com/advisingsystem/
│   │       ├── model/              # Data models
│   │       ├── service/            # Business logic
│   │       ├── ui/                 # Console UI
│   │       ├── util/               # Utilities
│   │       └── AdvisingSystemApp.java
│   └── test/
│       └── java/com/advisingsystem/  # Unit tests
├── pom.xml                         # Maven configuration
└── README.md
```

## Technologies Used

- **Language**: Java 17
- **Build Tool**: Maven
- **Testing Framework**: JUnit 5
- **Architecture Pattern**: Service-based architecture

## Installation

### Prerequisites
- Java 17 or higher
- Maven 3.6 or higher

### Build Instructions

1. Navigate to the project directory:
```bash
cd AdvisingSystem
```

2. Build the project:
```bash
mvn clean install
```

3. Run the application:
```bash
mvn exec:java -Dexec.mainClass="com.advisingsystem.AdvisingSystemApp"
```

Or compile and run directly:
```bash
javac -d bin src/main/java/com/advisingsystem/**/*.java
java -cp bin com.advisingsystem.AdvisingSystemApp
```

## Usage

Once the application starts, you'll see a main menu with options to:

1. Manage Students
2. Manage Courses
3. Manage Advisors
4. Manage Enrollments
5. Manage Advising Sessions
6. Manage Grades

Follow the on-screen prompts to navigate through each feature.

## Sample Data

The application comes preloaded with sample data including:
- 3 sample students
- 3 sample courses
- 2 sample advisors

This allows you to immediately explore the system's functionality.

## API Methods

### StudentService
- `addStudent(Student)` - Add a new student
- `getStudent(String)` - Get student by ID
- `getAllStudents()` - Get all students
- `getStudentsByMajor(String)` - Filter by major
- `getStudentsByGPARange(double, double)` - Filter by GPA range
- `updateStudentGPA(String, double)` - Update student GPA

### CourseService
- `addCourse(Course)` - Add a new course
- `getCourse(String)` - Get course by ID
- `getCoursesByDepartment(String)` - Filter by department
- `getCoursesBySemester(String)` - Filter by semester
- `getCoursesWithAvailableSeats()` - Get courses with open seats
- `enrollStudent(String)` - Enroll a student
- `dropStudent(String)` - Drop student from course

### AdvisorService
- `addAdvisor(Advisor)` - Add a new advisor
- `getAdvisor(String)` - Get advisor by ID
- `assignStudentToAdvisor(String, String)` - Assign student to advisor
- `removeStudentFromAdvisor(String, String)` - Remove student assignment
- `getStudentAdvisor(String)` - Get advisor for a student

### EnrollmentService
- `enrollStudentInCourse(String, String)` - Enroll with prerequisite check
- `dropCourse(String, String)` - Drop a course
- `getAvailableCoursesForStudent(String)` - Get courses student can take
- `getStudentTotalCreditsCompleted(String)` - Calculate completed credits

### GradeService
- `recordGrade(Grade)` - Record a grade
- `getStudentGrades(String)` - Get all grades for a student
- `calculateStudentGPA(String)` - Calculate student's GPA
- `getAverageGradeForCourse(String)` - Get course average

## Testing

Run the unit tests with:
```bash
mvn test
```

Tests are included for:
- StudentService
- CourseService

## Future Enhancements

- Database integration (SQL/NoSQL)
- Web-based UI using Spring Boot
- REST API implementation
- Email notifications
- Advanced reporting and analytics
- Export functionality (PDF/Excel)
- Multi-user authentication and authorization
- Data persistence layer

## License

This project is provided as-is for educational purposes.

## Author

Created as part of CSE015 Summer 2024 Project

## Support

For questions or issues, please refer to the project documentation or contact the development team.
