package org.nikita.jdbctask.dto;

import java.util.List;

public class OrderProductDTO {
    private long id;
    private final long orderDetailId;
    private final List<Long> productIds;

    public OrderProductDTO(long id, long orderDetailId, List<Long> productIds){
        this(orderDetailId, productIds);
        this.id = id;
    }

    public OrderProductDTO(long orderDetailId, List<Long> productIds){
        this.orderDetailId = orderDetailId;
        this.productIds = productIds;
    }

    public long getId() {
        return id;
    }

    public long getOrderDetailId() {
        return orderDetailId;
    }

    public List<Long> getProductIds() {
        return productIds;
    }
}
