package com.example.tasksmanagementsystem.controller;

import com.example.tasksmanagementsystem.object.User;
import com.example.tasksmanagementsystem.service.TaskServices;
import com.example.tasksmanagementsystem.stage.ProgramScreens;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.time.LocalDate;

public class NewTaskController {

    @FXML
    private Button addTaskButton;

    @FXML
    private DatePicker endDatePicker;

    @FXML
    private Pane root;

    @FXML
    private DatePicker startDatePicker;

    @FXML
    private TextField taskNameTextField;

    @FXML
    private TextArea textArea;

    private User currentUser;

    private final TaskServices taskServices = new TaskServices();
    private final ProgramScreens programScreens = new ProgramScreens();

    @FXML
    void addNewTask(ActionEvent event) throws SQLException {
        if (currentUser != null) {
            String taskName = taskNameTextField.getText();
            String taskOverview = textArea.getText();
            LocalDate taskStartDate = startDatePicker.getValue();
            LocalDate taskEndDate = endDatePicker.getValue();
            if (!taskEndDate.isBefore(taskStartDate)) {

                if (taskServices.newTask(taskName, taskOverview, taskStartDate, taskEndDate, currentUser)) {
                    showAlert(Alert.AlertType.INFORMATION, "Task Manager", "Task Added successfully.");
                    Stage stage = (Stage) taskNameTextField.getScene().getWindow();
                    stage.close();

                } else {
                    showAlert(Alert.AlertType.ERROR, "Task Error", "Failed to add task.");
                }

            } else if (taskEndDate.isBefore(LocalDate.now())) {
                showAlert(Alert.AlertType.ERROR, "Date Error", "End date is before today date!");
            } else {
                showAlert(Alert.AlertType.ERROR, "Date Error", "End date is before start date!");
            }
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
}
