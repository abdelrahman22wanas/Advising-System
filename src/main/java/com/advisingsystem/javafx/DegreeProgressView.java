package com.advisingsystem.javafx;

import com.advisingsystem.model.*;
import com.advisingsystem.service.*;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import java.util.*;

/**
 * View for displaying student degree progress and curriculum requirements
 */
public class DegreeProgressView extends VBox {
    
    private StudentService studentService;
    private CurriculumService curriculumService;
    private ComboBox<String> studentComboBox;
    private VBox progressContainer;
    
    public DegreeProgressView(StudentService studentService, CurriculumService curriculumService) {
        this.studentService = studentService;
        this.curriculumService = curriculumService;
        
        initializeUI();
    }
    
    private void initializeUI() {
        setPadding(new Insets(20));
        setSpacing(20);
        
        // Title and student selector
        HBox header = new HBox(15);
        header.setAlignment(Pos.CENTER_LEFT);
        
        Label titleLabel = new Label("Degree Progress Tracker");
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 20));
        
        Label selectLabel = new Label("Select Student:");
        
        studentComboBox = new ComboBox<>();
        studentComboBox.setPrefWidth(300);
        loadStudents();
        studentComboBox.setOnAction(e -> showStudentProgress());
        
        header.getChildren().addAll(titleLabel, new Region(), selectLabel, studentComboBox);
        HBox.setHgrow(header.getChildren().get(1), Priority.ALWAYS);
        
        // Progress container
        progressContainer = new VBox(15);
        progressContainer.setPadding(new Insets(10));
        
        ScrollPane scrollPane = new ScrollPane(progressContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.getStyleClass().add("scroll-pane");
        VBox.setVgrow(scrollPane, Priority.ALWAYS);
        
        getChildren().addAll(header, new Separator(), scrollPane);
        
        // Show first student if available
        if (!studentComboBox.getItems().isEmpty()) {
            studentComboBox.getSelectionModel().selectFirst();
            showStudentProgress();
        }
    }
    
    private void loadStudents() {
        studentComboBox.getItems().clear();
        for (Student student : studentService.getAllStudents()) {
            studentComboBox.getItems().add(student.getStudentId() + " - " + student.getFullName());
        }
    }
    
    private void showStudentProgress() {
        String selected = studentComboBox.getValue();
        if (selected == null) return;
        
        String studentId = selected.split(" - ")[0];
        Student student = studentService.getStudent(studentId);
        if (student == null) return;
        
        DegreeProgress progress = curriculumService.calculateProgress(student);
        if (progress == null) {
            showNoCurriculum(student);
            return;
        }
        
        progressContainer.getChildren().clear();
        
        // Student Info Card
        VBox studentInfo = createStudentInfoCard(student, progress);
        
        // Overall Progress Bar
        VBox overallProgress = createOverallProgressCard(progress);
        
        // Category Breakdown
        VBox categoryBreakdown = createCategoryBreakdown(progress);
        
        // Recommended Courses for Next Semester
        VBox recommendedCourses = createRecommendedCourses(student);
        
        // Remaining Courses
        VBox remainingCourses = createRemainingCoursesSection(progress);
        
        progressContainer.getChildren().addAll(
            studentInfo,
            overallProgress,
            categoryBreakdown,
            recommendedCourses,
            remainingCourses
        );
    }
    
    private VBox createStudentInfoCard(Student student, DegreeProgress progress) {
        VBox card = new VBox(10);
        card.getStyleClass().add("card");
        card.setPadding(new Insets(15));
        
        Label titleLabel = new Label("Student Information");
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 16));
        
        GridPane grid = new GridPane();
        grid.setHgap(15);
        grid.setVgap(8);
        
        addGridRow(grid, 0, "Name:", student.getFullName());
        addGridRow(grid, 1, "Student ID:", student.getStudentId());
        addGridRow(grid, 2, "Major:", student.getMajor());
        addGridRow(grid, 3, "GPA:", String.format("%.2f", student.getGpa()));
        addGridRow(grid, 4, "Standing:", student.getAcademicStanding());
        addGridRow(grid, 5, "Current Semester:", String.valueOf(student.getCurrentSemester()));
        addGridRow(grid, 6, "Graduation Year:", String.valueOf(student.getGraduationYear()));
        addGridRow(grid, 7, "Eligible for Graduation:", 
            progress.isEligibleForGraduation() ? "✅ Yes" : "❌ Not Yet");
        
        card.getChildren().addAll(titleLabel, new Separator(), grid);
        return card;
    }
    
    private VBox createOverallProgressCard(DegreeProgress progress) {
        VBox card = new VBox(10);
        card.getStyleClass().add("card");
        card.setPadding(new Insets(15));
        
        Label titleLabel = new Label("Overall Degree Progress");
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 16));
        
        double percentage = progress.getCompletionPercentage();
        
        Label progressLabel = new Label(String.format("%.1f%% Complete", percentage));
        progressLabel.setFont(Font.font("System", FontWeight.BOLD, 14));
        
        ProgressBar progressBar = new ProgressBar(percentage / 100.0);
        progressBar.setPrefWidth(Double.MAX_VALUE);
        progressBar.setPrefHeight(25);
        
        Label creditsLabel = new Label(String.format("Credits: %d / %d", 
            progress.getTotalCompletedCredits(), 
            progress.getTotalRequiredCredits()));
        creditsLabel.setFont(Font.font("System", FontWeight.NORMAL, 14));
        
        card.getChildren().addAll(titleLabel, new Separator(), progressLabel, progressBar, creditsLabel);
        return card;
    }
    
    private VBox createCategoryBreakdown(DegreeProgress progress) {
        VBox card = new VBox(10);
        card.getStyleClass().add("card");
        card.setPadding(new Insets(15));
        
        Label titleLabel = new Label("Progress by Category");
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 16));
        
        GridPane grid = new GridPane();
        grid.setHgap(15);
        grid.setVgap(10);
        grid.setPadding(new Insets(10, 0, 0, 0));
        
        Map<CourseCategory, Integer> completed = progress.getCompletedCreditsByCategory();
        Map<CourseCategory, Integer> required = progress.getRequiredCreditsByCategory();
        
        int row = 0;
        for (CourseCategory category : CourseCategory.values()) {
            int completedCredits = completed.getOrDefault(category, 0);
            int requiredCredits = required.getOrDefault(category, 0);
            
            if (requiredCredits > 0) {
                Label categoryLabel = new Label(category.getDisplayName() + ":");
                categoryLabel.setFont(Font.font("System", FontWeight.BOLD, 12));
                
                Label creditsLabel = new Label(String.format("%d / %d credits", completedCredits, requiredCredits));
                
                ProgressBar bar = new ProgressBar(
                    requiredCredits > 0 ? (double) completedCredits / requiredCredits : 0.0
                );
                bar.setPrefWidth(200);
                
                Label statusLabel = new Label(completedCredits >= requiredCredits ? "✅" : "⏳");
                
                grid.add(categoryLabel, 0, row);
                grid.add(bar, 1, row);
                grid.add(creditsLabel, 2, row);
                grid.add(statusLabel, 3, row);
                
                row++;
            }
        }
        
        card.getChildren().addAll(titleLabel, new Separator(), grid);
        return card;
    }
    
    private VBox createRecommendedCourses(Student student) {
        VBox card = new VBox(10);
        card.getStyleClass().add("card");
        card.setPadding(new Insets(15));
        
        Label titleLabel = new Label("Recommended Courses for Semester " + student.getCurrentSemester());
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 16));
        
        List<Course> recommended = curriculumService.getRecommendedCoursesForSemester(
            student, student.getCurrentSemester()
        );
        
        if (recommended.isEmpty()) {
            Label noCoursesLabel = new Label("No recommended courses for this semester or all completed.");
            card.getChildren().addAll(titleLabel, new Separator(), noCoursesLabel);
            return card;
        }
        
        VBox coursesList = new VBox(5);
        for (Course course : recommended) {
            HBox courseItem = new HBox(10);
            courseItem.setAlignment(Pos.CENTER_LEFT);
            
            Label idLabel = new Label(course.getCourseId());
            idLabel.setFont(Font.font("System", FontWeight.BOLD, 12));
            idLabel.setMinWidth(80);
            
            Label nameLabel = new Label(course.getCourseName());
            nameLabel.setMinWidth(250);
            
            Label creditsLabel = new Label(course.getCredits() + " credits");
            creditsLabel.setMinWidth(80);
            
            Label categoryLabel = new Label(course.getCategory().getDisplayName());
            categoryLabel.setFont(Font.font("System", FontWeight.NORMAL, 10));
            categoryLabel.setStyle("-fx-text-fill: #666;");
            
            courseItem.getChildren().addAll(idLabel, nameLabel, creditsLabel, categoryLabel);
            coursesList.getChildren().add(courseItem);
        }
        
        ScrollPane scrollPane = new ScrollPane(coursesList);
        scrollPane.setFitToWidth(true);
        scrollPane.setMaxHeight(200);
        
        card.getChildren().addAll(titleLabel, new Separator(), scrollPane);
        return card;
    }
    
    private VBox createRemainingCoursesSection(DegreeProgress progress) {
        VBox card = new VBox(10);
        card.getStyleClass().add("card");
        card.setPadding(new Insets(15));
        
        Label titleLabel = new Label("Remaining Required Courses");
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 16));
        
        List<Course> remaining = progress.getRemainingCourses();
        
        if (remaining.isEmpty()) {
            Label noneLabel = new Label("🎉 Congratulations! All required courses completed!");
            noneLabel.setFont(Font.font("System", FontWeight.BOLD, 14));
            noneLabel.setStyle("-fx-text-fill: green;");
            card.getChildren().addAll(titleLabel, new Separator(), noneLabel);
            return card;
        }
        
        Label countLabel = new Label(remaining.size() + " courses remaining");
        countLabel.setFont(Font.font("System", FontWeight.NORMAL, 12));
        countLabel.setStyle("-fx-text-fill: #666;");
        
        // Group by category
        Map<CourseCategory, List<Course>> byCategory = new TreeMap<>();
        for (Course course : remaining) {
            byCategory.computeIfAbsent(course.getCategory(), k -> new ArrayList<>()).add(course);
        }
        
        VBox categoriesBox = new VBox(15);
        
        for (Map.Entry<CourseCategory, List<Course>> entry : byCategory.entrySet()) {
            VBox categoryBox = new VBox(5);
            
            Label catLabel = new Label(entry.getKey().getDisplayName() + " (" + entry.getValue().size() + " courses)");
            catLabel.setFont(Font.font("System", FontWeight.BOLD, 13));
            
            VBox coursesList = new VBox(3);
            for (Course course : entry.getValue()) {
                Label courseLabel = new Label("  • " + course.getCourseId() + " - " + course.getCourseName() + 
                    " (" + course.getCredits() + " credits) - Semester " + course.getRecommendedSemester());
                courseLabel.setFont(Font.font("System", 11));
                coursesList.getChildren().add(courseLabel);
            }
            
            categoryBox.getChildren().addAll(catLabel, coursesList);
            categoriesBox.getChildren().add(categoryBox);
        }
        
        ScrollPane scrollPane = new ScrollPane(categoriesBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setMaxHeight(300);
        
        card.getChildren().addAll(titleLabel, new Separator(), countLabel, scrollPane);
        return card;
    }
    
    private void showNoCurriculum(Student student) {
        progressContainer.getChildren().clear();
        
        VBox message = new VBox(15);
        message.setAlignment(Pos.CENTER);
        message.setPadding(new Insets(50));
        
        Label titleLabel = new Label("❌ No Curriculum Found");
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 18));
        
        Label detailLabel = new Label("No curriculum available for major: " + student.getMajor());
        detailLabel.setFont(Font.font("System", 14));
        
        message.getChildren().addAll(titleLabel, detailLabel);
        progressContainer.getChildren().add(message);
    }
    
    private void addGridRow(GridPane grid, int row, String label, String value) {
        Label labelNode = new Label(label);
        labelNode.setFont(Font.font("System", FontWeight.BOLD, 12));
        
        Label valueNode = new Label(value);
        valueNode.setFont(Font.font("System", FontWeight.NORMAL, 12));
        
        grid.add(labelNode, 0, row);
        grid.add(valueNode, 1, row);
    }
}
