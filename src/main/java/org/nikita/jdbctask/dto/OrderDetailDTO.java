package org.nikita.jdbctask.dto;

import org.nikita.jdbctask.enums.OrderStatus;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class OrderDetailDTO implements Serializable {
    private long id;
    private final OrderStatus orderStatus;
    private final List<ProductDTO> products;
    private final BigDecimal totalAmount;

    public OrderDetailDTO(OrderStatus orderStatus, List<ProductDTO> products, BigDecimal totalAmount) {
        this.orderStatus = orderStatus;
        this.products = products;
        this.totalAmount = totalAmount;
    }

    public OrderDetailDTO(Long id, OrderStatus orderStatus, List<ProductDTO> products, BigDecimal totalAmount) {
        this(orderStatus, products, totalAmount);
        this.id = id;
    }

    @Override
    public String toString() {
        StringBuilder productString = new StringBuilder();
        for (ProductDTO product : products) {
            productString.append(product.getId()).append(" ");
        }

        return "OrderDetail:{" +
                "\n\tid: " + id +
                ",\n\torderStatus: " + orderStatus +
                ",\n\tproducts: " + productString +
                ",\n\ttotalAmount: " + totalAmount.toString() + "\n} ";
    }

    public long getId() {
        return id;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public List<ProductDTO> getProducts() {
        return products;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }
}
