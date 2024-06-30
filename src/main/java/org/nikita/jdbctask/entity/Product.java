package org.nikita.jdbctask.entity;

public record Product (int id, String name, Money price, int quantity, boolean available){

}
