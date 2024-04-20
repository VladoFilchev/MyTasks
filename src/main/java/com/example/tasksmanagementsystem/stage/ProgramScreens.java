package com.example.tasksmanagementsystem.stage;

import com.example.tasksmanagementsystem.controller.MainMenuController;
import com.example.tasksmanagementsystem.controller.NewTaskController;
import com.example.tasksmanagementsystem.object.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ProgramScreens {
    public void showRegistration() {
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/tasksmanagementsystem/scenes/registerScreen.fxml"));
            Parent root = loader.load();

            // Set up the stage and scene
            Stage stage = new Stage();
            stage.setTitle("Registration");
            Scene scene = new Scene(root);

            // Set the scene to the stage
            stage.setScene(scene);

            // Show the stage
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showLogin() {
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/tasksmanagementsystem/scenes/loginScreen.fxml"));
            Parent root = loader.load();

            // Set up the stage and scene
            Stage stage = new Stage();
            stage.setTitle("Login");
            Scene scene = new Scene(root);

            // Set the scene to the stage
            stage.setScene(scene);

            // Show the stage
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showMainMenu(User currentuser) {
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/tasksmanagementsystem/scenes/mainMenuScreen.fxml"));
            Parent root = loader.load();

            MainMenuController controller = loader.getController();
            controller.setCurrentUser(currentuser);

            // Set up the stage and scene
            Stage stage = new Stage();
            stage.setTitle("Main Menu");
            Scene scene = new Scene(root);

            // Set the scene to the stage
            stage.setScene(scene);

            // Show the stage
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showNewTask(User currentUser) {
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/tasksmanagementsystem/scenes/newTaskScreen.fxml"));
            Parent root = loader.load();

            NewTaskController controller = loader.getController();
            controller.setCurrentUser(currentUser);

            // Set up the stage and scene
            Stage stage = new Stage();
            stage.setTitle("New Task");
            Scene scene = new Scene(root);

            // Set the scene to the stage
            stage.setScene(scene);

            // Show the stage
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
