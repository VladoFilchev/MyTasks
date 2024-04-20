package com.example.tasksmanagementsystem.service;

import com.example.tasksmanagementsystem.DAL.DatabaseConnection;
import com.example.tasksmanagementsystem.enums.TaskStatus;
import com.example.tasksmanagementsystem.object.Task;
import com.example.tasksmanagementsystem.object.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
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

    public boolean  newTask(String taskName, String taskOverview, LocalDate taskStartDate, LocalDate taskEndDate, User user) throws SQLException {
        if (!validateTaskData(taskName, taskOverview, taskStartDate, taskEndDate)) {
            System.out.println("Invalid Task Data");
            return false;
        }
        Task newTask = new Task(1, taskName, TaskStatus.NOT_STARTED, taskOverview, taskStartDate, taskEndDate);

        try (Connection connection = DatabaseConnection.connect()) {
            String query = "INSERT INTO public.task (status,task_name,overview,start_date,end_date,user_id) VALUES (?,?,?,?,?,?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1,newTask.getStatus().toString());
            statement.setString(2,newTask.getTaskName());
            statement.setString(3,newTask.getOverview());
            statement.setDate(4, Date.valueOf(newTask.getStartDate()));
            statement.setDate(5, Date.valueOf(newTask.getEndDate()));
            statement.setInt(6,user.getId());

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Task Created Successfully");
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Error creating task: " + e.getMessage());
        }

        return false;


        }

    private boolean validateTaskData(String taskName, String taskOverview, LocalDate taskStartDate, LocalDate taskEndDate) {
        // Check if any field is empty
        if (taskName.isEmpty() || taskOverview.isEmpty() || taskStartDate==null || taskEndDate==null) {
            System.out.println("All fields are required.");
            return false;
        }
        return true;

    }
}
