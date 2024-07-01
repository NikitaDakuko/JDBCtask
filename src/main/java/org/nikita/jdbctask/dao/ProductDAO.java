package org.nikita.jdbctask.dao;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.nikita.jdbctask.entity.Product;

import java.util.List;

@Stateless
public class ProductDAO {
    @PersistenceContext(unitName = "restapi_PU")
    static EntityManager entityManager;

    public static void create(Product product){
        entityManager.persist(product);
    }

    public static List<Product> getAll(){
        return entityManager.createNamedQuery("product.findAll", Product.class).getResultList();
    }

    public static Product findById(long id){
        return entityManager.find(Product.class, id);
    }

    public static void update(Product product){
        entityManager.merge(product);
    }

    public static void delete(Product product){
        if (entityManager.contains(product))
            entityManager.remove(product);
    }

    public static void delete(Long id){
        delete(findById(id));
    }


}
