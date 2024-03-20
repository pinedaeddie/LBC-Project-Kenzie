package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.OrderRepository;
import com.kenzie.appserver.repositories.model.OrderRecord;
import com.kenzie.appserver.service.model.Item;
import com.kenzie.appserver.service.model.Order;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    private OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order findById(String id) {
        return null;
    }

    public Order addNewOrder (Order example) {
        OrderRecord orderRecord = new OrderRecord();
        return example;
    }

    public List<Order> findAll() {
        List<Order> orders = new ArrayList<>();
        return null;
    }

    public Order removeItemFromOrder(Order id, Item itemName) {
        return null;
    }
}
