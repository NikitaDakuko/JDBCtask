package org.nikita.jdbctask.dao;

import org.nikita.jdbctask.DatabaseConfig;
import org.nikita.jdbctask.dto.ProductDTO;
import org.nikita.jdbctask.interfaces.DAO;
import org.nikita.jdbctask.mapper.dto.ProductDTOMapper;

import java.sql.*;
import java.util.List;

/**
 * Data Access Object for Product entity.
 * Works with DTOs
 */
public class ProductDAO implements DAO<ProductDTO> {
    private final ProductDTOMapper mapper = new ProductDTOMapper();
    private final Connection connection;

    /**
     * @param connection custom DB connection for testing
     */
    public ProductDAO(Connection connection) {
        this.connection = connection;
    }

    public ProductDAO() {
        this.connection = DatabaseConfig.getConnection();
    }

    /**
     * @param products List of ProductDTO to be inserted into a database
     */
    @Override
    public List<Long> create(List<ProductDTO> products) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO " + DatabaseConfig.productTableName +
                            "(name, price, quantity, available) " +
                            "VALUES (?, ?, ?, ?)");

            for (ProductDTO product : products) {
                statement.setString(1, product.getName());
                statement.setBigDecimal(2, product.getPrice());
                statement.setLong(3, product.getQuantity());
                statement.setBoolean(4, product.getAvailability());
                statement.addBatch();
            }
            statement.executeBatch();
        } catch (SQLException e){
            System.out.println("Could not create products, SQLException: " + e);
        }
        return null;
    }

    /**
     * @param id ID of a record that you want to retrieve
     * @return DTO of the Product entity
     */
    @Override
    public ProductDTO findById(Long id)  {
        List<ProductDTO> results = mapper.listFromResult(defaultFindById(connection, DatabaseConfig.productTableName, id));
        return results.get(0);
    }

    /**
     * @return All Product entities that are currently stored in the DB
     */
    @Override
    public List<ProductDTO> getAll() {
        return mapper.listFromResult(defaultGetAll(connection, DatabaseConfig.productTableName));
    }

    public List<ProductDTO> getMultiple(List<Long> ids) {
        try {
            String idString = ids.toString().replace("[", "(").replace("]", ")");
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM " + DatabaseConfig.productTableName + " WHERE id IN " + idString);

            return mapper.listFromResult(statement.executeQuery());
        } catch (SQLException e){
            System.out.println("Could not get products by IDs, SQLException: " + e);
            return null;
        }
    }

    /**
     * @param products List of Product DTOs to be updated
     */
    @Override
    public void update(List<ProductDTO> products) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE " + DatabaseConfig.productTableName +
                            " SET name = ?, price = ?, quantity = ?, available = ?" +
                            " WHERE id = ?");

            for (ProductDTO product : products) {
                statement.setString(1, product.getName());
                statement.setBigDecimal(2, product.getPrice());
                statement.setLong(3, product.getQuantity());
                statement.setBoolean(4, product.getAvailability());
                statement.setLong(5, product.getId());
                statement.addBatch();
            }
            if (statement.executeUpdate() != 1) throw new SQLException();
        }catch (SQLException e){
            System.out.println("Could not update products, SQLException: " + e);
        }
    }

    /**
     * @param id ID of the Product you want to be deleted
     */
    @Override
    public void delete(Long id) {
        defaultDelete(connection, DatabaseConfig.productTableName, id);
    }
}
