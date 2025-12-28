package com.advisingsystem.javafx;

import com.advisingsystem.model.*;
import com.advisingsystem.service.*;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;

/**
 * Course management view
 */
public class CourseView extends VBox {
    
    private CourseService courseService;
    private TableView<CourseTableModel> courseTable;
    private TextField searchField;
    
    public CourseView(CourseService courseService) {
        this.courseService = courseService;
        initializeUI();
        refreshTable();
    }
    
    private void initializeUI() {
        setSpacing(15);
        setPadding(new Insets(20));
        
        HBox toolbar = createToolbar();
        courseTable = createCourseTable();
        VBox.setVgrow(courseTable, Priority.ALWAYS);
        
        getChildren().addAll(toolbar, courseTable);
    }
    
    private HBox createToolbar() {
        HBox toolbar = new HBox(10);
        toolbar.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
        
        Button addBtn = new Button("➕ Add Course");
        addBtn.getStyleClass().add("primary-button");
        addBtn.setOnAction(e -> showAddCourseDialog());
        
        Button viewBtn = new Button("👁️ View Details");
        viewBtn.setDisable(true);
        viewBtn.setOnAction(e -> showCourseDetails());
        
        courseTable.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            viewBtn.setDisable(newVal == null);
        });
        
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        searchField = new TextField();
        searchField.setPromptText("🔍 Search courses...");
        searchField.setPrefWidth(250);
        searchField.textProperty().addListener((obs, oldVal, newVal) -> filterTable(newVal));
        
        Button refreshBtn = new Button("🔄 Refresh");
        refreshBtn.setOnAction(e -> refreshTable());
        
        toolbar.getChildren().addAll(addBtn, viewBtn, spacer, searchField, refreshBtn);
        
        return toolbar;
    }
    
    private TableView<CourseTableModel> createCourseTable() {
        TableView<CourseTableModel> table = new TableView<>();
        table.getStyleClass().add("data-table");
        
        TableColumn<CourseTableModel, String> idCol = new TableColumn<>("Course ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("courseId"));
        idCol.setPrefWidth(100);
        
        TableColumn<CourseTableModel, String> nameCol = new TableColumn<>("Course Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        nameCol.setPrefWidth(250);
        
        TableColumn<CourseTableModel, String> deptCol = new TableColumn<>("Department");
        deptCol.setCellValueFactory(new PropertyValueFactory<>("department"));
        deptCol.setPrefWidth(150);
        
        TableColumn<CourseTableModel, Integer> creditsCol = new TableColumn<>("Credits");
        creditsCol.setCellValueFactory(new PropertyValueFactory<>("credits"));
        creditsCol.setPrefWidth(80);
        
        TableColumn<CourseTableModel, String> semesterCol = new TableColumn<>("Semester");
        semesterCol.setCellValueFactory(new PropertyValueFactory<>("semester"));
        semesterCol.setPrefWidth(120);
        
        TableColumn<CourseTableModel, String> enrollmentCol = new TableColumn<>("Enrollment");
        enrollmentCol.setCellValueFactory(new PropertyValueFactory<>("enrollment"));
        enrollmentCol.setPrefWidth(120);
        
        TableColumn<CourseTableModel, Integer> availableCol = new TableColumn<>("Available");
        availableCol.setCellValueFactory(new PropertyValueFactory<>("availableSeats"));
        availableCol.setPrefWidth(90);
        
        table.getColumns().addAll(idCol, nameCol, deptCol, creditsCol, semesterCol, enrollmentCol, availableCol);
        
        return table;
    }
    
    private void refreshTable() {
        var data = FXCollections.<CourseTableModel>observableArrayList();
        courseService.getAllCourses().forEach(course -> {
            data.add(new CourseTableModel(course));
        });
        courseTable.setItems(data);
    }
    
    private void filterTable(String searchText) {
        if (searchText == null || searchText.isEmpty()) {
            refreshTable();
            return;
        }
        
        var data = FXCollections.<CourseTableModel>observableArrayList();
        String search = searchText.toLowerCase();
        
        courseService.getAllCourses().forEach(course -> {
            if (course.getCourseId().toLowerCase().contains(search) ||
                course.getCourseName().toLowerCase().contains(search) ||
                course.getDepartment().toLowerCase().contains(search)) {
                data.add(new CourseTableModel(course));
            }
        });
        
        courseTable.setItems(data);
    }
    
    private void showAddCourseDialog() {
        Dialog<Course> dialog = new Dialog<>();
        dialog.setTitle("Add New Course");
        dialog.setHeaderText("Enter course information");
        
        ButtonType addButtonType = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);
        
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));
        
        TextField idField = new TextField();
        TextField nameField = new TextField();
        TextField deptField = new TextField();
        TextField creditsField = new TextField();
        TextField capacityField = new TextField();
        TextField semesterField = new TextField();
        
        grid.add(new Label("Course ID:"), 0, 0);
        grid.add(idField, 1, 0);
        grid.add(new Label("Course Name:"), 0, 1);
        grid.add(nameField, 1, 1);
        grid.add(new Label("Department:"), 0, 2);
        grid.add(deptField, 1, 2);
        grid.add(new Label("Credits:"), 0, 3);
        grid.add(creditsField, 1, 3);
        grid.add(new Label("Capacity:"), 0, 4);
        grid.add(capacityField, 1, 4);
        grid.add(new Label("Semester:"), 0, 5);
        grid.add(semesterField, 1, 5);
        
        dialog.getDialogPane().setContent(grid);
        
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButtonType) {
                try {
                    return new Course(
                        idField.getText().trim(),
                        nameField.getText().trim(),
                        deptField.getText().trim(),
                        Integer.parseInt(creditsField.getText().trim()),
                        Integer.parseInt(capacityField.getText().trim()),
                        semesterField.getText().trim()
                    );
                } catch (Exception e) {
                    showError("Invalid input. Please check all fields.");
                    return null;
                }
            }
            return null;
        });
        
        dialog.showAndWait().ifPresent(course -> {
            if (course != null) {
                courseService.addCourse(course);
                refreshTable();
                showInfo("Course added successfully!");
            }
        });
    }
    
    private void showCourseDetails() {
        CourseTableModel selected = courseTable.getSelectionModel().getSelectedItem();
        if (selected == null) return;
        
        Course course = courseService.getCourse(selected.getCourseId());
        
        Alert details = new Alert(Alert.AlertType.INFORMATION);
        details.setTitle("Course Details");
        details.setHeaderText(course.getCourseName());
        
        StringBuilder prereqs = new StringBuilder();
        if (course.getPrerequisites().isEmpty()) {
            prereqs.append("None");
        } else {
            course.getPrerequisites().forEach(p -> prereqs.append(p).append(", "));
            prereqs.setLength(prereqs.length() - 2);
        }
        
        String content = String.format(
            "Course ID: %s\n" +
            "Department: %s\n" +
            "Credits: %d\n" +
            "Semester: %s\n" +
            "Instructor: %s\n" +
            "Capacity: %d\n" +
            "Enrolled: %d\n" +
            "Available Seats: %d\n" +
            "Prerequisites: %s",
            course.getCourseId(),
            course.getDepartment(),
            course.getCredits(),
            course.getSemester(),
            course.getInstructor() != null ? course.getInstructor() : "TBA",
            course.getCapacity(),
            course.getEnrolledCount(),
            course.getAvailableSeats(),
            prereqs.toString()
        );
        
        details.setContentText(content);
        details.showAndWait();
    }
    
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    private void showInfo(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    public static class CourseTableModel {
        private final SimpleStringProperty courseId;
        private final SimpleStringProperty courseName;
        private final SimpleStringProperty department;
        private final SimpleIntegerProperty credits;
        private final SimpleStringProperty semester;
        private final SimpleStringProperty enrollment;
        private final SimpleIntegerProperty availableSeats;
        
        public CourseTableModel(Course course) {
            this.courseId = new SimpleStringProperty(course.getCourseId());
            this.courseName = new SimpleStringProperty(course.getCourseName());
            this.department = new SimpleStringProperty(course.getDepartment());
            this.credits = new SimpleIntegerProperty(course.getCredits());
            this.semester = new SimpleStringProperty(course.getSemester());
            this.enrollment = new SimpleStringProperty(course.getEnrolledCount() + "/" + course.getCapacity());
            this.availableSeats = new SimpleIntegerProperty(course.getAvailableSeats());
        }
        
        public String getCourseId() { return courseId.get(); }
        public String getCourseName() { return courseName.get(); }
        public String getDepartment() { return department.get(); }
        public int getCredits() { return credits.get(); }
        public String getSemester() { return semester.get(); }
        public String getEnrollment() { return enrollment.get(); }
        public int getAvailableSeats() { return availableSeats.get(); }
    }
}
