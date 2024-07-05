package org.nikita.jdbctask.dao;

import org.nikita.jdbctask.DatabaseConfig;
import org.nikita.jdbctask.entity.OrderApproval;
import org.nikita.jdbctask.interfaces.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class OrderApprovalDAO implements DAO<OrderApproval> {
    private final String tableName = "public.orderApproval";
    private final Connection connection;

    public OrderApprovalDAO(){
        try {
            this.connection = DatabaseConfig.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void create(OrderApproval orderApproval){
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
    public List<OrderApproval> getAll(){
//        try {
//            return parseResult(connection.prepareStatement(
//                    "SELECT * FROM " + tableName).executeQuery());
//        }
//        catch (SQLException e) {
//            System.out.println("Could not get orderApprovals, SQLException: "+ e.getMessage());
//        }
        return null;
    }

    @Override
    public OrderApproval findById(Long id){
        try {
            Statement statement = connection.createStatement();
            return parseResult(statement.executeQuery(
                    "SELECT * FROM " + tableName + " WHERE id=" + id)).get(0);
        }
        catch (SQLException e) {
            System.out.println("Could not find orderApproval with id = " + id + ", SQLException: "+ e.getMessage());
        }
        return null;
    }

    @Override
    public void update(OrderApproval orderApproval){
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
        try {
            if(connection.createStatement().executeUpdate(
                    "DELETE FROM " + tableName +
                            " WHERE id=" + id) != 1) throw new SQLException();
        }
        catch (SQLException e) {
            System.out.println("Could not delete orderApproval with id = " + id + ", SQLException: "+ e.getMessage());
        }
    }

    private List<OrderApproval> parseResult(ResultSet result) {
        List<OrderApproval> orderApprovalList = new ArrayList<>();
        try {
            while (result.next()){
                orderApprovalList.add(OrderApproval.fromResult(result));
            }
        }
        catch (SQLException e) {
            System.out.println("SQLException: "+ e.getMessage());
        }
        return orderApprovalList;
    }
}
