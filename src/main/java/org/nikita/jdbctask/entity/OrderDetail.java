package org.nikita.jdbctask.entity;

import org.postgresql.util.PGmoney;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class OrderDetail implements Serializable {
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

    public static OrderDetail fromResult(ResultSet result) throws SQLException {
        return new OrderDetail(
                result.getLong("id"),
                OrderStatus.valueOf(result.getString("oderStatus")),
                (List<Product>) result.getArray("products"),
                new PGmoney(result
                        .getString("totalAmount")
                        .substring(2)
                        .replace(",", ""))
        );
    }

    public String toString(){
        StringBuilder productString = new StringBuilder();
        for (Product product : products) {
            productString.append(product.toString()).append(" ");
        }

        return "OrderDetail:{"+
                " id: " + id +
                ", orderStatus: " + orderStatus +
                ", products: " + productString +
                ", totalAmount: " + totalAmount.toString();
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
