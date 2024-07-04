package org.nikita.jdbctask;

import org.nikita.jdbctask.dao.ProductDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try {
            PreparedStatement statement = DatabaseConfig.getConnection().prepareStatement(
                    "SELECT * FROM public.product");

            ResultSet rs = statement.executeQuery();
            System.out.println(ProductDAO.parseResult(rs));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}