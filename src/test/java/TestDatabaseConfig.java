import org.nikita.jdbctask.DatabaseConfig;
import org.postgresql.Driver;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class TestDatabaseConfig {
    private static final Properties properties = new Properties();

    static {
        try (InputStream input = DatabaseConfig.class.getClassLoader().getResourceAsStream("testDB.properties")) {
            if (input == null) {
                System.out.println("Sorry, unable to find testDB.properties");
                System.exit(1);
            }

            // Load the properties file
            properties.load(input);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public static Connection getConnection() {
        try {
            DriverManager.registerDriver(new Driver());
            return DriverManager.getConnection(
                    properties.getProperty("testDB.url"),
                    properties.getProperty("testDB.username"),
                    properties.getProperty("testDB.password")
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
