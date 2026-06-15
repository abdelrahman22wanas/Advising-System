package com.advisingsystem.controller;

import com.advisingsystem.model.Course;
import com.advisingsystem.model.CourseCategory;
import com.advisingsystem.model.Curriculum;
import com.advisingsystem.model.DegreeProgress;
import com.advisingsystem.service.CurriculumService;
import com.advisingsystem.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/curriculum")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CurriculumController {

    @Autowired
    private CurriculumService curriculumService;

    @Autowired
    private StudentService studentService;

    @GetMapping("/majors")
    public ResponseEntity<List<String>> getAllMajors() {
        return ResponseEntity.ok(curriculumService.getAllMajors());
    }

    @GetMapping("/{major}")
    public ResponseEntity<Curriculum> getCurriculum(@PathVariable String major) {
        Curriculum curriculum = curriculumService.getCurriculum(major);
        if (curriculum != null) {
            return ResponseEntity.ok(curriculum);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/progress/{studentId}")
    public ResponseEntity<DegreeProgress> getDegreeProgress(@PathVariable String studentId) {
        var student = studentService.getStudent(studentId);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        DegreeProgress progress = curriculumService.calculateProgress(student);
        if (progress != null) {
            return ResponseEntity.ok(progress);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/recommendations/{studentId}/{semester}")
    public ResponseEntity<List<Course>> getRecommendations(
            @PathVariable String studentId,
            @PathVariable int semester) {
        var student = studentService.getStudent(studentId);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(curriculumService.getRecommendedCoursesForSemester(student, semester));
    }

    @GetMapping("/remaining/{studentId}")
    public ResponseEntity<Map<CourseCategory, Integer>> getRemainingCredits(@PathVariable String studentId) {
        var student = studentService.getStudent(studentId);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(curriculumService.getRemainingCreditsByCategory(student));
    }
}
