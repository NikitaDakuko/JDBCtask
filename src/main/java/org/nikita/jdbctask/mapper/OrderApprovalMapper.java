package org.nikita.jdbctask.mapper;

import org.nikita.jdbctask.dto.OrderApprovalDTO;
import org.nikita.jdbctask.entity.OrderApproval;
import org.nikita.jdbctask.entity.OrderDetail;
import org.nikita.jdbctask.interfaces.Mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class OrderApprovalMapper implements Mapper<OrderApproval, OrderApprovalDTO> {
    @Override
    public OrderApproval fromDTO(OrderApprovalDTO orderApprovalDTO) {
        return new OrderApproval(
                orderApprovalDTO.getId(),
                orderApprovalDTO.getOrderId(),
                orderApprovalDTO.getOrderDetail()
        );
    }

    @Override
    public OrderApprovalDTO toDTO(OrderApproval orderApproval) {
        return new OrderApprovalDTO(
                orderApproval.getOrderId(),
                orderApproval.getOrderId(),
                orderApproval.getOrderDetail()
        );
    }

    @Override
    public OrderApprovalDTO fromResult(ResultSet resultSet) {
        try {
            return new OrderApprovalDTO(
                    resultSet.getLong("id"),
                    resultSet.getLong("orderId"),
                    resultSet.getObject("orderDetail", OrderDetail.class)
            );
        }
        catch (SQLException e) {
            System.out.println("SQLException: "+ e.getMessage());
        }
        return null;
    }

    @Override
    public List<OrderApprovalDTO> listFromResult(ResultSet resultSet) {
        return List.of();
    }
}
