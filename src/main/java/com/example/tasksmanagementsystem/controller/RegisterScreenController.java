package com.example.tasksmanagementsystem.controller;

import com.example.tasksmanagementsystem.service.RegistrationServices;
import com.example.tasksmanagementsystem.stage.ProgramScreens;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class RegisterScreenController {

    @FXML
    private AnchorPane anchor;

    @FXML
    private TextField firstName;

    @FXML
    private HBox hBox;

    @FXML
    private TextField lastName;

    @FXML
    private Hyperlink loginLink;

    @FXML
    private PasswordField password;

    @FXML
    private Button registerButton;

    @FXML
    private VBox root;

    @FXML
    private TextField username;

    @FXML
    private Text welcome;

    private final RegistrationServices registrationServices = new RegistrationServices();
    private final ProgramScreens programScreens = new ProgramScreens();

    @FXML
    void clickRegisterButton(ActionEvent event) {
        String first = firstName.getText();
        String last = lastName.getText();
        String user = username.getText();
        String pass = password.getText();

        if (registrationServices.registerUser(first, last, user, pass)) {
            showAlert(Alert.AlertType.INFORMATION, "Registration Successful", "User registered successfully.");
            Stage stage = (Stage) firstName.getScene().getWindow();
            stage.close();

            //Open the login stage
            programScreens.showLogin();


        } else {
            showAlert(Alert.AlertType.ERROR, "Registration Failed", "Failed to register user.");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    void pressLoginLink(ActionEvent event) {
        Stage stage = (Stage) firstName.getScene().getWindow();
        stage.close();

        //Open the login stage
        programScreens.showLogin();
    }

}


