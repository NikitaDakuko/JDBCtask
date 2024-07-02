package org.nikita.jdbctask;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import org.postgresql.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@ApplicationPath("/api")
public class ApplicationConfig extends Application {
    public static final String DB_URL = "jdbc:postgresql://127.0.0.1:5432/JDBCdb";
    public static final String DB_USER = "postgres";
    public static final String DB_PASS = "admin";

    public static Connection getConnection()
    {
        try {
            DriverManager.registerDriver(new Driver());
            return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
        } catch (SQLException ex) {
            throw new RuntimeException("Error connecting to the database", ex);
        }
    }

}
