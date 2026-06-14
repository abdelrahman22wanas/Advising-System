package com.advisingsystem.config;

import com.advisingsystem.data.CSECurriculumLoader;
import com.advisingsystem.javafx.SampleDataLoader;
import com.advisingsystem.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    public StudentService studentService() {
        return new StudentService();
    }

    @Bean
    public CourseService courseService() {
        return new CourseService();
    }

    @Bean
    public AdvisorService advisorService() {
        return new AdvisorService();
    }

    @Bean
    public EnrollmentService enrollmentService(StudentService studentService, CourseService courseService) {
        return new EnrollmentService(studentService, courseService);
    }

    @Bean
    public AdvicingSessionService advicingSessionService(StudentService studentService, AdvisorService advisorService) {
        return new AdvicingSessionService(studentService, advisorService);
    }

    @Bean
    public GradeService gradeService(StudentService studentService) {
        return new GradeService(studentService);
    }

    @Bean
    public CurriculumService curriculumService(CourseService courseService) {
        return new CurriculumService(courseService);
    }

    @Bean
    public CommandLineRunner initializeData(
            StudentService studentService,
            CourseService courseService,
            AdvisorService advisorService,
            EnrollmentService enrollmentService,
            AdvicingSessionService sessionService,
            GradeService gradeService) {
        return args -> {
            try {
                // Load CSE curriculum
                CSECurriculumLoader.loadCurriculum(courseService);
                
                // Load sample data
                SampleDataLoader.loadSampleData(studentService, courseService, advisorService, enrollmentService, sessionService, gradeService);
                
                System.out.println("Sample data loaded successfully!");
            } catch (Exception e) {
                System.err.println("Error loading initial data: " + e.getMessage());
                e.printStackTrace();
            }
        };
    }
}
