package com.kenzie.appserver.service.model;

import java.util.Date;
import java.util.List;

public class Order {

    private final String id;
    private final String name;
    private final Date orderDate;
    private final List<Item> orderItems;
    private final Integer quantity;
    private final Double price;
    private final Double orderTotal;

    public Order(String id, String name, Date orderDate, List<Item> orderItems, Integer quantity, Double price, Double orderTotal) {
        this.id = id;
        this.name = name;
        this.orderDate = orderDate;
        this.orderItems = orderItems;
        this.quantity = quantity;
        this.price = price;
        this.orderTotal = orderTotal;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public List<Item> getOrderItems() {
        return orderItems;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Double getPrice() {
        return price;
    }

    public Double getOrderTotal() {
        return orderTotal;
    }



    public double calculateOrderTotal() {
        double total = 0.0;
        for (Item item : orderItems) {
            total += item.getPrice() * item.getQuantity();
        }
        return total;
    }
}
