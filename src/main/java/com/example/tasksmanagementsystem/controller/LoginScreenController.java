package com.example.tasksmanagementsystem.controller;

import com.example.tasksmanagementsystem.service.LoginServices;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
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

    private final LoginServices loginServices=new LoginServices();

    @FXML
    void loginClicked(ActionEvent event) {
        String username=textField.getText();
        String password=passwordField.getText();
if(loginServices.authenticate(username,password)){
    showAlert(Alert.AlertType.INFORMATION, "Login Successful", "Logged in successfully.");
    Stage stage = (Stage) textField.getScene().getWindow();
    stage.close();
}else{
    showAlert(Alert.AlertType.ERROR, "Login Failed", "Failed to log in user.");
}
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

}

