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

public class OrderApprovalDAO implements DAO<OrderApprovalDTO> {
    private final OrderDetailDAO orderDetailDAO;
    private final OrderApprovalDTOMapper mapper = new OrderApprovalDTOMapper();
    private final Connection connection;

    public OrderApprovalDAO(Connection connection) {
        this.connection = connection;
        this.orderDetailDAO = new OrderDetailDAO(connection);
    }

    public OrderApprovalDAO() {
        this.connection = DatabaseConfig.getConnection();
        this.orderDetailDAO = new OrderDetailDAO(connection);
    }

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

                for (int i = 0; i < orderApprovals.size(); i++) {
                    statement.setLong(1, insertedOrderDetailIds.get(i));
                    statement.addBatch();
                }
                if (statement.executeUpdate() != 1) throw new SQLException();
            }
        } catch (SQLException e) {
            System.out.println("Could not create order approval by ID, SQLException: " + e);
        }
        return null;
    }

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
            List<OrderApprovalDTO> dtos = mapper.listFromResult(statement.executeQuery());
            if (dtos.isEmpty())
                throw new SQLException("There are no order approvals with ID =" + id);
            return dtos.get(0);
        } catch (SQLException e) {
            System.out.println("Could not get order approval by ID, SQLException: " + e);
            return null;
        }
    }

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
                            "GROUP by oa.\"orderDetailId\";"
            );
            return mapper.listFromResult(statement.executeQuery());
        } catch (SQLException e) {
            System.out.println("Could not get order approvals, SQLException: " + e);
            return null;
        }
    }

    @Override
    public void update(List<OrderApprovalDTO> orderApprovals) {
    }

    @Override
    public void delete(Long id) {
        defaultDelete(connection, DatabaseConfig.orderApprovalTableName, id);
    }
}