package com.advisingsystem.javafx;

import com.advisingsystem.model.*;
import com.advisingsystem.service.*;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class EnrollmentView extends VBox {
    
    private EnrollmentService enrollmentService;
    private StudentService studentService;
    private CourseService courseService;
    
    public EnrollmentView(EnrollmentService enrollmentService, StudentService studentService,
                         CourseService courseService) {
        this.enrollmentService = enrollmentService;
        this.studentService = studentService;
        this.courseService = courseService;
        initializeUI();
    }
    
    private void initializeUI() {
        setSpacing(20);
        setPadding(new Insets(20));
        
        // Enroll section
        VBox enrollSection = createEnrollSection();
        
        // Drop section
        VBox dropSection = createDropSection();
        
        getChildren().addAll(enrollSection, new Separator(), dropSection);
    }
    
    private VBox createEnrollSection() {
        VBox section = new VBox(10);
        section.getStyleClass().add("section-box");
        section.setPadding(new Insets(20));
        
        Label title = new Label("📝 Enroll Student in Course");
        title.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        
        ComboBox<String> studentCombo = new ComboBox<>();
        ComboBox<String> courseCombo = new ComboBox<>();
        
        studentService.getAllStudents().forEach(s -> 
            studentCombo.getItems().add(s.getStudentId() + " - " + s.getFullName())
        );
        courseService.getAllCourses().forEach(c -> 
            courseCombo.getItems().add(c.getCourseId() + " - " + c.getCourseName())
        );
        
        grid.add(new Label("Student:"), 0, 0);
        grid.add(studentCombo, 1, 0);
        grid.add(new Label("Course:"), 0, 1);
        grid.add(courseCombo, 1, 1);
        
        Button enrollBtn = new Button("Enroll");
        enrollBtn.getStyleClass().add("primary-button");
        enrollBtn.setOnAction(e -> {
            if (studentCombo.getValue() != null && courseCombo.getValue() != null) {
                String studentId = studentCombo.getValue().split(" - ")[0];
                String courseId = courseCombo.getValue().split(" - ")[0];
                
                if (enrollmentService.enrollStudentInCourse(studentId, courseId)) {
                    showInfo("Student enrolled successfully!");
                    studentCombo.setValue(null);
                    courseCombo.setValue(null);
                } else {
                    showError("Enrollment failed. Check prerequisites and course capacity.");
                }
            }
        });
        
        section.getChildren().addAll(title, grid, enrollBtn);
        
        return section;
    }
    
    private VBox createDropSection() {
        VBox section = new VBox(10);
        section.getStyleClass().add("section-box");
        section.setPadding(new Insets(20));
        
        Label title = new Label("🗑️ Drop Course");
        title.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        
        ComboBox<String> studentCombo = new ComboBox<>();
        ComboBox<String> courseCombo = new ComboBox<>();
        
        studentService.getAllStudents().forEach(s -> 
            studentCombo.getItems().add(s.getStudentId() + " - " + s.getFullName())
        );
        
        studentCombo.setOnAction(e -> {
            courseCombo.getItems().clear();
            if (studentCombo.getValue() != null) {
                String studentId = studentCombo.getValue().split(" - ")[0];
                enrollmentService.getStudentEnrolledCourses(studentId).forEach(c ->
                    courseCombo.getItems().add(c.getCourseId() + " - " + c.getCourseName())
                );
            }
        });
        
        grid.add(new Label("Student:"), 0, 0);
        grid.add(studentCombo, 1, 0);
        grid.add(new Label("Course:"), 0, 1);
        grid.add(courseCombo, 1, 1);
        
        Button dropBtn = new Button("Drop Course");
        dropBtn.setOnAction(e -> {
            if (studentCombo.getValue() != null && courseCombo.getValue() != null) {
                String studentId = studentCombo.getValue().split(" - ")[0];
                String courseId = courseCombo.getValue().split(" - ")[0];
                
                if (enrollmentService.dropCourse(studentId, courseId)) {
                    showInfo("Course dropped successfully!");
                    courseCombo.setValue(null);
                    courseCombo.getItems().clear();
                    studentCombo.fireEvent(e);
                } else {
                    showError("Failed to drop course.");
                }
            }
        });
        
        section.getChildren().addAll(title, grid, dropBtn);
        
        return section;
    }
    
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    private void showInfo(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
