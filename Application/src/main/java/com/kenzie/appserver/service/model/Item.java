package com.kenzie.appserver.service.model;

public class Item {

    private final String id;
    private final String name;
    private final Integer quantity;
    private final Double price;

    public Item(String id, String name, Integer quantity, Double price) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public String getId() {
        return id;
    }
    public Integer getQuantity() {
        return quantity;
    }
    public Double getPrice() {
        return price;
    }
    public String getName() {
        return name;
    }
}
