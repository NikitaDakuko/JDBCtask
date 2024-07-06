package org.nikita.jdbctask.dto;

import org.nikita.jdbctask.entity.OrderDetail;

public class OrderApprovalDTO {
    private long id;
    private final long orderId;
    private final OrderDetail orderDetail;

    public OrderApprovalDTO (long orderId, OrderDetail orderDetail){
        this.orderId = orderId;
        this.orderDetail = orderDetail;
    }

    public OrderApprovalDTO (long id, long orderId, OrderDetail orderDetail){
        this(orderId, orderDetail);
        this.id = orderId;
    }

    public OrderDetail getOrderDetail() {
        return orderDetail;
    }

    public long getOrderId() {
        return orderId;
    }

    public long getId() {
        return id;
    }
}
