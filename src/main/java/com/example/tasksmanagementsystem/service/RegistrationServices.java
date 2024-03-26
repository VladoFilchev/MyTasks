package com.example.tasksmanagementsystem.service;

import com.example.tasksmanagementsystem.DAL.DatabaseConnection;
import com.example.tasksmanagementsystem.object.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static com.example.tasksmanagementsystem.DAL.RegistrationQueries.isUsernameUnique;

public class RegistrationServices {

    public boolean registerUser(String firstName, String lastName, String username, String password) {
        if (!validateRegistrationData(firstName, lastName, username, password)) {
            System.out.println("Invalid registration data.");
            return false;
        }

        // Create a new user object
        User newUser = new User(null,1, firstName, lastName, username, password);

        // Insert the new user into the database
        try (Connection connection = DatabaseConnection.connect()) {
            String query = "INSERT INTO public.user (username, password, first_name, last_name) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, newUser.getUsername());
            statement.setString(2, newUser.getPassword());
            statement.setString(3, newUser.getFirstName());
            statement.setString(4, newUser.getLastName());

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("User registered successfully.");
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Error registering user: " + e.getMessage());
        }

        return false;
    }

    private boolean validateRegistrationData(String firstName, String lastName, String username, String password) {
        // Check if any field is empty
        if (firstName.isEmpty() || lastName.isEmpty() || username.isEmpty() || password.isEmpty()) {
            System.out.println("All fields are required.");
            return false;
        }

        // Check if the username is unique
        if (!isUsernameUnique(username)) {
            System.out.println("Username is already taken.");
            return false;
        }
        return true;

    }

}
