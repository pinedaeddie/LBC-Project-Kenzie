package com.kenzie.appserver.service.model;

public class Item {

    private final String itemId;
    private final String name;
    private final Integer quantity;
    private final Double price;

    public Item(String itemId, String name, Integer quantity, Double price) {

        this.itemId = itemId;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }
    public String getName() {
        return name;
    }

    public String getItemId() {
        return itemId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Double getPrice() {
        return price;
    }
}
