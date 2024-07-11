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
    public static final String productTableName = "product";
    public static final String orderApprovalTableName = "\"orderApproval\"";
    public static final String orderDetailTableName = "\"orderDetail\"";
    public static final String orderProductTableName = "\"orderProduct\"";


    static {
        try (InputStream input = DatabaseConfig.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (input == null) {
                System.out.println("Sorry, unable to find db.properties");
                System.exit(1);
            }

            properties.load(input);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public static Connection getConnection(){
        try {
            DriverManager.registerDriver(new Driver());
            return DriverManager.getConnection(
                    properties.getProperty("db.url"),
                    properties.getProperty("db.username"),
                    properties.getProperty("db.password")
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void recreateSchema(Connection connection) throws SQLException {
        connection.prepareStatement(
                "DROP TABLE IF EXISTS " + productTableName + " CASCADE;\n" +
                        "CREATE TABLE IF NOT EXISTS " + productTableName + "\n" +
                        "(\n" +
                        "    id serial NOT NULL,\n" +
                        "    name character varying(255) COLLATE pg_catalog.\"default\" NOT NULL,\n" +
                        "    price numeric NOT NULL,\n" +
                        "    quantity integer NOT NULL,\n" +
                        "    available boolean NOT NULL,\n" +
                        "    PRIMARY KEY (id)" +
                        ");\n" +

                        "DROP TABLE IF EXISTS " + orderDetailTableName + " CASCADE;\n" +
                        "CREATE TABLE IF NOT EXISTS " + orderDetailTableName + "\n" +
                        "(\n" +
                        "    id serial NOT NULL,\n" +
                        "    \"totalAmount\" numeric NOT NULL,\n" +
                        "    \"orderStatus\" character varying(32) COLLATE pg_catalog.\"default\" NOT NULL,\n" +
                        "    CONSTRAINT \"orderDetail_pkey\" PRIMARY KEY (id)\n" +
                        ");\n" +

                        "DROP TABLE IF EXISTS " + orderProductTableName + " CASCADE;\n" +
                        "CREATE TABLE IF NOT EXISTS " + orderProductTableName + "\n" +
                        "(\n" +
                        "    \"orderDetailId\" bigint NOT NULL,\n" +
                        "    \"productId\" bigint NOT NULL,\n" +
                        "    CONSTRAINT \"orderDetailId\" FOREIGN KEY (\"orderDetailId\")\n" +
                        "        REFERENCES " + DatabaseConfig.orderDetailTableName + " (id) MATCH SIMPLE\n" +
                        "        ON UPDATE NO ACTION\n" +
                        "        ON DELETE CASCADE,\n" +
                        "    CONSTRAINT \"productId\" FOREIGN KEY (\"productId\")\n" +
                        "        REFERENCES " + DatabaseConfig.productTableName + " (id) MATCH SIMPLE\n" +
                        "        ON UPDATE NO ACTION\n" +
                        "        ON DELETE CASCADE\n" +
                        ");\n" +

                        "DROP TABLE IF EXISTS " + orderApprovalTableName + " CASCADE;\n" +
                        "CREATE TABLE IF NOT EXISTS " + orderApprovalTableName + "\n" +
                        "(\n" +
                        "    id serial NOT NULL,\n" +
                        "    \"orderDetailId\" bigint NOT NULL,\n" +
                        "    CONSTRAINT \"orderApproval_pkey\" PRIMARY KEY (id)\n" +
                        ")"
        ).execute();
    }
}
