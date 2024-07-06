package org.nikita.jdbctask.entity;

public class OrderApproval {
    private long id;;
    private OrderDetail orderDetail;

    public OrderApproval (OrderDetail orderDetail){
        this.orderDetail = orderDetail;
    }

    public OrderApproval (long id, OrderDetail orderDetail){
        this(orderDetail);
        this.id = id;
    }

    public OrderDetail getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(OrderDetail orderDetail) {
        this.orderDetail = orderDetail;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
