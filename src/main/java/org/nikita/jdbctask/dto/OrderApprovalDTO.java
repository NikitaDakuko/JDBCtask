package org.nikita.jdbctask.dto;

import java.io.Serializable;

public class OrderApprovalDTO implements Serializable {
    private long id;
    private final OrderDetailDTO orderDetail;

    public OrderApprovalDTO (OrderDetailDTO orderDetail){
        this.orderDetail = orderDetail;
    }

    public OrderApprovalDTO (long id, OrderDetailDTO orderDetail){
        this(orderDetail);
        this.id = id;
    }

    @Override
    public String toString(){
        return "OrderApproval{" +
                " id: " + this.id +
                ", orderDetail: " + this.orderDetail + "} ";
    }

    public OrderDetailDTO getOrderDetail() {
        return orderDetail;
    }

    public long getId() {
        return id;
    }
}
