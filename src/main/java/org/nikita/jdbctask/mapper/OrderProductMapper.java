package org.nikita.jdbctask.mapper;

import org.nikita.jdbctask.dto.OrderProductDTO;
import org.nikita.jdbctask.interfaces.Mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderProductMapper implements Mapper<List<Long>, OrderProductDTO> {
    @Override
    public List<Long> fromDTO(OrderProductDTO dto) {
        return List.of();
    }

    @Override
    public OrderProductDTO toDTO(List<Long> longs) {
        return null;
    }

    @Override
    public OrderProductDTO fromResult(ResultSet resultSet) {
        try {
            List<Long> products = new ArrayList<>();
            long id, orderId;

            if (resultSet.next()){
                id = resultSet.getLong("id");
                orderId = resultSet.getLong("orderId");
                products.add(resultSet.getLong("productId"));

                while (resultSet.next())
                    products.add(resultSet.getLong("productId"));

                return new OrderProductDTO(id, orderId, products);
            }
            throw new SQLException();

        } catch (SQLException e) {
            System.out.println("SQLException: "+ e.getMessage());
        }
        return null;
    }

    @Override
    public List<OrderProductDTO> listFromResult(ResultSet resultSet){
        return null;
    }
}
