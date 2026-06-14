package com.advisingsystem.model;

import java.util.*;

/**
 * Represents an academic advisor
 */
public class Advisor {
    private String advisorId;
    private String firstName;
    private String lastName;
    private String email;
    private String office;
    private String phoneNumber;
    private List<String> specializations;
    private int maxStudents;
    private List<String> assignedStudentIds;

    public Advisor(String advisorId, String firstName, String lastName, String email, String office) {
        this.advisorId = advisorId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.office = office;
        this.specializations = new ArrayList<>();
        this.assignedStudentIds = new ArrayList<>();
        this.maxStudents = 25;
    }

    // Getters and Setters
    public String getAdvisorId() {
        return advisorId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<String> getSpecializations() {
        return new ArrayList<>(specializations);
    }

    public void addSpecialization(String specialization) {
        if (!specializations.contains(specialization)) {
            specializations.add(specialization);
        }
    }

    public int getMaxStudents() {
        return maxStudents;
    }

    public void setMaxStudents(int maxStudents) {
        this.maxStudents = maxStudents;
    }

    public List<String> getAssignedStudentIds() {
        return new ArrayList<>(assignedStudentIds);
    }

    public void assignStudent(String studentId) {
        if (assignedStudentIds.size() < maxStudents && !assignedStudentIds.contains(studentId)) {
            assignedStudentIds.add(studentId);
        }
    }

    public void removeStudent(String studentId) {
        assignedStudentIds.remove(studentId);
    }

    public boolean canAcceptMoreStudents() {
        return assignedStudentIds.size() < maxStudents;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    @Override
    public String toString() {
        return "Advisor{" +
                "advisorId='" + advisorId + '\'' +
                ", name='" + getFullName() + '\'' +
                ", office='" + office + '\'' +
                ", students=" + assignedStudentIds.size() + "/" + maxStudents +
                '}';
    }
}
