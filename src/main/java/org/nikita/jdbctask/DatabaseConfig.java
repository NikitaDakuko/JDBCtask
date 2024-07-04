package org.nikita.jdbctask;

import org.postgresql.Driver;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConfig {
    private static final Properties properties = new Properties();

    static {
        try (InputStream input = DatabaseConfig.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (input == null) {
                System.out.println("Sorry, unable to find db.properties");
                System.exit(1);
            }

            // Load the properties file
            properties.load(input);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public static Connection getConnection() throws SQLException {
        DriverManager.registerDriver(new Driver());
        return DriverManager.getConnection(getDbUrl(), getDbUsername(), getDbPassword());
    }

    private static String getDbUrl() {
        return properties.getProperty("db.url");
    }

    private static String getDbUsername() {
        return properties.getProperty("db.username");
    }

    private static String getDbPassword() {
        return properties.getProperty("db.password");
    }
}
