package org.nikita.jdbctask.mapper.dto;

import org.nikita.jdbctask.dto.OrderProductDTO;
import org.nikita.jdbctask.interfaces.DTOmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderProductDTOMapper implements DTOmapper<OrderProductDTO> {
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
