package org.nikita.jdbctask.mapper.entity;

import org.nikita.jdbctask.dto.OrderDetailDTO;
import org.nikita.jdbctask.entity.OrderDetail;
import org.nikita.jdbctask.interfaces.EntityMapper;

public class OrderDetailEntityMapper implements EntityMapper<OrderDetail, OrderDetailDTO> {
    @Override
    public OrderDetail fromDTO(OrderDetailDTO orderDetailDTO) {
        ProductEntityMapper productMapper = new ProductEntityMapper();

        return new OrderDetail(
                orderDetailDTO.getId(),
                orderDetailDTO.getOrderStatus(),
                productMapper.fromDTO(orderDetailDTO.getProducts()),
                orderDetailDTO.getTotalAmount()
        );
    }

    @Override
    public OrderDetailDTO toDTO(OrderDetail orderDetail) {
        ProductEntityMapper productMapper = new ProductEntityMapper();

        return new OrderDetailDTO(
                orderDetail.getId(),
                orderDetail.getOrderStatus(),
                productMapper.toDTO(orderDetail.getProducts()),
                orderDetail.getTotalAmount()
        );
    }
}
