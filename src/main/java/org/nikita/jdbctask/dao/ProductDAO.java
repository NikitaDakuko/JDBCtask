package org.nikita.jdbctask.dao;

import org.nikita.jdbctask.entity.Product;
import org.nikita.jdbctask.interfaces.IProductDAO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductDAO implements IProductDAO {
    private final Map<Long, Product> db = new HashMap<>();

    @Override
    public void create(Product product){
        db.put(product.getId(), product);
    }

    @Override
    public List<Product> getAll(){
        return db.values().stream().toList();
    }

    @Override
    public Product findById(Long id){
        return db.get(id);
    }

    @Override
    public void update(Product product){
        db.put(product.getId(), product);
    }

    @Override
    public void delete(Product product){
        db.remove(product.getId());
    }
}
