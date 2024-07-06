package org.nikita.jdbctask.dao;

import org.nikita.jdbctask.dto.OrderProductDTO;
import org.nikita.jdbctask.interfaces.DAO;

import java.sql.*;

public class OrderProductDAO implements DAO<OrderProductDTO> {
    private final String tableName = "public.\"orderProducts\"";
    private final Connection connection = getConnection();

    @Override
    public void create(OrderProductDTO dto) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO " + tableName + "(\"orderDetailId\", \"productId\") VALUES (?, ?);");
            for (Long productId : dto.getProductIds()){
                statement.setLong(1, dto.getOrderDetailId());
                statement.setLong(2, productId);
                statement.addBatch();
            }
            statement.executeBatch();
            connection.commit();
        }
        catch (SQLException e) {
            System.out.println("Could not create orderProducts, SQLException: "+ e.getMessage());
        }
    }

    @Override
    public ResultSet findById(Long orderId) {
        try {
            Statement statement = connection.createStatement();
            return statement.executeQuery(
                    "SELECT * FROM " + tableName + " WHERE orderId=" + orderId);
        }
        catch (SQLException e) {
            System.out.println(
                    "Could not find products for orderId = " + orderId + ", SQLException: "+ e.getMessage());
        }
        return null;
    }

    @Override
    public ResultSet getAll() {
        return null;
    }

    @Override
    public void update(OrderProductDTO dto) {

    }

    @Override
    public void delete(Long id){
        defaultDelete(connection, tableName, id);
    }
}
