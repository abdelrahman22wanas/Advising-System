package com.advisingsystem.model;

/**
 * Categories for courses based on university requirements
 */
public enum CourseCategory {
    UNIVERSITY_REQUIREMENTS("University Requirements"),
    COLLEGE_REQUIREMENTS("College Requirements"),
    MAJOR_CORE("Major Core Courses"),
    MAJOR_ELECTIVE("Major Elective Courses"),
    FREE_ELECTIVE("Free Elective Courses"),
    CAPSTONE("Capstone Project"),
    GENERAL_EDUCATION("General Education");

    private final String displayName;

    CourseCategory(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
