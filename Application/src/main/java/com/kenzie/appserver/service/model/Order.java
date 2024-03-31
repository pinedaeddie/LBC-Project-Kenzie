package com.kenzie.appserver.service.model;

import java.util.Date;
import java.util.List;

public class Order {

    private final String id;
    private final String userName;
    private final Date orderDate;
    private final List<String> items;
    private final Double orderTotal;

    public Order(String id, String userName, Date orderDate, List<String> items, Double orderTotal) {
        this.id = id;
        this.userName = userName;
        this.orderDate = orderDate;
        this.items = items;
        this.orderTotal = orderTotal;
    }

    public String getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public List<String> getItems() {
        return items;
    }

    public Double getOrderTotal() {
        return orderTotal;
    }
}
