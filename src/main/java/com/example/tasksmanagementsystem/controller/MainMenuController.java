package com.example.tasksmanagementsystem.controller;

import com.example.tasksmanagementsystem.enums.TaskStatus;
import com.example.tasksmanagementsystem.object.Task;
import com.example.tasksmanagementsystem.object.User;
import com.example.tasksmanagementsystem.service.LoginServices;
import com.example.tasksmanagementsystem.service.TaskServices;
import com.example.tasksmanagementsystem.stage.ProgramScreens;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import static com.example.tasksmanagementsystem.service.TaskServices.loadUserTasks;

public class MainMenuController {

    @FXML
    private ContextMenu contextMenu;

    @FXML
    private MenuItem deleteItem;


    @FXML
    private Button logOutButton;

    @FXML
    private MenuButton menuButton;

    @FXML
    private Button newTaskButton;

    @FXML
    private MenuItem nextWeekTasks;

    @FXML
    private Pane root;

    @FXML
    private TableColumn<Task, Integer> taskID;

    @FXML
    private TableColumn<Task, String> taskName;

    @FXML
    private TableColumn<Task, String> taskOverview;

    @FXML
    private TableColumn<Task, LocalDate> taskStartDate;

    @FXML
    private TableColumn<Task, TaskStatus> taskStatus;

    @FXML
    private TableView<Task> taskTable;

    @FXML
    private TableColumn<Task, LocalDate> taskEndDate;

    @FXML
    private MenuItem thisWeekTasks;

    @FXML
    private MenuItem todayTasks;

    @FXML
    private MenuItem tomorrowTasks;

    @FXML
    private Button refreshButton;

    private User currentUser;

    private final ProgramScreens programScreens = new ProgramScreens();


    @FXML
    void deleteSelectedItem(ActionEvent event) {
        Task selectedTask = taskTable.getSelectionModel().getSelectedItem();

        if (selectedTask != null) {
            try {
                // Delete the selected task
                TaskServices.deleteTask(selectedTask);

                // Remove the selected item from the table
                taskTable.getItems().remove(selectedTask);
            } catch (SQLException e) {
                e.printStackTrace(); // Handle the exception
            }
        }
    }


    @FXML
    void refreshTable(ActionEvent event) {
        if (currentUser != null) {
            ObservableList<Task> taskList = TaskServices.loadUserTasks(currentUser);
            loadTables(taskList);
        }
    }


    @FXML
    void logOut(ActionEvent event) {
        Stage stage = (Stage) root.getScene().getWindow();
        stage.close();

        //Open the login stage
        programScreens.showLogin();
    }

    @FXML
    void openTaskCreator(ActionEvent event) {
        programScreens.showNewTask(currentUser);
    }

    @FXML
    void showNextWeekTasks(ActionEvent event) {
        if (currentUser != null) {
            ObservableList<Task> taskList = TaskServices.getTasksForNextWeekList(currentUser);
            loadTables(taskList);
        }
    }

    @FXML
    void showTodayTasks(ActionEvent event) {
        if (currentUser != null) {
            ObservableList<Task> taskList = TaskServices.getTasksForTodayList(currentUser);
            loadTables(taskList);
        }
    }

    @FXML
    void showTommorrowTasks(ActionEvent event) {
        if (currentUser != null) {
            ObservableList<Task> taskList = TaskServices.getTasksForTomorrowList(currentUser);
            loadTables(taskList);
        }
    }

    @FXML
    void showWeekTasks(ActionEvent event) {
        if (currentUser != null) {
            ObservableList<Task> taskList = TaskServices.getTasksForThisWeekList(currentUser);
            loadTables(taskList);
        }
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
        refreshTable(null);
    }

    public void showTaskAlert(User currentUser) {
        if (currentUser != null) {
            this.currentUser = currentUser;
            List<Task> tasks = TaskServices.getTasksForToday(currentUser);
            for (Task task : tasks) {
                if (task.getStatus().equals(TaskStatus.NOT_STARTED)) {
                    showAlert(Alert.AlertType.INFORMATION, "Task Starter", task.getTaskName() + "\nIs starting today");
                    TaskServices.updateTaskStatus(task.getID(), TaskStatus.IN_PROGRESS);
                }
            }
            List<Task> endTasks = TaskServices.getTasksForEndDay(currentUser);
            for (Task task : endTasks) {
                if (task.getStatus().equals(TaskStatus.IN_PROGRESS)) {
                    showAlert(Alert.AlertType.WARNING, "Task Warning", task.getTaskName() + "\nIs ending soon");
                    if (!task.getEndDate().isBefore(LocalDate.now())) {
                        TaskServices.updateTaskStatus(task.getID(), TaskStatus.FINISHED);
                    }
                }
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

    private void loadTables(ObservableList<Task> taskList) {
        showTaskAlert(currentUser);

        // Populate the table columns
        taskID.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getID()));
        taskName.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getTaskName()));
        taskOverview.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getOverview()));
        taskStartDate.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getStartDate()));
        taskEndDate.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getEndDate()));
        taskStatus.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getStatus()));
        taskTable.setItems(taskList);
    }

}
