package org.nikita.jdbctask;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

@ApplicationPath("/api")
public class ApplicationConfig extends Application {
    public static final String DB_URL = "jdbc:postgresql://127.0.0.1:5432/JDBCdb";
    public static final String DB_USER = "postgres";
    public static final String DB_PASS = "admin";
}
