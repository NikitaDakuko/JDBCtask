package org.nikita.jdbctask.mapper.dto;

import org.nikita.jdbctask.dao.ProductDAO;
import org.nikita.jdbctask.dto.ProductDTO;
import org.nikita.jdbctask.interfaces.DTOmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDTOMapper implements DTOmapper<ProductDTO> {
    @Override
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

    public List<ProductDTO> parseProductsString(String productsString){
        List<Long> ids = new ArrayList<>();
        for (String s:productsString.replaceAll("\\s+","").split(",")){
            ids.add(Long.parseLong(s));
        }
        return new ProductDAO().getMultiple(ids);
    }
}
