package org.nikita.jdbctask.entity;

import org.postgresql.util.PGmoney;

import java.util.List;

public record OrderDetail (int id, OrderStatus orderStatus, List<Product> products, PGmoney totalAmount ){

}
