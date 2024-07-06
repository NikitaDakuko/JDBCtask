package org.nikita.jdbctask.entity;

import java.io.Serializable;

public class OrderApproval implements Serializable {
    private long id;
    private long orderId;
    private OrderDetail orderDetail;

    public OrderApproval (long orderId, OrderDetail orderDetail){
        this.orderId = orderId;
        this.orderDetail = orderDetail;
    }

    public OrderApproval (long id, long orderId, OrderDetail orderDetail){
        this(orderId, orderDetail);
        this.id = orderId;
    }

    public String toString(){
        return "OrderApproval{" +
                " id: " + this.id +
                ", orderId: " + this.orderId +
                ", orderDetail: " + this.orderDetail + "} ";
    }

    public OrderDetail getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(OrderDetail orderDetail) {
        this.orderDetail = orderDetail;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
