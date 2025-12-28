# Academic Advising System - Architecture & Class Diagrams

## System Architecture Overview

```
┌─────────────────────────────────────────────────────────────────────┐
│                         PRESENTATION LAYER                           │
│                     (AdvisingSystemUI - Console)                     │
└────────────────────────────┬────────────────────────────────────────┘
                             │
                             │ uses
                             ▼
┌─────────────────────────────────────────────────────────────────────┐
│                          SERVICE LAYER                               │
├─────────────────────────────────────────────────────────────────────┤
│ ┌──────────────────┐  ┌──────────────────┐  ┌──────────────────┐   │
│ │ StudentService   │  │ CourseService    │  │ AdvisorService   │   │
│ ├──────────────────┤  ├──────────────────┤  ├──────────────────┤   │
│ │ - students: Map  │  │ - courses: Map   │  │ - advisors: Map  │   │
│ ├──────────────────┤  ├──────────────────┤  ├──────────────────┤   │
│ │ + add()          │  │ + add()          │  │ + add()          │   │
│ │ + get()          │  │ + get()          │  │ + get()          │   │
│ │ + getAll()       │  │ + getAll()       │  │ + getAll()       │   │
│ │ + search()       │  │ + search()       │  │ + assign()       │   │
│ └──────────────────┘  └──────────────────┘  └──────────────────┘   │
│                                                                       │
│ ┌──────────────────┐  ┌──────────────────┐  ┌──────────────────┐   │
│ │ EnrollmentService│  │ GradeService     │  │ SessionService   │   │
│ ├──────────────────┤  ├──────────────────┤  ├──────────────────┤   │
│ │ - validators     │  │ - grades: List   │  │ - sessions: Map  │   │
│ ├──────────────────┤  ├──────────────────┤  ├──────────────────┤   │
│ │ + enroll()       │  │ + record()       │  │ + create()       │   │
│ │ + drop()         │  │ + calculate()    │  │ + get()          │   │
│ │ + validate()     │  │ + getAverage()   │  │ + schedule()     │   │
│ └──────────────────┘  └──────────────────┘  └──────────────────┘   │
└─────────────────────────────────────────────────────────────────────┘
                             │
                             │ uses
                             ▼
┌─────────────────────────────────────────────────────────────────────┐
│                           MODEL LAYER                                │
├─────────────────────────────────────────────────────────────────────┤
│ ┌──────────────┐  ┌──────────────┐  ┌──────────────┐               │
│ │   Student    │  │    Course    │  │   Advisor    │               │
│ ├──────────────┤  ├──────────────┤  ├──────────────┤               │
│ │ - studentId  │  │ - courseId   │  │ - advisorId  │               │
│ │ - firstName  │  │ - courseName │  │ - firstName  │               │
│ │ - lastName   │  │ - department │  │ - lastName   │               │
│ │ - email      │  │ - credits    │  │ - email      │               │
│ │ - major      │  │ - capacity   │  │ - office     │               │
│ │ - gpa        │  │ - enrolled   │  │ - students[] │               │
│ │ - enrolled[] │  │ - semester   │  └──────────────┘               │
│ │ - completed[]│  └──────────────┘                                 │
│ └──────────────┘           │                                        │
│        │                   │          ┌──────────────┐  ┌───────┐  │
│        │                   └──────────▶│    Grade     │  │ Advse │  │
│        │                             ├──────────────┤  │ Sessio│  │
│        └─────────────────────────────▶│ - studentId  │  ├───────┤  │
│                                      │ - courseId   │  │-sessId│  │
│                                      │ - grade      │  │-studId│  │
│                                      │ - gpa        │  │-advId │  │
│                                      └──────────────┘  │-date  │  │
│                                                        └───────┘  │
└─────────────────────────────────────────────────────────────────────┘
                             │
                             │ uses
                             ▼
┌─────────────────────────────────────────────────────────────────────┐
│                         UTILITY LAYER                                │
│                      (SystemUtil - Validators)                       │
└─────────────────────────────────────────────────────────────────────┘
```

## Detailed Class Diagram

```
┌─────────────────────────────────────────┐
│            Student                       │
├─────────────────────────────────────────┤
│ - studentId: String                     │
│ - firstName: String                     │
│ - lastName: String                      │
│ - email: String                         │
│ - major: String                         │
│ - graduationYear: int                   │
│ - completedCourses: List<Course>        │
│ - enrolledCourses: List<Course>         │
│ - gpa: double                           │
├─────────────────────────────────────────┤
│ + getters/setters()                     │
│ + enrollCourse(Course)                  │
│ + dropCourse(Course)                    │
│ + addCompletedCourse(Course)            │
│ + getFullName(): String                 │
└─────────────────────────────────────────┘

┌─────────────────────────────────────────┐
│            Course                        │
├─────────────────────────────────────────┤
│ - courseId: String                      │
│ - courseName: String                    │
│ - department: String                    │
│ - credits: int                          │
│ - capacity: int                         │
│ - enrolledCount: int                    │
│ - prerequisites: List<String>           │
│ - semester: String                      │
│ - instructor: String                    │
├─────────────────────────────────────────┤
│ + getters/setters()                     │
│ + hasAvailableSeats(): boolean          │
│ + incrementEnrolled()                   │
│ + decrementEnrolled()                   │
│ + getAvailableSeats(): int              │
│ + addPrerequisite(String)               │
└─────────────────────────────────────────┘

┌─────────────────────────────────────────┐
│            Advisor                       │
├─────────────────────────────────────────┤
│ - advisorId: String                     │
│ - firstName: String                     │
│ - lastName: String                      │
│ - email: String                         │
│ - office: String                        │
│ - phoneNumber: String                   │
│ - specializations: List<String>         │
│ - maxStudents: int                      │
│ - assignedStudentIds: List<String>      │
├─────────────────────────────────────────┤
│ + getters/setters()                     │
│ + assignStudent(String)                 │
│ + removeStudent(String)                 │
│ + canAcceptMoreStudents(): boolean      │
│ + getFullName(): String                 │
└─────────────────────────────────────────┘

┌─────────────────────────────────────────┐
│         AdvicingSession                  │
├─────────────────────────────────────────┤
│ - sessionId: String                     │
│ - studentId: String                     │
│ - advisorId: String                     │
│ - sessionDate: LocalDateTime            │
│ - topic: String                         │
│ - notes: String                         │
│ - status: String                        │
│ - recommendedCourses: List<String>      │
├─────────────────────────────────────────┤
│ + getters/setters()                     │
│ + addRecommendedCourse(String)          │
│ + isPast(): boolean                     │
│ + getFormattedDate(): String            │
└─────────────────────────────────────────┘

┌─────────────────────────────────────────┐
│            Grade                         │
├─────────────────────────────────────────┤
│ - studentId: String                     │
│ - courseId: String                      │
│ - letterGrade: String                   │
│ - gpa: double                           │
│ - semester: String                      │
├─────────────────────────────────────────┤
│ + getters/setters()                     │
│ + getGPAForGrade(String): double        │
└─────────────────────────────────────────┘
```

## Service Class Relationships

```
┌────────────────────────────────────────────────────┐
│        StudentService                              │
├────────────────────────────────────────────────────┤
│ - students: Map<String, Student>                   │
├────────────────────────────────────────────────────┤
│ + addStudent(Student): void                        │
│ + getStudent(String): Student                      │
│ + getAllStudents(): List<Student>                  │
│ + removeStudent(String): boolean                   │
│ + getStudentsByMajor(String): List<Student>        │
│ + getStudentsGraduatingInYear(int): List<Student>  │
│ + getStudentsByGPARange(double, double)            │
│ + updateStudentGPA(String, double): void           │
│ + studentExists(String): boolean                   │
└────────────────────────────────────────────────────┘
              ▲
              │ used by
              │
┌────────────────────────────────────────────────────┐
│     EnrollmentService                              │
├────────────────────────────────────────────────────┤
│ - studentService: StudentService                   │
│ - courseService: CourseService                     │
│ - gradeService: GradeService                       │
├────────────────────────────────────────────────────┤
│ + enrollStudentInCourse(String, String): boolean   │
│ + dropCourse(String, String): boolean              │
│ + completeCourse(String, String): boolean          │
│ + getAvailableCoursesForStudent(String)            │
│ + hasPrerequisites(String, Course): boolean        │
│ + getStudentEnrolledCourses(String)                │
│ + getStudentCompletedCourses(String)               │
│ + getStudentTotalCreditsCompleted(String): int     │
└────────────────────────────────────────────────────┘
```

## Data Flow Diagram

```
┌─────────────────┐
│    User Input   │
│    (Console)    │
└────────┬────────┘
         │
         ▼
┌─────────────────────────────┐
│   AdvisingSystemUI          │
│ - Parse user input          │
│ - Call appropriate service  │
│ - Display results           │
└────────┬────────────────────┘
         │
         ▼
┌─────────────────────────────────────────────┐
│         Service Layer                       │
│ ┌──────────────┐  ┌──────────────────┐    │
│ │ Validate     │  │ Process Business │    │
│ │ Input        │  │ Logic            │    │
│ └──────────────┘  └──────────────────┘    │
└────────┬──────────────────────────────────┘
         │
         ▼
┌─────────────────────────────┐
│   Model Layer               │
│ ┌────────────────────────┐  │
│ │ Create/Update/Delete   │  │
│ │ Objects                │  │
│ └────────────────────────┘  │
└────────┬────────────────────┘
         │
         ▼
┌─────────────────────────────┐
│   In-Memory Data Storage    │
│ (HashMap/ArrayList)         │
└────────┬────────────────────┘
         │
         ▼
┌─────────────────────────────┐
│   Service Processing        │
│ (Query/Filter/Calculate)    │
└────────┬────────────────────┘
         │
         ▼
┌─────────────────────────────┐
│   Return Results            │
│   Back to UI                │
└────────┬────────────────────┘
         │
         ▼
┌─────────────────────────────┐
│   Display to User           │
│   (Console Output)          │
└─────────────────────────────┘
```

## Key Design Patterns Applied

### 1. Service Layer Pattern
```
┌────────────┐     ┌────────────┐     ┌────────────┐
│     UI     │────▶│  Service   │────▶│   Model    │
└────────────┘     └────────────┘     └────────────┘
```

### 2. Repository Pattern (via Service)
```
Services act as repositories providing access to data
StudentService.getStudent(id) - retrieves from map
CourseService.getCourse(id) - retrieves from map
```

### 3. Dependency Injection
```
EnrollmentService(StudentService, CourseService, GradeService)
Dependencies injected via constructor
```

## Future Integration Points

```
Current System (In-Memory)
         │
         ▼
┌─────────────────┐
│  Add DAO Layer  │────▶ Database (SQL/NoSQL)
└─────────────────┘
         │
         ▼
┌─────────────────┐
│ REST API Layer  │────▶ Web Clients
└─────────────────┘
         │
         ▼
┌─────────────────┐
│  Mobile Clients │
└─────────────────┘
```

---

This architecture provides a solid foundation for scaling, testing, and maintaining the Academic Advising System.
