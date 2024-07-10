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
    public List<Long> create(List<OrderApprovalDTO> orderApprovals) throws SQLException {
        List<OrderDetailDTO> orderDetailDTOS = new ArrayList<>();
        for (OrderApprovalDTO orderApproval : orderApprovals)
            orderDetailDTOS.add(orderApproval.getOrderDetail());

        List<Long> insertedOrderDetailIds = orderDetailDAO.create(orderDetailDTOS);

        if (insertedOrderDetailIds.size() == orderApprovals.size()) {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO " + DatabaseConfig.orderApprovalTableName + "(\n" +
                            "id, \"orderDetailId\")\n" +
                            "VALUES (?, ?)");

            for (int i = 0; i < orderApprovals.size(); i++) {
                statement.setLong(1, orderApprovals.get(i).getId());
                statement.setLong(2, insertedOrderDetailIds.get(i));
                statement.addBatch();
            }
            if (statement.executeUpdate() != 1) throw new SQLException();
        } else throw new SQLException("Could not insert all order details");
        return null;
    }

    @Override
    public OrderApprovalDTO findById(Long id) throws SQLException {
        return mapper.fromResult(defaultFindById(connection, DatabaseConfig.orderApprovalTableName, id));
    }

    @Override
    public List<OrderApprovalDTO> getAll() throws SQLException {
        return mapper.listFromResult(defaultGetAll(connection, DatabaseConfig.orderApprovalTableName));
    }

    @Override
    public void update(List<OrderApprovalDTO> orderApprovals) throws SQLException {
    }

    @Override
    public void delete(Long id) throws SQLException {
        defaultDelete(connection, DatabaseConfig.orderApprovalTableName, id);
    }
}
