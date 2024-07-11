package org.nikita.jdbctask.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Data Transfer Object for Product entity
 */
public class ProductDTO implements Serializable {
    private Long id;
    private final String name;
    private final BigDecimal price;
    private final int quantity;
    private final boolean available;

    /**
     * Constructor for inserting into a database. ID is autogenerated in the database
     */
    public ProductDTO(Long id, String name, BigDecimal price, int quantity, boolean available) {
        this(name, price, quantity, available);
        this.id = id;
    }

    /**
     * Constructor for retrieving from a database
     */
    public ProductDTO(String name, BigDecimal price, int quantity, boolean available) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.available = available;
    }

    @Override
    public String toString() {
        return "Product{" +
                "\n\tid=" + id +
                ",\n\tname=" + name +
                ",\n\tprice=" + price +
                ",\n\tquantity=" + quantity +
                ",\n\tavailable=" + available + "\n} ";
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
