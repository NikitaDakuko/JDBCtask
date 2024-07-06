package org.nikita.jdbctask.dto;

import org.nikita.jdbctask.entity.OrderStatus;
import org.nikita.jdbctask.entity.Product;
import org.postgresql.util.PGmoney;

import java.util.List;

public class OrderDetailDTO {
    private long id;
    private final OrderStatus orderStatus;
    private final List<Product> products;
    private final PGmoney totalAmount;

    public OrderDetailDTO(OrderStatus orderStatus, List<Product> products, PGmoney totalAmount){
        this.orderStatus = orderStatus;
        this.products = products;
        this.totalAmount = totalAmount;
    }

    public OrderDetailDTO(Long id, OrderStatus orderStatus, List<Product> products, PGmoney totalAmount){
        this(orderStatus, products, totalAmount);
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public List<Product> getProducts() {
        return products;
    }

    public PGmoney getTotalAmount() {
        return totalAmount;
    }
}
