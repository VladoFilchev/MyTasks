package com.example.tasksmanagementsystem.controller;

import com.example.tasksmanagementsystem.object.User;
import com.example.tasksmanagementsystem.service.LoginServices;
import com.example.tasksmanagementsystem.stage.ProgramScreens;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginScreenController {

    @FXML
    private HBox hBox;

    @FXML
    private Button loginButton;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Text passwordText;

    @FXML
    private AnchorPane root;

    @FXML
    private TextField textField;

    @FXML
    private Text usernameText;

    @FXML
    private Text welcome;

    @FXML
    private Hyperlink registerLink;

    public User currentUser;


    private final LoginServices loginServices = new LoginServices();
    private final ProgramScreens programScreens = new ProgramScreens();

    @FXML
    void loginClicked(ActionEvent event) {
        String username = textField.getText();
        String password = passwordField.getText();
        if (loginServices.authenticate(username, password)) {
            currentUser = loginServices.getUser(username);
            showAlert(Alert.AlertType.INFORMATION, "Login Successful", "Logged in successfully.");
            Stage stage = (Stage) textField.getScene().getWindow();
            stage.close();
            programScreens.showMainMenu(currentUser);

        } else {
            showAlert(Alert.AlertType.ERROR, "Login Failed", "Failed to log in user.");
        }
    }

    @FXML
    void clickedRegister(ActionEvent event) {
        Stage stage = (Stage) usernameText.getScene().getWindow();
        stage.close();

        //Open the login stage
        programScreens.showRegistration();
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }


}

