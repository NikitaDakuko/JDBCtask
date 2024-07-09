package org.nikita.jdbctask.dao;

import org.nikita.jdbctask.DatabaseConfig;
import org.nikita.jdbctask.dto.ProductDTO;
import org.nikita.jdbctask.interfaces.DAO;
import org.nikita.jdbctask.mapper.dto.ProductDTOMapper;

import java.sql.*;
import java.util.List;

public class ProductDAO implements DAO<ProductDTO> {
    private final String tableName = "public.product";
    private final ProductDTOMapper mapper = new ProductDTOMapper();
    private final Connection connection;

    public ProductDAO(Connection connection){
        this.connection = connection;
    }

    public ProductDAO(){
        this.connection = DatabaseConfig.getConnection();
    }

    @Override
    public void create(ProductDTO product){
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO " + tableName +
                            "(name, price, quantity, available) " +
                            "VALUES (?, ?, ?, ?)");
            statement.setString(1, product.getName());
            statement.setBigDecimal(2, product.getPrice());
            statement.setLong(3, product.getQuantity());
            statement.setBoolean(4, product.getAvailability());

            if (statement.executeUpdate()!=1) throw new SQLException();
        }
        catch (SQLException e) {
            System.out.println("Could not create product, SQLException: "+ e.getMessage());
        }
    }

    @Override
    public ProductDTO findById(Long id) {
        List<ProductDTO> results = mapper.listFromResult(defaultFindById(connection, tableName ,id));
        if(!results.isEmpty())
            return results.get(0);
        System.out.println("querry is empty!?!?!!?");
        return null;
    }

    @Override
    public List<ProductDTO> getAll() {
        return mapper.listFromResult(defaultGetAll(connection, tableName));
    }

    public List<ProductDTO> getMultiple(List<Long> ids){
        try {
            Statement statement = connection.createStatement();
            String idString = ids.toString().replace("[", "(").replace("]", ")");

            return mapper.listFromResult(statement.executeQuery(
                    "SELECT * FROM " + tableName + " WHERE id IN (" + idString + ")"));
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
                            " SET name = ?, price = ?, quantity = ?, available = ?" +
                            " WHERE id = ?");
            statement.setString(1, product.getName());
            statement.setBigDecimal(2, product.getPrice());
            statement.setLong(3, product.getQuantity());
            statement.setBoolean(4, product.getAvailability());
            statement.setLong(5, product.getId());

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
