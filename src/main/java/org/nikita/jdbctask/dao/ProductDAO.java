package org.nikita.jdbctask.dao;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.nikita.jdbctask.entity.Product;

import java.util.List;

@Stateless
public class ProductDAO {
    @PersistenceContext(unitName = "restapi_PU")
    EntityManager entityManager;

    public void create(Product product){
        entityManager.persist(product);
    }

    public List<Product> getAll(){
        return entityManager.createNamedQuery("product.findAll", Product.class).getResultList();
    }

    public Product findById(long id){
        return entityManager.find(Product.class, id);
    }

    public void update(Product product){
        entityManager.merge(product);
    }

    public void delete(Product product){
        if (entityManager.contains(product))
            entityManager.remove(product);
    }


}
