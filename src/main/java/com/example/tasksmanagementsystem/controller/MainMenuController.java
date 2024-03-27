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

import java.time.LocalDate;

import static com.example.tasksmanagementsystem.service.TaskServices.loadUserTasks;

public class MainMenuController {

    @FXML
    private ContextMenu contextMenu;

    @FXML
    private MenuItem deleteItem;

    @FXML
    private MenuItem editItem;

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

    private User currentUser ;

    private final ProgramScreens programScreens = new ProgramScreens();


    @FXML
    void deleteSelectedItem(ActionEvent event) {

    }

    @FXML
    void editSelectedItem(ActionEvent event) {

    }


    @FXML
    void refreshTable(ActionEvent event) {
        if (currentUser != null) {
            ObservableList<Task> taskList = TaskServices.loadUserTasks(currentUser);

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


    @FXML
    void logOut(ActionEvent event) {
        Stage stage = (Stage) root.getScene().getWindow();
        stage.close();

        //Open the login stage
        programScreens.showLogin();
    }

    @FXML
    void openTaskCreator(ActionEvent event) {

    }

    @FXML
    void showNextWeekTasks(ActionEvent event) {

    }

    @FXML
    void showTodayTasks(ActionEvent event) {

    }

    @FXML
    void showTommorrowTasks(ActionEvent event) {

    }

    @FXML
    void showWeekTasks(ActionEvent event) {

    }
    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
        refreshTable(null); 
    }

}
