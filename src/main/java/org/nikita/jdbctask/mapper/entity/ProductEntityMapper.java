package org.nikita.jdbctask.mapper.entity;

import org.nikita.jdbctask.dto.ProductDTO;
import org.nikita.jdbctask.entity.Product;
import org.nikita.jdbctask.interfaces.EntityMapper;

import java.util.ArrayList;
import java.util.List;

public class ProductEntityMapper implements EntityMapper<Product, ProductDTO> {
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
