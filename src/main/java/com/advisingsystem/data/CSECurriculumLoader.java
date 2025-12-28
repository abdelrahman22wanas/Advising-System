package com.advisingsystem.data;

import com.advisingsystem.model.*;
import com.advisingsystem.service.CourseService;

/**
 * Loads comprehensive CSE curriculum data based on university requirements
 * This includes all courses from the CSE study plan with proper prerequisites,
 * categories, and semester recommendations
 */
public class CSECurriculumLoader {

    /**
     * Load the complete CSE curriculum with all required courses
     */
    public static Curriculum loadCSECurriculum(CourseService courseService) {
        Curriculum curriculum = new Curriculum("Computer Science and Engineering", 132);
        curriculum.setMinimumGPA(2);

        // ===== UNIVERSITY REQUIREMENTS (21 credits) =====
        loadUniversityRequirements(curriculum, courseService);

        // ===== COLLEGE REQUIREMENTS (15 credits) =====
        loadCollegeRequirements(curriculum, courseService);

        // ===== MAJOR CORE COURSES (81 credits) =====
        loadMajorCoreCourses(curriculum, courseService);

        // ===== MAJOR ELECTIVE COURSES (9 credits minimum) =====
        loadMajorElectives(curriculum, courseService);

        // ===== FREE ELECTIVE COURSES (6 credits) =====
        loadFreeElectives(curriculum, courseService);

        return curriculum;
    }

    private static void loadUniversityRequirements(Curriculum curriculum, CourseService courseService) {
        // English Courses
        Course eng101 = createCourse("ENG101", "English Composition I", "English", 3, 
            "Fundamentals of writing and composition", CourseCategory.UNIVERSITY_REQUIREMENTS, 1);
        courseService.addCourse(eng101);
        curriculum.addCourse(eng101, CourseCategory.UNIVERSITY_REQUIREMENTS, 1);

        Course eng102 = createCourse("ENG102", "English Composition II", "English", 3,
            "Advanced writing and research skills", CourseCategory.UNIVERSITY_REQUIREMENTS, 2);
        eng102.addPrerequisite("ENG101");
        courseService.addCourse(eng102);
        curriculum.addCourse(eng102, CourseCategory.UNIVERSITY_REQUIREMENTS, 2);

        // Communication
        Course comm101 = createCourse("COMM101", "Public Speaking", "Communication", 3,
            "Oral communication and presentation skills", CourseCategory.UNIVERSITY_REQUIREMENTS, 3);
        courseService.addCourse(comm101);
        curriculum.addCourse(comm101, CourseCategory.UNIVERSITY_REQUIREMENTS, 3);

        // Humanities Electives (2 courses)
        Course hum101 = createCourse("HUM101", "World Literature", "Humanities", 3,
            "Survey of world literary traditions", CourseCategory.UNIVERSITY_REQUIREMENTS, 4);
        courseService.addCourse(hum101);
        curriculum.addCourse(hum101, CourseCategory.UNIVERSITY_REQUIREMENTS, 4);

        Course phil101 = createCourse("PHIL101", "Introduction to Philosophy", "Philosophy", 3,
            "Fundamental philosophical questions and methods", CourseCategory.UNIVERSITY_REQUIREMENTS, 5);
        courseService.addCourse(phil101);
        curriculum.addCourse(phil101, CourseCategory.UNIVERSITY_REQUIREMENTS, 5);

        // Social Sciences (2 courses)
        Course psy101 = createCourse("PSY101", "General Psychology", "Psychology", 3,
            "Introduction to psychological principles", CourseCategory.UNIVERSITY_REQUIREMENTS, 2);
        courseService.addCourse(psy101);
        curriculum.addCourse(psy101, CourseCategory.UNIVERSITY_REQUIREMENTS, 2);

        Course soc101 = createCourse("SOC101", "Introduction to Sociology", "Sociology", 3,
            "Social structures and human behavior", CourseCategory.UNIVERSITY_REQUIREMENTS, 3);
        courseService.addCourse(soc101);
        curriculum.addCourse(soc101, CourseCategory.UNIVERSITY_REQUIREMENTS, 3);
    }

    private static void loadCollegeRequirements(Curriculum curriculum, CourseService courseService) {
        // Mathematics Sequence
        Course math151 = createCourse("MATH151", "Calculus I", "Mathematics", 3,
            "Limits, derivatives, and applications", CourseCategory.COLLEGE_REQUIREMENTS, 1);
        courseService.addCourse(math151);
        curriculum.addCourse(math151, CourseCategory.COLLEGE_REQUIREMENTS, 1);

        Course math152 = createCourse("MATH152", "Calculus II", "Mathematics", 3,
            "Integration techniques and series", CourseCategory.COLLEGE_REQUIREMENTS, 2);
        math152.addPrerequisite("MATH151");
        courseService.addCourse(math152);
        curriculum.addCourse(math152, CourseCategory.COLLEGE_REQUIREMENTS, 2);

        Course math251 = createCourse("MATH251", "Multivariable Calculus", "Mathematics", 3,
            "Calculus of several variables", CourseCategory.COLLEGE_REQUIREMENTS, 3);
        math251.addPrerequisite("MATH152");
        courseService.addCourse(math251);
        curriculum.addCourse(math251, CourseCategory.COLLEGE_REQUIREMENTS, 3);

        // Science Courses
        Course phys201 = createCourse("PHYS201", "Physics I", "Physics", 4,
            "Mechanics and thermodynamics with lab", CourseCategory.COLLEGE_REQUIREMENTS, 2);
        phys201.addPrerequisite("MATH151");
        courseService.addCourse(phys201);
        curriculum.addCourse(phys201, CourseCategory.COLLEGE_REQUIREMENTS, 2);

        Course chem101 = createCourse("CHEM101", "General Chemistry", "Chemistry", 2,
            "Principles of chemistry with lab", CourseCategory.COLLEGE_REQUIREMENTS, 1);
        courseService.addCourse(chem101);
        curriculum.addCourse(chem101, CourseCategory.COLLEGE_REQUIREMENTS, 1);
    }

    private static void loadMajorCoreCourses(Curriculum curriculum, CourseService courseService) {
        // Programming Fundamentals
        Course cs111 = createCourse("CS111", "Introduction to Programming", "Computer Science", 3,
            "Introduction to programming using Python", CourseCategory.MAJOR_CORE, 1);
        courseService.addCourse(cs111);
        curriculum.addCourse(cs111, CourseCategory.MAJOR_CORE, 1);

        Course cs112 = createCourse("CS112", "Object-Oriented Programming", "Computer Science", 3,
            "OOP concepts using Java", CourseCategory.MAJOR_CORE, 2);
        cs112.addPrerequisite("CS111");
        courseService.addCourse(cs112);
        curriculum.addCourse(cs112, CourseCategory.MAJOR_CORE, 2);

        // Data Structures and Algorithms
        Course cs211 = createCourse("CS211", "Data Structures", "Computer Science", 3,
            "Linear and non-linear data structures", CourseCategory.MAJOR_CORE, 3);
        cs211.addPrerequisite("CS112");
        courseService.addCourse(cs211);
        curriculum.addCourse(cs211, CourseCategory.MAJOR_CORE, 3);

        Course cs311 = createCourse("CS311", "Algorithms", "Computer Science", 3,
            "Algorithm design and analysis", CourseCategory.MAJOR_CORE, 4);
        cs311.addPrerequisite("CS211");
        cs311.addPrerequisite("MATH251");
        courseService.addCourse(cs311);
        curriculum.addCourse(cs311, CourseCategory.MAJOR_CORE, 4);

        // Computer Organization
        Course cs221 = createCourse("CS221", "Computer Organization", "Computer Science", 3,
            "Digital logic and computer architecture", CourseCategory.MAJOR_CORE, 3);
        cs221.addPrerequisite("CS112");
        courseService.addCourse(cs221);
        curriculum.addCourse(cs221, CourseCategory.MAJOR_CORE, 3);

        Course cs321 = createCourse("CS321", "Computer Architecture", "Computer Science", 3,
            "Advanced computer architecture concepts", CourseCategory.MAJOR_CORE, 4);
        cs321.addPrerequisite("CS221");
        courseService.addCourse(cs321);
        curriculum.addCourse(cs321, CourseCategory.MAJOR_CORE, 4);

        // Systems Programming
        Course cs231 = createCourse("CS231", "Assembly Language Programming", "Computer Science", 3,
            "Low-level programming and system calls", CourseCategory.MAJOR_CORE, 4);
        cs231.addPrerequisite("CS221");
        courseService.addCourse(cs231);
        curriculum.addCourse(cs231, CourseCategory.MAJOR_CORE, 4);

        Course cs331 = createCourse("CS331", "Operating Systems", "Computer Science", 3,
            "OS principles and implementation", CourseCategory.MAJOR_CORE, 5);
        cs331.addPrerequisite("CS231");
        cs331.addPrerequisite("CS211");
        courseService.addCourse(cs331);
        curriculum.addCourse(cs331, CourseCategory.MAJOR_CORE, 5);

        // Software Engineering
        Course cs241 = createCourse("CS241", "Software Engineering I", "Computer Science", 3,
            "Software development lifecycle and methodologies", CourseCategory.MAJOR_CORE, 5);
        cs241.addPrerequisite("CS211");
        courseService.addCourse(cs241);
        curriculum.addCourse(cs241, CourseCategory.MAJOR_CORE, 5);

        Course cs341 = createCourse("CS341", "Software Engineering II", "Computer Science", 3,
            "Advanced software design patterns", CourseCategory.MAJOR_CORE, 6);
        cs341.addPrerequisite("CS241");
        courseService.addCourse(cs341);
        curriculum.addCourse(cs341, CourseCategory.MAJOR_CORE, 6);

        // Database Systems
        Course cs351 = createCourse("CS351", "Database Systems", "Computer Science", 3,
            "Database design and SQL", CourseCategory.MAJOR_CORE, 5);
        cs351.addPrerequisite("CS211");
        courseService.addCourse(cs351);
        curriculum.addCourse(cs351, CourseCategory.MAJOR_CORE, 5);

        // Networks
        Course cs361 = createCourse("CS361", "Computer Networks", "Computer Science", 3,
            "Network protocols and architecture", CourseCategory.MAJOR_CORE, 6);
        cs361.addPrerequisite("CS331");
        courseService.addCourse(cs361);
        curriculum.addCourse(cs361, CourseCategory.MAJOR_CORE, 6);

        // Theory of Computation
        Course cs371 = createCourse("CS371", "Theory of Computation", "Computer Science", 3,
            "Automata, languages, and computability", CourseCategory.MAJOR_CORE, 6);
        cs371.addPrerequisite("CS311");
        courseService.addCourse(cs371);
        curriculum.addCourse(cs371, CourseCategory.MAJOR_CORE, 6);

        // Compiler Design
        Course cs381 = createCourse("CS381", "Compiler Design", "Computer Science", 3,
            "Lexical analysis, parsing, and code generation", CourseCategory.MAJOR_CORE, 7);
        cs381.addPrerequisite("CS371");
        courseService.addCourse(cs381);
        curriculum.addCourse(cs381, CourseCategory.MAJOR_CORE, 7);

        // Programming Languages
        Course cs391 = createCourse("CS391", "Programming Languages", "Computer Science", 3,
            "Language paradigms and design", CourseCategory.MAJOR_CORE, 6);
        cs391.addPrerequisite("CS211");
        courseService.addCourse(cs391);
        curriculum.addCourse(cs391, CourseCategory.MAJOR_CORE, 6);

        // Web Development
        Course cs401 = createCourse("CS401", "Web Application Development", "Computer Science", 3,
            "Full-stack web development", CourseCategory.MAJOR_CORE, 5);
        cs401.addPrerequisite("CS211");
        courseService.addCourse(cs401);
        curriculum.addCourse(cs401, CourseCategory.MAJOR_CORE, 5);

        // Discrete Mathematics
        Course math271 = createCourse("MATH271", "Discrete Mathematics", "Mathematics", 3,
            "Logic, sets, relations, and graph theory", CourseCategory.MAJOR_CORE, 2);
        math271.addPrerequisite("MATH151");
        courseService.addCourse(math271);
        curriculum.addCourse(math271, CourseCategory.MAJOR_CORE, 2);

        Course math371 = createCourse("MATH371", "Probability and Statistics", "Mathematics", 3,
            "Statistical methods for computer science", CourseCategory.MAJOR_CORE, 4);
        math371.addPrerequisite("MATH152");
        courseService.addCourse(math371);
        curriculum.addCourse(math371, CourseCategory.MAJOR_CORE, 4);

        // Linear Algebra
        Course math272 = createCourse("MATH272", "Linear Algebra", "Mathematics", 3,
            "Matrices, vector spaces, and transformations", CourseCategory.MAJOR_CORE, 3);
        math272.addPrerequisite("MATH152");
        courseService.addCourse(math272);
        curriculum.addCourse(math272, CourseCategory.MAJOR_CORE, 3);

        // Numerical Methods
        Course math373 = createCourse("MATH373", "Numerical Methods", "Mathematics", 3,
            "Numerical algorithms and computation", CourseCategory.MAJOR_CORE, 5);
        math373.addPrerequisite("MATH251");
        math373.addPrerequisite("CS112");
        courseService.addCourse(math373);
        curriculum.addCourse(math373, CourseCategory.MAJOR_CORE, 5);

        // Professional Development
        Course cs191 = createCourse("CS191", "Professional Ethics in Computing", "Computer Science", 2,
            "Ethical issues in technology", CourseCategory.MAJOR_CORE, 4);
        courseService.addCourse(cs191);
        curriculum.addCourse(cs191, CourseCategory.MAJOR_CORE, 4);

        Course cs291 = createCourse("CS291", "Technical Writing", "Computer Science", 2,
            "Writing technical documentation", CourseCategory.MAJOR_CORE, 5);
        cs291.addPrerequisite("ENG102");
        courseService.addCourse(cs291);
        curriculum.addCourse(cs291, CourseCategory.MAJOR_CORE, 5);

        // Capstone Project
        Course cs491 = createCourse("CS491", "Senior Project I", "Computer Science", 3,
            "Capstone project design phase", CourseCategory.CAPSTONE, 7);
        cs491.addPrerequisite("CS341");
        courseService.addCourse(cs491);
        curriculum.addCourse(cs491, CourseCategory.CAPSTONE, 7);

        Course cs492 = createCourse("CS492", "Senior Project II", "Computer Science", 3,
            "Capstone project implementation", CourseCategory.CAPSTONE, 8);
        cs492.addPrerequisite("CS491");
        courseService.addCourse(cs492);
        curriculum.addCourse(cs492, CourseCategory.CAPSTONE, 8);
    }

    private static void loadMajorElectives(Curriculum curriculum, CourseService courseService) {
        // Students must choose at least 3 courses (9 credits) from these electives

        Course cs411 = createCourse("CS411", "Artificial Intelligence", "Computer Science", 3,
            "AI concepts and machine learning basics", CourseCategory.MAJOR_ELECTIVE, 7);
        cs411.addPrerequisite("CS311");
        cs411.addPrerequisite("MATH371");
        courseService.addCourse(cs411);
        curriculum.addCourse(cs411, CourseCategory.MAJOR_ELECTIVE, 7);

        Course cs412 = createCourse("CS412", "Machine Learning", "Computer Science", 3,
            "Advanced machine learning algorithms", CourseCategory.MAJOR_ELECTIVE, 7);
        cs412.addPrerequisite("CS411");
        cs412.addPrerequisite("MATH272");
        courseService.addCourse(cs412);
        curriculum.addCourse(cs412, CourseCategory.MAJOR_ELECTIVE, 7);

        Course cs421 = createCourse("CS421", "Computer Graphics", "Computer Science", 3,
            "3D graphics and rendering", CourseCategory.MAJOR_ELECTIVE, 7);
        cs421.addPrerequisite("CS211");
        cs421.addPrerequisite("MATH272");
        courseService.addCourse(cs421);
        curriculum.addCourse(cs421, CourseCategory.MAJOR_ELECTIVE, 7);

        Course cs431 = createCourse("CS431", "Cybersecurity", "Computer Science", 3,
            "Security principles and cryptography", CourseCategory.MAJOR_ELECTIVE, 7);
        cs431.addPrerequisite("CS361");
        courseService.addCourse(cs431);
        curriculum.addCourse(cs431, CourseCategory.MAJOR_ELECTIVE, 7);

        Course cs441 = createCourse("CS441", "Mobile Application Development", "Computer Science", 3,
            "iOS and Android development", CourseCategory.MAJOR_ELECTIVE, 6);
        cs441.addPrerequisite("CS211");
        courseService.addCourse(cs441);
        curriculum.addCourse(cs441, CourseCategory.MAJOR_ELECTIVE, 6);

        Course cs451 = createCourse("CS451", "Cloud Computing", "Computer Science", 3,
            "Cloud architecture and services", CourseCategory.MAJOR_ELECTIVE, 7);
        cs451.addPrerequisite("CS361");
        courseService.addCourse(cs451);
        curriculum.addCourse(cs451, CourseCategory.MAJOR_ELECTIVE, 7);

        Course cs461 = createCourse("CS461", "Big Data Analytics", "Computer Science", 3,
            "Processing and analyzing large datasets", CourseCategory.MAJOR_ELECTIVE, 7);
        cs461.addPrerequisite("CS351");
        cs461.addPrerequisite("MATH371");
        courseService.addCourse(cs461);
        curriculum.addCourse(cs461, CourseCategory.MAJOR_ELECTIVE, 7);

        Course cs471 = createCourse("CS471", "Game Development", "Computer Science", 3,
            "Game engines and interactive systems", CourseCategory.MAJOR_ELECTIVE, 6);
        cs471.addPrerequisite("CS211");
        courseService.addCourse(cs471);
        curriculum.addCourse(cs471, CourseCategory.MAJOR_ELECTIVE, 6);

        Course cs481 = createCourse("CS481", "Human-Computer Interaction", "Computer Science", 3,
            "UI/UX design principles", CourseCategory.MAJOR_ELECTIVE, 6);
        cs481.addPrerequisite("CS211");
        courseService.addCourse(cs481);
        curriculum.addCourse(cs481, CourseCategory.MAJOR_ELECTIVE, 6);
    }

    private static void loadFreeElectives(Curriculum curriculum, CourseService courseService) {
        // Students can choose any courses for free electives
        Course bus201 = createCourse("BUS201", "Introduction to Business", "Business", 3,
            "Business fundamentals and entrepreneurship", CourseCategory.FREE_ELECTIVE, 6);
        courseService.addCourse(bus201);
        curriculum.addCourse(bus201, CourseCategory.FREE_ELECTIVE, 6);

        Course art101 = createCourse("ART101", "Digital Art", "Art", 3,
            "Digital creativity and design", CourseCategory.FREE_ELECTIVE, 7);
        courseService.addCourse(art101);
        curriculum.addCourse(art101, CourseCategory.FREE_ELECTIVE, 7);
    }

    private static Course createCourse(String id, String name, String dept, int credits, 
                                      String description, CourseCategory category, int semester) {
        Course course = new Course(id, name, dept, credits, 30, "TBD");
        course.setDescription(description);
        course.setCategory(category);
        course.setRecommendedSemester(semester);
        return course;
    }
}
