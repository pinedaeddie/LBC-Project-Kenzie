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

    public void startOrder (OrderRecord order){
        order.setId(order.getId());
        order.setItems(order.getItems());
        order.setOrderDate(new Date());
        orderRepository.save(order);
    }

    public OrderRecord addItemToOrder (String id, String item){

        Optional<OrderRecord> record = orderRepository.findById(id);
        List<String> itemList = record.get().getItems();
        itemList.add(item);
        OrderRecord newRecord = new OrderRecord();
        newRecord.setItems(itemList);
        newRecord.setUserName(record.get().getUserName());
        newRecord.setId(record.get().getId());
        newRecord.setOrderDate(record.get().getOrderDate());
        orderRepository.save(newRecord);
        return newRecord;
    }

    public OrderRecord searchOrderByName (String name) {
        Optional<OrderRecord> record = orderRepository.findById(name);
        if (!record.isPresent()) {
            throw new IllegalArgumentException("Item id is invalid: " + name);
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

    public void removeItemFromOrder(String orderId, String item) {
        Optional<OrderRecord> recordOptional = orderRepository.findById(orderId);
        if (recordOptional.isPresent()) {
            OrderRecord orderRecord = recordOptional.get();
            List<String> itemList = orderRecord.getItems();
            itemList.removeIf(product -> product.equals(item));
            OrderRecord record = new OrderRecord();
            record.setItems(itemList);
            record.setUserName(recordOptional.get().getUserName());
            record.setId(recordOptional.get().getId());
            record.setOrderDate(recordOptional.get().getOrderDate());
            orderRepository.save(record);
        } else {
            throw new IllegalArgumentException("Order record not found: " + orderId);
        }
    }
}
