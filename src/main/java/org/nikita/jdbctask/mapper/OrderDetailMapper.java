package org.nikita.jdbctask.mapper;

import org.nikita.jdbctask.dto.OrderDetailDTO;
import org.nikita.jdbctask.dto.ProductDTO;
import org.nikita.jdbctask.entity.OrderDetail;
import org.nikita.jdbctask.entity.OrderStatus;
import org.nikita.jdbctask.interfaces.Mapper;
import org.postgresql.util.PGmoney;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class OrderDetailMapper implements Mapper<OrderDetail, OrderDetailDTO> {
    @Override
    public OrderDetail fromDTO(OrderDetailDTO orderDetailDTO) {
        ProductMapper productMapper = new ProductMapper();

        return new OrderDetail(
                orderDetailDTO.getId(),
                orderDetailDTO.getOrderStatus(),
                productMapper.fromDTO(orderDetailDTO.getProducts()),
                orderDetailDTO.getTotalAmount()
        );
    }

    @Override
    public OrderDetailDTO toDTO(OrderDetail orderDetail) {
        ProductMapper productMapper = new ProductMapper();

        return new OrderDetailDTO(
                orderDetail.getId(),
                orderDetail.getOrderStatus(),
                productMapper.toDTO(orderDetail.getProducts()),
                orderDetail.getTotalAmount()
        );
    }

    @Override
    public OrderDetailDTO fromResult(ResultSet resultSet) throws SQLException {
        return new OrderDetailDTO(
                resultSet.getLong("id"),
                OrderStatus.valueOf(resultSet.getString("oderStatus")),
                (List<ProductDTO>) resultSet.getArray("products"),
                new PGmoney(resultSet
                        .getString("totalAmount")
                        .substring(2)
                        .replace(",", ""))
        );
    }
}
