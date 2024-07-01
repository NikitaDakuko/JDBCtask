package org.nikita.jdbctask.entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "products")
@NamedQueries({
        @NamedQuery(name = "products.findAll", query = "SELECT p FROM products p")
})
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private Money price;
    private int quantity;
    private boolean available;

    public Product(int id, String name, Money price, int quantity, boolean available) {
        this.id = id;
        this.name = name;
        this.price = price;
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

    public Money getPrice() {
        return price;
    }

    public void setPrice(Money price){
        this.price = price;
    }

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
                "price=" + price + ", " +
                "quantity=" + quantity + ", " +
                "available=" + available + ']';
    }


}
