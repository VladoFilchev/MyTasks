package com.example.tasksmanagementsystem.DAL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegistrationQueries {
    public static boolean isUsernameUnique(String username) {
        String query = "SELECT COUNT(*) FROM public.user WHERE username = ?";
        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count == 0; // If count is 0, username is unique
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Return false in case of any exception or error
    }
}
