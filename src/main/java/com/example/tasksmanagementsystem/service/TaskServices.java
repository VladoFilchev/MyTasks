package com.example.tasksmanagementsystem.service;

import com.example.tasksmanagementsystem.DAL.DatabaseConnection;
import com.example.tasksmanagementsystem.enums.TaskStatus;
import com.example.tasksmanagementsystem.object.Task;
import com.example.tasksmanagementsystem.object.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class TaskServices {

    public static ObservableList<Task> loadUserTasks(User user) {
        ObservableList<Task> taskList = FXCollections.observableArrayList();

        try (Connection connection = DatabaseConnection.connect()) {
            String query = "SELECT * FROM public.task WHERE user_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, user.getId());

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int taskId = resultSet.getInt("id");
                TaskStatus status = TaskStatus.valueOf(resultSet.getString("status"));
                String taskName = resultSet.getString("task_name");
                String overview = resultSet.getString("overview");
                LocalDate startDate = resultSet.getDate("start_date").toLocalDate();
                LocalDate endDate = resultSet.getDate("end_date").toLocalDate();

                Task task = new Task(taskId, taskName, status, overview, startDate, endDate);
                taskList.add(task);
            }
        } catch (SQLException e) {
            System.out.println("Error loading user tasks: " + e.getMessage());
        }

        return taskList;
    }
}
