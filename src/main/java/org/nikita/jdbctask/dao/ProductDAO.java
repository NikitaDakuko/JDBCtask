package org.nikita.jdbctask.dao;

import org.nikita.jdbctask.DatabaseConfig;
import org.nikita.jdbctask.dto.ProductDTO;
import org.nikita.jdbctask.interfaces.DAO;
import org.nikita.jdbctask.mapper.dto.ProductDTOMapper;

import java.sql.*;
import java.util.List;

public class ProductDAO implements DAO<ProductDTO> {
    private final ProductDTOMapper mapper = new ProductDTOMapper();
    private final Connection connection;

    public ProductDAO(Connection connection) {
        this.connection = connection;
    }

    public ProductDAO() {
        this.connection = DatabaseConfig.getConnection();
    }

    @Override
    public List<Long> create(List<ProductDTO> products) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO " + DatabaseConfig.productTableName +
                        "(id, name, price, quantity, available) " +
                        "VALUES (?, ?, ?, ?, ?)");

        for (ProductDTO product : products) {
            statement.setLong(1, product.getId());
            statement.setString(2, product.getName());
            statement.setBigDecimal(3, product.getPrice());
            statement.setLong(4, product.getQuantity());
            statement.setBoolean(5, product.getAvailability());
            statement.addBatch();
        }
        if (statement.executeUpdate() != 1) throw new SQLException();
        return null;
    }

    @Override
    public ProductDTO findById(Long id) throws SQLException {
        List<ProductDTO> results = mapper.listFromResult(defaultFindById(connection, DatabaseConfig.productTableName, id));
        return results.get(0);
    }

    @Override
    public List<ProductDTO> getAll() throws SQLException {
        return mapper.listFromResult(defaultGetAll(connection, DatabaseConfig.productTableName));
    }

    public List<ProductDTO> getMultiple(List<Long> ids) throws SQLException {
        String idString = ids.toString().replace("[", "(").replace("]", ")");
        PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM " + DatabaseConfig.productTableName + " WHERE id IN " + idString);
        // idArray = connection.createArrayOf("INTEGER", ids.toArray());
        //statement.setArray(1, idArray);

        return mapper.listFromResult(statement.executeQuery());

    }

    @Override
    public void update(List<ProductDTO> products) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(
                "UPDATE " + DatabaseConfig.productTableName +
                        " SET name = ?, price = ?, quantity = ?, available = ?" +
                        " WHERE id = ?");

        for (ProductDTO product : products) {
            statement.setLong(1, product.getId());
            statement.setString(2, product.getName());
            statement.setBigDecimal(3, product.getPrice());
            statement.setLong(4, product.getQuantity());
            statement.setBoolean(5, product.getAvailability());
            statement.addBatch();
        }
        if (statement.executeUpdate() != 1) throw new SQLException();
    }

    @Override
    public void delete(Long id) throws SQLException {
        defaultDelete(connection, DatabaseConfig.productTableName, id);
    }
}
