package com.advisingsystem.javafx;

import com.advisingsystem.service.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Main view with navigation and content area
 */
public class MainView extends BorderPane {
    
    private StudentService studentService;
    private CourseService courseService;
    private AdvisorService advisorService;
    private AdvicingSessionService sessionService;
    private GradeService gradeService;
    private EnrollmentService enrollmentService;
    
    private StackPane contentArea;
    private Label titleLabel;
    
    public MainView(StudentService studentService, CourseService courseService,
                    AdvisorService advisorService, AdvicingSessionService sessionService,
                    GradeService gradeService, EnrollmentService enrollmentService) {
        this.studentService = studentService;
        this.courseService = courseService;
        this.advisorService = advisorService;
        this.sessionService = sessionService;
        this.gradeService = gradeService;
        this.enrollmentService = enrollmentService;
        
        initializeUI();
    }
    
    private void initializeUI() {
        // Top bar
        VBox topBar = createTopBar();
        setTop(topBar);
        
        // Left navigation
        VBox navigation = createNavigation();
        setLeft(navigation);
        
        // Center content area
        contentArea = new StackPane();
        contentArea.setPadding(new Insets(20));
        contentArea.getStyleClass().add("content-area");
        setCenter(contentArea);
        
        // Show dashboard by default
        showDashboard();
    }
    
    private VBox createTopBar() {
        VBox topBar = new VBox();
        topBar.getStyleClass().add("top-bar");
        topBar.setPadding(new Insets(15, 20, 15, 20));
        
        Label appTitle = new Label("🎓 Academic Advising System");
        appTitle.setFont(Font.font("System", FontWeight.BOLD, 24));
        appTitle.getStyleClass().add("app-title");
        
        titleLabel = new Label("Dashboard");
        titleLabel.setFont(Font.font("System", FontWeight.NORMAL, 16));
        titleLabel.getStyleClass().add("page-title");
        
        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);
        
        topBar.getChildren().addAll(appTitle, titleLabel);
        
        return topBar;
    }
    
    private VBox createNavigation() {
        VBox nav = new VBox(10);
        nav.setPadding(new Insets(20));
        nav.getStyleClass().add("navigation");
        nav.setPrefWidth(220);
        
        Label navTitle = new Label("Navigation");
        navTitle.setFont(Font.font("System", FontWeight.BOLD, 14));
        navTitle.getStyleClass().add("nav-title");
        
        Button dashboardBtn = createNavButton("📊 Dashboard", () -> showDashboard());
        Button studentsBtn = createNavButton("👨‍🎓 Students", () -> showStudents());
        Button coursesBtn = createNavButton("📚 Courses", () -> showCourses());
        Button advisorsBtn = createNavButton("👔 Advisors", () -> showAdvisors());
        Button enrollmentBtn = createNavButton("📝 Enrollment", () -> showEnrollment());
        Button sessionsBtn = createNavButton("💬 Sessions", () -> showSessions());
        Button gradesBtn = createNavButton("🎯 Grades", () -> showGrades());
        
        Separator sep = new Separator();
        sep.setPadding(new Insets(10, 0, 10, 0));
        
        Button aboutBtn = createNavButton("ℹ️ About", () -> showAbout());
        
        nav.getChildren().addAll(
            navTitle,
            new Separator(),
            dashboardBtn,
            studentsBtn,
            coursesBtn,
            advisorsBtn,
            enrollmentBtn,
            sessionsBtn,
            gradesBtn,
            sep,
            aboutBtn
        );
        
        return nav;
    }
    
    private Button createNavButton(String text, Runnable action) {
        Button btn = new Button(text);
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.getStyleClass().add("nav-button");
        btn.setOnAction(e -> action.run());
        return btn;
    }
    
    private void showDashboard() {
        titleLabel.setText("Dashboard");
        DashboardView dashboard = new DashboardView(
            studentService, courseService, advisorService, sessionService, gradeService
        );
        contentArea.getChildren().clear();
        contentArea.getChildren().add(dashboard);
    }
    
    private void showStudents() {
        titleLabel.setText("Student Management");
        StudentView studentView = new StudentView(studentService, advisorService, enrollmentService);
        contentArea.getChildren().clear();
        contentArea.getChildren().add(studentView);
    }
    
    private void showCourses() {
        titleLabel.setText("Course Management");
        CourseView courseView = new CourseView(courseService);
        contentArea.getChildren().clear();
        contentArea.getChildren().add(courseView);
    }
    
    private void showAdvisors() {
        titleLabel.setText("Advisor Management");
        AdvisorView advisorView = new AdvisorView(advisorService, studentService);
        contentArea.getChildren().clear();
        contentArea.getChildren().add(advisorView);
    }
    
    private void showEnrollment() {
        titleLabel.setText("Enrollment Management");
        EnrollmentView enrollmentView = new EnrollmentView(enrollmentService, studentService, courseService);
        contentArea.getChildren().clear();
        contentArea.getChildren().add(enrollmentView);
    }
    
    private void showSessions() {
        titleLabel.setText("Advising Sessions");
        SessionView sessionView = new SessionView(sessionService, studentService, advisorService);
        contentArea.getChildren().clear();
        contentArea.getChildren().add(sessionView);
    }
    
    private void showGrades() {
        titleLabel.setText("Grade Management");
        GradeView gradeView = new GradeView(gradeService, studentService, courseService);
        contentArea.getChildren().clear();
        contentArea.getChildren().add(gradeView);
    }
    
    private void showAbout() {
        titleLabel.setText("About");
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText("Academic Advising System - JavaFX Edition");
        alert.setContentText("Version 2.0.0\nJava 21 with JavaFX\n\n" +
            "A comprehensive system for managing academic advising,\n" +
            "student records, courses, and enrollment.\n\n" +
            "© 2025 Academic Advising System");
        alert.showAndWait();
    }
}
