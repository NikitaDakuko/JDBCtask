package org.nikita.jdbctask.dao;

import org.nikita.jdbctask.DatabaseConfig;
import org.nikita.jdbctask.dto.OrderProductDTO;
import org.nikita.jdbctask.interfaces.DAO;
import org.nikita.jdbctask.mapper.dto.OrderProductDTOMapper;

import java.sql.*;
import java.util.List;

public class OrderProductDAO implements DAO<OrderProductDTO> {
    private final String tableName = "public.\"orderProducts\"";
    private final OrderProductDTOMapper mapper = new OrderProductDTOMapper();
    private final Connection connection;

    public OrderProductDAO(Connection connection){
        this.connection = connection;
    }

    public OrderProductDAO(){
        this.connection = DatabaseConfig.getConnection();
    }

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

    public List<OrderProductDTO> findByOrderId(Long orderId) {
        try {
            Statement statement = connection.createStatement();
            return mapper.listFromResult(statement.executeQuery(
                    "SELECT * FROM " + tableName + " WHERE orderId=" + orderId));
        }
        catch (SQLException e) {
            System.out.println(
                    "Could not find products for orderId= " + orderId + ", SQLException: "+ e.getMessage());
        }
        return null;
    }

    @Override
    public OrderProductDTO findById(Long orderId) { return null; }

    @Override
    public List<OrderProductDTO> getAll() {
        return null;
    }

    @Override
    public void update(OrderProductDTO dto) {
        delete(dto.getOrderDetailId());
        create(dto);
    }

    @Override
    public void delete(Long orderDetailId){
        try {
            if(connection.createStatement().executeUpdate(
                    "DELETE FROM " + tableName +
                            " WHERE orderId=" + orderDetailId) != 1) throw new SQLException();
        }
        catch (SQLException e) {
            System.out.println(
                    "Could not delete " + tableName + " record with orderId = " + orderDetailId + ", SQLException: "+ e.getMessage());
        }
    }
}
