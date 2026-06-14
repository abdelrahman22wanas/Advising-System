# CSE Curriculum Integration

## Overview
The Academic Advising System has been enhanced with a comprehensive **Computer Science and Engineering (CSE) curriculum** based on university requirements. The system now includes:

- **60+ courses** across all categories
- Complete prerequisite tracking
- Degree progress monitoring
- Category-based course organization
- Semester-by-semester planning

## Curriculum Structure

### Total Credits Required: 132

### Course Categories

#### 1. University Requirements (21 credits)
- **English**: ENG101, ENG102 (6 credits)
- **Communication**: COMM101 (3 credits)
- **Humanities**: HUM101, PHIL101 (6 credits)
- **Social Sciences**: PSY101, SOC101 (6 credits)

#### 2. College Requirements (15 credits)
- **Mathematics**: MATH151, MATH152, MATH251 (9 credits)
- **Science**: PHYS201 (4 credits), CHEM101 (2 credits)

#### 3. Major Core Courses (81 credits)
Programming & Algorithms:
- CS111: Introduction to Programming (Python)
- CS112: Object-Oriented Programming (Java)
- CS211: Data Structures
- CS311: Algorithms

Computer Systems:
- CS221: Computer Organization
- CS321: Computer Architecture
- CS231: Assembly Language Programming
- CS331: Operating Systems

Software Engineering:
- CS241: Software Engineering I
- CS341: Software Engineering II

Database & Networks:
- CS351: Database Systems
- CS361: Computer Networks

Theory & Languages:
- CS371: Theory of Computation
- CS381: Compiler Design
- CS391: Programming Languages

Web Development:
- CS401: Web Application Development

Mathematics for CS:
- MATH271: Discrete Mathematics
- MATH272: Linear Algebra
- MATH371: Probability and Statistics
- MATH373: Numerical Methods

Professional Development:
- CS191: Professional Ethics in Computing
- CS291: Technical Writing

#### 4. Capstone Project (6 credits)
- CS491: Senior Project I
- CS492: Senior Project II

#### 5. Major Electives (9 credits minimum - choose 3)
- CS411: Artificial Intelligence
- CS412: Machine Learning
- CS421: Computer Graphics
- CS431: Cybersecurity
- CS441: Mobile Application Development
- CS451: Cloud Computing
- CS461: Big Data Analytics
- CS471: Game Development
- CS481: Human-Computer Interaction

#### 6. Free Electives (6 credits)
- BUS201: Introduction to Business
- ART101: Digital Art
- Or any other courses

## New Features

### 1. Course Model Enhancements
- Added `CourseCategory` enum for course classification
- Added `description` field for course details
- Added `recommendedSemester` for curriculum planning
- Enhanced toString() to display category information

### 2. Student Model Enhancements
- Added `currentSemester` tracking
- Added `totalCreditsEarned` counter
- Added `getAcademicStanding()` method (Freshman/Sophomore/Junior/Senior)
- Enhanced toString() with academic standing and credits

### 3. New Domain Models
- **CourseCategory**: Enum for course categorization
- **Curriculum**: Represents a complete degree program
- **DegreeProgress**: Tracks student progress toward graduation

### 4. New Services
- **CurriculumService**: Manages curricula and calculates degree progress
  - `calculateProgress(Student)`: Gets detailed progress report
  - `getRecommendedCoursesForSemester(Student, semester)`: Suggests courses
  - `getAvailableCourses(Student)`: Lists eligible courses
  - `arePrerequisitesMet(Course, completedCourseIds)`: Validates prerequisites

### 5. Data Loaders
- **CSECurriculumLoader**: Loads complete CSE curriculum with all courses
  - Properly configured prerequisites
  - Categorized courses
  - Semester recommendations

### 6. New UI View
- **DegreeProgressView**: Comprehensive degree tracking interface
  - Student information card
  - Overall progress bar
  - Category-by-category breakdown
  - Recommended courses for current semester
  - Remaining required courses grouped by category
  - Visual progress indicators (✅ complete, ⏳ in progress)

## How to Use

### Viewing Degree Progress
1. Launch the application
2. Click "📈 Degree Progress" in the navigation menu
3. Select a student from the dropdown
4. View:
   - Overall completion percentage
   - Credits completed vs. required
   - Progress by category
   - Recommended courses for next semester
   - All remaining required courses

### Sample Students
The system includes 5 sample students at different stages:
- **STU001 (John Doe)**: 3rd semester, 3.8 GPA, 30+ credits
- **STU002 (Jane Smith)**: 3rd semester, 3.4 GPA, 20+ credits
- **STU003 (Bob Johnson)**: 2nd semester, 3.9 GPA, 15+ credits
- **STU004 (Alice Williams)**: 6th semester, 3.85 GPA, 50+ credits (Senior)
- **STU005 (Charlie Brown)**: 1st semester, 0.0 GPA, just starting

### Graduation Requirements
To be eligible for graduation:
- Complete all 132 required credits
- Maintain minimum 2.0 GPA
- Complete all category requirements
- Finish capstone project (CS491 & CS492)

## Technical Implementation

### Prerequisites System
Courses properly enforce prerequisites:
```
CS111 (Intro Programming)
  → CS112 (OOP)
    → CS211 (Data Structures)
      → CS311 (Algorithms)
```

### Category-Based Progress Tracking
The system tracks completion separately for each category:
- University Requirements
- College Requirements
- Major Core
- Major Electives
- Capstone
- Free Electives

### Semester Planning
Each course has a recommended semester (1-8) to guide students through the 4-year program.

## Files Modified/Created

### New Files:
1. `CourseCategory.java` - Course category enum
2. `Curriculum.java` - Curriculum model
3. `DegreeProgress.java` - Progress tracking model
4. `CurriculumService.java` - Curriculum management service
5. `CSECurriculumLoader.java` - CSE curriculum data loader
6. `DegreeProgressView.java` - UI for degree progress

### Modified Files:
1. `Course.java` - Added category, description, recommendedSemester
2. `Student.java` - Added currentSemester, totalCreditsEarned, academicStanding
3. `AdvisingSystemFXApp.java` - Added CurriculumService
4. `MainView.java` - Added Degree Progress navigation item
5. `SampleDataLoader.java` - Updated to load CSE curriculum

## Future Enhancements
- Support for multiple majors/curricula
- What-if analysis for major changes
- Course substitution tracking
- Transfer credit management
- Printable degree audit reports
- Four-year plan generator
- Course availability by semester

## References
- Based on "Vol 4 University Requirements.pdf"
- Based on "Vol 4 CSE Study Plans.pdf"
- Implements standard CS curriculum structure
- Follows ABET accreditation guidelines
