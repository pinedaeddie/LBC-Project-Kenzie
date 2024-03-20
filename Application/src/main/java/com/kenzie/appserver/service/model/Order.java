package com.kenzie.appserver.service.model;

import java.util.Date;
import java.util.List;

public class Order {
    private String orderId;
    private Date orderDate;
    private List<Item> orderItemList;
    private double orderTotal;


    public Order(String orderId, Date orderDate, List<Item> orderItemList, double orderTotal) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.orderItemList = orderItemList;
        this.orderTotal = orderTotal;
    }

    public String getOrderId() {
        return orderId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public List<Item> getOrderItemList() {
        return orderItemList;
    }

    public double getOrderTotal() {
        return orderTotal;
    }
}
