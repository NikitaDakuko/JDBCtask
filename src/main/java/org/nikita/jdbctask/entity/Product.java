package org.nikita.jdbctask.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

@Entity
@Table(name = "products")
@NamedQueries({
        @NamedQuery(name = "products.findAll", query = "SELECT p FROM products p")
})
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    //private PGmoney price;
    private int quantity;
    private boolean available;

    public Product(Long id, String name,  int quantity, boolean available){
        this(name, quantity, available);
        this.id = id;
    }

    public Product(String name, int quantity, boolean available) {
        this.name = name;
        //this.price = price;
        this.quantity = quantity;
        this.available = available;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

//    public PGmoney getPrice() {
//        return price;
//    }
//
//    public void setPrice(PGmoney price){
//        this.price = price;
//    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity){
        this.quantity = quantity;
    }

    public boolean getAvailability() {
        return available;
    }

    public void setAvailability(boolean available){
        this.available = available;
    }

    @Override
    public String toString() {
        return "Product[" +
                "id=" + id + ", " +
                "name=" + name + ", " +
                //"price=" + price + ", " +
                "quantity=" + quantity + ", " +
                "available=" + available + ']';
    }

    public static Product fromResult(ResultSet result) throws SQLException {
        return new Product(
                result.getLong("id"),
                result.getString("name"),
                //new PGmoney(result.getString("price")),
                result.getInt("quantity"),
                result.getBoolean("available")
        );
    }
}
