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

    public static void recreateProductsTable(Connection connection){
        try {
            connection.prepareStatement(
                    "DROP TABLE IF EXISTS public.\"product\";\n" +
                            "CREATE TABLE IF NOT EXISTS public.\"product\"\n" +
                            "(\n" +
                            "    id serial NOT NULL,\n" +
                            "    name character varying(255) COLLATE pg_catalog.\"default\" NOT NULL,\n" +
                            "    price money NOT NULL,\n" +
                            "    quantity integer NOT NULL,\n" +
                            "    available boolean NOT NULL,\n" +
                            "    PRIMARY KEY (id)" +
                            ")").execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
