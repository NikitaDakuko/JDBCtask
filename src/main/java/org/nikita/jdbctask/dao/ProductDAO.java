package org.nikita.jdbctask.dao;

import org.nikita.jdbctask.DatabaseConfig;
import org.nikita.jdbctask.entity.Product;
import org.nikita.jdbctask.interfaces.DAO;
import org.postgresql.util.PGmoney;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO implements DAO<Product> {
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
    public void create(Product product){
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
    public List<Product> getAll(){
        try {
            return parseResult(connection.prepareStatement(
                    "SELECT * FROM " + tableName).executeQuery());
        }
        catch (SQLException e) {
            System.out.println("Could not get products, SQLException: "+ e.getMessage());
        }
        return null;
    }

    @Override
    public Product findById(Long id){
        try {
            Statement statement = connection.createStatement();
            return parseResult(statement.executeQuery(
                    "SELECT * FROM " + tableName + " WHERE id=" + id)).get(0);
        }
        catch (SQLException e) {
            System.out.println("Could not find product with id = " + id + ", SQLException: "+ e.getMessage());
        }
        return null;
    }

    @Override
    public void update(Product product){
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE " + tableName +
                            "SET name=?, price=?, quantity=?, available=?)" +
                            " WHERE id=" + product.getId());
            statement.setString(1, product.getName());
            statement.setObject(2, product.getPrice(), PGmoney.class.getModifiers());
            statement.setLong(3, product.getQuantity());
            statement.setBoolean(4, product.getAvailability());

            System.out.println(statement);

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

    private List<Product> parseResult(ResultSet result) {
        List<Product> products = new ArrayList<>();
        try {
            while (result.next()){
                products.add(Product.fromResult(result));
            }
        }
        catch (SQLException e) {
            System.out.println("SQLException: "+ e.getMessage());
        }
        return products;
    }
}
