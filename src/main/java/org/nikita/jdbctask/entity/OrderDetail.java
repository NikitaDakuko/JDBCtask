package org.nikita.jdbctask.entity;

import java.util.List;

public record OrderDetail (int id, OrderStatus orderStatus, List<Product> products, Money totalAmount ){

}
