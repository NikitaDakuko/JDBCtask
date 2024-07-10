package org.nikita.jdbctask.dao;

import org.nikita.jdbctask.DatabaseConfig;
import org.nikita.jdbctask.dto.OrderDetailDTO;
import org.nikita.jdbctask.dto.ProductDTO;
import org.nikita.jdbctask.interfaces.DAO;
import org.nikita.jdbctask.mapper.dto.OrderDetailDTOMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class OrderDetailDAO implements DAO<OrderDetailDTO> {
    private final OrderDetailDTOMapper mapper = new OrderDetailDTOMapper();
    private final Connection connection;

    public OrderDetailDAO(Connection connection) {
        this.connection = connection;
    }

    public OrderDetailDAO() {
        this.connection = DatabaseConfig.getConnection();
    }

    @Override
    public List<Long> create(List<OrderDetailDTO> orderDetails){
        try {
            PreparedStatement insertDetailsStatement = connection.prepareStatement(
                    insertStringBuilder(orderDetails).toString());

            List<Long> ids = returnId(insertDetailsStatement.executeQuery());

            if (ids.size() == orderDetails.size()) {
                PreparedStatement orderProductStatement = connection.prepareStatement(
                        "INSERT INTO " + DatabaseConfig.orderProductTableName + "(\n" +
                                "\"orderDetailId\", \"productId\")\n" +
                                "VALUES (?, ?);"
                );

                for (int i = 0; i < orderDetails.size(); i++) {
                    for (ProductDTO p : orderDetails.get(i).getProducts()) {
                        orderProductStatement.setLong(1, ids.get(i));
                        orderProductStatement.setLong(2, p.getId());
                        orderProductStatement.addBatch();
                    }
                    orderProductStatement.executeBatch();
                }
                return ids;
            }
        }catch (SQLException e){
            System.out.println("Could not create order details, SQLException: " + e);
        }
        return null;
    }

    @Override
    public OrderDetailDTO findById(Long id) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT od.id, od.\"totalAmount\", od.\"orderStatus\", array_agg(op.\"productId\") \"productIds\"" +
                            " FROM " + DatabaseConfig.orderDetailTableName + " od\n" +
                            "JOIN " + DatabaseConfig.orderProductTableName + " op\n" +
                            "ON od.id = op.\"orderDetailId\"\n" +
                            "WHERE od.id = ?\n" +
                            "GROUP by od.id;"
            );
            statement.setLong(1, id);
            List<OrderDetailDTO> dtos = mapper.listFromResult(statement.executeQuery());
            if (dtos.isEmpty())
                throw new SQLException("There are no order details with ID =" + id);
            return dtos.get(0);
        }catch (SQLException e){
            System.out.println("Could not find order details by ID, SQLException: " + e);
            return null;
        }
    }

    @Override
    public List<OrderDetailDTO> getAll(){
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT od.id, od.\"totalAmount\", od.\"orderStatus\", array_agg(op.\"productId\") \"productIds\"" +
                            " FROM " + DatabaseConfig.orderDetailTableName + " od\n" +
                            "JOIN " + DatabaseConfig.orderProductTableName + " op\n" +
                            "ON od.id = op.\"orderDetailId\"\n" +
                            "GROUP by od.id;"
            );
            return mapper.listFromResult(statement.executeQuery());
        }catch (SQLException e){
            System.out.println("Could not get all order details, SQLException: " + e);
            return null;
        }
    }

    @Override
    public void update(List<OrderDetailDTO> orderDetails){
    }

    @Override
    public void delete(Long id){
        try {
            if (connection.createStatement().executeUpdate(
                    "DELETE FROM " + DatabaseConfig.orderProductTableName +
                            " WHERE \"orderDetailId\"=" + id) != 1) throw new SQLException();

            defaultDelete(connection, DatabaseConfig.orderDetailTableName, id);
        } catch (SQLException e){
            System.out.println("Could not delete order details, SQLException: " + e);
        }
    }

    private static StringBuilder insertStringBuilder(List<OrderDetailDTO> orderDetails) {
        StringBuilder insertDetailsQuerry = new StringBuilder(
                "INSERT INTO " + DatabaseConfig.orderDetailTableName +
                        "(\"totalAmount\", \"orderStatus\") VALUES\n");

        for (OrderDetailDTO orderDetail : orderDetails) {
            insertDetailsQuerry
                    .append("((")
                    .append(orderDetail.getTotalAmount())
                    .append("::numeric), ('")
                    .append(orderDetail.getOrderStatus().name())
                    .append("')),\n");
        }
        insertDetailsQuerry.replace(insertDetailsQuerry.length() - 2, insertDetailsQuerry.length(), "\nRETURNING id;");
        return insertDetailsQuerry;
    }

}
