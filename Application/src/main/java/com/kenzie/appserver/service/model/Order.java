package com.kenzie.appserver.service.model;

import java.util.Date;
import java.util.List;

public class Order {

    private final String id;
    private final String name;
    private final Date orderDate;
    private final List<String> items;
    private final Double orderTotal;

    public Order(String id, String name, Date orderDate, List<String> orderItems, Double orderTotal) {
        this.id = id;
        this.name = name;
        this.orderDate = orderDate;
        this.items = orderItems;
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

    public List<String> getOrderItems() {
        return items;
    }

    public Double getOrderTotal() {
        return orderTotal;
    }



}
