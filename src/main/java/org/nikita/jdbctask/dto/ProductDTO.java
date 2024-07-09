package org.nikita.jdbctask.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class ProductDTO implements Serializable {
    private Long id;
    private final String name;
    private final BigDecimal price;
    private final int quantity;
    private final boolean available;

    public ProductDTO(Long id, String name, BigDecimal price, int quantity, boolean available) {
        this(name, price, quantity, available);
        this.id = id;
    }

    public ProductDTO(String name, BigDecimal price, int quantity, boolean available) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.available = available;
    }

    @Override
    public String toString() {
        return "Product{" +
                " id=" + id +
                ", name=" + name +
                ", price=" + price +
                ", quantity=" + quantity +
                ", available=" + available + "} ";
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public boolean getAvailability() {
        return available;
    }
}
