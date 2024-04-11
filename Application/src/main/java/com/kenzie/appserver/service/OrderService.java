package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.OrderRepository;
import com.kenzie.appserver.repositories.model.OrderRecord;
import com.kenzie.appserver.service.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void startOrder (OrderRecord order){
        if (order == null) {
            throw new IllegalArgumentException("OrderRecord cannot be null");
        }

        Optional<OrderRecord> existingRecord = orderRepository.findById(order.getUserName());
        if(!existingRecord.isPresent()){
            order.setId(UUID.randomUUID().toString());
            order.setOrderDate(new Date());
            orderRepository.save(order);
        }else {
            throw new IllegalArgumentException("The UserName already exists! Please try another!");
        }
    }

    public OrderRecord addItemToOrder(String userName, List<String> items) {
        Optional<OrderRecord> record = orderRepository.findById(userName);
        if (record.isPresent()){
            List<String> itemList = record.get().getItems();
            itemList.addAll(items);
            OrderRecord newRecord = new OrderRecord();
            newRecord.setItems(itemList);
            newRecord.setUserName(record.get().getUserName());
            newRecord.setId(record.get().getId());
            newRecord.setOrderDate(record.get().getOrderDate());
            orderRepository.save(newRecord);
            return newRecord;
        } else {
            throw new IllegalArgumentException("UserName was not found, unable to add item to : "+ userName);
        }
    }

    public OrderRecord searchOrderByName (String name) {
        Optional<OrderRecord> record = orderRepository.findById(name);
        if (!record.isPresent()) {
            throw new IllegalArgumentException("Order name is invalid: " + name);
        }
        return record.get();
    }

    public List<Order> findAll() {

        List<Order> order = new ArrayList<>();
        Iterable<OrderRecord> orderRecord = orderRepository.findAll();
        if(!orderRecord.iterator().hasNext()){
            throw new IllegalArgumentException("No Orders Found" );
        }
        for (OrderRecord recordList : orderRecord){
            Order newOrder = new Order(recordList.getId(), recordList.getUserName(), recordList.getOrderDate(), recordList.getItems());
            order.add(newOrder);
        }
        return order;
    }

    public OrderRecord removeItemFromOrder(String username, String item) {
        Optional<OrderRecord> recordOptional = orderRepository.findById(username);
        if (recordOptional.isPresent()) {
            OrderRecord orderRecord = recordOptional.get();
            List<String> itemList = orderRecord.getItems();
            itemList.removeIf(product -> product.equalsIgnoreCase(item));
            orderRecord.setItems(itemList);
            orderRepository.save(orderRecord);
            return orderRecord; // Return the updated OrderRecord
        } else {
            throw new UnsupportedOperationException("Order record not found: " + username);
        }
    }

    public void save(OrderRecord orderRecord) {
        orderRepository.save(orderRecord);
    }
}
