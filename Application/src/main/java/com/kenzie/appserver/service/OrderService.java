package com.kenzie.appserver.service;

import com.kenzie.appserver.controller.model.OrderRequest;
import com.kenzie.appserver.controller.model.OrderResponse;
import com.kenzie.appserver.repositories.OrderRepository;
import com.kenzie.appserver.repositories.model.OrderRecord;
import com.kenzie.appserver.service.model.Item;
import com.kenzie.appserver.service.model.Order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderService {
    @Autowired
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order findById(String id) {
        Optional<OrderRecord> optionalOrderRecord = orderRepository.findById(id);
        if (!optionalOrderRecord.isPresent()) {
            throw new IllegalArgumentException("Product id is invalid" + id);
        }
        return null;
    }

    public void startOrder(Order order) {

        OrderRecord orderRecord = new OrderRecord();
        orderRecord.setId(order.getId());
        orderRecord.setName(order.getName());
        orderRecord.setOrderDate(order.getOrderDate());
        orderRecord.setQuantity(order.getQuantity());
        orderRecord.setOrderItems(orderRecord.getOrderItems());
        orderRepository.save(orderRecord);
    }


    public void addItemToOrder(Item item) {

    }
    public double addNewItem(Order order) {
        OrderRecord orderRecord = new OrderRecord();
        orderRecord.setId(order.getId());
        orderRecord.setName(order.getName());
        orderRecord.setOrderDate(order.getOrderDate());
        orderRecord.setQuantity(order.getQuantity());
        orderRecord.setOrderItems(orderRecord.getOrderItems());


        // Calculate the order total using the calculateOrderTotal method
        double orderTotal = order.calculateOrderTotal();
        orderRecord.setItemCost(orderTotal);

        orderRepository.save(orderRecord);
        return orderTotal;
    }
}
