package org.nikita.jdbctask.mapper;

import org.nikita.jdbctask.dto.ProductDTO;
import org.nikita.jdbctask.entity.Product;
import org.nikita.jdbctask.interfaces.Mapper;
import org.postgresql.util.PGmoney;

import java.sql.ResultSet;
import java.sql.SQLException;

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
    public ProductDTO fromEntity(Product product) {
        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getQuantity(),
                product.getAvailability()
        );
    }

    @Override
    public ProductDTO fromResult(ResultSet result) throws SQLException {
        return new ProductDTO(
                result.getLong("id"),
                result.getString("name"),
                new PGmoney(result
                        .getString("price")
                        .substring(2)
                        .replace(",", "")),
                result.getInt("quantity"),
                result.getBoolean("available")
        );
    }
}
