package org.nikita.jdbctask.mapper.dto;

import org.nikita.jdbctask.dto.OrderApprovalDTO;
import org.nikita.jdbctask.dto.OrderDetailDTO;
import org.nikita.jdbctask.interfaces.DTOmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class OrderApprovalDTOMapper implements DTOmapper<OrderApprovalDTO> {
    @Override
    public OrderApprovalDTO fromResult(ResultSet resultSet) {
        try {
            return new OrderApprovalDTO(
                    resultSet.getLong("id"),
                    resultSet.getObject("orderDetail", OrderDetailDTO.class)
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
