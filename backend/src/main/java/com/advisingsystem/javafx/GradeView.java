package com.advisingsystem.javafx;

import com.advisingsystem.model.*;
import com.advisingsystem.service.*;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;

public class GradeView extends VBox {
    
    private GradeService gradeService;
    private StudentService studentService;
    private CourseService courseService;
    private TableView<GradeTableModel> gradeTable;
    
    public GradeView(GradeService gradeService, StudentService studentService,
                    CourseService courseService) {
        this.gradeService = gradeService;
        this.studentService = studentService;
        this.courseService = courseService;
        initializeUI();
        refreshTable();
    }
    
    private void initializeUI() {
        setSpacing(15);
        setPadding(new Insets(20));
        
        HBox toolbar = createToolbar();
        gradeTable = createGradeTable();
        VBox.setVgrow(gradeTable, Priority.ALWAYS);
        
        getChildren().addAll(toolbar, gradeTable);
    }
    
    private HBox createToolbar() {
        HBox toolbar = new HBox(10);
        
        Button addBtn = new Button("➕ Record Grade");
        addBtn.getStyleClass().add("primary-button");
        addBtn.setOnAction(e -> showRecordGradeDialog());
        
        Button gpaBtn = new Button("📊 Calculate GPA");
        gpaBtn.setOnAction(e -> showCalculateGPADialog());
        
        Button refreshBtn = new Button("🔄 Refresh");
        refreshBtn.setOnAction(e -> refreshTable());
        
        toolbar.getChildren().addAll(addBtn, gpaBtn, refreshBtn);
        
        return toolbar;
    }
    
    private TableView<GradeTableModel> createGradeTable() {
        TableView<GradeTableModel> table = new TableView<>();
        table.getStyleClass().add("data-table");
        
        TableColumn<GradeTableModel, String> studentCol = new TableColumn<>("Student");
        studentCol.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        studentCol.setPrefWidth(150);
        
        TableColumn<GradeTableModel, String> courseCol = new TableColumn<>("Course");
        courseCol.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        courseCol.setPrefWidth(200);
        
        TableColumn<GradeTableModel, String> gradeCol = new TableColumn<>("Letter Grade");
        gradeCol.setCellValueFactory(new PropertyValueFactory<>("letterGrade"));
        
        TableColumn<GradeTableModel, String> gpaCol = new TableColumn<>("GPA Points");
        gpaCol.setCellValueFactory(new PropertyValueFactory<>("gpa"));
        
        TableColumn<GradeTableModel, String> semesterCol = new TableColumn<>("Semester");
        semesterCol.setCellValueFactory(new PropertyValueFactory<>("semester"));
        semesterCol.setPrefWidth(120);
        
        table.getColumns().addAll(studentCol, courseCol, gradeCol, gpaCol, semesterCol);
        
        return table;
    }
    
    private void refreshTable() {
        var data = FXCollections.<GradeTableModel>observableArrayList();
        gradeService.getAllGrades().forEach(grade -> {
            Student student = studentService.getStudent(grade.getStudentId());
            Course course = courseService.getCourse(grade.getCourseId());
            data.add(new GradeTableModel(grade,
                student != null ? student.getFullName() : "Unknown",
                course != null ? course.getCourseName() : "Unknown"));
        });
        gradeTable.setItems(data);
    }
    
    private void showRecordGradeDialog() {
        Dialog<Grade> dialog = new Dialog<>();
        dialog.setTitle("Record Grade");
        
        ButtonType recordButtonType = new ButtonType("Record", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(recordButtonType, ButtonType.CANCEL);
        
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));
        
        ComboBox<String> studentCombo = new ComboBox<>();
        ComboBox<String> courseCombo = new ComboBox<>();
        ComboBox<String> gradeCombo = new ComboBox<>();
        TextField semesterField = new TextField();
        
        studentService.getAllStudents().forEach(s ->
            studentCombo.getItems().add(s.getStudentId() + " - " + s.getFullName())
        );
        courseService.getAllCourses().forEach(c ->
            courseCombo.getItems().add(c.getCourseId() + " - " + c.getCourseName())
        );
        gradeCombo.getItems().addAll("A", "A-", "B+", "B", "B-", "C+", "C", "C-", "D+", "D", "F");
        
        grid.add(new Label("Student:"), 0, 0);
        grid.add(studentCombo, 1, 0);
        grid.add(new Label("Course:"), 0, 1);
        grid.add(courseCombo, 1, 1);
        grid.add(new Label("Letter Grade:"), 0, 2);
        grid.add(gradeCombo, 1, 2);
        grid.add(new Label("Semester:"), 0, 3);
        grid.add(semesterField, 1, 3);
        
        dialog.getDialogPane().setContent(grid);
        
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == recordButtonType && studentCombo.getValue() != null &&
                courseCombo.getValue() != null && gradeCombo.getValue() != null) {
                String studentId = studentCombo.getValue().split(" - ")[0];
                String courseId = courseCombo.getValue().split(" - ")[0];
                String letterGrade = gradeCombo.getValue();
                double gpa = Grade.getGPAForGrade(letterGrade);
                return new Grade(studentId, courseId, letterGrade, gpa, semesterField.getText());
            }
            return null;
        });
        
        dialog.showAndWait().ifPresent(grade -> {
            gradeService.recordGrade(grade);
            refreshTable();
            showInfo("Grade recorded successfully!");
        });
    }
    
    private void showCalculateGPADialog() {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Calculate Student GPA");
        
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        
        VBox content = new VBox(10);
        content.setPadding(new Insets(20));
        
        ComboBox<String> studentCombo = new ComboBox<>();
        studentService.getAllStudents().forEach(s ->
            studentCombo.getItems().add(s.getStudentId() + " - " + s.getFullName())
        );
        
        Label resultLabel = new Label();
        resultLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        
        Button calculateBtn = new Button("Calculate");
        calculateBtn.setOnAction(e -> {
            if (studentCombo.getValue() != null) {
                String studentId = studentCombo.getValue().split(" - ")[0];
                double gpa = gradeService.calculateStudentGPA(studentId);
                resultLabel.setText(String.format("GPA: %.2f", gpa));
            }
        });
        
        content.getChildren().addAll(
            new Label("Select Student:"),
            studentCombo,
            calculateBtn,
            resultLabel
        );
        
        dialog.getDialogPane().setContent(content);
        dialog.showAndWait();
    }
    
    private void showInfo(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    public static class GradeTableModel {
        private final SimpleStringProperty studentName;
        private final SimpleStringProperty courseName;
        private final SimpleStringProperty letterGrade;
        private final SimpleStringProperty gpa;
        private final SimpleStringProperty semester;
        
        public GradeTableModel(Grade grade, String studentName, String courseName) {
            this.studentName = new SimpleStringProperty(studentName);
            this.courseName = new SimpleStringProperty(courseName);
            this.letterGrade = new SimpleStringProperty(grade.getLetterGrade());
            this.gpa = new SimpleStringProperty(String.format("%.2f", grade.getGpa()));
            this.semester = new SimpleStringProperty(grade.getSemester());
        }
        
        public String getStudentName() { return studentName.get(); }
        public String getCourseName() { return courseName.get(); }
        public String getLetterGrade() { return letterGrade.get(); }
        public String getGpa() { return gpa.get(); }
        public String getSemester() { return semester.get(); }
    }
}
