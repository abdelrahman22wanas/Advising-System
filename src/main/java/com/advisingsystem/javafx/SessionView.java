package com.advisingsystem.javafx;

import com.advisingsystem.model.*;
import com.advisingsystem.service.*;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import java.time.LocalDateTime;

public class SessionView extends VBox {
    
    private AdvicingSessionService sessionService;
    private StudentService studentService;
    private AdvisorService advisorService;
    private TableView<SessionTableModel> sessionTable;
    
    public SessionView(AdvicingSessionService sessionService, StudentService studentService,
                      AdvisorService advisorService) {
        this.sessionService = sessionService;
        this.studentService = studentService;
        this.advisorService = advisorService;
        initializeUI();
        refreshTable();
    }
    
    private void initializeUI() {
        setSpacing(15);
        setPadding(new Insets(20));
        
        HBox toolbar = createToolbar();
        sessionTable = createSessionTable();
        VBox.setVgrow(sessionTable, Priority.ALWAYS);
        
        getChildren().addAll(toolbar, sessionTable);
    }
    
    private HBox createToolbar() {
        HBox toolbar = new HBox(10);
        
        Button addBtn = new Button("➕ Create Session");
        addBtn.getStyleClass().add("primary-button");
        addBtn.setOnAction(e -> showCreateSessionDialog());
        
        Button completeBtn = new Button("✅ Mark Complete");
        completeBtn.setDisable(true);
        completeBtn.setOnAction(e -> markComplete());
        
        sessionTable.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            completeBtn.setDisable(newVal == null);
        });
        
        Button refreshBtn = new Button("🔄 Refresh");
        refreshBtn.setOnAction(e -> refreshTable());
        
        toolbar.getChildren().addAll(addBtn, completeBtn, refreshBtn);
        
        return toolbar;
    }
    
    private TableView<SessionTableModel> createSessionTable() {
        TableView<SessionTableModel> table = new TableView<>();
        table.getStyleClass().add("data-table");
        
        TableColumn<SessionTableModel, String> idCol = new TableColumn<>("Session ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("sessionId"));
        
        TableColumn<SessionTableModel, String> studentCol = new TableColumn<>("Student");
        studentCol.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        studentCol.setPrefWidth(150);
        
        TableColumn<SessionTableModel, String> advisorCol = new TableColumn<>("Advisor");
        advisorCol.setCellValueFactory(new PropertyValueFactory<>("advisorName"));
        advisorCol.setPrefWidth(150);
        
        TableColumn<SessionTableModel, String> topicCol = new TableColumn<>("Topic");
        topicCol.setCellValueFactory(new PropertyValueFactory<>("topic"));
        topicCol.setPrefWidth(200);
        
        TableColumn<SessionTableModel, String> dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        dateCol.setPrefWidth(150);
        
        TableColumn<SessionTableModel, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        
        table.getColumns().addAll(idCol, studentCol, advisorCol, topicCol, dateCol, statusCol);
        
        return table;
    }
    
    private void refreshTable() {
        var data = FXCollections.<SessionTableModel>observableArrayList();
        sessionService.getAllSessions().forEach(session -> {
            Student student = studentService.getStudent(session.getStudentId());
            Advisor advisor = advisorService.getAdvisor(session.getAdvisorId());
            data.add(new SessionTableModel(session, 
                student != null ? student.getFullName() : "Unknown",
                advisor != null ? advisor.getFullName() : "Unknown"));
        });
        sessionTable.setItems(data);
    }
    
    private void showCreateSessionDialog() {
        Dialog<AdvicingSession> dialog = new Dialog<>();
        dialog.setTitle("Create Advising Session");
        
        ButtonType createButtonType = new ButtonType("Create", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(createButtonType, ButtonType.CANCEL);
        
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));
        
        ComboBox<String> studentCombo = new ComboBox<>();
        ComboBox<String> advisorCombo = new ComboBox<>();
        TextField topicField = new TextField();
        
        studentService.getAllStudents().forEach(s ->
            studentCombo.getItems().add(s.getStudentId() + " - " + s.getFullName())
        );
        advisorService.getAllAdvisors().forEach(a ->
            advisorCombo.getItems().add(a.getAdvisorId() + " - " + a.getFullName())
        );
        
        grid.add(new Label("Student:"), 0, 0);
        grid.add(studentCombo, 1, 0);
        grid.add(new Label("Advisor:"), 0, 1);
        grid.add(advisorCombo, 1, 1);
        grid.add(new Label("Topic:"), 0, 2);
        grid.add(topicField, 1, 2);
        
        dialog.getDialogPane().setContent(grid);
        
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == createButtonType && studentCombo.getValue() != null && 
                advisorCombo.getValue() != null && !topicField.getText().isEmpty()) {
                String studentId = studentCombo.getValue().split(" - ")[0];
                String advisorId = advisorCombo.getValue().split(" - ")[0];
                return sessionService.createSession(studentId, advisorId, 
                    LocalDateTime.now(), topicField.getText());
            }
            return null;
        });
        
        dialog.showAndWait().ifPresent(session -> {
            refreshTable();
            showInfo("Session created successfully!");
        });
    }
    
    private void markComplete() {
        SessionTableModel selected = sessionTable.getSelectionModel().getSelectedItem();
        if (selected == null) return;
        
        if (sessionService.completeSession(selected.getSessionId())) {
            refreshTable();
            showInfo("Session marked as completed!");
        }
    }
    
    private void showInfo(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    public static class SessionTableModel {
        private final SimpleStringProperty sessionId;
        private final SimpleStringProperty studentName;
        private final SimpleStringProperty advisorName;
        private final SimpleStringProperty topic;
        private final SimpleStringProperty date;
        private final SimpleStringProperty status;
        
        public SessionTableModel(AdvicingSession session, String studentName, String advisorName) {
            this.sessionId = new SimpleStringProperty(session.getSessionId());
            this.studentName = new SimpleStringProperty(studentName);
            this.advisorName = new SimpleStringProperty(advisorName);
            this.topic = new SimpleStringProperty(session.getTopic());
            this.date = new SimpleStringProperty(session.getFormattedDate());
            this.status = new SimpleStringProperty(session.getStatus());
        }
        
        public String getSessionId() { return sessionId.get(); }
        public String getStudentName() { return studentName.get(); }
        public String getAdvisorName() { return advisorName.get(); }
        public String getTopic() { return topic.get(); }
        public String getDate() { return date.get(); }
        public String getStatus() { return status.get(); }
    }
}
