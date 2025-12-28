package com.advisingsystem.javafx;

import com.advisingsystem.service.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Dashboard view showing system statistics and overview
 */
public class DashboardView extends VBox {
    
    private StudentService studentService;
    private CourseService courseService;
    private AdvisorService advisorService;
    private AdvicingSessionService sessionService;
    private GradeService gradeService;
    
    public DashboardView(StudentService studentService, CourseService courseService,
                        AdvisorService advisorService, AdvicingSessionService sessionService,
                        GradeService gradeService) {
        this.studentService = studentService;
        this.courseService = courseService;
        this.advisorService = advisorService;
        this.sessionService = sessionService;
        this.gradeService = gradeService;
        
        initializeUI();
    }
    
    private void initializeUI() {
        setSpacing(20);
        setPadding(new Insets(20));
        
        // Welcome message with icon
        HBox welcomeBox = new HBox(10);
        welcomeBox.setAlignment(Pos.CENTER_LEFT);
        Label welcomeIcon = new Label("🎓");
        welcomeIcon.setFont(Font.font("System", FontWeight.BOLD, 28));
        Label welcome = new Label("Welcome to the Academic Advising System");
        welcome.setFont(Font.font("System", FontWeight.BOLD, 22));
        welcome.setStyle("-fx-text-fill: linear-gradient(to right, #667eea, #764ba2);");
        welcomeBox.getChildren().addAll(welcomeIcon, welcome);
        
        // Statistics cards
        HBox statsBox = createStatisticsCards();
        
        // Charts
        HBox chartsBox = createCharts();
        
        // Recent activity
        VBox activityBox = createActivityBox();
        
        getChildren().addAll(welcomeBox, statsBox, chartsBox, activityBox);
    }
    
    private HBox createStatisticsCards() {
        HBox statsBox = new HBox(15);
        statsBox.setAlignment(Pos.CENTER);
        
        VBox studentCard = createStatCard("👨‍🎓 Students", String.valueOf(studentService.getTotalStudents()), 
            "linear-gradient(from 0% 0% to 100% 100%, #667eea 0%, #764ba2 100%)", "#ffffff");
        VBox courseCard = createStatCard("📚 Courses", String.valueOf(courseService.getTotalCourses()), 
            "linear-gradient(from 0% 0% to 100% 100%, #f093fb 0%, #f5576c 100%)", "#ffffff");
        VBox advisorCard = createStatCard("👔 Advisors", String.valueOf(advisorService.getTotalAdvisors()), 
            "linear-gradient(from 0% 0% to 100% 100%, #4facfe 0%, #00f2fe 100%)", "#ffffff");
        VBox sessionCard = createStatCard("💬 Sessions", String.valueOf(sessionService.getTotalSessions()), 
            "linear-gradient(from 0% 0% to 100% 100%, #43e97b 0%, #38f9d7 100%)", "#ffffff");
        
        HBox.setHgrow(studentCard, Priority.ALWAYS);
        HBox.setHgrow(courseCard, Priority.ALWAYS);
        HBox.setHgrow(advisorCard, Priority.ALWAYS);
        HBox.setHgrow(sessionCard, Priority.ALWAYS);
        
        statsBox.getChildren().addAll(studentCard, courseCard, advisorCard, sessionCard);
        
        return statsBox;
    }
    
    private VBox createStatCard(String title, String value, String bgColor, String textColor) {
        VBox card = new VBox(8);
        card.getStyleClass().add("stat-card");
        card.setAlignment(Pos.CENTER);
        card.setPadding(new Insets(25, 20, 25, 20));
        card.setStyle("-fx-background-color: " + bgColor + "; -fx-background-radius: 15;");
        
        Label valueLabel = new Label(value);
        valueLabel.setFont(Font.font("System", FontWeight.BOLD, 36));
        valueLabel.setStyle("-fx-text-fill: " + textColor + ";");
        
        Label titleLabel = new Label(title);
        titleLabel.setFont(Font.font("System", FontWeight.SEMI_BOLD, 15));
        titleLabel.setStyle("-fx-text-fill: " + textColor + ";");
        
        card.getChildren().addAll(valueLabel, titleLabel);
        
        return card;
    }
    
    private HBox createCharts() {
        HBox chartsBox = new HBox(20);
        chartsBox.setAlignment(Pos.CENTER);
        
        // Students by major chart
        PieChart majorChart = createMajorDistributionChart();
        majorChart.setTitle("Students by Major");
        majorChart.setMaxWidth(400);
        majorChart.setMaxHeight(300);
        
        // Course enrollment chart
        BarChart<String, Number> enrollmentChart = createEnrollmentChart();
        enrollmentChart.setTitle("Course Enrollment");
        enrollmentChart.setMaxWidth(500);
        enrollmentChart.setMaxHeight(300);
        
        HBox.setHgrow(majorChart, Priority.ALWAYS);
        HBox.setHgrow(enrollmentChart, Priority.ALWAYS);
        
        chartsBox.getChildren().addAll(majorChart, enrollmentChart);
        
        return chartsBox;
    }
    
    private PieChart createMajorDistributionChart() {
        PieChart chart = new PieChart();
        
        var majors = new java.util.HashMap<String, Integer>();
        studentService.getAllStudents().forEach(student -> {
            majors.put(student.getMajor(), majors.getOrDefault(student.getMajor(), 0) + 1);
        });
        
        majors.forEach((major, count) -> {
            chart.getData().add(new PieChart.Data(major + " (" + count + ")", count));
        });
        
        chart.setLegendVisible(true);
        chart.setLabelsVisible(false);
        
        return chart;
    }
    
    private BarChart<String, Number> createEnrollmentChart() {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Course");
        yAxis.setLabel("Enrolled Students");
        
        BarChart<String, Number> chart = new BarChart<>(xAxis, yAxis);
        chart.setLegendVisible(false);
        
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        
        courseService.getAllCourses().forEach(course -> {
            if (course.getEnrolledCount() > 0) {
                series.getData().add(new XYChart.Data<>(course.getCourseId(), course.getEnrolledCount()));
            }
        });
        
        chart.getData().add(series);
        
        return chart;
    }
    
    private VBox createActivityBox() {
        VBox activityBox = new VBox(10);
        activityBox.setPadding(new Insets(20));
        activityBox.getStyleClass().add("activity-box");
        
        Label title = new Label("Quick Stats");
        title.setFont(Font.font("System", FontWeight.BOLD, 16));
        
        Label coursesAvailable = new Label("• Courses with available seats: " + 
            courseService.getCoursesWithAvailableSeats().size());
        Label upcomingSessions = new Label("• Upcoming sessions: " + 
            sessionService.getUpcomingSessions().size());
        Label advisorsAvailable = new Label("• Advisors with capacity: " + 
            advisorService.getAdvisorsWithAvailableCapacity().size());
        
        activityBox.getChildren().addAll(title, new Separator(), coursesAvailable, upcomingSessions, advisorsAvailable);
        
        return activityBox;
    }
}
