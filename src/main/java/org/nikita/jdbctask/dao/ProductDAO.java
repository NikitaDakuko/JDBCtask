package org.nikita.jdbctask.dao;

import org.nikita.jdbctask.dto.ProductDTO;
import org.nikita.jdbctask.interfaces.DAO;

import java.sql.*;
import java.util.List;

public class ProductDAO implements DAO<ProductDTO> {
    private final String tableName = "public.product";
    private final Connection connection = getConnection();

    @Override
    public void create(ProductDTO product){
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO " + tableName +
                            "(name, price, quantity, available) " +
                            "VALUES (?, "+ product.getPrice().val + ", ?, ?)");
            statement.setString(1, product.getName());
            statement.setLong(2, product.getQuantity());
            statement.setBoolean(3, product.getAvailability());

            if (statement.executeUpdate()!=1) throw new SQLException();
        }
        catch (SQLException e) {
            System.out.println("Could not create product, SQLException: "+ e.getMessage());
        }
    }

    @Override
    public ResultSet findById(Long id) {
        return defaultFindById(connection, tableName, id);
    }

    @Override
    public ResultSet getAll() {
        return defaultGetAll(connection, tableName);
    }

    public ResultSet getMultiple(List<Long> ids){
        try {
            Statement statement = getConnection().createStatement();
            String idString = ids.toString().replace("[", "(").replace("]", ")");

            return statement.executeQuery(
                    "SELECT * FROM " + tableName + " WHERE id IN (" + idString + ")");
        }
        catch (SQLException e) {
            System.out.println(
                    "Could not find " + tableName + " record, SQLException: "+ e.getMessage());
        }
        return null;
    }

    @Override
    public void update(ProductDTO product){
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE " + tableName +
                            " SET name = ?, price = "+product.getPrice().val + ", quantity = ?, available = ?" +
                            " WHERE id = ?");
            statement.setString(1, product.getName());
            statement.setLong(2, product.getQuantity());
            statement.setBoolean(3, product.getAvailability());
            statement.setLong(4, product.getId());

            if (statement.executeUpdate()!=1) throw new SQLException();
        }
        catch (SQLException e) {
            System.out.println("Could not update product with id = " + product.getId() + ", SQLException: "+ e.getMessage());
        }
    }

    @Override
    public void delete(Long id){
        defaultDelete(connection, tableName, id);
    }
}
