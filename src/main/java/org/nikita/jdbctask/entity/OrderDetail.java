package org.nikita.jdbctask.entity;

import org.nikita.jdbctask.enums.OrderStatus;

import java.math.BigDecimal;
import java.util.List;

public class OrderDetail {
    private long id;
    private OrderStatus orderStatus;
    private List<Product> products;
    private BigDecimal totalAmount;

    public OrderDetail(OrderStatus orderStatus, List<Product> products, BigDecimal totalAmount){
        this.orderStatus = orderStatus;
        this.products = products;
        this.totalAmount = totalAmount;
    }

    public OrderDetail(Long id, OrderStatus orderStatus, List<Product> products, BigDecimal totalAmount){
        this(orderStatus, products, totalAmount);
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }
}
