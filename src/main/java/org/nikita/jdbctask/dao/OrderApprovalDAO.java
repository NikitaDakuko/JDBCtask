package org.nikita.jdbctask.dao;

import org.nikita.jdbctask.dto.OrderApprovalDTO;
import org.nikita.jdbctask.interfaces.DAO;

import java.sql.Connection;
import java.sql.ResultSet;

public class OrderApprovalDAO implements DAO<OrderApprovalDTO> {
    private final String tableName = "public.orderApproval";
    private final Connection connection = getConnection();

    @Override
    public void create(OrderApprovalDTO orderApproval){
//        try {
//            PreparedStatement statement = connection.prepareStatement(
//                    "INSERT INTO " + tableName +
//                            "(name, price, quantity, available) " +
//                            "VALUES (?, "+ product.getPrice().val + ", ?, ?)");
//            statement.setString(1, product.getName());
//            statement.setLong(2, product.getQuantity());
//            statement.setBoolean(3, product.getAvailability());
//
//            if (statement.executeUpdate()!=1) throw new SQLException();
//        }
//        catch (SQLException e) {
//            System.out.println("Could not create orderApproval, SQLException: "+ e.getMessage());
//        }
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
    public void update(OrderApprovalDTO orderApproval){
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
//            System.out.println("Could not update orderApproval with id = " + product.getId() + ", SQLException: "+ e.getMessage());
//        }
    }

    @Override
    public void delete(Long id){
        defaultDelete(connection, tableName, id);
    }
}
