package org.nikita.jdbctask.entity;

import org.nikita.jdbctask.enums.OrderStatus;
import org.postgresql.util.PGmoney;

import java.util.List;

public class OrderDetail {
    private long id;
    private OrderStatus orderStatus;
    private List<Product> products;
    private PGmoney totalAmount;

    public OrderDetail(OrderStatus orderStatus, List<Product> products, PGmoney totalAmount){
        this.orderStatus = orderStatus;
        this.products = products;
        this.totalAmount = totalAmount;
    }

    public OrderDetail(Long id, OrderStatus orderStatus, List<Product> products, PGmoney totalAmount){
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

    public PGmoney getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(PGmoney totalAmount) {
        this.totalAmount = totalAmount;
    }
}
