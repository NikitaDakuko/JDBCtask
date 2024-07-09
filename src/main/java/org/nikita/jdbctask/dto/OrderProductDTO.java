package org.nikita.jdbctask.dto;

import java.io.Serializable;
import java.util.List;

public class OrderProductDTO implements Serializable {
    private final long orderDetailId;
    private final List<Long> productIds;

    public OrderProductDTO(long orderDetailId, List<Long> productIds){
        this.orderDetailId = orderDetailId;
        this.productIds = productIds;
    }

    @Override
    public String toString(){
        StringBuilder productString = new StringBuilder();
        for (Long pid : productIds) {
            productString.append(pid.toString()).append(" ");
        }

        return "OrderProduct:{"+
                ", orderDetailId: " + orderDetailId +
                ", products: " + productString + "}";
    }

    public long getOrderDetailId() {
        return orderDetailId;
    }

    public List<Long> getProductIds() {
        return productIds;
    }
}
