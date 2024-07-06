package org.nikita.jdbctask.mapper.entity;

import org.nikita.jdbctask.dto.OrderApprovalDTO;
import org.nikita.jdbctask.entity.OrderApproval;
import org.nikita.jdbctask.interfaces.EntityMapper;

public class OrderApprovalEntityMapper implements EntityMapper<OrderApproval, OrderApprovalDTO> {
    @Override
    public OrderApproval fromDTO(OrderApprovalDTO orderApprovalDTO) {
        return new OrderApproval(
                orderApprovalDTO.getId(),
                new OrderDetailEntityMapper().fromDTO(orderApprovalDTO.getOrderDetail())
        );
    }

    @Override
    public OrderApprovalDTO toDTO(OrderApproval orderApproval) {
        return new OrderApprovalDTO(
                orderApproval.getId(),
                new OrderDetailEntityMapper().toDTO(orderApproval.getOrderDetail())
        );
    }
}
