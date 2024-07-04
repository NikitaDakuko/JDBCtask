package org.nikita.jdbctask.entity;

import org.postgresql.util.PGmoney;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Product implements Serializable {
    private Long id;
    private String name;
    private PGmoney price;
    private int quantity;
    private boolean available;

    public Product(Long id, String name, PGmoney price, int quantity, boolean available){
        this(name, price, quantity, available);
        this.id = id;
    }

    public Product(String name, PGmoney price, int quantity, boolean available) {
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

    public PGmoney getPrice() {
        return price;
    }

    public void setPrice(PGmoney price){
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

    public static Product fromResult(ResultSet result) throws SQLException {
        return new Product(
                result.getLong("id"),
                result.getString("name"),
                new PGmoney(result.getString("price").substring(3)),
                result.getInt("quantity"),
                result.getBoolean("available")
        );
    }
}
