package org.nikita.jdbctask.dao;

import org.nikita.jdbctask.DatabaseConfig;
import org.nikita.jdbctask.entity.OrderDetail;
import org.nikita.jdbctask.interfaces.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailDAO implements DAO<OrderDetail> {
    private final String tableName = "public.orderApproval";
    private final Connection connection;

    public OrderDetailDAO(){
        try {
            this.connection = DatabaseConfig.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void create(OrderDetail orderDetail){
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
//            System.out.println("Could not create orderDetail, SQLException: "+ e.getMessage());
//        }
    }

    @Override
    public List<OrderDetail> getAll(){
//        try {
//            return parseResult(connection.prepareStatement(
//                    "SELECT * FROM " + tableName).executeQuery());
//        }
//        catch (SQLException e) {
//            System.out.println("Could not get orderDetails, SQLException: "+ e.getMessage());
//        }
        return null;
    }

    @Override
    public OrderDetail findById(Long id){
        try {
            Statement statement = connection.createStatement();
            return parseResult(statement.executeQuery(
                    "SELECT * FROM " + tableName + " WHERE id=" + id)).get(0);
        }
        catch (SQLException e) {
            System.out.println("Could not find orderDetail with id = " + id + ", SQLException: "+ e.getMessage());
        }
        return null;
    }

    @Override
    public void update(OrderDetail orderDetail){
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
        try {
            if(connection.createStatement().executeUpdate(
                    "DELETE FROM " + tableName +
                            " WHERE id=" + id) != 1) throw new SQLException();
        }
        catch (SQLException e) {
            System.out.println("Could not delete orderDetail with id = " + id + ", SQLException: "+ e.getMessage());
        }
    }

    private List<OrderDetail> parseResult(ResultSet result) {
        List<OrderDetail> orderDetailList = new ArrayList<>();
        try {
            while (result.next()){
                orderDetailList.add(OrderDetail.fromResult(result));
            }
        }
        catch (SQLException e) {
            System.out.println("SQLException: "+ e.getMessage());
        }
        return orderDetailList;
    }
}
