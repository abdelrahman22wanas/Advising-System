package com.advisingsystem.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Utility class for common operations
 */
public class SystemUtil {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Validates email format
     */
    public static boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return email != null && email.matches(emailRegex);
    }

    /**
     * Validates ID format
     */
    public static boolean isValidId(String id) {
        return id != null && !id.isEmpty() && id.length() >= 3;
    }

    /**
     * Validates GPA range
     */
    public static boolean isValidGPA(double gpa) {
        return gpa >= 0.0 && gpa <= 4.0;
    }

    /**
     * Validates credit hours
     */
    public static boolean isValidCredits(int credits) {
        return credits > 0 && credits <= 6;
    }

    /**
     * Gets current timestamp
     */
    public static String getCurrentTimestamp() {
        return LocalDateTime.now().format(formatter);
    }

    /**
     * Formats a LocalDateTime to string
     */
    public static String formatDateTime(LocalDateTime dateTime) {
        return dateTime.format(formatter);
    }

    /**
     * Pads a string with spaces
     */
    public static String padRight(String s, int n) {
        return String.format("%-" + n + "s", s);
    }

    /**
     * Pads a string on the left with spaces
     */
    public static String padLeft(String s, int n) {
        return String.format("%" + n + "s", s);
    }
}
