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

    public static void recreateProductsTable(Connection connection) throws SQLException {
        String tableName = DatabaseConfig.productTableName;

        connection.prepareStatement(
                "DROP TABLE IF EXISTS " + tableName + " CASCADE;\n" +
                        "CREATE TABLE IF NOT EXISTS " + tableName + "\n" +
                        "(\n" +
                        "    id serial NOT NULL,\n" +
                        "    name character varying(255) COLLATE pg_catalog.\"default\" NOT NULL,\n" +
                        "    price numeric NOT NULL,\n" +
                        "    quantity integer NOT NULL,\n" +
                        "    available boolean NOT NULL,\n" +
                        "    PRIMARY KEY (id)" +
                        ")").execute();
    }

    public static void recreateOrderDetailTable(Connection connection) throws SQLException {
        String tableName = DatabaseConfig.orderDetailTableName;

        connection.prepareStatement(
                "DROP TABLE IF EXISTS " + tableName + " CASCADE;\n" +
                        "CREATE TABLE IF NOT EXISTS " + tableName + "\n" +
                        "(\n" +
                        "    id serial NOT NULL,\n" +
                        "    \"totalAmount\" numeric NOT NULL,\n" +
                        "    \"orderStatus\" character varying(32) COLLATE pg_catalog.\"default\" NOT NULL,\n" +
                        "    CONSTRAINT \"orderDetail_pkey\" PRIMARY KEY (id)\n" +
                        ")").execute();
    }

    public static void recreateOrderProductTable(Connection connection) throws SQLException {
        String tableName = DatabaseConfig.orderProductTableName;

        connection.prepareStatement(
                "DROP TABLE IF EXISTS " + tableName + " CASCADE;\n" +
                        "CREATE TABLE IF NOT EXISTS " + tableName + "\n" +
                        "(\n" +
                        "    \"orderDetailId\" bigint NOT NULL,\n" +
                        "    \"productId\" bigint NOT NULL,\n" +
                        "    CONSTRAINT \"orderDetailId\" FOREIGN KEY (\"orderDetailId\")\n" +
                        "        REFERENCES " + DatabaseConfig.orderDetailTableName + " (id) MATCH SIMPLE\n" +
                        "        ON UPDATE NO ACTION\n" +
                        "        ON DELETE NO ACTION,\n" +
                        "    CONSTRAINT \"productId\" FOREIGN KEY (\"productId\")\n" +
                        "        REFERENCES " + DatabaseConfig.productTableName + " (id) MATCH SIMPLE\n" +
                        "        ON UPDATE NO ACTION\n" +
                        "        ON DELETE NO ACTION\n" +
                        ")").execute();
    }

    public static void recreateOrderApprovalTable(Connection connection) throws SQLException {
        String tableName = DatabaseConfig.orderApprovalTableName;

        connection.prepareStatement(
                "DROP TABLE IF EXISTS " + tableName + " CASCADE;\n" +
                        "CREATE TABLE IF NOT EXISTS " + tableName + "\n" +
                        "(\n" +
                        "    id serial NOT NULL,\n" +
                        "    \"totalAmount\" numeric NOT NULL,\n" +
                        "    \"orderStatus\" character varying(32) COLLATE pg_catalog.\"default\" NOT NULL,\n" +
                        "    CONSTRAINT \"orderDetail_pkey\" PRIMARY KEY (id)\n" +
                        ")").execute();
    }
}
