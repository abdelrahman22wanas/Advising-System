ACADEMIC ADVISING SYSTEM - USER GUIDE
=====================================

## Quick Start

1. **Build the project:**
   mvn clean install

2. **Run the application:**
   mvn exec:java -Dexec.mainClass="com.advisingsystem.AdvisingSystemApp"

3. **Sample data will be automatically loaded**

## Main Menu Options

### 1. MANAGE STUDENTS
   - Add new students to the system
   - View all students with their details
   - View individual student information
   - Update student GPAs
   - Search students by major
   - Filter students by GPA range

### 2. MANAGE COURSES
   - Add new courses
   - View all available courses
   - View specific course details (enrollment, capacity, prerequisites)
   - Search courses by department
   - Search courses by semester
   - View courses with available seats

### 3. MANAGE ADVISORS
   - Add new advisors
   - View all advisors and their workload
   - View advisor details
   - Assign students to advisors
   - Remove students from advisors

### 4. MANAGE ENROLLMENTS
   - Enroll students in courses (with prerequisite validation)
   - Drop courses
   - View student's current enrollments
   - View student's completed courses
   - View available courses for a specific student

### 5. MANAGE ADVISING SESSIONS
   - Create new advising sessions
   - View all sessions
   - View sessions by student
   - View sessions by advisor
   - View upcoming scheduled sessions
   - Mark sessions as completed

### 6. MANAGE GRADES
   - Record grades for students
   - View all grades for a student
   - View all grades for a course
   - View calculated student GPA
   - View average grade for a course

## Sample Data

The system comes with sample data:

**Students:**
- STU001: John Doe (CS Major, GPA: 3.8, Graduating 2025)
- STU002: Jane Smith (CS Major, GPA: 3.5, Graduating 2026)
- STU003: Bob Johnson (Mathematics Major, GPA: 3.2, Graduating 2024)

**Courses:**
- CS101: Introduction to Java (3 credits, Capacity: 30)
- CS201: Data Structures (3 credits, Capacity: 25)
- MATH101: Calculus I (4 credits, Capacity: 35)

**Advisors:**
- ADV001: Dr. Anderson (Room 101)
- ADV002: Prof. Brown (Room 202)

## Common Workflows

### Enrolling a Student in a Course:
1. Go to "Manage Enrollments"
2. Select "Enroll Student in Course"
3. Enter the Student ID
4. Enter the Course ID
5. System will check prerequisites and capacity

### Recording a Grade:
1. Go to "Manage Grades"
2. Select "Record Grade"
3. Enter Student ID, Course ID, and letter grade
4. Grade will be recorded with GPA conversion

### Creating an Advising Session:
1. Go to "Manage Advising Sessions"
2. Select "Create Session"
3. Enter Student ID and Advisor ID
4. Enter the topic for discussion
5. Session will be scheduled for current date/time

## System Validations

- **Prerequisites**: Students cannot enroll in courses without completing prerequisites
- **Course Capacity**: Enrollment is blocked if course is full
- **GPA Range**: GPA values are constrained to 0.0-4.0
- **Email Format**: System validates email addresses during student creation
- **Advisor Capacity**: Advisors can have maximum 25 students assigned

## Tips and Best Practices

1. Always assign an advisor to a student for proper mentoring
2. Check prerequisites before enrolling in courses
3. Record grades after students complete courses
4. Schedule regular advising sessions with students
5. Use the search features to find relevant students or courses

## Grade Scale

The system uses the following grade scale:
- A = 4.0
- A- = 3.7
- B+ = 3.3
- B = 3.0
- B- = 2.7
- C+ = 2.3
- C = 2.0
- C- = 1.7
- D+ = 1.3
- D = 1.0
- F = 0.0

## Troubleshooting

**Issue: Cannot enroll student in course**
- Solution: Check if student has prerequisites and if course has available seats

**Issue: Cannot assign student to advisor**
- Solution: Check if advisor has reached maximum student capacity (25 students)

**Issue: Course shows 0 available seats**
- Solution: Course is full. Try a different section or semester.

## Development

To modify or extend the system:

1. Edit model classes in `src/main/java/com/advisingsystem/model/`
2. Modify business logic in `src/main/java/com/advisingsystem/service/`
3. Update UI in `src/main/java/com/advisingsystem/ui/`
4. Add tests in `src/test/java/com/advisingsystem/`

## Testing

Run tests:
```
mvn test
```

Current test coverage includes:
- StudentService tests
- CourseService tests
