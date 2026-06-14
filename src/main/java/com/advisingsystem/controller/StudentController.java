package com.advisingsystem.controller;

import com.advisingsystem.model.Student;
import com.advisingsystem.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@CrossOrigin(origins = "*", maxAge = 3600)
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<Student> getStudent(@PathVariable String studentId) {
        Student student = studentService.getStudent(studentId);
        if (student != null) {
            return ResponseEntity.ok(student);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Student> addStudent(@RequestBody Student student) {
        studentService.addStudent(student);
        return ResponseEntity.status(201).body(student);
    }

    @DeleteMapping("/{studentId}")
    public ResponseEntity<Void> removeStudent(@PathVariable String studentId) {
        if (studentService.removeStudent(studentId)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/major/{major}")
    public ResponseEntity<List<Student>> getStudentsByMajor(@PathVariable String major) {
        return ResponseEntity.ok(studentService.getStudentsByMajor(major));
    }

    @GetMapping("/gpa-range/{minGpa}/{maxGpa}")
    public ResponseEntity<List<Student>> getStudentsByGPARange(
            @PathVariable double minGpa,
            @PathVariable double maxGpa) {
        return ResponseEntity.ok(studentService.getStudentsByGPARange(minGpa, maxGpa));
    }

    @GetMapping("/graduation/{year}")
    public ResponseEntity<List<Student>> getStudentsGraduatingInYear(@PathVariable int year) {
        return ResponseEntity.ok(studentService.getStudentsGraduatingInYear(year));
    }
}
