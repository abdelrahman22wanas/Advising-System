package com.advisingsystem.config;

import com.advisingsystem.data.CSECurriculumLoader;
import com.advisingsystem.data.SampleDataLoader;
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
    public GradeService gradeService(CourseService courseService, StudentService studentService) {
        return new GradeService(courseService, studentService);
    }

    @Bean
    public EnrollmentService enrollmentService(StudentService studentService, CourseService courseService, GradeService gradeService) {
        return new EnrollmentService(studentService, courseService, gradeService);
    }

    @Bean
    public AdvicingSessionService advicingSessionService() {
        return new AdvicingSessionService();
    }

    @Bean
    public CurriculumService curriculumService() {
        return new CurriculumService();
    }

    @Bean
    public WarningService warningService(
            StudentService studentService,
            GradeService gradeService,
            CurriculumService curriculumService) {
        return new WarningService(studentService, gradeService, curriculumService);
    }

    @Bean
    public CommandLineRunner initializeData(
            StudentService studentService,
            CourseService courseService,
            AdvisorService advisorService,
            GradeService gradeService,
            EnrollmentService enrollmentService,
            CurriculumService curriculumService,
            AdvicingSessionService sessionService) {
        return args -> {
            try {
                SampleDataLoader.loadSampleData(studentService, courseService, advisorService,
                    gradeService, enrollmentService, curriculumService, sessionService);
                System.out.println("Sample data loaded successfully!");
            } catch (Exception e) {
                System.err.println("Error loading initial data: " + e.getMessage());
                e.printStackTrace();
            }
        };
    }
}
