package org.nikita.jdbctask.dao;

import org.nikita.jdbctask.DatabaseConfig;
import org.nikita.jdbctask.entity.Product;
import org.nikita.jdbctask.interfaces.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO implements DAO<Product> {
    private final String tableName = "public.products";
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
                    "INSERT INTO " + tableName + "(name, price, quantity, available) VALUES (?, ?, ?, ?)");
            statement.setString(1, product.getName());
            //statement.setString(2, product.getPrice().toString());
            statement.setString(3, String.valueOf(product.getQuantity()));
            statement.setString(3, String.valueOf(product.getAvailability()));
            int i = statement.executeUpdate();
            if (i!=1) throw new SQLException();
        }
        catch (SQLException e) {
            System.out.println("could not get products, SQLException: "+ e.getMessage());
        }
    }

    @Override
    public List<Product> getAll(){
        try {
            Statement statement = connection.createStatement();
            return parseResult(statement.executeQuery("SELECT * FROM "+ tableName));
        }
        catch (SQLException e) {
            System.out.println("could not get products, SQLException: "+ e.getMessage());
        }
        return null;
    }

    @Override
    public Product findById(Long id){
        try {
            Statement statement = connection.createStatement();
            return parseResult(statement.executeQuery("SELECT * FROM " + tableName + " WHERE id=" + id)).get(0);
        }
        catch (SQLException e) {
            System.out.println("could not find product by id, SQLException: "+ e.getMessage());
        }
        return null;
    }

    @Override
    public void update(Product product){
//        db.put(product.getId(), product);
    }

    @Override
    public void delete(Product product){
//        db.remove(product.getId());
    }

    public static List<Product> parseResult(ResultSet result) {
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
