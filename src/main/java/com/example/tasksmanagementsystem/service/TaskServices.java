package com.example.tasksmanagementsystem.service;

import com.example.tasksmanagementsystem.DAL.DatabaseConnection;
import com.example.tasksmanagementsystem.enums.TaskStatus;
import com.example.tasksmanagementsystem.object.Task;
import com.example.tasksmanagementsystem.object.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


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

    public boolean newTask(String taskName, String taskOverview, LocalDate taskStartDate, LocalDate taskEndDate, User user) throws SQLException {
        if (!validateTaskData(taskName, taskOverview, taskStartDate, taskEndDate)) {
            System.out.println("Invalid Task Data");
            return false;
        }
        Task newTask = new Task(1, taskName, TaskStatus.NOT_STARTED, taskOverview, taskStartDate, taskEndDate);

        try (Connection connection = DatabaseConnection.connect()) {
            String query = "INSERT INTO public.task (status,task_name,overview,start_date,end_date,user_id) VALUES (?,?,?,?,?,?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, newTask.getStatus().toString());
            statement.setString(2, newTask.getTaskName());
            statement.setString(3, newTask.getOverview());
            statement.setDate(4, Date.valueOf(newTask.getStartDate()));
            statement.setDate(5, Date.valueOf(newTask.getEndDate()));
            statement.setInt(6, user.getId());

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
        if (taskName.isEmpty() || taskOverview.isEmpty() || taskStartDate == null || taskEndDate == null) {
            System.out.println("All fields are required.");
            return false;
        }
        return true;

    }

    public static List<Task> getTasksForToday(User user) {
        List<Task> tasks = new ArrayList<>();
        LocalDate today = LocalDate.now();

        try (Connection connection = DatabaseConnection.connect()) {
            String query = "SELECT * FROM public.task WHERE start_date = ? AND user_id=?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setDate(1, java.sql.Date.valueOf(today));
            statement.setInt(2, user.getId());

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                // Retrieve task details from the result set and create Task objects
                int taskId = resultSet.getInt("id");
                String taskName = resultSet.getString("task_name");
                String taskOverview = resultSet.getString("overview");
                LocalDate startTaskDate = resultSet.getDate("start_date").toLocalDate();
                LocalDate endTaskDate = resultSet.getDate("end_date").toLocalDate();
                TaskStatus taskStatus = TaskStatus.valueOf(resultSet.getString("status"));

                // Retrieve other task attributes as needed

                // Create a Task object
                Task task = new Task(taskId, taskName, taskStatus, taskOverview, startTaskDate, endTaskDate);
                // Add the task to the list
                tasks.add(task);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tasks;
    }

    public static List<Task> getTasksForEndDay(User user) {
        List<Task> tasks = new ArrayList<>();
        LocalDate today = LocalDate.now();

        try (Connection connection = DatabaseConnection.connect()) {
            String query = "SELECT * FROM public.task WHERE end_date = ? AND user_id=?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setDate(1, java.sql.Date.valueOf(today.plusDays(1)));
            statement.setInt(2, user.getId());

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                // Retrieve task details from the result set and create Task objects
                int taskId = resultSet.getInt("id");
                String taskName = resultSet.getString("task_name");
                String taskOverview = resultSet.getString("overview");
                LocalDate startTaskDate = resultSet.getDate("start_date").toLocalDate();
                LocalDate endTaskDate = resultSet.getDate("end_date").toLocalDate();
                TaskStatus taskStatus = TaskStatus.valueOf(resultSet.getString("status"));

                // Retrieve other task attributes as needed

                // Create a Task object
                Task task = new Task(taskId, taskName, taskStatus, taskOverview, startTaskDate, endTaskDate);
                // Add the task to the list
                tasks.add(task);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tasks;
    }

    public static void updateTaskStatus(int taskId, TaskStatus newStatus) {
        try (Connection connection = DatabaseConnection.connect()) {
            String query = "UPDATE public.task SET status = ? WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, newStatus.toString());
            statement.setInt(2, taskId);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Task status updated successfully.");
            } else {
                System.out.println("No task found with ID: " + taskId);
            }
        } catch (SQLException e) {
            System.out.println("Error updating task status: " + e.getMessage());
        }
    }

    // Fetch tasks for today
    public static ObservableList<Task> getTasksForTodayList(User user) {
        LocalDate today = LocalDate.now();
        return getTasksForDate(today, user);
    }

    // Fetch tasks for tomorrow
    public static ObservableList<Task> getTasksForTomorrowList(User user) {
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        return getTasksForDate(tomorrow, user);
    }

    // Fetch tasks for this week
    public static ObservableList<Task> getTasksForThisWeekList(User user) {
        LocalDate today = LocalDate.now();
        LocalDate endOfWeek = today.plusDays(7 - today.getDayOfWeek().getValue());
        return getTasksInRange(today, endOfWeek, user);
    }

    // Fetch tasks for next week
    public static ObservableList<Task> getTasksForNextWeekList(User user) {
        LocalDate today = LocalDate.now();
        LocalDate startOfNextWeek = today.plusDays(7 - today.getDayOfWeek().getValue() + 1);
        LocalDate endOfNextWeek = startOfNextWeek.plusDays(7);
        return getTasksInRange(startOfNextWeek, endOfNextWeek, user);
    }

    private static ObservableList<Task> getTasksInRange(LocalDate startDate, LocalDate endDate, User user) {
        ObservableList<Task> tasks = FXCollections.observableArrayList();
        try (Connection connection = DatabaseConnection.connect()) {
            String query = "SELECT * FROM public.task WHERE start_date >= ? AND start_date <= ? AND user_id=?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setObject(1, startDate);
            statement.setObject(2, endDate);
            statement.setObject(3, user.getId());

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Task task = createTaskFromResultSet(resultSet);
                tasks.add(task);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching tasks: " + e.getMessage());
        }
        return tasks;
    }

    private static ObservableList<Task> getTasksForDate(LocalDate date, User user) {
        ObservableList<Task> tasks = FXCollections.observableArrayList();
        try (Connection connection = DatabaseConnection.connect()) {
            String query = "SELECT * FROM public.task WHERE start_date = ? AND user_id=?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setObject(1, date);
            statement.setObject(2, user.getId());


            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Task task = createTaskFromResultSet(resultSet);
                tasks.add(task);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching tasks: " + e.getMessage());
        }
        return tasks;
    }


    private static Task createTaskFromResultSet(ResultSet resultSet) throws SQLException {
        int taskId = resultSet.getInt("id");
        TaskStatus status = TaskStatus.valueOf(resultSet.getString("status"));
        String taskName = resultSet.getString("task_name");
        String overview = resultSet.getString("overview");
        LocalDate startDate = resultSet.getDate("start_date").toLocalDate();
        LocalDate endDate = resultSet.getDate("end_date").toLocalDate();

        return new Task(taskId, taskName, status, overview, startDate, endDate);
    }

    public static void deleteTask(Task task) throws SQLException {
        // Check if the task is not null
        if (task != null) {
            // Create a database connection
            try (Connection connection = DatabaseConnection.connect()) {
                // Define the SQL query to delete the task
                String query = "DELETE FROM public.task WHERE id = ?";

                // Create a prepared statement
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setInt(1, task.getID()); // Set the task ID as parameter

                // Execute the update (delete) operation
                statement.executeUpdate();
            }
        }
    }

}
