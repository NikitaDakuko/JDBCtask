package org.nikita.jdbctask.mapper.dto;

import org.nikita.jdbctask.dto.ProductDTO;
import org.nikita.jdbctask.interfaces.DTOmapper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDTOMapper implements DTOmapper<ProductDTO> {
    @Override
    public ProductDTO fromResult(ResultSet resultSet, Connection connection){
        return fromResult(resultSet);
    }

    @Override
    public List<ProductDTO> listFromResult (ResultSet resultSet, Connection connection){
        return listFromResult(resultSet);
    }

    public List<ProductDTO> listFromResult (ResultSet resultSet){
        List<ProductDTO> dtos = new ArrayList<>();
        try {
            while (resultSet.next()){
                dtos.add(fromResult(resultSet));
            }
        }
        catch (SQLException e) {
            System.out.println("SQLException while parsing resultSet: "+ e.getMessage());
        }
        return dtos;
    }

    public ProductDTO fromResult(ResultSet resultSet){
        try {
            if (resultSet != null){
                return new ProductDTO(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getBigDecimal("price"),
                        resultSet.getInt("quantity"),
                        resultSet.getBoolean("available")
                );
            }
        }
        catch (SQLException e) {
            System.out.println("SQLException while parsing product: " + e.getMessage());
        }
        return null;
    }
}
