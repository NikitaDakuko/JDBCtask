package org.nikita.jdbctask.mapper.dto;

import org.nikita.jdbctask.dao.ProductDAO;
import org.nikita.jdbctask.dto.ProductDTO;
import org.nikita.jdbctask.interfaces.DTOmapper;
import org.postgresql.util.PGmoney;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDTOMapper implements DTOmapper<ProductDTO> {
    @Override
    public ProductDTO fromResult(ResultSet resultSet){
        try {
            return new ProductDTO(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    new PGmoney(resultSet
                            .getString("price")
                            .substring(2)
                            .replace(",", "")),
                    resultSet.getInt("quantity"),
                    resultSet.getBoolean("available")
            );
        }
        catch (SQLException e) {
            System.out.println("SQLException: "+ e.getMessage());
        }
        return null;
    }

    public List<ProductDTO> parseProductsString(String productsString){
        List<Long> ids = new ArrayList<>();
        for (String s:productsString.replaceAll("\\s+","").split(",")){
            ids.add(Long.parseLong(s));
        }
        return listFromResult(new ProductDAO().getMultiple(ids));
    }
}
