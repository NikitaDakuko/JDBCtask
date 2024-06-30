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
    private final long id;
    private final String name;
    private final Money price;
    private final int quantity;
    private final boolean available;

    public Product(int id, String name, Money price, int quantity, boolean available) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.available = available;
    }

    public long id() {
        return id;
    }

    public String name() {
        return name;
    }

    public Money price() {
        return price;
    }

    public int quantity() {
        return quantity;
    }

    public boolean available() {
        return available;
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
