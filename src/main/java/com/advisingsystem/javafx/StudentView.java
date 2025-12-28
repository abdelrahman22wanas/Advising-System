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
 * Student management view with table and CRUD operations
 */
public class StudentView extends VBox {
    
    private StudentService studentService;
    private AdvisorService advisorService;
    private EnrollmentService enrollmentService;
    
    private TableView<StudentTableModel> studentTable;
    private TextField searchField;
    
    public StudentView(StudentService studentService, AdvisorService advisorService,
                       EnrollmentService enrollmentService) {
        this.studentService = studentService;
        this.advisorService = advisorService;
        this.enrollmentService = enrollmentService;
        
        initializeUI();
        refreshTable();
    }
    
    private void initializeUI() {
        setSpacing(15);
        setPadding(new Insets(20));
        
        // Top toolbar
        HBox toolbar = createToolbar();
        
        // Table
        studentTable = createStudentTable();
        VBox.setVgrow(studentTable, Priority.ALWAYS);
        
        getChildren().addAll(toolbar, studentTable);
    }
    
    private HBox createToolbar() {
        HBox toolbar = new HBox(10);
        toolbar.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
        
        Button addBtn = new Button("➕ Add Student");
        addBtn.getStyleClass().add("primary-button");
        addBtn.setOnAction(e -> showAddStudentDialog());
        
        Button editBtn = new Button("✏️ Edit");
        editBtn.setOnAction(e -> showEditStudentDialog());
        editBtn.setDisable(true);
        
        Button deleteBtn = new Button("🗑️ Delete");
        deleteBtn.setOnAction(e -> deleteStudent());
        deleteBtn.setDisable(true);
        
        Button viewBtn = new Button("👁️ View Details");
        viewBtn.setOnAction(e -> showStudentDetails());
        viewBtn.setDisable(true);
        
        studentTable.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            boolean hasSelection = newVal != null;
            editBtn.setDisable(!hasSelection);
            deleteBtn.setDisable(!hasSelection);
            viewBtn.setDisable(!hasSelection);
        });
        
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        searchField = new TextField();
        searchField.setPromptText("🔍 Search by name or major...");
        searchField.setPrefWidth(250);
        searchField.textProperty().addListener((obs, oldVal, newVal) -> filterTable(newVal));
        
        Button refreshBtn = new Button("🔄 Refresh");
        refreshBtn.setOnAction(e -> refreshTable());
        
        toolbar.getChildren().addAll(addBtn, editBtn, deleteBtn, viewBtn, spacer, searchField, refreshBtn);
        
        return toolbar;
    }
    
    private TableView<StudentTableModel> createStudentTable() {
        TableView<StudentTableModel> table = new TableView<>();
        table.getStyleClass().add("data-table");
        
        TableColumn<StudentTableModel, String> idCol = new TableColumn<>("Student ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        idCol.setPrefWidth(100);
        
        TableColumn<StudentTableModel, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        nameCol.setPrefWidth(180);
        
        TableColumn<StudentTableModel, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        emailCol.setPrefWidth(200);
        
        TableColumn<StudentTableModel, String> majorCol = new TableColumn<>("Major");
        majorCol.setCellValueFactory(new PropertyValueFactory<>("major"));
        majorCol.setPrefWidth(150);
        
        TableColumn<StudentTableModel, String> gpaCol = new TableColumn<>("GPA");
        gpaCol.setCellValueFactory(new PropertyValueFactory<>("gpa"));
        gpaCol.setPrefWidth(80);
        
        TableColumn<StudentTableModel, Integer> yearCol = new TableColumn<>("Grad Year");
        yearCol.setCellValueFactory(new PropertyValueFactory<>("graduationYear"));
        yearCol.setPrefWidth(100);
        
        TableColumn<StudentTableModel, Integer> enrolledCol = new TableColumn<>("Enrolled");
        enrolledCol.setCellValueFactory(new PropertyValueFactory<>("enrolledCourses"));
        enrolledCol.setPrefWidth(80);
        
        table.getColumns().addAll(idCol, nameCol, emailCol, majorCol, gpaCol, yearCol, enrolledCol);
        
        return table;
    }
    
    private void refreshTable() {
        var data = FXCollections.<StudentTableModel>observableArrayList();
        studentService.getAllStudents().forEach(student -> {
            data.add(new StudentTableModel(student));
        });
        studentTable.setItems(data);
    }
    
    private void filterTable(String searchText) {
        if (searchText == null || searchText.isEmpty()) {
            refreshTable();
            return;
        }
        
        var data = FXCollections.<StudentTableModel>observableArrayList();
        String search = searchText.toLowerCase();
        
        studentService.getAllStudents().forEach(student -> {
            if (student.getFullName().toLowerCase().contains(search) ||
                student.getMajor().toLowerCase().contains(search) ||
                student.getEmail().toLowerCase().contains(search)) {
                data.add(new StudentTableModel(student));
            }
        });
        
        studentTable.setItems(data);
    }
    
    private void showAddStudentDialog() {
        Dialog<Student> dialog = new Dialog<>();
        dialog.setTitle("Add New Student");
        dialog.setHeaderText("Enter student information");
        
        ButtonType addButtonType = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);
        
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));
        
        TextField idField = new TextField();
        TextField firstNameField = new TextField();
        TextField lastNameField = new TextField();
        TextField emailField = new TextField();
        TextField majorField = new TextField();
        TextField yearField = new TextField();
        
        grid.add(new Label("Student ID:"), 0, 0);
        grid.add(idField, 1, 0);
        grid.add(new Label("First Name:"), 0, 1);
        grid.add(firstNameField, 1, 1);
        grid.add(new Label("Last Name:"), 0, 2);
        grid.add(lastNameField, 1, 2);
        grid.add(new Label("Email:"), 0, 3);
        grid.add(emailField, 1, 3);
        grid.add(new Label("Major:"), 0, 4);
        grid.add(majorField, 1, 4);
        grid.add(new Label("Graduation Year:"), 0, 5);
        grid.add(yearField, 1, 5);
        
        dialog.getDialogPane().setContent(grid);
        
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButtonType) {
                try {
                    return new Student(
                        idField.getText().trim(),
                        firstNameField.getText().trim(),
                        lastNameField.getText().trim(),
                        emailField.getText().trim(),
                        majorField.getText().trim(),
                        Integer.parseInt(yearField.getText().trim())
                    );
                } catch (Exception e) {
                    showError("Invalid input. Please check all fields.");
                    return null;
                }
            }
            return null;
        });
        
        dialog.showAndWait().ifPresent(student -> {
            if (student != null) {
                studentService.addStudent(student);
                refreshTable();
                showInfo("Student added successfully!");
            }
        });
    }
    
    private void showEditStudentDialog() {
        StudentTableModel selected = studentTable.getSelectionModel().getSelectedItem();
        if (selected == null) return;
        
        Student student = studentService.getStudent(selected.getStudentId());
        
        Dialog<Boolean> dialog = new Dialog<>();
        dialog.setTitle("Edit Student");
        dialog.setHeaderText("Update student GPA");
        
        ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);
        
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));
        
        TextField gpaField = new TextField(String.format("%.2f", student.getGpa()));
        
        grid.add(new Label("Student: " + student.getFullName()), 0, 0, 2, 1);
        grid.add(new Label("New GPA (0.0-4.0):"), 0, 1);
        grid.add(gpaField, 1, 1);
        
        dialog.getDialogPane().setContent(grid);
        
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                try {
                    double gpa = Double.parseDouble(gpaField.getText().trim());
                    if (gpa >= 0.0 && gpa <= 4.0) {
                        studentService.updateStudentGPA(student.getStudentId(), gpa);
                        return true;
                    } else {
                        showError("GPA must be between 0.0 and 4.0");
                    }
                } catch (Exception e) {
                    showError("Invalid GPA value");
                }
            }
            return false;
        });
        
        dialog.showAndWait().ifPresent(saved -> {
            if (saved) {
                refreshTable();
                showInfo("Student updated successfully!");
            }
        });
    }
    
    private void deleteStudent() {
        StudentTableModel selected = studentTable.getSelectionModel().getSelectedItem();
        if (selected == null) return;
        
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirm Delete");
        confirm.setHeaderText("Delete student: " + selected.getFullName());
        confirm.setContentText("Are you sure you want to delete this student?");
        
        confirm.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                studentService.removeStudent(selected.getStudentId());
                refreshTable();
                showInfo("Student deleted successfully!");
            }
        });
    }
    
    private void showStudentDetails() {
        StudentTableModel selected = studentTable.getSelectionModel().getSelectedItem();
        if (selected == null) return;
        
        Student student = studentService.getStudent(selected.getStudentId());
        Advisor advisor = advisorService.getStudentAdvisor(student.getStudentId());
        
        Alert details = new Alert(Alert.AlertType.INFORMATION);
        details.setTitle("Student Details");
        details.setHeaderText(student.getFullName());
        
        String content = String.format(
            "Student ID: %s\n" +
            "Email: %s\n" +
            "Major: %s\n" +
            "GPA: %.2f\n" +
            "Graduation Year: %d\n" +
            "Enrolled Courses: %d\n" +
            "Completed Courses: %d\n" +
            "Advisor: %s",
            student.getStudentId(),
            student.getEmail(),
            student.getMajor(),
            student.getGpa(),
            student.getGraduationYear(),
            student.getEnrolledCourses().size(),
            student.getCompletedCourses().size(),
            advisor != null ? advisor.getFullName() : "Not assigned"
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
    
    // Table model class
    public static class StudentTableModel {
        private final SimpleStringProperty studentId;
        private final SimpleStringProperty fullName;
        private final SimpleStringProperty email;
        private final SimpleStringProperty major;
        private final SimpleStringProperty gpa;
        private final SimpleIntegerProperty graduationYear;
        private final SimpleIntegerProperty enrolledCourses;
        
        public StudentTableModel(Student student) {
            this.studentId = new SimpleStringProperty(student.getStudentId());
            this.fullName = new SimpleStringProperty(student.getFullName());
            this.email = new SimpleStringProperty(student.getEmail());
            this.major = new SimpleStringProperty(student.getMajor());
            this.gpa = new SimpleStringProperty(String.format("%.2f", student.getGpa()));
            this.graduationYear = new SimpleIntegerProperty(student.getGraduationYear());
            this.enrolledCourses = new SimpleIntegerProperty(student.getEnrolledCourses().size());
        }
        
        public String getStudentId() { return studentId.get(); }
        public String getFullName() { return fullName.get(); }
        public String getEmail() { return email.get(); }
        public String getMajor() { return major.get(); }
        public String getGpa() { return gpa.get(); }
        public int getGraduationYear() { return graduationYear.get(); }
        public int getEnrolledCourses() { return enrolledCourses.get(); }
    }
}
