package org.nikita.jdbctask.dao;

import org.nikita.jdbctask.DatabaseConfig;
import org.nikita.jdbctask.dto.OrderDetailDTO;
import org.nikita.jdbctask.dto.ProductDTO;
import org.nikita.jdbctask.interfaces.DAO;
import org.nikita.jdbctask.mapper.dto.OrderDetailDTOMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
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
    public List<Long> create(List<OrderDetailDTO> orderDetails) {
        try {
            PreparedStatement insertDetailsStatement = connection.prepareStatement(
                    insertQuerryBuilder(orderDetails));

            List<Long> ids = returnId(insertDetailsStatement.executeQuery());

            if (ids.size() == orderDetails.size()) {
                List<OrderDetailDTO> orderDetailsWithIds = new ArrayList<>();
                for (int i = 0; i < ids.size(); i++)
                    orderDetailsWithIds.add(
                            new OrderDetailDTO(
                                    ids.get(i),
                                    orderDetails.get(i).getOrderStatus(),
                                    orderDetails.get(i).getProducts(),
                                    orderDetails.get(i).getTotalAmount()
                            )
                    );
                createOrderProducts(orderDetailsWithIds);
                return ids;
            }
        } catch (SQLException e) {
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
            List<OrderDetailDTO> dtos = mapper.listFromResult(statement.executeQuery(), connection);
            if (dtos.isEmpty())
                throw new SQLException("There are no order details with ID =" + id);
            return dtos.get(0);
        } catch (SQLException e) {
            System.out.println("Could not find order details by ID, SQLException: " + e);
            return null;
        }
    }

    @Override
    public List<OrderDetailDTO> getAll() {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT od.id, od.\"totalAmount\", od.\"orderStatus\", array_agg(op.\"productId\") \"productIds\"" +
                            " FROM " + DatabaseConfig.orderDetailTableName + " od\n" +
                            "JOIN " + DatabaseConfig.orderProductTableName + " op\n" +
                            "ON od.id = op.\"orderDetailId\"\n" +
                            "GROUP by od.id\n" +
                            "ORDER by oa.id;"
            );
            return mapper.listFromResult(statement.executeQuery(), connection);
        } catch (SQLException e) {
            System.out.println("Could not get all order details, SQLException: " + e);
            return null;
        }
    }

    @Override
    public void update(List<OrderDetailDTO> orderDetails) {
        try {
            PreparedStatement updateOrderDetail = connection.prepareStatement(
                    "UPDATE " + DatabaseConfig.orderDetailTableName +
                            "\nSET \"totalAmount\"=?, \"orderStatus\"=?" +
                            "\nWHERE id=?"
            );

            PreparedStatement deleteOrderProduct = connection.prepareStatement(
                    "DELETE FROM " + DatabaseConfig.orderProductTableName +
                            "\n WHERE \"orderDetailId\" = ?"
            );

            for (OrderDetailDTO dto : orderDetails) {
                updateOrderDetail.setBigDecimal(1, dto.getTotalAmount());
                updateOrderDetail.setString(2, dto.getOrderStatus().name());
                updateOrderDetail.setLong(3, dto.getId());
                updateOrderDetail.addBatch();;

                deleteOrderProduct.setLong(1, dto.getId());
                deleteOrderProduct.addBatch();
            }
            updateOrderDetail.executeUpdate();
            deleteOrderProduct.execute();
            createOrderProducts(orderDetails);

        } catch (SQLException e) {
            System.out.println("Could not update order details, SQLException: " + e);
        }
    }

    @Override
    public void delete(Long id) {
        defaultDelete(connection, DatabaseConfig.orderDetailTableName, id);
    }

    private String insertQuerryBuilder(List<OrderDetailDTO> orderDetails) {
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
        return insertDetailsQuerry.toString();
    }

    private void createOrderProducts(List<OrderDetailDTO> orderDetails) throws SQLException {
        PreparedStatement orderProductStatement = connection.prepareStatement(
                "INSERT INTO " + DatabaseConfig.orderProductTableName + "(\n" +
                        "\"orderDetailId\", \"productId\")\n" +
                        "VALUES (?, ?);"
        );

        for (OrderDetailDTO od : orderDetails) {
            for (ProductDTO p : od.getProducts()) {
                orderProductStatement.setLong(1, od.getId());
                orderProductStatement.setLong(2, p.getId());
                orderProductStatement.addBatch();
            }
            orderProductStatement.executeBatch();
        }
    }
}
