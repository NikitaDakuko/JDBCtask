package org.nikita.jdbctask;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.nikita.jdbctask.ApplicationConfig.*;

public class Main {
    public static void main(String[] args) {

        try (Connection conn = DriverManager.getConnection(
                DB_URL, DB_USER, DB_PASS)) {

            if (conn != null) {
                System.out.println("Connected to the database!");
            } else {
                System.out.println("Failed to make connection!");
            }

        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }
    }
}