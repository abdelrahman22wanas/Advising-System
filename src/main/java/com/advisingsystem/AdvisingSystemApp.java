package com.advisingsystem;

import com.advisingsystem.ui.AdvisingSystemUI;

/**
 * Main entry point for the Advising System application
 */
public class AdvisingSystemApp {
    public static void main(String[] args) {
        System.out.println("Welcome to the Academic Advising System!");
        AdvisingSystemUI ui = new AdvisingSystemUI();
        ui.start();
    }
}
