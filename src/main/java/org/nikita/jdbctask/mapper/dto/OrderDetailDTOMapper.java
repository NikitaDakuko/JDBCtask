package org.nikita.jdbctask.mapper.dto;

import org.nikita.jdbctask.dao.ProductDAO;
import org.nikita.jdbctask.dto.OrderDetailDTO;
import org.nikita.jdbctask.enums.OrderStatus;
import org.nikita.jdbctask.interfaces.DTOmapper;
import org.postgresql.util.PGmoney;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class OrderDetailDTOMapper implements DTOmapper<OrderDetailDTO> {
    @Override
    public OrderDetailDTO fromResult(ResultSet resultSet) {
        try {
            return new OrderDetailDTO(
                    resultSet.getLong("id"),
                    OrderStatus.valueOf(resultSet.getString("oderStatus")),
                    new ProductDAO()
                            .getMultiple((List<Long>) resultSet.getArray("products")),
                    new PGmoney(resultSet
                            .getString("totalAmount")
                            .substring(2)
                            .replace(",", ""))
            );
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
        return null;
    }
}
