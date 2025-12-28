# 🎓 Academic Advising System

<div align="center">

![Java](https://img.shields.io/badge/Java-21-orange?style=for-the-badge&logo=java)
![JavaFX](https://img.shields.io/badge/JavaFX-21.0.1-blue?style=for-the-badge&logo=java)
![Maven](https://img.shields.io/badge/Maven-3.x-red?style=for-the-badge&logo=apache-maven)
![License](https://img.shields.io/badge/License-Educational-green?style=for-the-badge)

**A modern, feature-rich Academic Advising System with JavaFX GUI and comprehensive CSE curriculum integration**

[GitHub Repository](https://github.com/abdelrahman22wanas/Advising-System.git) • [Installation](#-installation) • [Features](#-features) • [Usage](#-usage)

</div>

---

## 📋 Table of Contents

- [Overview](#-overview)
- [Features](#-features)
- [Project Structure](#-project-structure)
- [Technologies](#-technologies)
- [Installation](#-installation)
- [Usage](#-usage)
- [Screenshots](#-screenshots)
- [Development](#-development)
- [Contributing](#-contributing)

---

## 🌟 Overview

The **Academic Advising System** is a comprehensive Java application designed to streamline academic advising processes in educational institutions. Built with **Java 21** and **JavaFX**, it features a modern dark-mode interface and complete CSE (Computer Science & Engineering) curriculum integration.

### What's New? 🎉

- ✅ **Java 21 LTS** - Upgraded from Java 17
- ✅ **Modern JavaFX GUI** - 11 interactive views with dark mode
- ✅ **CSE Curriculum** - 71 courses with prerequisites and categories
- ✅ **Degree Progress Tracking** - Visual progress monitoring
- ✅ **Dark Mode Theme** - Cyan accents on dark navy backgrounds
- ✅ **Sample Data** - 15 students, 6 advisors, 15 sessions pre-loaded

---

## ✨ Features

### 🎯 Core Functionality

#### **Student Management**
- Add, edit, and remove students
- Track academic standing and GPA
- Monitor enrolled and completed courses
- Search by major, GPA range, or graduation year
- View comprehensive student profiles

#### **Course Management**
- 71 CSE courses across 7 categories:
  - University Requirements (21 credits)
  - College Requirements (15 credits)
  - Major Core Courses (81 credits)
  - Capstone Project (6 credits)
  - Major Electives (9+ credits)
  - Free Electives (6 credits)
- Prerequisite tracking and validation
- Course capacity management
- Semester-based organization
- Search by department or semester

#### **Advisor Management**
- Assign students to advisors
- Track advisor workload (2-3 students each)
- Monitor specializations
- View advisor-student relationships
- Manage contact information

#### **Enrollment System**
- Smart enrollment with prerequisite validation
- Automatic capacity checking
- Drop/add functionality
- View enrollment history
- Calculate completed credits
- Track current semester enrollments

#### **Advising Sessions**
- Schedule sessions between students and advisors
- Track session status (Scheduled, In Progress, Completed, Cancelled)
- Record session topics and detailed notes
- View upcoming and past sessions
- Add recommendations and action items

#### **Grade Management**
- Record grades for completed courses
- Automatic GPA calculation
- View grades by student or course
- Calculate course averages
- Semester-based grade tracking
- Letter grade to GPA conversion

#### **Degree Progress Tracking** 🆕
- Visual progress bars for each course category
- Real-time credit completion tracking
- Graduation eligibility status
- Recommended courses for next semester
- Remaining courses breakdown
- Category-based progress monitoring

### 🎨 User Interface

- **Modern Dark Mode** - Sleek dark navy theme with cyan accents
- **Interactive Dashboard** - Real-time statistics with emoji icons
- **Responsive Design** - Adaptive layouts for all screen sizes
- **Color-Coded Cards** - Purple, pink, blue, and green gradients
- **Smooth Animations** - Hover effects and transitions
- **Professional Styling** - Custom CSS with gradient buttons

---

## 📁 Project Structure

```
AdvisingSystem/
├── src/
│   ├── main/
│   │   ├── java/com/advisingsystem/
│   │   │   ├── AdvisingSystemApp.java          # Console entry point
│   │   │   ├── data/
│   │   │   │   └── CSECurriculumLoader.java    # Loads 71 CSE courses
│   │   │   ├── javafx/
│   │   │   │   ├── AdvisingSystemFXApp.java    # JavaFX entry point
│   │   │   │   ├── DashboardView.java          # Main dashboard
│   │   │   │   ├── StudentView.java            # Student management
│   │   │   │   ├── CourseView.java             # Course management
│   │   │   │   ├── AdvisorView.java            # Advisor management
│   │   │   │   ├── EnrollmentView.java         # Enrollment system
│   │   │   │   ├── SessionView.java            # Advising sessions
│   │   │   │   ├── GradeView.java              # Grade management
│   │   │   │   ├── DegreeProgressView.java     # Progress tracking
│   │   │   │   ├── MainView.java               # Main layout
│   │   │   │   └── SampleDataLoader.java       # Sample data
│   │   │   ├── model/
│   │   │   │   ├── Student.java                # Student entity
│   │   │   │   ├── Course.java                 # Course entity
│   │   │   │   ├── Advisor.java                # Advisor entity
│   │   │   │   ├── AdvicingSession.java        # Session entity
│   │   │   │   ├── Grade.java                  # Grade entity
│   │   │   │   ├── CourseCategory.java         # Course categories
│   │   │   │   ├── Curriculum.java             # Curriculum structure
│   │   │   │   └── DegreeProgress.java         # Progress tracking
│   │   │   ├── service/
│   │   │   │   ├── StudentService.java         # Student operations
│   │   │   │   ├── CourseService.java          # Course operations
│   │   │   │   ├── AdvisorService.java         # Advisor operations
│   │   │   │   ├── EnrollmentService.java      # Enrollment logic
│   │   │   │   ├── AdvicingSessionService.java # Session management
│   │   │   │   ├── GradeService.java           # Grade operations
│   │   │   │   └── CurriculumService.java      # Curriculum logic
│   │   │   ├── ui/
│   │   │   │   └── AdvisingSystemUI.java       # Console UI
│   │   │   └── util/
│   │   │       └── SystemUtil.java             # Utilities
│   │   └── resources/
│   │       └── styles.css                      # Dark mode CSS
│   └── test/
│       └── java/com/advisingsystem/
│           ├── StudentServiceTest.java         # Student tests
│           └── CourseServiceTest.java          # Course tests
├── pom.xml                                     # Maven config
├── README.md                                   # This file
├── ARCHITECTURE.md                             # System architecture
├── CSE_CURRICULUM_INTEGRATION.md               # Curriculum details
├── GUIDE.md                                    # User guide
├── QUICK_START.md                              # Quick start guide
├── build-and-run.bat                           # Windows script
└── build-and-run.sh                            # Linux/Mac script
```

---

## 🛠 Technologies

| Technology | Version | Purpose |
|------------|---------|---------|
| **Java** | 21 LTS | Core programming language |
| **JavaFX** | 21.0.1 | GUI framework |
| **Maven** | 3.x | Build automation |
| **JUnit** | 5.9.2 | Unit testing |
| **CSS** | 3 | UI styling |

### Architecture Pattern
- **Layered Architecture**: Presentation → Service → Model
- **MVC Pattern**: Model-View-Controller separation
- **Service-Oriented**: Business logic in service layer

---

## 📥 Installation

### Prerequisites

- ☕ **Java 21** or higher ([Download](https://www.oracle.com/java/technologies/downloads/))
- 📦 **Maven 3.6+** ([Download](https://maven.apache.org/download.cgi))
- 💻 **Git** (optional, for cloning)

### Verify Installation

```bash
java -version   # Should show Java 21
mvn -v          # Should show Maven 3.x
```

### Setup Steps

#### 1️⃣ Clone the Repository
```bash
git clone https://github.com/abdelrahman22wanas/Advising-System.git
cd Advising-System
```

#### 2️⃣ Build the Project
```bash
mvn clean install
```

#### 3️⃣ Run the Application

**Option A: Using Maven Plugin** (Recommended)
```bash
mvn javafx:run
```

**Option B: Using Build Scripts**

**Windows:**
```bash
build-and-run.bat
```

**Linux/Mac:**
```bash
bash build-and-run.sh
```

**Option C: Run JAR File**
```bash
mvn clean package
java -jar target/advising-system-1.0.0.jar
```

---

## 🚀 Usage

### First Launch

When you first run the application:
1. **Sample data loads automatically** - 15 students, 6 advisors, 15 sessions
2. **Dashboard appears** - Shows statistics with emoji icons
3. **Navigation sidebar** - Click tabs to explore features

### Main Views

#### **📊 Dashboard**
- Total students, courses, advisors, sessions
- Color-coded stat cards with gradients
- Quick overview of system status

#### **👨‍🎓 Students**
- Add new students with full details
- Edit student information
- View enrolled and completed courses
- Search by major or GPA

#### **📚 Courses**
- Browse 71 CSE courses
- View prerequisites and descriptions
- Check enrollment capacity
- Filter by category or semester

#### **👨‍🏫 Advisors**
- Manage advisor profiles
- Assign students to advisors
- Track advisor workload
- View specializations

#### **📝 Enrollment**
- Enroll students in courses
- Automatic prerequisite validation
- Drop courses
- View enrollment history

#### **💬 Sessions**
- Schedule advising sessions
- Track session status
- Add notes and recommendations
- View upcoming meetings

#### **📈 Grades**
- Record student grades
- Automatic GPA calculation
- View grade history
- Calculate course averages

#### **🎯 Degree Progress**
- Visual progress tracking
- Category-based breakdown
- Graduation eligibility status
- Recommended courses

### Sample Data

The system includes pre-loaded data:

**Students (15):**
- STU001-STU005: Seniors and juniors (GPA 3.5-3.92)
- STU006-STU010: Sophomores (GPA 3.4-3.75)
- STU011-STU015: Freshmen (GPA 3.2-3.7)

**Advisors (6):**
- Dr. Anderson, Prof. Brown, Dr. Davis
- Dr. Miller, Prof. Martinez, Dr. Thompson

**Courses (71):**
- University Requirements: ENG101, ENG102, COMM101, etc.
- Major Core: CS111, CS112, CS211, CS311, etc.
- Major Electives: AI, ML, Graphics, Security, etc.
- Free Electives: Business, Arts, Sciences

**Sessions (15):**
- 8 Completed sessions
- 1 In Progress
- 6 Scheduled for future

---

## 📷 Screenshots

### Dark Mode Interface
- **Background**: Dark navy (#1a1a2e)
- **Accents**: Cyan (#64ffda, #00d4ff)
- **Cards**: Dark blue panels (#16213e)
- **Text**: Light gray (#ccd6f6)

### Key Features
- Gradient buttons with hover effects
- Smooth transitions and animations
- Professional data tables
- Color-coded progress bars
- Responsive layout design

---

## 👨‍💻 Development

### Running Tests
```bash
mvn test
```

### Building JAR
```bash
mvn clean package
```

### Clean Build
```bash
mvn clean
```

### Project Development Timeline

1. **Phase 1**: Core Models & Services (Console UI)
2. **Phase 2**: Java 17 → Java 21 Upgrade
3. **Phase 3**: JavaFX GUI Implementation (11 views)
4. **Phase 4**: CSE Curriculum Integration (71 courses)
5. **Phase 5**: Bug Fixes (NullPointerException fixes)
6. **Phase 6**: UI Enhancement (Purple gradient theme)
7. **Phase 7**: Dark Mode Transformation
8. **Phase 8**: Curriculum Expansion (48 → 71 courses)
9. **Phase 9**: Sample Data Expansion (15 students, 6 advisors, 15 sessions)
10. **Phase 10**: ComboBox Fix (Dropdown visibility)

---

## 🤝 Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

---

## 📄 License

This project is licensed for **Educational Use**.

---

## 📧 Contact

**Repository**: [https://github.com/abdelrahman22wanas/Advising-System.git](https://github.com/abdelrahman22wanas/Advising-System.git)

---

## 🙏 Acknowledgments

- Built with Java 21 LTS
- UI powered by JavaFX 21.0.1
- CSE Curriculum based on university requirements
- Dark mode inspired by modern development tools

---

<div align="center">

**⭐ Star this repository if you find it helpful!**

Made with ❤️ for Academic Excellence

</div>

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
