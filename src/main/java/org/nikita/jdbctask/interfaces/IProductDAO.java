package org.nikita.jdbctask.interfaces;

import org.nikita.jdbctask.entity.Product;

import java.util.List;

public interface IProductDAO {

    void create(Product product);

    Product findById(Long id);

    List<Product> getAll();

    void update(Product product);

    void delete(Product product);

    default void delete(Long id){
        delete(findById(id));
    }
}
