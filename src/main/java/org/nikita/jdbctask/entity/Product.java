package org.nikita.jdbctask.entity;

import java.math.BigDecimal;

public class Product{
    private Long id;
    private String name;
    private BigDecimal price;
    private int quantity;
    private boolean available;

    public Product(Long id, String name, BigDecimal price, int quantity, boolean available){
        this(name, price, quantity, available);
        this.id = id;
    }

    public Product(String name, BigDecimal price, int quantity, boolean available) {
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price){
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
}
