package com.kenzie.appserver.service.model;

import java.util.ArrayList;
import java.util.List;

public class Customer {

    private final String id;
    private final String name;
    private final List<Order> incompleteOrder;
    private final List<Order> completedOrder;

    public Customer(String id, String name, List<Order> incompleteOrder, List<Order> completedOrders) {

        this.id = id;
        this.name = name;
        this.incompleteOrder = incompleteOrder;
        this.completedOrder = completedOrders;
    }

    public List<Order> getIncompleteOrder() {
        return incompleteOrder;
    }

    public List<Order> getCompletedOrder() {
        return completedOrder;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}



