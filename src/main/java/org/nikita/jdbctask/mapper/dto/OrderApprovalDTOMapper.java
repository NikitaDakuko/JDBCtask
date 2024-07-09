package org.nikita.jdbctask.mapper.dto;

import org.nikita.jdbctask.DatabaseConfig;
import org.nikita.jdbctask.dto.OrderApprovalDTO;
import org.nikita.jdbctask.dto.OrderDetailDTO;
import org.nikita.jdbctask.dto.ProductDTO;
import org.nikita.jdbctask.enums.OrderStatus;
import org.nikita.jdbctask.interfaces.DTOmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderApprovalDTOMapper implements DTOmapper<OrderApprovalDTO> {
    @Override
    public OrderApprovalDTO fromResult(ResultSet resultSet) {
        return null;
    }

    @Override
    public List<OrderApprovalDTO> listFromResult(ResultSet resultSet) {
        List<OrderApprovalDTO> dtos = new ArrayList<>();
        try {
            String idString = DatabaseConfig.orderApprovalTableName + ".id";

            long currentId = -1L;
            List<ProductDTO> currentProducts = new ArrayList<>();
            OrderDetailDTO currentBase = null;

            while (resultSet.next()){
                if(resultSet.getLong(idString) != currentId) {
                    if (currentId != -1L)
                        dtos.add(combine(currentId, currentBase, currentProducts));

                    currentId = resultSet.getLong(idString);
                    currentBase = new OrderDetailDTO(
                            resultSet.getLong(DatabaseConfig.orderDetailTableName+".id"),
                            OrderStatus.valueOf(
                                    resultSet.getString(DatabaseConfig.orderDetailTableName+".orderStatus")),
                            null,
                            resultSet.getBigDecimal(DatabaseConfig.orderDetailTableName+".totalAmount")
                    );
                    currentProducts.add(addProduct(resultSet));
                }
                else {
                    currentProducts.add(addProduct(resultSet));
                }
            }
            assert currentBase != null;
            dtos.add(combine(currentId, currentBase, currentProducts));

            return dtos;
        }
        catch (SQLException e) {
            System.out.println("SQLException while parsing resultSet: "+ e.getMessage());
        }


        return null;
    }

    private ProductDTO addProduct(ResultSet resultSet){
        try {
            return new ProductDTO(
                    resultSet.getLong(DatabaseConfig.productTableName + ".id"),
                    resultSet.getString(DatabaseConfig.productTableName + ".name"),
                    resultSet.getBigDecimal(DatabaseConfig.productTableName + ".price"),
                    resultSet.getInt(DatabaseConfig.productTableName + ".quantity"),
                    resultSet.getBoolean(DatabaseConfig.productTableName + ".available")
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private OrderApprovalDTO combine(Long id, OrderDetailDTO base, List<ProductDTO> products){
        return new OrderApprovalDTO(
                id,
                new OrderDetailDTO(
                        base.getId(),
                        base.getOrderStatus(),
                        products,
                        base.getTotalAmount()
                )
        );
    }
}
