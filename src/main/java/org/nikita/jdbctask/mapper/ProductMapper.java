package org.nikita.jdbctask.mapper;

import org.nikita.jdbctask.dto.ProductDTO;
import org.nikita.jdbctask.entity.Product;
import org.nikita.jdbctask.interfaces.Mapper;
import org.postgresql.util.PGmoney;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductMapper implements Mapper<Product, ProductDTO> {
    @Override
    public Product fromDTO(ProductDTO dto){
        return new Product(
                dto.getId(),
                dto.getName(),
                dto.getPrice(),
                dto.getQuantity(),
                dto.getAvailability()
        );
    }

    @Override
    public ProductDTO toDTO(Product product) {
        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getQuantity(),
                product.getAvailability()
        );
    }

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

    public List<ProductDTO> toDTO(List<Product> products){
        List<ProductDTO> productList = new ArrayList<>();

        for(Product product:products)
            productList.add(toDTO(product));

        return productList;
    }

    public List<Product> fromDTO(List<ProductDTO> products){
        List<Product> productList = new ArrayList<>();

        for(ProductDTO dto:products)
            productList.add(fromDTO(dto));

        return productList;
    }
}
