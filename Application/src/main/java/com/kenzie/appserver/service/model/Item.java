package com.kenzie.appserver.service.model;

public class Item {

    private final Integer quantity;
    private final Double price;

    public Item(Integer quantity, Double price) {

        this.quantity = quantity;
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Double getPrice() {
        return price;
    }
}
