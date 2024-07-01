package org.nikita.jdbctask.entity;

import java.io.Serializable;

public class Money implements Serializable {
    private final int value;
    private final String currency;

    public Money(int value, String currency){
        this.value = value;
        this.currency = currency;
    }

    @Override
    public String toString(){
        return this.value + " " + this.currency;
    }
}
