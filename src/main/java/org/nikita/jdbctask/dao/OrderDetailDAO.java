package org.nikita.jdbctask.dao;

import org.nikita.jdbctask.DatabaseConfig;
import org.nikita.jdbctask.dto.OrderDetailDTO;
import org.nikita.jdbctask.dto.ProductDTO;
import org.nikita.jdbctask.interfaces.DAO;
import org.nikita.jdbctask.mapper.dto.OrderDetailDTOMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for OrderDetail entity.
 * Works with DTOs
 */
public class OrderDetailDAO implements DAO<OrderDetailDTO> {
    private final OrderDetailDTOMapper mapper = new OrderDetailDTOMapper();
    private final Connection connection;

    /**
     * @param connection custom DB connection for testing
     */
    public OrderDetailDAO(Connection connection) {
        this.connection = connection;
    }

    public OrderDetailDAO() {
        this.connection = DatabaseConfig.getConnection();
    }

    /**
     * @param orderDetails List of OrderDetailDTO to be inserted into a database
     */
    @Override
    public List<Long> create(List<OrderDetailDTO> orderDetails) {
        try {
            PreparedStatement insertDetailsStatement = connection.prepareStatement(
                    insertQueryBuilder(orderDetails));

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

    /**
     * @param id ID of a record that you want to retrieve
     * @return DTO of the OrderDetail entity
     */
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

    /**
     * @return All OrderApproval entities that are currently stored in the DB
     */
    @Override
    public List<OrderDetailDTO> getAll() {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT od.id, od.\"totalAmount\", od.\"orderStatus\", array_agg(op.\"productId\") \"productIds\"" +
                            " FROM " + DatabaseConfig.orderDetailTableName + " od\n" +
                            "JOIN " + DatabaseConfig.orderProductTableName + " op\n" +
                            "ON od.id = op.\"orderDetailId\"\n" +
                            "GROUP by od.id\n" +
                            "ORDER by od.id;"
            );
            return mapper.listFromResult(statement.executeQuery(), connection);
        } catch (SQLException e) {
            System.out.println("Could not get all order details, SQLException: " + e);
            return null;
        }
    }

    /**
     * @param orderDetails List of OrderDetail DTOs to be updated
     */
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

    /**
     * @param id ID of the OrderDetail you want to be deleted
     */
    @Override
    public void delete(Long id) {
        defaultDelete(connection, DatabaseConfig.orderDetailTableName, id);
    }


    /**
     * Custom SQL query generator which adds statement to return IDs
     * @param orderDetails List of OrderDetail DTOs to be inserted
     * @return SQL query
     */
    private String insertQueryBuilder(List<OrderDetailDTO> orderDetails) {
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

    /**
     * Query for inserting into a link table to facilitate Many-to-Many relationship between OrderDetail and Product entities
     * @param orderDetails List of OrderDetail DTOs, Products of which to be linked
     * @throws SQLException Error when inserting
     */
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

    /**
     * @param resultSet Result set after inserting a list of OrderDetail entities
     * @return List of IDs of inserted OrderDetail entities
     */
    private List<Long> returnId(ResultSet resultSet) {
        try {
            List<Long> insertedIds = new ArrayList<>();
            while (resultSet.next()) {
                insertedIds.add(resultSet.getLong(1));
            }
            return insertedIds;
        } catch (SQLException e) {
            System.out.println("Could not get return IDs. SQLException: " + e);
            return null;
        }
    }
}
