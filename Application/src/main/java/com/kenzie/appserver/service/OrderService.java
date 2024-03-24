package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.OrderRepository;
import com.kenzie.appserver.repositories.model.OrderRecord;
import com.kenzie.appserver.service.model.Item;
import com.kenzie.appserver.service.model.Order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrderService {
    @Autowired
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order findById(String id) {

        try {
            Optional<OrderRecord> optionalOrderRecord = orderRepository.findById(id);
            if (!optionalOrderRecord.isPresent()) {
                throw new NoSuchElementException("Order with ID not found: " + id);
            }
            OrderRecord orderRecord = optionalOrderRecord.get();
            Order order = new Order(orderRecord.getId(), orderRecord.getName(), new Date(),
                    orderRecord.getOrderItems(), orderRecord.getQuantity(), orderRecord.getOrderTotal());
            return order;
        } catch (NoSuchElementException e) {
            throw new IllegalArgumentException("Product id is invalid: " + id);
        }
    }

    public void startOrder(Order order) {

        if (order == null) {
            throw new IllegalArgumentException("Order is null!");
        }
        Optional<OrderRecord> existingOrder = orderRepository.findById(order.getId());
        if (existingOrder.isPresent()) {
            throw new IllegalArgumentException("Order with ID " + order.getId() + " already exists!");
        }

        OrderRecord orderRecord = new OrderRecord();
        orderRecord.setId(order.getId());
        orderRecord.setName(order.getName());
        orderRecord.setOrderDate(order.getOrderDate());
        orderRecord.setQuantity(order.getQuantity());
        orderRecord.setOrderItems(orderRecord.getOrderItems());
        orderRepository.save(orderRecord);
    }

    public void addItemToOrder(String id, Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Item is null!");
        }
        Optional<OrderRecord> record = orderRepository.findById(id);
        if (!record.isPresent()) {
            throw new IllegalArgumentException("Order record not found: " + id);
        }
        OrderRecord orderRecord = record.get();
        List<Item> newItem = orderRecord.getOrderItems();
        newItem.add(item);
        orderRecord.setOrderItems(newItem);
        orderRepository.save(orderRecord);
    }

    public Order getOrderDetail(String id) {
        Optional<OrderRecord> record = orderRepository.findById(id);
        if (!record.isPresent()) {
            throw new IllegalArgumentException("Order record not found for ID: " + id);
        }
        OrderRecord orderRecord = record.get();
        Order order = new Order(orderRecord.getId(), orderRecord.getName(), new Date(),
                orderRecord.getOrderItems(), orderRecord.getQuantity(), orderRecord.getOrderTotal());
        return order;
    }

    public double getTotal(String id) {

        Optional<OrderRecord> record = orderRepository.findById(id);
        OrderRecord orderRecord = record.get();
        List<Item> newItem = orderRecord.getOrderItems();

        double total = 0.0;
        for (Item item : newItem) {
            total += item.getPrice() * item.getQuantity();
        }
        orderRecord.setOrderTotal(total);
        orderRepository.save(orderRecord);
        return total;
    }

    public void removeItemFromOrder(String itemId, String orderId) {
        Optional<OrderRecord> record = orderRepository.findById(orderId);
        if (!record.isPresent()) {
            throw new IllegalArgumentException("Order record not found: " + orderId);
        }
        OrderRecord orderRecord = record.get();
        List<Item> itemList = orderRecord.getOrderItems();

        Iterator<Item> iterator = itemList.iterator();
        while (iterator.hasNext()) {
            Item item = iterator.next();
            if (item.getItemId().equals(itemId)) {
                iterator.remove();
                break;
            }
        }
        orderRecord.setOrderItems(itemList);
        orderRepository.save(orderRecord);
    }

    public Item searchItemById(String id) {
        Optional<OrderRecord> record = orderRepository.findById(id);
        if (!record.isPresent()) {
            throw new IllegalArgumentException("Item id is invalid: " + id);
        }

        OrderRecord orderRecord = record.get();
        List<Item> itemList = orderRecord.getOrderItems();

        for (Item item : itemList) {
            if (item.getItemId().equals(id)) {
                return item;
            }
        }

        throw new IllegalArgumentException("Item with id " + id + " not found in order.");
    }
}
