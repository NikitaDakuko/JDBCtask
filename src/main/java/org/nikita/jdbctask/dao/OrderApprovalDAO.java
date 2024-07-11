package org.nikita.jdbctask.dao;

import org.nikita.jdbctask.DatabaseConfig;
import org.nikita.jdbctask.dto.OrderApprovalDTO;
import org.nikita.jdbctask.dto.OrderDetailDTO;
import org.nikita.jdbctask.interfaces.DAO;
import org.nikita.jdbctask.mapper.dto.OrderApprovalDTOMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for OrderApproval entity.
 * Works with DTOs
 */
public class OrderApprovalDAO implements DAO<OrderApprovalDTO> {
    private final OrderDetailDAO orderDetailDAO;
    private final OrderApprovalDTOMapper mapper = new OrderApprovalDTOMapper();
    private final Connection connection;

    /**
     * @param connection custom DB connection for testing
     */
    public OrderApprovalDAO(Connection connection) {
        this.connection = connection;
        this.orderDetailDAO = new OrderDetailDAO(connection);
    }

    public OrderApprovalDAO() {
        this.connection = DatabaseConfig.getConnection();
        this.orderDetailDAO = new OrderDetailDAO(connection);
    }

    /**
     * @param orderApprovals List of OrderApprovalDTO to be inserted into a database
     */
    @Override
    public List<Long> create(List<OrderApprovalDTO> orderApprovals) {
        try {
            List<OrderDetailDTO> orderDetailDTOS = new ArrayList<>();
            for (OrderApprovalDTO orderApproval : orderApprovals)
                orderDetailDTOS.add(orderApproval.getOrderDetail());

            List<Long> insertedOrderDetailIds = orderDetailDAO.create(orderDetailDTOS);

            if (insertedOrderDetailIds.size() == orderApprovals.size()) {
                PreparedStatement statement = connection.prepareStatement(
                        "INSERT INTO " + DatabaseConfig.orderApprovalTableName + "(\n" +
                                "\"orderDetailId\")\n" +
                                "VALUES (?)");

                for (Long id:insertedOrderDetailIds){
                    statement.setLong(1, id);
                    statement.addBatch();
                }
                statement.executeBatch();
            }
        } catch (SQLException e) {
            System.out.println("Could not create order approval, SQLException: " + e);
        }
        return null;
    }

    /**
     * @param id ID of a record that you want to retrieve
     * @return DTO of the OrderApproval entity
     */
    @Override
    public OrderApprovalDTO findById(Long id) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT oa.id, oa.\"orderDetailId\", od.\"totalAmount\", od.\"orderStatus\", array_agg(op.\"productId\") \"productIds\"" +
                            " FROM " + DatabaseConfig.orderApprovalTableName + " oa\n" +
                            "JOIN " + DatabaseConfig.orderDetailTableName + " od\n" +
                            "ON od.id = oa.\"orderDetailId\"" +
                            "JOIN " + DatabaseConfig.orderProductTableName + " op\n" +
                            "ON oa.\"orderDetailId\" = op.\"orderDetailId\"\n" +
                            "WHERE oa.id = ?\n" +
                            "GROUP by oa.\"orderDetailId\", od.\"orderStatus\",od.\"totalAmount\", oa.id;"
            );
            statement.setLong(1, id);
            List<OrderApprovalDTO> dtos = mapper.listFromResult(statement.executeQuery(), connection);
            if (dtos.isEmpty())
                throw new SQLException("There are no order approvals with ID =" + id);
            return dtos.get(0);
        } catch (SQLException e) {
            System.out.println("Could not get order approval by ID, SQLException: " + e);
            return null;
        }
    }

    /**
     * @return All OrderApproval entities that are currently stored in the DB
     */
    @Override
    public List<OrderApprovalDTO> getAll() {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT oa.id, oa.\"orderDetailId\", od.\"totalAmount\", od.\"orderStatus\", array_agg(op.\"productId\") \"productIds\"" +
                            " FROM " + DatabaseConfig.orderApprovalTableName + " oa\n" +
                            "JOIN " + DatabaseConfig.orderDetailTableName + " od\n" +
                            "ON od.id = oa.\"orderDetailId\"" +
                            "JOIN " + DatabaseConfig.orderProductTableName + " op\n" +
                            "ON oa.\"orderDetailId\" = op.\"orderDetailId\"\n" +
                            "GROUP by oa.\"orderDetailId\", od.\"orderStatus\",od.\"totalAmount\", oa.id\n" +
                            "ORDER by oa.id;"
            );
            return mapper.listFromResult(statement.executeQuery(), connection);
        } catch (SQLException e) {
            System.out.println("Could not get order approvals, SQLException: " + e);
            return null;
        }
    }

    /**
     * @param orderApprovals List of OrderApproval DTOs to be updated
     */
    @Override
    public void update(List<OrderApprovalDTO> orderApprovals) {
        List<OrderDetailDTO> orderDetails = new ArrayList<>();
        for (OrderApprovalDTO oa : orderApprovals)
            orderDetails.add(oa.getOrderDetail());

        new OrderDetailDAO(connection).update(orderDetails);
    }

    /**
     * @param id ID of the OrderApproval you want to be deleted
     */
    @Override
    public void delete(Long id) {
        defaultDelete(connection, DatabaseConfig.orderApprovalTableName, id);
    }
}