package com.example.tasksmanagementsystem.DAL;

import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {


    private static final String DB_URL = "jdbc:postgresql://localhost:5432/tms";
    private static final String USER = "postgres";
    private static final String PASSWORD = "1234";

    public static java.sql.Connection connect() {
        try {
            Class.forName("org.postgresql.Driver");

            return DriverManager.getConnection(DB_URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error connecting to the database", e);
        }
    }

}
