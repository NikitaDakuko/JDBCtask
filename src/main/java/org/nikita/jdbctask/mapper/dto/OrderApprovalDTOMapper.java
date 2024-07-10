package org.nikita.jdbctask.mapper.dto;

import org.nikita.jdbctask.dao.ProductDAO;
import org.nikita.jdbctask.dto.OrderApprovalDTO;
import org.nikita.jdbctask.dto.OrderDetailDTO;
import org.nikita.jdbctask.enums.OrderStatus;
import org.nikita.jdbctask.interfaces.DTOmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class OrderApprovalDTOMapper implements DTOmapper<OrderApprovalDTO> {
    @Override
    public OrderApprovalDTO fromResult(ResultSet resultSet) {
        try {
            if (resultSet != null){
                return new OrderApprovalDTO(
                        resultSet.getLong("id"),
                        new OrderDetailDTO(
                                resultSet.getLong("orderDetailId"),
                                OrderStatus.valueOf(resultSet.getString("orderStatus")),
                                new ProductDAO().getMultiple(
                                        new ArrayList<>(Arrays.asList((Long[]) resultSet.getArray("productIds").getArray()))),
                                resultSet.getBigDecimal("totalAmount")
                ));
            }
        }
        catch (SQLException e) {
            System.out.println("SQLException while parsing order approval: " + e.getMessage());
        }
        return null;
    }
}
