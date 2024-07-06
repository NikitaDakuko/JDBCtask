package org.nikita.jdbctask.dto;

import org.nikita.jdbctask.enums.OrderStatus;
import org.postgresql.util.PGmoney;

import java.io.Serializable;
import java.util.List;

public class OrderDetailDTO implements Serializable {
    private long id;
    private final OrderStatus orderStatus;
    private final List<ProductDTO> products;
    private final PGmoney totalAmount;

    public OrderDetailDTO(OrderStatus orderStatus, List<ProductDTO> products, PGmoney totalAmount){
        this.orderStatus = orderStatus;
        this.products = products;
        this.totalAmount = totalAmount;
    }

    public OrderDetailDTO(Long id, OrderStatus orderStatus, List<ProductDTO> products, PGmoney totalAmount){
        this(orderStatus, products, totalAmount);
        this.id = id;
    }

    @Override
    public String toString(){
        StringBuilder productString = new StringBuilder();
        for (ProductDTO product : products) {
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

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public List<ProductDTO> getProducts() {
        return products;
    }

    public PGmoney getTotalAmount() {
        return totalAmount;
    }
}
