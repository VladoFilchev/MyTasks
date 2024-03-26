package com.example.tasksmanagementsystem.service;

import com.example.tasksmanagementsystem.DAL.DatabaseConnection;
import com.example.tasksmanagementsystem.object.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginServices {

    public boolean authenticate(String username, String password) {
        try (Connection connection = DatabaseConnection.connect()) {
            String query = "SELECT * FROM public.user WHERE username = ? AND password = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();
            return resultSet.next(); // If a row is returned, authentication is successful
        } catch (SQLException e) {
            System.out.println("Error during authentication: " + e.getMessage());
            return false;
        }
    }

    public User getUser(String username) {
        try (Connection connection = DatabaseConnection.connect()) {
            String query = "SELECT * FROM public.user WHERE username = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String password = resultSet.getString("password");
                // You can fetch additional user information here if needed
                return new User(null, id, firstName, lastName, username, password);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving user information: " + e.getMessage());
        }
        return null;
    }
}
