package org.nikita.jdbctask.dao;

import org.nikita.jdbctask.DatabaseConfig;
import org.nikita.jdbctask.dto.ProductDTO;
import org.nikita.jdbctask.interfaces.DAO;

import java.sql.*;

public class ProductDAO implements DAO<ProductDTO> {
    private final String tableName = "public.product";
    private final Connection connection;

    public ProductDAO(){
        try {
            this.connection = DatabaseConfig.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

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
    public ResultSet getAll(){
        try {
            return connection.prepareStatement(
                    "SELECT * FROM " + tableName).executeQuery();
        }
        catch (SQLException e) {
            System.out.println("Could not get products, SQLException: "+ e.getMessage());
        }
        return null;
    }

    @Override
    public ResultSet findById(Long id){
        try {
            return connection.createStatement().executeQuery(
                    "SELECT * FROM " + tableName + " WHERE id=" + id);
        }
        catch (SQLException e) {
            System.out.println("Could not find product with id = " + id + ", SQLException: "+ e.getMessage());
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
        try {
            if(connection.createStatement().executeUpdate(
                    "DELETE FROM " + tableName +
                            " WHERE id=" + id) != 1) throw new SQLException();
        }
        catch (SQLException e) {
            System.out.println("Could not delete product with id = " + id + ", SQLException: "+ e.getMessage());
        }
    }
}
