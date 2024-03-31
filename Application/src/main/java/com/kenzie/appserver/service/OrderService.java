package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.OrderRepository;
import com.kenzie.appserver.repositories.model.OrderRecord;
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

    public OrderRecord addItemToOrder (String id, String item){

        Optional<OrderRecord> record = orderRepository.findById(id);
        List<String> itemList = record.get().getItems();
        itemList.add(item);
        OrderRecord newRecord = new OrderRecord();
        newRecord.setItems(itemList);
        newRecord.setName(record.get().getName());
        newRecord.setId(record.get().getId());
        newRecord.setOrderDate(record.get().getOrderDate());
        newRecord.setOrderTotal(newRecord.getOrderTotal());
        orderRepository.save(newRecord);
        return newRecord;
    }

    public void placeOrder(OrderRecord order){
        order.setId(order.getId());
        order.setItems(order.getItems());
        order.setOrderTotal(order.getOrderTotal());
        order.setOrderDate(new Date());
        orderRepository.save(order);
    }

    public List<Order> findAll() {

        List<Order> order = new ArrayList<>();
        Iterable<OrderRecord> orderRecord = orderRepository.findAll();
        if(!orderRecord.iterator().hasNext()){
            throw new IllegalArgumentException("No Orders Found" );
        }
        for (OrderRecord recordList : orderRecord){
            Order newOrder = new Order(recordList.getId(), recordList.getName(), recordList.getOrderDate(), recordList.getItems(), recordList.getOrderTotal());
            order.add(newOrder);
        }
        return order;
    }

    public Order getOrderDetail(String id) {
        Optional<OrderRecord> record = orderRepository.findById(id);
        if (!record.isPresent()) {
            throw new IllegalArgumentException("Order record not found for ID: " + id);
        }
        OrderRecord orderRecord = record.get();
        Order order = new Order(orderRecord.getId(), orderRecord.getName(), new Date(),
                orderRecord.getItems(), orderRecord.getOrderTotal());
        return order;
    }

    public void removeItemFromOrder(String orderId, String item) {
        Optional<OrderRecord> recordOptional = orderRepository.findById(orderId);
        if (recordOptional.isPresent()) {
            OrderRecord orderRecord = recordOptional.get();
            List<String> itemList = orderRecord.getItems();
            itemList.removeIf(product -> product.equals(item));
            OrderRecord record = new OrderRecord();
            record.setItems(itemList);
            record.setName(recordOptional.get().getName());
            record.setId(recordOptional.get().getId());
            record.setOrderDate(recordOptional.get().getOrderDate());
            record.setOrderTotal(recordOptional.get().getOrderTotal());
            orderRepository.save(record);
        } else {
            throw new IllegalArgumentException("Order record not found: " + orderId);
        }
    }

    public OrderRecord searchOrderByName(String name) {
        Optional<OrderRecord> record = orderRepository.findById(name);
        if (!record.isPresent()) {
            throw new IllegalArgumentException("Item id is invalid: " + name);
        }
        return record.get();
    }


//    public double getTotal(String id) {
//
//        Optional<OrderRecord> record = orderRepository.findById(id);
//        OrderRecord orderRecord = record.get();
//        List<String> newItem = orderRecord.getOrderItems();
//
//        double total = 0.0;
//        for (String item : newItem) {
//            total += item.getPrice() * item.getQuantity();
//        }
//        orderRecord.setOrderTotal(total);
//        orderRepository.save(orderRecord);
//        return total;
//    }
}
