package com.advisingsystem.service;

import com.advisingsystem.model.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service class for managing students
 */
public class StudentService {
    private Map<String, Student> students;

    public StudentService() {
        this.students = new HashMap<>();
    }

    public void addStudent(Student student) {
        if (!students.containsKey(student.getStudentId())) {
            students.put(student.getStudentId(), student);
        }
    }

    public Student getStudent(String studentId) {
        return students.get(studentId);
    }

    public List<Student> getAllStudents() {
        return new ArrayList<>(students.values());
    }

    public boolean removeStudent(String studentId) {
        return students.remove(studentId) != null;
    }

    public List<Student> getStudentsByMajor(String major) {
        return students.values().stream()
                .filter(s -> s.getMajor().equalsIgnoreCase(major))
                .collect(Collectors.toList());
    }

    public List<Student> getStudentsGraduatingInYear(int year) {
        return students.values().stream()
                .filter(s -> s.getGraduationYear() == year)
                .collect(Collectors.toList());
    }

    public List<Student> getStudentsByGPARange(double minGpa, double maxGpa) {
        return students.values().stream()
                .filter(s -> s.getGpa() >= minGpa && s.getGpa() <= maxGpa)
                .collect(Collectors.toList());
    }

    public int getTotalStudents() {
        return students.size();
    }

    public void updateStudentGPA(String studentId, double newGpa) {
        Student student = getStudent(studentId);
        if (student != null) {
            student.setGpa(newGpa);
        }
    }

    public boolean studentExists(String studentId) {
        return students.containsKey(studentId);
    }
}
