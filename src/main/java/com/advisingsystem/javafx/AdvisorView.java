package com.advisingsystem.javafx;

import com.advisingsystem.model.*;
import com.advisingsystem.service.*;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;

public class AdvisorView extends VBox {
    
    private AdvisorService advisorService;
    private StudentService studentService;
    private TableView<AdvisorTableModel> advisorTable;
    
    public AdvisorView(AdvisorService advisorService, StudentService studentService) {
        this.advisorService = advisorService;
        this.studentService = studentService;
        initializeUI();
        refreshTable();
    }
    
    private void initializeUI() {
        setSpacing(15);
        setPadding(new Insets(20));
        
        HBox toolbar = createToolbar();
        advisorTable = createAdvisorTable();
        VBox.setVgrow(advisorTable, Priority.ALWAYS);
        
        getChildren().addAll(toolbar, advisorTable);
    }
    
    private HBox createToolbar() {
        HBox toolbar = new HBox(10);
        
        Button addBtn = new Button("➕ Add Advisor");
        addBtn.getStyleClass().add("primary-button");
        addBtn.setOnAction(e -> showAddAdvisorDialog());
        
        Button assignBtn = new Button("👥 Assign Student");
        assignBtn.setDisable(true);
        assignBtn.setOnAction(e -> showAssignStudentDialog());
        
        advisorTable.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            assignBtn.setDisable(newVal == null);
        });
        
        Button refreshBtn = new Button("🔄 Refresh");
        refreshBtn.setOnAction(e -> refreshTable());
        
        toolbar.getChildren().addAll(addBtn, assignBtn, refreshBtn);
        
        return toolbar;
    }
    
    private TableView<AdvisorTableModel> createAdvisorTable() {
        TableView<AdvisorTableModel> table = new TableView<>();
        table.getStyleClass().add("data-table");
        
        TableColumn<AdvisorTableModel, String> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("advisorId"));
        
        TableColumn<AdvisorTableModel, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        nameCol.setPrefWidth(180);
        
        TableColumn<AdvisorTableModel, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        emailCol.setPrefWidth(200);
        
        TableColumn<AdvisorTableModel, String> officeCol = new TableColumn<>("Office");
        officeCol.setCellValueFactory(new PropertyValueFactory<>("office"));
        
        TableColumn<AdvisorTableModel, String> studentsCol = new TableColumn<>("Students");
        studentsCol.setCellValueFactory(new PropertyValueFactory<>("studentCount"));
        
        table.getColumns().addAll(idCol, nameCol, emailCol, officeCol, studentsCol);
        
        return table;
    }
    
    private void refreshTable() {
        var data = FXCollections.<AdvisorTableModel>observableArrayList();
        advisorService.getAllAdvisors().forEach(advisor -> {
            data.add(new AdvisorTableModel(advisor));
        });
        advisorTable.setItems(data);
    }
    
    private void showAddAdvisorDialog() {
        Dialog<Advisor> dialog = new Dialog<>();
        dialog.setTitle("Add New Advisor");
        
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
        TextField officeField = new TextField();
        
        grid.add(new Label("Advisor ID:"), 0, 0);
        grid.add(idField, 1, 0);
        grid.add(new Label("First Name:"), 0, 1);
        grid.add(firstNameField, 1, 1);
        grid.add(new Label("Last Name:"), 0, 2);
        grid.add(lastNameField, 1, 2);
        grid.add(new Label("Email:"), 0, 3);
        grid.add(emailField, 1, 3);
        grid.add(new Label("Office:"), 0, 4);
        grid.add(officeField, 1, 4);
        
        dialog.getDialogPane().setContent(grid);
        
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButtonType) {
                return new Advisor(
                    idField.getText().trim(),
                    firstNameField.getText().trim(),
                    lastNameField.getText().trim(),
                    emailField.getText().trim(),
                    officeField.getText().trim()
                );
            }
            return null;
        });
        
        dialog.showAndWait().ifPresent(advisor -> {
            advisorService.addAdvisor(advisor);
            refreshTable();
            showInfo("Advisor added successfully!");
        });
    }
    
    private void showAssignStudentDialog() {
        AdvisorTableModel selected = advisorTable.getSelectionModel().getSelectedItem();
        if (selected == null) return;
        
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Assign Student to Advisor");
        dialog.setHeaderText("Advisor: " + selected.getFullName());
        
        ButtonType assignButtonType = new ButtonType("Assign", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(assignButtonType, ButtonType.CANCEL);
        
        VBox content = new VBox(10);
        content.setPadding(new Insets(20));
        
        ComboBox<String> studentCombo = new ComboBox<>();
        studentService.getAllStudents().forEach(student -> {
            studentCombo.getItems().add(student.getStudentId() + " - " + student.getFullName());
        });
        
        content.getChildren().addAll(new Label("Select Student:"), studentCombo);
        dialog.getDialogPane().setContent(content);
        
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == assignButtonType && studentCombo.getValue() != null) {
                return studentCombo.getValue().split(" - ")[0];
            }
            return null;
        });
        
        dialog.showAndWait().ifPresent(studentId -> {
            if (advisorService.assignStudentToAdvisor(studentId, selected.getAdvisorId())) {
                refreshTable();
                showInfo("Student assigned successfully!");
            } else {
                showError("Failed to assign student. Advisor may be at capacity.");
            }
        });
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
    
    public static class AdvisorTableModel {
        private final SimpleStringProperty advisorId;
        private final SimpleStringProperty fullName;
        private final SimpleStringProperty email;
        private final SimpleStringProperty office;
        private final SimpleStringProperty studentCount;
        
        public AdvisorTableModel(Advisor advisor) {
            this.advisorId = new SimpleStringProperty(advisor.getAdvisorId());
            this.fullName = new SimpleStringProperty(advisor.getFullName());
            this.email = new SimpleStringProperty(advisor.getEmail());
            this.office = new SimpleStringProperty(advisor.getOffice());
            this.studentCount = new SimpleStringProperty(
                advisor.getAssignedStudentIds().size() + "/" + advisor.getMaxStudents()
            );
        }
        
        public String getAdvisorId() { return advisorId.get(); }
        public String getFullName() { return fullName.get(); }
        public String getEmail() { return email.get(); }
        public String getOffice() { return office.get(); }
        public String getStudentCount() { return studentCount.get(); }
    }
}
