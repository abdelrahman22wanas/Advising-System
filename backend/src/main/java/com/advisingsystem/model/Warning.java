package com.advisingsystem.model;

public class Warning {
    private String warningId;
    private String studentId;
    private String type;
    private String message;
    private String severity;

    public Warning(String warningId, String studentId, String type, String message, String severity) {
        this.warningId = warningId;
        this.studentId = studentId;
        this.type = type;
        this.message = message;
        this.severity = severity;
    }

    public String getWarningId() { return warningId; }
    public String getStudentId() { return studentId; }
    public String getType() { return type; }
    public String getMessage() { return message; }
    public String getSeverity() { return severity; }
}
