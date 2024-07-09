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
    public ResultSet create(ProductDTO product){
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO " + tableName +
                            "(id, name, price, quantity, available) " +
                            "VALUES (?, ?, ?, ?, ?)");
            statement.setLong(1, product.getId());
            statement.setString(2, product.getName());
            statement.setBigDecimal(3, product.getPrice());
            statement.setLong(4, product.getQuantity());
            statement.setBoolean(5, product.getAvailability());

            if (statement.executeUpdate()!=1) throw new SQLException();
        }
        catch (SQLException e) {
            System.out.println("Could not create product, SQLException: "+ e.getMessage());
        }
        return null;
    }

    @Override
    public ProductDTO findById(Long id) {
        List<ProductDTO> results = mapper.listFromResult(defaultFindById(connection, tableName ,id));
        return results.get(0);
    }

    @Override
    public List<ProductDTO> getAll() {
        return mapper.listFromResult(defaultGetAll(connection, tableName));
    }

    public List<ProductDTO> getMultiple(List<Long> ids){
        try {
            String idString = ids.toString().replace("[", "(").replace("]", ")");
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM " + tableName + " WHERE id IN " + idString);
            // idArray = connection.createArrayOf("INTEGER", ids.toArray());
            //statement.setArray(1, idArray);

            return mapper.listFromResult(statement.executeQuery());
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
