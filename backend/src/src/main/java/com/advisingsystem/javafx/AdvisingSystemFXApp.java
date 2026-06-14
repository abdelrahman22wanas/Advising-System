package com.advisingsystem.javafx;

import com.advisingsystem.service.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * JavaFX main application for the Academic Advising System
 */
public class AdvisingSystemFXApp extends Application {
    
    private StudentService studentService;
    private CourseService courseService;
    private AdvisorService advisorService;
    private AdvicingSessionService sessionService;
    private GradeService gradeService;
    private EnrollmentService enrollmentService;
    private CurriculumService curriculumService;
    
    @Override
    public void start(Stage primaryStage) {
        initializeServices();
        loadSampleData();
        
        MainView mainView = new MainView(
            studentService, 
            courseService, 
            advisorService, 
            sessionService, 
            gradeService, 
            enrollmentService,
            curriculumService
        );
        
        Scene scene = new Scene(mainView, 1400, 900);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        
        primaryStage.setTitle("Academic Advising System - CSE Curriculum");
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();
    }
    
    private void initializeServices() {
        studentService = new StudentService();
        courseService = new CourseService();
        advisorService = new AdvisorService();
        sessionService = new AdvicingSessionService();
        gradeService = new GradeService(courseService, studentService);
        enrollmentService = new EnrollmentService(studentService, courseService, gradeService);
        curriculumService = new CurriculumService();
    }
    
    private void loadSampleData() {
        SampleDataLoader.loadSampleData(
            studentService, 
            courseService, 
            advisorService, 
            gradeService, 
            enrollmentService,
            curriculumService,
            sessionService
        );
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
