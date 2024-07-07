package org.nikita.jdbctask.dao;

import org.nikita.jdbctask.DatabaseConfig;
import org.nikita.jdbctask.dto.OrderDetailDTO;
import org.nikita.jdbctask.interfaces.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderDetailDAO implements DAO<OrderDetailDTO> {
    private final String tableName = "public.\"orderDetail\"";
    private final Connection connection;

    public OrderDetailDAO(Connection connection){
        this.connection = connection;
    }

    public OrderDetailDAO(){
        this.connection = DatabaseConfig.getConnection();
    }

    @Override
    public void create(OrderDetailDTO orderDetail){
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO " + tableName +
                            "(\"orderStatus\", \"totalAmount\") " +
                            "VALUES (?, " + orderDetail.getTotalAmount().val + ")");
            statement.setString(1, orderDetail.getOrderStatus().name());

            if (statement.executeUpdate()!=1) throw new SQLException();
        }
        catch (SQLException e) {
            System.out.println("Could not create orderDetail, SQLException: "+ e.getMessage());
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

    @Override
    public void update(OrderDetailDTO orderDetail){
//        try {
//            PreparedStatement statement = connection.prepareStatement(
//                    "UPDATE " + tableName +
//                            " SET name = ?, price = "+orderApproval.getPrice().val + ", quantity = ?, available = ?" +
//                            " WHERE id = ?");
//            statement.setString(1, orderApproval.getName());
//            statement.setLong(2, orderApproval.getQuantity());
//            statement.setBoolean(3, orderApproval.getAvailability());
//            statement.setLong(4, orderApproval.getId());
//
//            if (statement.executeUpdate()!=1) throw new SQLException();
//        }
//        catch (SQLException e) {
//            System.out.println("Could not update orderDetail with id = " + product.getId() + ", SQLException: "+ e.getMessage());
//        }
    }

    @Override
    public void delete(Long id){
        defaultDelete(connection, tableName, id);
    }
}
