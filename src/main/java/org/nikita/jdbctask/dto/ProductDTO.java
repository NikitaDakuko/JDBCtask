package org.nikita.jdbctask.dto;

import org.postgresql.util.PGmoney;

public class ProductDTO {
    private Long id;
    private final String name;
    private final PGmoney price;
    private final int quantity;
    private final boolean available;

    public ProductDTO(Long id, String name, PGmoney price, int quantity, boolean available) {
        this(name, price, quantity, available);
        this.id = id;
    }

    public ProductDTO(String name, PGmoney price, int quantity, boolean available) {
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

    public PGmoney getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public boolean getAvailability() {
        return available;
    }
}
