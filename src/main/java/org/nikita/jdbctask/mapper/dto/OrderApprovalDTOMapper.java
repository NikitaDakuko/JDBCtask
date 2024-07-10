package org.nikita.jdbctask.mapper.dto;

import org.nikita.jdbctask.dao.OrderDetailDAO;
import org.nikita.jdbctask.dto.OrderApprovalDTO;
import org.nikita.jdbctask.interfaces.DTOmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderApprovalDTOMapper implements DTOmapper<OrderApprovalDTO> {
    @Override
    public OrderApprovalDTO fromResult(ResultSet resultSet) {
        try {
            if (resultSet != null){
                return new OrderApprovalDTO(
                        resultSet.getLong("id"),
                        new OrderDetailDAO().findById(resultSet.getLong("orderDetailId"))
                );
            }
        }
        catch (SQLException e) {
            System.out.println("SQLException while parsing order approval: " + e.getMessage());
        }
        return null;
    }
}
